<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${jogo != null ? 'Editar Jogo' : 'Novo Jogo'}</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 900px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; }
        form { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 30px; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; }
        input, select { padding: 10px; border-radius: 4px; border: 1px solid #ced4da; font-size: 1rem; }
        .form-full-width { grid-column: 1 / -1; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 12px; border: none; cursor: pointer; font-size: 16px; font-weight: bold; transition: background-color 0.2s; }
        input[type="submit"]:hover { background-color: #45a049; }
        .checkbox-group { border: 1px solid #ced4da; border-radius: 4px; padding: 10px; height: 120px; overflow-y: auto; }
        .checkbox-item { display: flex; align-items: center; margin-bottom: 5px; }
        .checkbox-item input[type="checkbox"] { margin-right: 8px; width: 16px; height: 16px; }
        .checkbox-item label { font-weight: normal; margin-bottom: 0; }
        .back-link { margin-bottom: 20px; }
        .back-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<div class="container">
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/jogos">‹ Voltar para a Lista de Jogos</a>
    </div>

    <h1>${jogo != null ? 'Editar Jogo' : 'Novo Jogo'}</h1>

    <form action="jogos" method="POST">
        <c:if test="${jogo != null}">
            <input type="hidden" name="nomeOriginal" value="<c:out value='${jogo.getNome()}'/>">
        </c:if>

        <div class="form-group form-full-width">
            <label for="nome">Nome do Jogo:</label>
            <input type="text" id="nome" name="nome" value="<c:out value='${jogo.getNome()}'/>" ${jogo != null ? 'readonly' : ''} required>
        </div>
        <div class="form-group">
            <label for="anoLanc">Ano de Lançamento:</label>
            <input type="number" id="anoLanc" name="anoLanc" value="${jogo.getAnoLanc()}" required>
        </div>
        <div class="form-group">
            <label for="numMin">Mínimo de Jogadores:</label>
            <input type="number" id="numMin" name="numMin" value="${jogo.getNumMin()}" min="1" required>
        </div>
        <div class="form-group">
            <label for="numMax">Máximo de Jogadores:</label>
            <input type="number" id="numMax" name="numMax" value="${jogo.getNumMax()}" min="1" required>
        </div>
        <div class="form-group">
            <label>Categorias:</label>
            <div class="checkbox-group">
                <c:forEach var="cat" items="${listaCategorias}">
                    <c:set var="checked" value="${false}" />
                    <c:if test="${jogo != null}">
                        <c:forEach var="jogoCat" items="${jogo.getCategorias()}">
                            <c:if test="${jogoCat.getIdentificador() == cat.getIdentificador()}">
                                <c:set var="checked" value="${true}" />
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <div class="checkbox-item">
                        <input type="checkbox" name="categorias" id="cat-${cat.getIdentificador()}" value="${cat.getIdentificador()}" ${checked ? 'checked' : ''}>
                        <label for="cat-${cat.getIdentificador()}">${cat.getDescricao()}</label>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="form-group form-full-width">
            <input type="submit" value="${jogo != null ? 'Atualizar Jogo' : 'Salvar Jogo'}">
        </div>
    </form>
</div>

</body>
</html>
