package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.RelatorioDAO;
import br.com.avaliacaogames.model.DadoRelatorioJogo;
import com.google.gson.Gson;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/relatorios")
public class RelatorioServlet extends HttpServlet {

    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Double> mediaGeralPorCategoria = relatorioDAO.getMediaGeralPorCategoria();
        Map<String, DadoRelatorioJogo> topJogoPorFaixa = relatorioDAO.getTopJogoPorFaixaEtaria();
        Map<String, Double> mediaPorNumJogadores = relatorioDAO.getMediaPorNumJogadores();

        Gson gson = new Gson();
        String mediaGeralPorCategoriaJson = gson.toJson(mediaGeralPorCategoria);
        String topJogoPorFaixaJson = gson.toJson(topJogoPorFaixa);
        String mediaPorNumJogadoresJson = gson.toJson(mediaPorNumJogadores);

        req.setAttribute("mediaGeralPorCategoriaJson", mediaGeralPorCategoriaJson);
        req.setAttribute("topJogoPorFaixa", topJogoPorFaixa); // Para a tabela
        req.setAttribute("mediaPorNumJogadoresJson", mediaPorNumJogadoresJson);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/relatorios.jsp");
        dispatcher.forward(req, resp);
    }
}
