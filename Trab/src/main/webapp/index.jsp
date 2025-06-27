<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <title>Login</title>
    <style>
        body { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; font-family: sans-serif; }
        .login-container { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 350px; text-align: center; }
        h1 { margin-bottom: 20px; }
        .form-group { text-align: left; margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input[type="email"], input[type="password"] { width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ddd; border-radius: 4px; }
        .btn-login { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .links { margin-top: 20px; }
        .error, .success { padding: 10px; margin-bottom: 15px; border-radius: 4px; text-align: center; }
        .error { background-color: #f8d7da; color: #721c24; }
        .success { background-color: #d4edda; color: #155724; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Login</h1>
        <c:if test="${not empty erro}"><p class="error">${erro}</p></c:if>
        <c:if test="${param.success == 'true'}"><p class="success">Registo efetuado com sucesso! Faça o login.</p></c:if>
        <c:if test="${param.deleted == 'true'}"><p class="success">O seu perfil foi excluído com sucesso.</p></c:if>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" required>
            </div>
            <button type="submit" class="btn-login">Entrar</button>
        </form>
        <div class="links">
            <a href="${pageContext.request.contextPath}/register">Não tem uma conta? Cadastre-se</a>
        </div>
    </div>
</body>
</html>
