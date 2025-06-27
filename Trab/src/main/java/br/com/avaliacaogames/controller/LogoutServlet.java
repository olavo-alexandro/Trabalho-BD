package br.com.avaliacaogames.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Pega a sessão sem criar uma nova
        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp"); // Redireciona para a página de login
    }
}