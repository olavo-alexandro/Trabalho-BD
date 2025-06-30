package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.UsuarioDAO;
import br.com.avaliacaogames.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet para gerir as ações do perfil do utilizador logado.
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect("perfil");
            return;
        }

        switch (action) {
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete_profile":
                deleteProfile(req, resp);
                break;
            default:
                resp.sendRedirect("perfil");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       updateUsuario(req, resp);
    }


    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        Usuario usuarioExistente = usuarioDAO.findByEmail(usuarioLogado.getEmail());
        req.setAttribute("usuario", usuarioExistente);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/formUsuario.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateUsuario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Usuario usuario = buildUsuarioFromRequest(req);
            usuarioDAO.update(usuario);

            // Atualiza o objeto usuário na sessão para que as novas informações apareçam imediatamente
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);

            resp.sendRedirect("perfil");
        } catch (RuntimeException e) {
            req.setAttribute("erro", e.getMessage());
            showEditForm(req, resp);
        }
    }

    private void deleteProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if (usuarioLogado != null) {
                // Remove o usuário da base de dados
                usuarioDAO.deleteByEmail(usuarioLogado.getEmail());
                // Invalida a sessão (logout)
                session.invalidate();
                // Redireciona para a página de login com uma mensagem de sucesso
                resp.sendRedirect(req.getContextPath() + "/index.jsp?deleted=true");
                return;
            }
        }
        // Se, por alguma razão, não houver sessão, apenas redireciona para o login
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    private Usuario buildUsuarioFromRequest(HttpServletRequest req) {
        // Pega o email da sessão, pois ele não pode ser alterado
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String email = usuarioLogado.getEmail();

        String nome = req.getParameter("nome");
        String userName = req.getParameter("userName");
        String senha = req.getParameter("senha");
        LocalDate dataNasc = LocalDate.parse(req.getParameter("dataNasc"));

        return new Usuario(email, nome, userName, senha, dataNasc);
    }
}
