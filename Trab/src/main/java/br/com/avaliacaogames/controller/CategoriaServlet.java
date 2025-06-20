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

@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

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
                deleteCategoria(req, resp);
                break;
            default: // "list"
                listCategorias(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            createCategoria(req, resp);
        } else {
            updateCategoria(req, resp);
        }
    }

    private void listCategorias(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Categoria> categorias = categoriaDAO.findAll();
        req.setAttribute("listaCategorias", categorias);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/listaCategorias.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formCategoria.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Categoria categoriaExistente = categoriaDAO.findById(id);
        req.setAttribute("categoria", categoriaExistente);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/formCategoria.jsp");
        dispatcher.forward(req, resp);
    }

    private void createCategoria(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String descricao = req.getParameter("descricao");
        Categoria novaCategoria = new Categoria();
        novaCategoria.setDescricao(descricao);
        categoriaDAO.save(novaCategoria);
        resp.sendRedirect("categorias");
    }

    private void updateCategoria(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String descricao = req.getParameter("descricao");
        Categoria categoria = new Categoria(id, descricao);
        categoriaDAO.update(categoria);
        resp.sendRedirect("categorias");
    }

    private void deleteCategoria(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        categoriaDAO.deleteById(id);
        resp.sendRedirect("categorias");
    }
}
