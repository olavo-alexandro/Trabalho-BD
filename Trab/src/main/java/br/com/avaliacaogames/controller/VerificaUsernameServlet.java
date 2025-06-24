package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet dedicado a responder requisições AJAX para verificar a disponibilidade de um username.
 */
@WebServlet("/verifica-username")
public class VerificaUsernameServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        boolean emUso = false;
        if (username != null && !username.trim().isEmpty()) {
            emUso = usuarioDAO.usernameExists(username);
        }

        // Define o tipo de conteúdo da resposta como JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Escreve a resposta JSON que será lida pelo JavaScript na página
        // Ex: {"disponivel": true} ou {"disponivel": false}
        resp.getWriter().write("{\"disponivel\": " + !emUso + "}");
    }
}
