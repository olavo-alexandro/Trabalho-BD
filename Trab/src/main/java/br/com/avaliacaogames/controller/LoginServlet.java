package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.UsuarioDAO;
import br.com.avaliacaogames.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario usuario = usuarioDAO.findByEmailAndPassword(email, senha);

        if (usuario != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            req.setAttribute("erro", "Email ou senha inválidos.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
