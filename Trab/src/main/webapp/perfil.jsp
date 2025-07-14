<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil</title>
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
        .content-box { max-width: 900px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1, h2 { color: #333; margin: 0; }
        h2 { margin-top: 30px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
        .btn-edit { background-color: #E67E22; color: white; padding: 8px 12px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border-bottom: 1px solid #dee2e6; padding: 12px; text-align: left; }
        th { background-color: #e9ecef; }
        .profile-info { line-height: 1.6; margin-bottom: 20px; }
        .details-row { display: none; }
        .details-content { background-color: #f8f9fa; padding: 15px; }
        .details-content p { margin: 5px 0; }
        .details-content a { display: inline-block; margin-top: 10px; color: #007bff; }
        .btn-details { cursor: pointer; color: #007bff; text-decoration: underline; background: none; border: none; font: inherit; padding: 0;}
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
                <a href="${pageContext.request.contextPath}/relatorios">Relatórios</a>
            </nav>
            <div class="logout-link">
                <a href="${pageContext.request.contextPath}/logout">Sair</a>
            </div>
        </aside>

    <main class="main-content">
        <div class="content-box">
            <div class="header">
                <h1>Meu Perfil</h1>
                <a href="usuarios?action=edit" class="btn-edit">Editar Perfil</a>
            </div>

            <div class="profile-info">
                <p><strong>Nome:</strong> <c:out value="${sessionScope.usuarioLogado.nome}"/></p>
                <p><strong>Username:</strong> <c:out value="${sessionScope.usuarioLogado.userName}"/></p>
                <p><strong>Idade:</strong> ${sessionScope.usuarioLogado.idade} anos</p>
            </div>

            <h2>Minhas Avaliações</h2>
            <table>
                <thead>
                    <tr>
                        <th>Jogo</th>
                        <th>Nota Geral</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <c:forEach var="aval" items="${minhasAvaliacoes}" varStatus="loop">
                    <tbody>
                        <tr>
                            <td>${aval.getJogo().getNome()}</td>
                            <td><fmt:formatNumber value="${aval.getNotaGeral()}" minFractionDigits="1" maxFractionDigits="1"/></td>
                            <td>
                                <button class="btn-details" onclick="toggleDetails('details-${loop.index}')">Ver Detalhes</button>
                            </td>
                        </tr>
                        <tr class="details-row" id="details-${loop.index}">
                            <td colspan="3">
                                <div class="details-content">
                                    <p><strong>Complexidade:</strong> ${aval.getNotaComplex()}</p>
                                    <p><strong>Diversão:</strong> ${aval.getNotaDiver()}</p>
                                    <p><strong>Rejogabilidade:</strong> ${aval.getNotaRejo()}</p>
                                    <p><strong>Qualidade dos Componentes:</strong> ${aval.getQualidadeComp()}</p>
                                    <hr>
                                    <p><strong>Comentário:</strong> <c:out value="${not empty aval.getComentario() ? aval.getComentario() : '-'}"/></p>
                                    <a href="avaliacoes?action=edit&jogoNome=${aval.getJogo().getNome()}">Alterar Avaliação</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </main>

    <script>
        function toggleDetails(elementId) {
            var element = document.getElementById(elementId);
            if (element.style.display === "none" || element.style.display === "") {
                element.style.display = "table-row";
            } else {
                element.style.display = "none";
            }
        }
    </script>

</body>
</html>
