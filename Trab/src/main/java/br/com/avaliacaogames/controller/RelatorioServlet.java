package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.RelatorioDAO;
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


        Gson gson = new Gson();
        String mediaGeralPorCategoriaJson = gson.toJson(mediaGeralPorCategoria);


        req.setAttribute("mediaGeralPorCategoriaJson", mediaGeralPorCategoriaJson);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/relatorios.jsp");
        dispatcher.forward(req, resp);
    }
}
