<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Ranking de Jogos</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 0; background-color: #f0f2f5; }
        .sidebar { width: 250px; background-color: #fff; position: fixed; height: 100%; box-shadow: 2px 0 5px rgba(0,0,0,0.1); padding-top: 20px; }
        .sidebar-header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; }
        .sidebar-header h2 { margin: 0; }
        .sidebar-header p { color: #666; font-size: 0.9em; }
        .sidebar nav a { display: block; padding: 12px 20px; color: #333; text-decoration: none; font-weight: bold; }
        .sidebar nav a:hover { background-color: #f0f2f5; }
        .logout-link { position: absolute; bottom: 20px; width: 100%; text-align: center; }
        .logout-link a { color: #E74C3C; text-decoration: none; font-weight: bold; }
        .main-content { margin-left: 270px; padding: 20px; }
        h1 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        th, td { border-bottom: 1px solid #dee2e6; padding: 15px; text-align: left; }
        th { background-color: #e9ecef; }
        .rank { font-weight: bold; text-align: center; width: 50px; }
        .actions a { text-decoration: none; color: #007bff; font-weight: bold; }
    </style>
</head>
<body>

    <aside class="sidebar">
            <div class="sidebar-header">
                <h2>Jogos de Tabuleiro</h2>
                <p>Bem-vindo(a), <c:out value="${sessionScope.usuarioLogado.userName}"/></p>
            </div>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Página Inicial</a>
                <a href="${pageContext.request.contextPath}/perfil">Ver Perfil</a>
            </nav>
            <div class="logout-link">
                <a href="${pageContext.request.contextPath}/logout">Sair</a>
            </div>
        </aside>

    <main class="main-content">
        <h1>Ranking de Jogos</h1>
        <p>Os jogos mais bem avaliados pela comunidade.</p>

        <table>
            <thead>
                <tr>
                    <th class="rank">#</th>
                    <th>Jogo</th>
                    <th>Nota Média</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="jogo" items="${listaJogos}" varStatus="loop">
                    <tr>
                        <td class="rank">${loop.count}</td>
                        <td>${jogo.getNome()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${jogo.getNotaMedia() > 0}">
                                    <b><fmt:formatNumber value="${jogo.getNotaMedia()}" minFractionDigits="1" maxFractionDigits="1"/></b>
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="actions">
                            <a href="detalhes-jogo?nome=${jogo.getNome()}">Ver Mais</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </main>

</body>
</html>
