package br.com.avaliacaogames.controller;

import br.com.avaliacaogames.dao.CategoriaDAO;
import br.com.avaliacaogames.model.Categoria;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que atua como Controller para as operações de Categoria.
 * Mapeado para a URL /categorias
 */
@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    /**
     * Método chamado quando a página é carregada (via GET) ou quando um link aponta para ela.
     * A principal função aqui é buscar a lista de categorias e encaminhar para a página JSP.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Busca todas as categorias no banco de dados usando o DAO
        List<Categoria> categorias = categoriaDAO.findAll();

        // Adiciona a lista de categorias como um atributo na requisição.
        // A página JSP vai usar este atributo para exibir os dados.
        req.setAttribute("listaCategorias", categorias);

        // Encaminha a requisição (com a lista de categorias) para o arquivo JSP.
        RequestDispatcher dispatcher = req.getRequestDispatcher("/cadastroCategoria.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Método chamado quando um formulário da página é enviado (via POST).
     * A principal função é receber os dados do formulário e salvar uma nova categoria.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pega o valor do campo 'descricao' que veio do formulário
        String descricao = req.getParameter("descricao");

        // Cria um novo objeto Categoria com a descrição informada
        Categoria novaCategoria = new Categoria();
        novaCategoria.setDescricao(descricao);

        // Salva a nova categoria no banco de dados usando o DAO
        categoriaDAO.save(novaCategoria);

        // Redireciona o usuário de volta para a mesma página (/categorias via GET),
        // para que a lista de categorias seja atualizada e exibida.
        resp.sendRedirect("categorias");
    }
}