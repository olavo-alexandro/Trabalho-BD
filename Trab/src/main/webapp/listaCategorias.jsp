<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Categorias</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .btn-new { background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .home-link { margin-bottom: 20px; }
        .home-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .home-link a:hover { text-decoration: underline; }
        .actions a { margin-right: 10px; text-decoration: none; }
        .actions a.edit { color: #E67E22; }
        .actions a.delete { color: #E74C3C; }
    </style>
</head>
<body>

<div class="container">
    <div class="home-link">
        <a href="${pageContext.request.contextPath}/home">‹ Voltar para a Home</a>
    </div>

    <div class="header">
        <h1>Categorias</h1>
        <a href="categorias?action=new" class="btn-new">Adicionar Nova Categoria</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cat" items="${listaCategorias}">
                <tr>
                    <td>${cat.getIdentificador()}</td>
                    <td>${cat.getDescricao()}</td>
                    <td class="actions">
                        <a href="categorias?action=edit&id=${cat.getIdentificador()}" class="edit">Editar</a>
                        <a href="categorias?action=delete&id=${cat.getIdentificador()}" class="delete" onclick="return confirm('Tem certeza que deseja excluir esta categoria?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
