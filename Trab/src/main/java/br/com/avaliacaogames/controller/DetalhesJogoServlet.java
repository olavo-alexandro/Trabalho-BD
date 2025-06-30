package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.AvaliacaoDAO;
import br.com.avaliacaogames.dao.JogoDAO;
import br.com.avaliacaogames.model.Avaliacao;
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
 * Servlet para buscar os detalhes de um jogo específico e suas avaliações.
 */
@WebServlet("/detalhes-jogo")
public class DetalhesJogoServlet extends HttpServlet {

    private final JogoDAO jogoDAO = new JogoDAO();
    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nomeJogo = req.getParameter("nome");

        if (nomeJogo != null && !nomeJogo.isEmpty()) {
            // Busca o jogo com todos os seus detalhes (incluindo categorias)
            Jogo jogo = jogoDAO.findByName(nomeJogo);
            // Busca a lista de avaliações para este jogo específico
            List<Avaliacao> avaliacoesDoJogo = avaliacaoDAO.findByJogoNome(nomeJogo);

            req.setAttribute("jogo", jogo);
            req.setAttribute("avaliacoesDoJogo", avaliacoesDoJogo);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/detalhesJogo.jsp");
            dispatcher.forward(req, resp);
        } else {
            // Se nenhum nome de jogo for fornecido, redireciona para a home
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
