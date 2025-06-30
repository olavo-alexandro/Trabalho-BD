<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Categorias</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1, h2 { text-align: center; color: #333; }
        form { display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px; }
        label { font-weight: bold; }
        input[type="text"] { padding: 8px; border-radius: 4px; border: 1px solid #ddd; }
        input[type="submit"] { background-color: #4CAF50; color: white; padding: 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        input[type="submit"]:hover { background-color: #45a049; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even) { background-color: #f9f9f9; }
    </style>
</head>
<body>

    <div class="container">
        <h1>Gestão de Categorias</h1>

        <!-- Formulário para cadastrar uma nova categoria -->
        <form action="categorias" method="POST">
            <h2>Nova Categoria</h2>
            <label for="descricao">Descrição:</label>
            <input type="text" id="descricao" name="descricao" required>
            <input type="submit" value="Salvar Categoria">
        </form>

        <hr>

        <!-- Tabela para listar as categorias existentes -->
        <h2>Categorias Cadastradas</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Descrição</th>
                </tr>
            </thead>
            <tbody>
                <!--
                    Loop para iterar sobre a 'listaCategorias' que foi enviada pelo Servlet.
                    Para cada 'categoria' na lista, uma nova linha (<tr>) é criada.
                -->
                <c:forEach var="categoria" items="${listaCategorias}">
                    <tr>
                        <td>${categoria.getIdentificador()}</td>
                        <td>${categoria.getDescricao()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>