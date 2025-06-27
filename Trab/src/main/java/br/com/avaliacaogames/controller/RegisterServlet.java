package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.UsuarioDAO;
import br.com.avaliacaogames.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet público e dedicado exclusivamente ao fluxo de registo de novos utilizadores.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Mostra o formulário de registo.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formCadastro.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Processa os dados do formulário de registo.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String userName = req.getParameter("userName");
        String senha = req.getParameter("senha");
        String dataNascStr = req.getParameter("dataNasc");

        try {
            LocalDate dataNasc = LocalDate.parse(dataNascStr);
            Usuario novoUsuario = new Usuario(email, nome, userName, senha, dataNasc);

            usuarioDAO.save(novoUsuario);

            // Se o registo for bem-sucedido, redireciona para a página de login com mensagem de sucesso
            resp.sendRedirect(req.getContextPath() + "/index.jsp?success=true");

        } catch (RuntimeException e) {
            // Se o DAO lançar uma exceção (email ou username duplicado)
            req.setAttribute("erro", e.getMessage());
            // Reencaminha de volta para o formulário de registo para exibir o erro
            RequestDispatcher dispatcher = req.getRequestDispatcher("/formCadastro.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
