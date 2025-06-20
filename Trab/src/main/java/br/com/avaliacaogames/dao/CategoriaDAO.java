package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Categoria;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Categoria.
 * Contém os métodos para interagir com a tabela 'categoria' no banco de dados.
 */
public class CategoriaDAO {

    /**
     * Salva uma nova categoria no banco de dados.
     * @param categoria O objeto Categoria a ser salvo.
     */
    public void save(Categoria categoria) {
        // Query SQL para inserir uma nova categoria.
        // O 'identificador' é SERIAL, então não precisamos inseri-lo.
        String sql = "INSERT INTO categoria (descricao) VALUES (?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            // Cria uma conexão com o banco
            conn = ConnectionFactory.getConnection();

            // Cria um PreparedStatement, para executar a query
            pstm = conn.prepareStatement(sql);

            // Adiciona o valor do parâmetro da query
            pstm.setString(1, categoria.getDescricao());

            // Executa a query
            pstm.execute();

            System.out.println("Categoria salva com sucesso!");
        } catch (SQLException e) {
            // Em caso de erro, imprime a exceção
            e.printStackTrace();
        } finally {
            // Fecha as conexões
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retorna uma lista com todas as categorias do banco de dados.
     * @return Uma lista de objetos Categoria.
     */
    public List<Categoria> findAll() {
        String sql = "SELECT * FROM categoria ORDER BY descricao";

        List<Categoria> categorias = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        // Objeto que vai guardar o resultado da busca
        ResultSet rset = null;

        try {
            conn = ConnectionFactory.getConnection();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            // Enquanto existirem dados no resultSet, crie um objeto Categoria
            while (rset.next()) {
                Categoria categoria = new Categoria();

                // Recupera o id do banco e atribui ao objeto
                categoria.setIdentificador(rset.getInt("identificador"));

                // Recupera a descrição do banco e atribui ao objeto
                categoria.setDescricao(rset.getString("descricao"));

                // Adiciona a categoria à lista
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categorias;
    }
}