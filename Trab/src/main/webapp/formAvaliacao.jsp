<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty avaliacao.userEmail ? 'Editar Avaliação' : 'Nova Avaliação'}</title>
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
        h1 { color: #333; }
        form { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-top: 20px; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; }
        input, select, textarea { padding: 10px; border-radius: 4px; border: 1px solid #ced4da; font-size: 1rem; width: 100%; box-sizing: border-box; }
        input[disabled] { background-color: #e9ecef; }
        textarea { resize: vertical; min-height: 80px; }
        .form-full-width { grid-column: 1 / -1; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 12px; border: none; cursor: pointer; font-size: 16px; font-weight: bold; }
        .back-link { margin-bottom: 20px; }
        .back-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .delete-section { margin-top: 25px; padding-top: 20px; border-top: 1px solid #eee; text-align: center; }
        .delete-section a { background-color: #dc3545; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; }
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
             <div class="back-link">

                <c:choose>
                    <c:when test="${not empty avaliacao.jogoNome}">
                        <a href="${pageContext.request.contextPath}/detalhes-jogo?nome=${avaliacao.getJogoNome()}">‹ Voltar para Detalhes do Jogo</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/perfil">‹ Voltar para o Perfil</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <h1>${not empty avaliacao.userEmail ? 'Editar Avaliação' : 'Nova Avaliação'}</h1>

            <form action="avaliacoes" method="POST">
                <div class="form-group">
                    <label>Utilizador:</label>
                    <input type="text" value="<c:out value='${sessionScope.usuarioLogado.nome}'/>" disabled>
                </div>

                <div class="form-group">
                    <label for="jogo">Jogo:</label>
                    <c:choose>
                        <c:when test="${not empty avaliacao.jogoNome}">
                            <input type="text" value="<c:out value='${avaliacao.getJogoNome()}'/>" disabled>
                            <input type="hidden" name="jogo" value="<c:out value='${avaliacao.getJogoNome()}'/>">
                        </c:when>
                        <c:otherwise>
                            <select name="jogo" id="jogo" required>
                                <option value="">Selecione um jogo</option>
                                <c:forEach var="j" items="${listaJogos}">
                                    <option value="${j.getNome()}">${j.getNome()}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="form-group">
                    <label for="notaComplex">Complexidade (0-10):</label>
                    <input type="number" id="notaComplex" name="notaComplex" value="${avaliacao.getNotaComplex()}" min="0" max="10" required>
                </div>
                <div class="form-group">
                    <label for="notaRejo">Rejogabilidade (0-10):</label>
                    <input type="number" id="notaRejo" name="notaRejo" value="${avaliacao.getNotaRejo()}" min="0" max="10" required>
                </div>
                <div class="form-group">
                    <label for="notaDiver">Diversão (0-10):</label>
                    <input type="number" id="notaDiver" name="notaDiver" value="${avaliacao.getNotaDiver()}" min="0" max="10" required>
                </div>
                <div class="form-group">
                    <label for="qualidadeComp">Componentes (0-10):</label>
                    <input type="number" id="qualidadeComp" name="qualidadeComp" value="${avaliacao.getQualidadeComp()}" min="0" max="10" required>
                </div>

                <div class="form-group form-full-width">
                    <label for="comentario">Comentário (opcional):</label>
                    <textarea id="comentario" name="comentario"><c:out value='${avaliacao.getComentario()}'/></textarea>
                </div>

                <div class="form-group form-full-width">
                    <input type="submit" value="${not empty avaliacao.userEmail ? 'Atualizar Avaliação' : 'Salvar Avaliação'}">
                </div>
            </form>

            <c:if test="${not empty avaliacao.userEmail}">
                <div class="delete-section">
                    <a href="avaliacoes?action=delete&jogoNome=${avaliacao.getJogoNome()}" onclick="return confirm('Tem certeza que deseja excluir esta avaliação?')">Excluir Avaliação</a>
                </div>
            </c:if>
        </div>
    </main>

</body>
</html>
