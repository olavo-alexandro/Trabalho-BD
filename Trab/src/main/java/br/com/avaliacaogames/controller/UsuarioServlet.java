package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.UsuarioDAO;
import br.com.avaliacaogames.model.Usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteUsuario(req, resp);
                break;
            default:
                listUsuarios(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailOriginal = req.getParameter("emailOriginal");

        if (emailOriginal == null || emailOriginal.isEmpty()) {
            createUsuario(req, resp);
        } else {
            updateUsuario(req, resp);
        }
    }

    private void listUsuarios(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.findAll();
        req.setAttribute("listaUsuarios", usuarios);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/listaUsuarios.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formUsuario.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        Usuario usuarioExistente = usuarioDAO.findByEmail(email);
        req.setAttribute("usuario", usuarioExistente);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formUsuario.jsp");
        dispatcher.forward(req, resp);
    }

    private void createUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Usuario novoUsuario = buildUsuarioFromRequest(req);
            usuarioDAO.save(novoUsuario);
            resp.sendRedirect("usuarios");
        } catch (RuntimeException e) {
            req.setAttribute("erro", e.getMessage());
            showNewForm(req, resp);
        }
    }

    private void updateUsuario(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Usuario usuario = buildUsuarioFromRequest(req);
        usuarioDAO.update(usuario);
        resp.sendRedirect("usuarios");
    }

    private void deleteUsuario(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        usuarioDAO.deleteByEmail(email);
        resp.sendRedirect("usuarios");
    }

    private Usuario buildUsuarioFromRequest(HttpServletRequest req) {
        String emailOriginal = req.getParameter("emailOriginal");
        String email = (emailOriginal != null && !emailOriginal.isEmpty()) ? emailOriginal : req.getParameter("email");
        String nome = req.getParameter("nome");
        String userName = req.getParameter("userName");
        String senha = req.getParameter("senha");
        LocalDate dataNasc = LocalDate.parse(req.getParameter("dataNasc"));

        return new Usuario(email, nome, userName, senha, dataNasc);
    }
}
