<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatórios</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 0; background-color: #f0f2f5; }
        .sidebar { width: 250px; background-color: #fff; position: fixed; height: 100%; box-shadow: 2px 0 5px rgba(0,0,0,0.1); padding-top: 20px; }
        .sidebar-header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; margin: 0 20px; }
        .sidebar-header h2 { margin: 0; }
        .sidebar-header p { color: #666; font-size: 0.9em; margin-top: 8px; }
        .sidebar nav { margin-top: 20px; }
        .sidebar nav a { display: block; padding: 12px 20px; color: #333; text-decoration: none; font-weight: bold; }
        .sidebar nav a:hover { background-color: #f0f2f5; }
        .sidebar nav a.active { background-color: #e9ecef; color: #0056b3; }
        .logout-link { position: absolute; bottom: 20px; width: 100%; text-align: center; }
        .logout-link a { color: #E74C3C; text-decoration: none; font-weight: bold; }
        .main-content { margin-left: 270px; padding: 20px; }
        .content-box { max-width: 900px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1, h2 { color: #333; }
        h2 { margin-top: 40px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
        .chart-container, .table-container { margin-top: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border-bottom: 1px solid #dee2e6; padding: 12px; text-align: left; }
        th { background-color: #e9ecef; }
        .no-data-message { text-align: center; color: #888; padding: 20px; font-style: italic; }
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
            <h1>Relatórios</h1>
            <p>Análise visual dos dados de avaliações do sistema.</p>

            <div id="data-media-geral-categoria" style="display:none;"><c:out value="${mediaGeralPorCategoriaJson}"/></div>
            <div id="data-top-jogo-faixa" style="display:none;"><c:out value="${topJogoPorFaixaJson}"/></div>
            <div id="data-media-num-jogadores" style="display:none;"><c:out value="${mediaPorNumJogadoresJson}"/></div>

            <h2>Média de Nota Geral por Categoria</h2>
            <div class="chart-container">
                <canvas id="chartMediaGeralCategoria"></canvas>
                <c:if test="${mediaGeralPorCategoriaJson == '{}'}">
                    <p class="no-data-message">Não existem dados para exibir neste relatório.</p>
                </c:if>
            </div>

            <h2>Jogo Mais Bem Avaliado por Faixa Etária</h2>
            <div class="table-container">
                <table id="tableTopJogo">
                    <thead>
                        <tr>
                            <th>Faixa Etária</th>
                            <th>Jogo</th>
                            <th>Média da Nota</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${topJogoPorFaixa}">
                            <tr>
                                <td><c:out value="${entry.key}"/></td>
                                <td><c:out value="${entry.value.nome}"/></td>
                                <td><b><fmt:formatNumber value="${entry.value.media}" minFractionDigits="1" maxFractionDigits="1"/></b></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${empty topJogoPorFaixa}">
                     <p class="no-data-message">Não existem dados para exibir neste relatório.</p>
                </c:if>
            </div>

            <h2>Média de Nota por Número de Jogadores</h2>
            <div class="chart-container">
                <canvas id="chartMediaNumJogadores"></canvas>
                <c:if test="${mediaPorNumJogadoresJson == '{}'}">
                    <p class="no-data-message">Não existem dados para exibir neste relatório.</p>
                </c:if>
            </div>
        </div>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            try {
                const mediaGeralCategoriaData = JSON.parse(document.getElementById('data-media-geral-categoria').textContent);
                const mediaGeralLabels = Object.keys(mediaGeralCategoriaData);
                const mediaGeralValues = Object.values(mediaGeralCategoriaData);
                if (mediaGeralLabels.length > 0) {
                    new Chart(document.getElementById('chartMediaGeralCategoria').getContext('2d'), {
                        type: 'bar',
                        data: {
                            labels: mediaGeralLabels,
                            datasets: [{
                                label: 'Média de Nota Geral',
                                data: mediaGeralValues,
                                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                            }]
                        },
                        options: { responsive: true, scales: { y: { beginAtZero: true, max: 10 } } }
                    });
                }
            } catch (e) { console.error("Erro ao renderizar gráfico de média por categoria:", e); }

            try {
                const mediaNumJogadoresData = JSON.parse(document.getElementById('data-media-num-jogadores').textContent);
                const mediaNumJogadoresLabels = Object.keys(mediaNumJogadoresData);
                const mediaNumJogadoresValues = Object.values(mediaNumJogadoresData);
                if (mediaNumJogadoresLabels.length > 0) {
                    new Chart(document.getElementById('chartMediaNumJogadores').getContext('2d'), {
                        type: 'bar',
                        data: {
                            labels: mediaNumJogadoresLabels,
                            datasets: [{
                                label: 'Média de Nota Geral',
                                data: mediaNumJogadoresValues,
                                backgroundColor: 'rgba(255, 159, 64, 0.6)',
                            }]
                        },
                        options: { responsive: true, scales: { y: { beginAtZero: true, max: 10 } } }
                    });
                }
            } catch (e) { console.error("Erro no gráfico por número de jogadores:", e); }
        });
    </script>

</body>
</html>
