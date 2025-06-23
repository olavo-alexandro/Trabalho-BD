<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Jogos</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 900px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .btn-new { background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #dee2e6; padding: 12px; text-align: left; }
        th { background-color: #e9ecef; }
        tr:nth-child(even) { background-color: #f8f9fa; }
        .categorias-lista { list-style-type: none; padding: 0; margin: 0; display: flex; flex-wrap: wrap; gap: 5px; }
        .categorias-lista li { background-color: #007bff; color: white; padding: 2px 8px; border-radius: 12px; font-size: 0.8em; }
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
        <a href="${pageContext.request.contextPath}/">‹ Voltar para a Home</a>
    </div>

    <div class="header">
        <h1>Jogos</h1>
        <a href="jogos?action=new" class="btn-new">Adicionar Novo Jogo</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Nome</th>
            <th>Ano</th>
            <th>Jogadores</th>
            <th>Categorias</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="j" items="${listaJogos}">
                <tr>
                    <td>${j.getNome()}</td>
                    <td>${j.getAnoLanc()}</td>
                    <td>${j.getNumMin()} - ${j.getNumMax()}</td>
                    <td>
                        <ul class="categorias-lista">
                            <c:forEach var="cat" items="${j.getCategorias()}">
                                <li>${cat.getDescricao()}</li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td class="actions">
                         <a href="jogos?action=edit&nome=${j.getNome()}" class="edit">Editar</a>
                         <a href="jogos?action=delete&nome=${j.getNome()}" class="delete" onclick="return confirm('Tem certeza que deseja excluir este jogo e todas as suas avaliações?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
