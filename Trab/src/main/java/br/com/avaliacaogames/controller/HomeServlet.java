package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.JogoDAO;
import br.com.avaliacaogames.model.Jogo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet para preparar os dados e exibir a página inicial (home).
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final JogoDAO jogoDAO = new JogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Jogo> jogos = jogoDAO.findForRanking();
        req.setAttribute("listaJogos", jogos);

        // Encaminha para a página JSP que vai exibir os dados
        RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
        dispatcher.forward(req, resp);
    }
}