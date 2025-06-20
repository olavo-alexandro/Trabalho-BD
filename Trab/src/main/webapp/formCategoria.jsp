<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${categoria != null ? 'Editar Categoria' : 'Nova Categoria'}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #333; }
        form { display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px; }
        label { font-weight: bold; }
        input[type="text"] { padding: 8px; border-radius: 4px; border: 1px solid #ddd; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        input[type="submit"]:hover { background-color: #45a049; }
        .back-link { margin-bottom: 20px; }
        .back-link a { text-decoration: none; color: #28a745; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>

<div class="container">
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/categorias">‹ Voltar para a Lista</a>
    </div>

    <h1>${categoria != null ? 'Editar Categoria' : 'Nova Categoria'}</h1>

    <form action="categorias" method="POST">
        <c:if test="${categoria != null}">
            <input type="hidden" name="id" value="${categoria.getIdentificador()}">
        </c:if>

        <label for="descricao">Descrição:</label>
        <input type="text" id="descricao" name="descricao" value="<c:out value='${categoria.getDescricao()}'/>" required>

        <input type="submit" value="${categoria != null ? 'Atualizar Categoria' : 'Salvar Categoria'}">
    </form>
</div>

</body>
</html>
