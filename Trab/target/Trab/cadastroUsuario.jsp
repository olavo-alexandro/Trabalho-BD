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
import java.util.List;

/**
 * Servlet que atua como Controller para as operações de Usuario.
 * Mapeado para a URL /usuarios
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Busca todos os usuários no banco
        List<Usuario> usuarios = usuarioDAO.findAll();

        // Adiciona a lista de usuários como um atributo na requisição
        req.setAttribute("listaUsuarios", usuarios);

        // Encaminha para a página JSP de cadastro e listagem de usuários
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroUsuario.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pega os parâmetros do formulário
        String email = req.getParameter("email");
        String nome = req.getParameter("nome");
        String userName = req.getParameter("userName");
        String senha = req.getParameter("senha");
        String dataNascStr = req.getParameter("dataNasc");

        // Converte a string da data para um objeto LocalDate
        LocalDate dataNasc = LocalDate.parse(dataNascStr);

        // Cria um novo objeto Usuario com os dados do formulário
        Usuario novoUsuario = new Usuario(email, nome, userName, senha, dataNasc);

        // Salva o novo usuário no banco
        usuarioDAO.save(novoUsuario);

        // Redireciona o usuário de volta para a mesma página (/usuarios via GET)
        resp.sendRedirect("usuarios");
    }
}