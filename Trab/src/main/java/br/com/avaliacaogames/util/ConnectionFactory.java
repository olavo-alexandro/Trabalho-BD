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


    // O driver JDBC do PostgreSQL
    private static final String DRIVER = "org.postgresql.Driver";


    private static final String URL = "jdbc:postgresql://localhost:5432/teste?currentSchema=trab";

    // Usuário do banco de dados
    private static final String USER = "postgres";

    // Senha do banco de dados
    private static final String PASS = "101010";

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
