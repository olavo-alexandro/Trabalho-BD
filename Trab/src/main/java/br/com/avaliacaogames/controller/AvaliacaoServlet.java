package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.AvaliacaoDAO;
import br.com.avaliacaogames.dao.JogoDAO;
import br.com.avaliacaogames.dao.UsuarioDAO;
import br.com.avaliacaogames.model.Avaliacao;
import br.com.avaliacaogames.model.Jogo;
import br.com.avaliacaogames.model.Usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/avaliacoes")
public class AvaliacaoServlet extends HttpServlet {

    private AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private JogoDAO jogoDAO = new JogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "delete":
                deleteAvaliacao(req, resp);
                break;
            default:
                listAvaliacoes(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        createOrUpdateAvaliacao(req, resp);
    }

    private void listAvaliacoes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Avaliacao> avaliacoes = avaliacaoDAO.findAll();
        req.setAttribute("listaAvaliacoes", avaliacoes);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/listaAvaliacoes.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.findAll();
        List<Jogo> jogos = jogoDAO.findAll();
        req.setAttribute("listaUsuarios", usuarios);
        req.setAttribute("listaJogos", jogos);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formAvaliacao.jsp");
        dispatcher.forward(req, resp);
    }

    private void createOrUpdateAvaliacao(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userEmail = req.getParameter("usuario");
        String jogoNome = req.getParameter("jogo");
        int notaComplex = Integer.parseInt(req.getParameter("notaComplex"));
        int notaRejo = Integer.parseInt(req.getParameter("notaRejo"));
        int notaDiver = Integer.parseInt(req.getParameter("notaDiver"));
        int qualidadeComp = Integer.parseInt(req.getParameter("qualidadeComp"));
        String comentario = req.getParameter("comentario");

        float notaGeral = (notaComplex + notaRejo + notaDiver + qualidadeComp) / 4.0f;

        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setUserEmail(userEmail);
        novaAvaliacao.setJogoNome(jogoNome);
        novaAvaliacao.setNotaComplex(notaComplex);
        novaAvaliacao.setNotaRejo(notaRejo);
        novaAvaliacao.setNotaDiver(notaDiver);
        novaAvaliacao.setQualidadeComp(qualidadeComp);
        novaAvaliacao.setNotaGeral(notaGeral);
        novaAvaliacao.setComentario(comentario);

        avaliacaoDAO.saveOrUpdate(novaAvaliacao);

        resp.sendRedirect("avaliacoes");
    }

    private void deleteAvaliacao(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userEmail = req.getParameter("userEmail");
        String jogoNome = req.getParameter("jogoNome");
        avaliacaoDAO.delete(userEmail, jogoNome);
        resp.sendRedirect("avaliacoes");
    }
}
