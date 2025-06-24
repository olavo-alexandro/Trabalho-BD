<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Usuários</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 800px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .btn-new { background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #dee2e6; padding: 12px; text-align: left; }
        th { background-color: #e9ecef; }
        tr:nth-child(even) { background-color: #f8f9fa; }
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
        <h1>Usuários</h1>
        <a href="usuarios?action=new" class="btn-new">Adicionar Novo Usuário</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Nome</th>
            <th>Username</th>
            <th>Email</th>
            <th>Idade</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="usuario" items="${listaUsuarios}">
                <tr>
                    <td>${usuario.getNome()}</td>
                    <td>${usuario.getUserName()}</td>
                    <td>${usuario.getEmail()}</td>
                    <td>${usuario.getIdade()}</td>
                    <td class="actions">
                        <a href="usuarios?action=edit&email=${usuario.getEmail()}" class="edit">Editar</a>
                        <a href="usuarios?action=delete&email=${usuario.getEmail()}" class="delete" onclick="return confirm('Tem certeza que deseja excluir este usuário?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
