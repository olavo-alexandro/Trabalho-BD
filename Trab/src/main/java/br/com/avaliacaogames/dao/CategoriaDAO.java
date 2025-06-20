package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Categoria;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void save(Categoria categoria) {
        String sql = "INSERT INTO categoria (descricao) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, categoria.getDescricao());
            pstm.execute();
            System.out.println("Categoria salva com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Categoria categoria) {
        String sql = "UPDATE categoria SET descricao = ? WHERE identificador = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, categoria.getDescricao());
            pstm.setInt(2, categoria.getIdentificador());
            pstm.execute();
            System.out.println("Categoria atualizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteById(int id) {
        String findOrphansSql = "SELECT jogoNome FROM catjogo GROUP BY jogoNome HAVING COUNT(categoriaID) = 1 AND MAX(categoriaID) = ?";
        String deleteGamesSql = "DELETE FROM jogos WHERE nome = ?";
        String deleteCategorySql = "DELETE FROM categoria WHERE identificador = ?";
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia a transação

            List<String> orphanGames = new ArrayList<>();
            try (PreparedStatement pstm = conn.prepareStatement(findOrphansSql)) {
                pstm.setInt(1, id);
                try (ResultSet rset = pstm.executeQuery()) {
                    while (rset.next()) {
                        orphanGames.add(rset.getString("jogoNome"));
                    }
                }
            }

            if (!orphanGames.isEmpty()) {
                System.out.println("Os seguintes jogos órfãos serão excluídos: " + orphanGames);
                try (PreparedStatement pstm = conn.prepareStatement(deleteGamesSql)) {
                    for (String gameName : orphanGames) {
                        pstm.setString(1, gameName);
                        pstm.executeUpdate(); // Usar executeUpdate para DML
                    }
                }
            }

            try (PreparedStatement pstm = conn.prepareStatement(deleteCategorySql)) {
                pstm.setInt(1, id);
                pstm.executeUpdate();
            }

            conn.commit();
            System.out.println("Categoria e jogos órfãos associados foram excluídos com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro na transação. Executando rollback.");
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Fecha a conexão e restaura o auto-commit
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Categoria findById(int id) {
        String sql = "SELECT * FROM categoria WHERE identificador = ?";
        Categoria categoria = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, id);
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    categoria = new Categoria();
                    categoria.setIdentificador(rset.getInt("identificador"));
                    categoria.setDescricao(rset.getString("descricao"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }

    public List<Categoria> findAll() {
        String sql = "SELECT * FROM categoria ORDER BY descricao";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdentificador(rset.getInt("identificador"));
                categoria.setDescricao(rset.getString("descricao"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}
