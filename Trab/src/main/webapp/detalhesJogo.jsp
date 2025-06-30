<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes de ${jogo.getNome()}</title>
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
        .header { display: flex; justify-content: space-between; align-items: center; }
        h1, h2 { color: #333; }
        h1 { margin: 0; }
        h2 { margin-top: 30px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
        .btn-avaliar { background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .game-info, .review-list { line-height: 1.6; margin-bottom: 20px; }
        .categorias-lista { list-style-type: none; padding: 0; margin: 0; display: flex; flex-wrap: wrap; gap: 5px; }
        .categorias-lista li { background-color: #007bff; color: white; padding: 2px 8px; border-radius: 12px; font-size: 0.8em; display: inline-block; }
        .review { border-bottom: 1px solid #eee; padding: 15px 0; }
        .review:last-child { border-bottom: none; }
        .review-header { font-weight: bold; }
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
        <div class="content-box">
            <div class="header">
                <h1><c:out value="${jogo.getNome()}"/></h1>
                <a href="avaliacoes?action=edit&jogoNome=${jogo.getNome()}" class="btn-avaliar">Avaliar este Jogo</a>
            </div>

            <div class="game-info">
                <p><strong>Ano de Lançamento:</strong> ${jogo.getAnoLanc()}</p>
                <p><strong>Jogadores:</strong> ${jogo.getNumMin()} a ${jogo.getNumMax()}</p>
                <p><strong>Nota Média da Comunidade:</strong> <b><fmt:formatNumber value="${jogo.getNotaMedia()}" minFractionDigits="1" maxFractionDigits="1"/></b></p>
                <p><strong>Categorias:</strong>
                    <ul class="categorias-lista">
                        <c:forEach var="cat" items="${jogo.getCategorias()}">
                            <li>${cat.getDescricao()}</li>
                        </c:forEach>
                    </ul>
                </p>
            </div>

            <h2>Avaliações da Comunidade</h2>
            <div class="review-list">
                <c:choose>
                    <c:when test="${not empty avaliacoesDoJogo}">
                        <c:forEach var="aval" items="${avaliacoesDoJogo}">
                            <div class="review">
                                <p class="review-header">${aval.getUsuario().getUserName()} - Nota: <b><fmt:formatNumber value="${aval.getNotaGeral()}" minFractionDigits="1" maxFractionDigits="1"/></b></p>
                                <p><c:out value="${not empty aval.getComentario() ? aval.getComentario() : 'Nenhum comentário.'}"/></p>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Este jogo ainda não possui avaliações.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </main>
</body>
</html>
