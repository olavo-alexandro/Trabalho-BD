<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Cadastro de Novo Utilizador</title>
    <style>
        body { display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #f0f2f5; font-family: sans-serif; padding: 20px 0; }
        .cadastro-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 450px; text-align: center; }
        h1 { margin-bottom: 20px; }
        form { display: flex; flex-direction: column; }
        .form-group { text-align: left; margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input { width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ddd; border-radius: 4px; }
        .btn-cadastro { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .links { margin-top: 20px; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 4px; }

        .disponibilidade-msg { font-size: 0.9em; margin-top: 5px; height: 1em; }
        .disponivel { color: #28a745; }
        .indisponivel { color: #dc3545; }
    </style>
</head>
<body>
    <div class="cadastro-container">
        <h1>Crie a sua Conta</h1>
        <c:if test="${not empty erro}"><p class="error">${erro}</p></c:if>
        <form action="${pageContext.request.contextPath}/register" method="POST">
            <div class="form-group"><label for="nome">Nome Completo:</label><input type="text" id="nome" name="nome" required></div>
            <div class="form-group"><label for="email">Email:</label><input type="email" id="email" name="email" required></div>
            <div class="form-group">
                <label for="userName">Username:</label>
                <input type="text" id="userName" name="userName" required>
                <span id="username-disponibilidade" class="disponibilidade-msg"></span>
            </div>
            <div class="form-group"><label for="senha">Senha:</label><input type="password" id="senha" name="senha" required></div>
            <div class="form-group"><label for="dataNasc">Data de Nascimento:</label><input type="date" id="dataNasc" name="dataNasc" required></div>
            <button type="submit" class="btn-cadastro" id="submit-button">Cadastrar</button>
        </form>
        <div class="links">
            <a href="${pageContext.request.contextPath}/index.jsp">Já tem uma conta? Faça login</a>
        </div>
    </div>

<script>
    document.getElementById('userName').addEventListener('keyup', function() {
        const username = this.value;
        const msgElement = document.getElementById('username-disponibilidade');
        const submitButton = document.getElementById('submit-button');

        if (username.length < 3) {
            msgElement.textContent = '';
            submitButton.disabled = false;
            return;
        }

        // Faz a requisição AJAX para o servlet de verificação
        fetch('verifica-username?username=' + encodeURIComponent(username))
            .then(response => response.json())
            .then(data => {
                if (data.disponivel) {
                    msgElement.textContent = 'Username disponível!';
                    msgElement.className = 'disponibilidade-msg disponivel';
                    submitButton.disabled = false; // Habilita o botão
                } else {
                    msgElement.textContent = 'Username já em uso!';
                    msgElement.className = 'disponibilidade-msg indisponivel';
                    submitButton.disabled = true; // Desabilita o botão
                }
            })
            .catch(error => console.error('Erro ao verificar username:', error));
    });
</script>

</body>
</html>
