package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.AvaliacaoDAO;
import br.com.avaliacaogames.dao.JogoDAO;
import br.com.avaliacaogames.model.Avaliacao;
import br.com.avaliacaogames.model.Jogo;
import br.com.avaliacaogames.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/avaliacoes")
public class AvaliacaoServlet extends HttpServlet {

    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private final JogoDAO jogoDAO = new JogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect("home");
            return;
        }

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteAvaliacao(req, resp);
                break;
            default:
                resp.sendRedirect("home");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        createOrUpdateAvaliacao(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Jogo> jogos = jogoDAO.findAll();
        req.setAttribute("listaJogos", jogos);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formAvaliacao.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String jogoNome = req.getParameter("jogoNome");

        Avaliacao avaliacao = avaliacaoDAO.findUnique(usuarioLogado.getEmail(), jogoNome);

        if (avaliacao == null) {
            avaliacao = new Avaliacao();
            avaliacao.setJogoNome(jogoNome);
        }

        req.setAttribute("avaliacao", avaliacao);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formAvaliacao.jsp");
        dispatcher.forward(req, resp);
    }

    private void createOrUpdateAvaliacao(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        String jogoNome = req.getParameter("jogo");
        int notaComplex = Integer.parseInt(req.getParameter("notaComplex"));
        int notaRejo = Integer.parseInt(req.getParameter("notaRejo"));
        int notaDiver = Integer.parseInt(req.getParameter("notaDiver"));
        int qualidadeComp = Integer.parseInt(req.getParameter("qualidadeComp"));
        String comentario = req.getParameter("comentario");

        float notaGeral = (notaComplex + notaRejo + notaDiver + qualidadeComp) / 4.0f;

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUserEmail(usuarioLogado.getEmail());
        avaliacao.setJogoNome(jogoNome);
        avaliacao.setNotaComplex(notaComplex);
        avaliacao.setNotaRejo(notaRejo);
        avaliacao.setNotaDiver(notaDiver);
        avaliacao.setQualidadeComp(qualidadeComp);
        avaliacao.setNotaGeral(notaGeral);
        avaliacao.setComentario(comentario);

        avaliacaoDAO.saveOrUpdate(avaliacao);

        resp.sendRedirect("detalhes-jogo?nome=" + URLEncoder.encode(jogoNome, StandardCharsets.UTF_8));
    }

    private void deleteAvaliacao(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        String jogoNome = req.getParameter("jogoNome");

        if (usuarioLogado != null && jogoNome != null && !jogoNome.isEmpty()) {
            avaliacaoDAO.delete(usuarioLogado.getEmail(), jogoNome);
        }

        resp.sendRedirect("detalhes-jogo?nome=" + URLEncoder.encode(jogoNome, StandardCharsets.UTF_8));
    }
}
