<form action="avaliacoes" method="POST">
    <h1>${avaliacao != null ? 'Editar Avaliação' : 'Nova Avaliação'}</h1>

    <%-- Campos do formulário --%>
    <div class="form-group">
        <label>Utilizador:</label>
        <input type="text" value="${sessionScope.usuarioLogado.nome}" disabled>
        <input type="hidden" name="usuario" value="${sessionScope.usuarioLogado.email}">
    </div>
    <div class="form-group">
        <label>Jogo:</label>
        <%-- Lógica para mostrar dropdown na criação ou texto na edição --%>
        <c:if test="${avaliacao == null}">
            <select name="jogo" required><!-- ... --></select>
        </c:if>
        <c:if test="${avaliacao != null}">
            <input type="text" value="<c:out value='${avaliacao.getJogoNome()}'/>" disabled>
            <input type="hidden" name="jogo" value="<c:out value='${avaliacao.getJogoNome()}'/>">
        </c:if>
    </div>

    <input type="number" name="notaComplex" value="${avaliacao.getNotaComplex()}" required>

</form>
