<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Avaliação</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 1000px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; }
        form { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 30px; border: 1px solid #ddd; padding: 20px; border-radius: 8px; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; }
        input, select, textarea { padding: 10px; border-radius: 4px; border: 1px solid #ced4da; font-size: 1rem; width: 100%; box-sizing: border-box; }
        textarea { resize: vertical; min-height: 80px; }
        .form-full-width { grid-column: 1 / -1; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 12px; border: none; cursor: pointer; font-size: 16px; font-weight: bold; transition: background-color 0.2s; }
        input[type="submit"]:hover { background-color: #45a049; }
        .back-link { margin-bottom: 20px; }
        .back-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<div class="container">
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/avaliacoes">‹ Voltar para a Lista</a>
    </div>

    <h1>Nova Avaliação</h1>

    <form action="avaliacoes" method="POST">
        <div class="form-group">
            <label for="usuario">Usuário:</label>
            <select name="usuario" id="usuario" required>
                <option value="">Selecione um usuário</option>
                <c:forEach var="u" items="${listaUsuarios}">
                    <option value="${u.getEmail()}">${u.getNome()} (${u.getUserName()})</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="jogo">Jogo:</label>
            <select name="jogo" id="jogo" required>
                <option value="">Selecione um jogo</option>
                <c:forEach var="j" items="${listaJogos}">
                    <option value="${j.getNome()}">${j.getNome()}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="notaComplex">Complexidade (0-10):</label>
            <input type="number" id="notaComplex" name="notaComplex" min="0" max="10" required>
        </div>
        <div class="form-group">
            <label for="notaRejo">Rejogabilidade (0-10):</label>
            <input type="number" id="notaRejo" name="notaRejo" min="0" max="10" required>
        </div>
        <div class="form-group">
            <label for="notaDiver">Diversão (0-10):</label>
            <input type="number" id="notaDiver" name="notaDiver" min="0" max="10" required>
        </div>
        <div class="form-group">
            <label for="qualidadeComp">Componentes (0-10):</label>
            <input type="number" id="qualidadeComp" name="qualidadeComp" min="0" max="10" required>
        </div>

        <div class="form-group form-full-width">
            <label for="comentario">Comentário (opcional):</label>
            <textarea id="comentario" name="comentario"></textarea>
        </div>

        <div class="form-group form-full-width">
            <input type="submit" value="Salvar Avaliação">
        </div>
    </form>
</div>

</body>
</html>
