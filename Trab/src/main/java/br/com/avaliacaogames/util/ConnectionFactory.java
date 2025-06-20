package br.com.avaliacaogames.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar e gerenciar a conexão com o banco de dados PostgreSQL.
 * Este é um exemplo do padrão de projeto Factory.
 */
public class ConnectionFactory {

    // --- INFORMAÇÕES DE CONEXÃO COM O BANCO ---
    // Altere estes valores para os do seu ambiente

    // O driver JDBC do PostgreSQL que adicionamos no pom.xml
    private static final String DRIVER = "org.postgresql.Driver";

    // O caminho (URL) para o banco. Formato: jdbc:postgresql://<host>:<porta>/<nome_do_banco>
    // Usamos o parâmetro ?currentSchema=trab para que não seja preciso especificar o schema em cada query.
    private static final String URL = "jdbc:postgresql://localhost:5432/teste?currentSchema=trab";

    // Usuário do banco de dados
    private static final String USER = "postgres";

    // Senha do banco de dados (!!! IMPORTANTE: COLOQUE A SUA SENHA AQUI !!!)
    private static final String PASS = "101010"; // <--- TROQUE PELA SUA SENHA

    /**
     * Método estático que estabelece e retorna uma conexão com o banco de dados.
     * @return um objeto do tipo Connection, que é a conexão estabelecida.
     * @throws RuntimeException se houver um erro ao conectar.
     */
    public static Connection getConnection() {
        try {
            // Carrega o driver do banco de dados na memória
            Class.forName(DRIVER);
            // Retorna a conexão com o banco de dados usando as credenciais definidas
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            // Lança uma exceção de runtime em caso de falha na conexão.
            // Isso simplifica o código nas classes DAO, que não precisarão tratar essa exceção diretamente.
            throw new RuntimeException("Erro na conexão com o banco de dados: " + e.getMessage(), e);
        }
    }
}
