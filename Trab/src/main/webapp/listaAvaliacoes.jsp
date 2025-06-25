<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Avaliações</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 1000px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .btn-new { background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 0.9em; }
        th, td { border: 1px solid #dee2e6; padding: 10px; text-align: center; vertical-align: middle;}
        th { background-color: #e9ecef; }
        tr:nth-child(even) { background-color: #f8f9fa; }
        td:nth-child(1), td:nth-child(2), td:last-child { text-align: left; }
        .home-link { margin-bottom: 20px; }
        .home-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .home-link a:hover { text-decoration: underline; }
        .actions a { color: #E74C3C; text-decoration: none;}
    </style>
</head>
<body>

<div class="container">
    <div class="home-link">
        <a href="${pageContext.request.contextPath}/">‹ Voltar para a Home</a>
    </div>

    <div class="header">
        <h1>Avaliações</h1>
        <a href="avaliacoes?action=new" class="btn-new">Adicionar Nova Avaliação</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Jogo</th>
            <th>Usuário</th>
            <th>Data</th>
            <th>Comp.</th>
            <th>Div.</th>
            <th>Rejo.</th>
            <th>Qual.</th>
            <th>Geral</th>
            <th>Comentário</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="aval" items="${listaAvaliacoes}">
                <tr>
                    <td>${aval.getJogo().getNome()}</td>
                    <td>${aval.getUsuario().getUserName()}</td>
                    <td><fmt:formatDate value="${aval.getDataAvalAsDate()}" pattern="dd/MM/yyyy"/></td>
                    <td>${aval.getNotaComplex()}</td>
                    <td>${aval.getNotaDiver()}</td>
                    <td>${aval.getNotaRejo()}</td>
                    <td>${aval.getQualidadeComp()}</td>
                    <td><b><fmt:formatNumber value="${aval.getNotaGeral()}" minFractionDigits="1" maxFractionDigits="1"/></b></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty aval.getComentario()}">${aval.getComentario()}</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="actions">
                        <a href="avaliacoes?action=delete&userEmail=${aval.getUsuario().getEmail()}&jogoNome=${aval.getJogo().getNome()}" onclick="return confirm('Tem certeza que deseja excluir esta avaliação?')">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
