<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${usuario != null ? 'Editar Usuário' : 'Novo Usuário'}</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 20px; background-color: #f8f9fa; color: #343a40; }
        .container { max-width: 800px; margin: auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; }
        form { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 30px; }
        .form-group { display: flex; flex-direction: column; }
        label { font-weight: bold; margin-bottom: 5px; }
        input { padding: 10px; border-radius: 4px; border: 1px solid #ced4da; font-size: 1rem; }
        .form-full-width { grid-column: 1 / -1; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 12px; border: none; cursor: pointer; font-size: 16px; font-weight: bold; transition: background-color 0.2s; }
        input[type="submit"]:hover { background-color: #45a049; }
        .back-link { margin-bottom: 20px; }
        .back-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
        .error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 15px; border-radius: 4px; margin-bottom: 20px; text-align: center; font-weight: bold; }
        .disponibilidade-msg { font-size: 0.9em; margin-top: 5px; height: 1em; }
        .disponivel { color: #28a745; }
        .indisponivel { color: #dc3545; }
    </style>
</head>
<body>

<div class="container">
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/usuarios">‹ Voltar para a Lista</a>
    </div>

    <h1>${usuario != null ? 'Editar Usuário' : 'Novo Usuário'}</h1>

    <c:if test="${not empty erro}">
        <div class="error-message">${erro}</div>
    </c:if>

    <form action="usuarios" method="POST">
        <c:if test="${usuario != null}">
            <input type="hidden" name="emailOriginal" value="<c:out value='${usuario.getEmail()}'/>">
        </c:if>

        <div class="form-group form-full-width">
            <label for="nome">Nome Completo:</label>
            <input type="text" id="nome" name="nome" value="<c:out value='${usuario.getNome()}'/>" required>
        </div>
        <div class="form-group form-full-width">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<c:out value='${usuario.getEmail()}'/>" ${usuario != null ? 'readonly' : ''} required>
        </div>
        <div class="form-group">
            <label for="userName">Username:</label>
            <input type="text" id="userName" name="userName" value="<c:out value='${usuario.getUserName()}'/>" required>
            <span id="username-disponibilidade" class="disponibilidade-msg"></span>
        </div>
        <div class="form-group">
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" value="<c:out value='${usuario.getSenha()}'/>" required>
        </div>
        <div class="form-group">
            <label for="dataNasc">Data de Nascimento:</label>
            <input type="date" id="dataNasc" name="dataNasc" value="${usuario.getDataNasc()}" required>
        </div>
        <div class="form-group form-full-width">
            <input type="submit" id="submit-button" value="${usuario != null ? 'Atualizar Usuário' : 'Salvar Usuário'}">
        </div>
    </form>
</div>

<script>
    const usernameInput = document.getElementById('userName');
    const msgElement = document.getElementById('username-disponibilidade');
    const submitButton = document.getElementById('submit-button');
    const isEditing = document.querySelector('input[name="emailOriginal"]') !== null;
    const originalUsername = isEditing ? usernameInput.value : '';

    usernameInput.addEventListener('keyup', function() {
        const username = this.value;

        if (isEditing && username === originalUsername) {
            msgElement.textContent = '';
            submitButton.disabled = false;
            return;
        }

        if (username.length < 3) {
            msgElement.textContent = '';
            submitButton.disabled = false;
            return;
        }

        fetch('verifica-username?username=' + encodeURIComponent(username))
            .then(response => response.json())
            .then(data => {
                if (data.disponivel) {
                    msgElement.textContent = 'Username disponível!';
                    msgElement.className = 'disponibilidade-msg disponivel';
                    submitButton.disabled = false;
                } else {
                    msgElement.textContent = 'Username já em uso!';
                    msgElement.className = 'disponibilidade-msg indisponivel';
                    submitButton.disabled = true;
                }
            })
            .catch(error => console.error('Erro ao verificar username:', error));
    });
</script>

</body>
</html>
