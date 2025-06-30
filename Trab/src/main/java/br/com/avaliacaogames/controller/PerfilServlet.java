package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.AvaliacaoDAO;
import br.com.avaliacaogames.model.Avaliacao;
import br.com.avaliacaogames.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet para gerir a página de perfil do utilizador logado.
 */
@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {
    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            // Se, por alguma razão, não houver utilizador logado, redireciona para o login
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // Busca a lista completa de avaliações feitas pelo utilizador logado
        List<Avaliacao> minhasAvaliacoes = avaliacaoDAO.findByUserEmail(usuarioLogado.getEmail());
        req.setAttribute("minhasAvaliacoes", minhasAvaliacoes);

        // Encaminha para a página de perfil para exibir os dados
        RequestDispatcher dispatcher = req.getRequestDispatcher("/perfil.jsp");
        dispatcher.forward(req, resp);
    }
}
