package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.CategoriaDAO;
import br.com.avaliacaogames.dao.JogoDAO;
import br.com.avaliacaogames.model.Categoria;
import br.com.avaliacaogames.model.Jogo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/jogos")
public class JogoServlet extends HttpServlet {

    private JogoDAO jogoDAO = new JogoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

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
                deleteJogo(req, resp);
                break;
            default: // "list"
                listJogos(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nomeOriginal = req.getParameter("nomeOriginal");

        if (nomeOriginal == null || nomeOriginal.isEmpty()) {
            createJogo(req, resp);
        } else {
            updateJogo(req, resp);
        }
    }

    private void listJogos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Jogo> jogos = jogoDAO.findAll();
        req.setAttribute("listaJogos", jogos);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/listaJogos.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Categoria> categorias = categoriaDAO.findAll();
        req.setAttribute("listaCategorias", categorias);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formJogo.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        Jogo jogoExistente = jogoDAO.findByName(nome);
        List<Categoria> categorias = categoriaDAO.findAll();
        req.setAttribute("jogo", jogoExistente);
        req.setAttribute("listaCategorias", categorias);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formJogo.jsp");
        dispatcher.forward(req, resp);
    }

    private void createJogo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jogo novoJogo = buildJogoFromRequest(req);
        jogoDAO.save(novoJogo);
        resp.sendRedirect("jogos");
    }

    private void updateJogo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Jogo jogo = buildJogoFromRequest(req);
        jogoDAO.update(jogo);
        resp.sendRedirect("jogos");
    }

    private void deleteJogo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nome = req.getParameter("nome");
        jogoDAO.deleteByName(nome);
        resp.sendRedirect("jogos");
    }

    private Jogo buildJogoFromRequest(HttpServletRequest req) {
        String nomeOriginal = req.getParameter("nomeOriginal");
        String nome = (nomeOriginal != null && !nomeOriginal.isEmpty()) ? nomeOriginal : req.getParameter("nome");

        int anoLanc = Integer.parseInt(req.getParameter("anoLanc"));
        int numMin = Integer.parseInt(req.getParameter("numMin"));
        int numMax = Integer.parseInt(req.getParameter("numMax"));
        String[] idsCategorias = req.getParameterValues("categorias");

        Jogo jogo = new Jogo(nome, anoLanc, numMin, numMax);
        List<Categoria> categoriasSelecionadas = new ArrayList<>();
        if (idsCategorias != null) {
            for (String idStr : idsCategorias) {
                categoriasSelecionadas.add(new Categoria(Integer.parseInt(idStr), null));
            }
        }
        jogo.setCategorias(categoriasSelecionadas);
        return jogo;
    }
}
