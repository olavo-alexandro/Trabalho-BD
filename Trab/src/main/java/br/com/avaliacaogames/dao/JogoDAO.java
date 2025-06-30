package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Categoria;
import br.com.avaliacaogames.model.Jogo;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JogoDAO {

    public void save(Jogo jogo) {
        String sqlJogo = "INSERT INTO jogos (nome, anoLanc, numMin, numMax) VALUES (?, ?, ?, ?)";
        String sqlCatJogo = "INSERT INTO catjogo (jogoNome, categoriaID) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmJogo = conn.prepareStatement(sqlJogo)) {
                pstmJogo.setString(1, jogo.getNome());
                pstmJogo.setInt(2, jogo.getAnoLanc());
                pstmJogo.setInt(3, jogo.getNumMin());
                pstmJogo.setInt(4, jogo.getNumMax());
                pstmJogo.executeUpdate();
            }

            try (PreparedStatement pstmCatJogo = conn.prepareStatement(sqlCatJogo)) {
                for (Categoria categoria : jogo.getCategorias()) {
                    pstmCatJogo.setString(1, jogo.getNome());
                    pstmCatJogo.setInt(2, categoria.getIdentificador());
                    pstmCatJogo.executeUpdate();
                }
            }
            conn.commit();
            System.out.println("Jogo e suas categorias salvos com sucesso!");
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
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

    public void update(Jogo jogo) {
        String sqlJogo = "UPDATE jogos SET anoLanc = ?, numMin = ?, numMax = ? WHERE nome = ?";
        String sqlDeleteCat = "DELETE FROM catjogo WHERE jogoNome = ?";
        String sqlInsertCat = "INSERT INTO catjogo (jogoNome, categoriaID) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstm = conn.prepareStatement(sqlJogo)) {
                pstm.setInt(1, jogo.getAnoLanc());
                pstm.setInt(2, jogo.getNumMin());
                pstm.setInt(3, jogo.getNumMax());
                pstm.setString(4, jogo.getNome());
                pstm.executeUpdate();
            }

            try (PreparedStatement pstm = conn.prepareStatement(sqlDeleteCat)) {
                pstm.setString(1, jogo.getNome());
                pstm.executeUpdate();
            }

            try (PreparedStatement pstm = conn.prepareStatement(sqlInsertCat)) {
                for (Categoria categoria : jogo.getCategorias()) {
                    pstm.setString(1, jogo.getNome());
                    pstm.setInt(2, categoria.getIdentificador());
                    pstm.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Jogo atualizado com sucesso!");
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
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

    public void deleteByName(String nome) {
        String sql = "DELETE FROM jogos WHERE nome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nome);
            pstm.executeUpdate();
            System.out.println("Jogo excluído com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Jogo findByName(String nome) {
        String sql = "SELECT j.nome, j.anoLanc, j.numMin, j.numMax, j.notamedia, c.identificador, c.descricao " +
                "FROM jogos j " +
                "LEFT JOIN catjogo cj ON j.nome = cj.jogoNome " +
                "LEFT JOIN categoria c ON cj.categoriaID = c.identificador " +
                "WHERE j.nome = ?";
        Jogo jogo = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, nome);
            try (ResultSet rset = pstm.executeQuery()) {
                while (rset.next()) {
                    if (jogo == null) {
                        jogo = new Jogo();
                        jogo.setNome(rset.getString("nome"));
                        jogo.setAnoLanc(rset.getInt("anoLanc"));
                        jogo.setNumMin(rset.getInt("numMin"));
                        jogo.setNumMax(rset.getInt("numMax"));
                        jogo.setNotaMedia(rset.getFloat("notamedia"));
                        jogo.setCategorias(new ArrayList<>());
                    }
                    int idCategoria = rset.getInt("identificador");
                    if (idCategoria > 0) {
                        Categoria categoria = new Categoria(idCategoria, rset.getString("descricao"));
                        jogo.getCategorias().add(categoria);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jogo;
    }

    public List<Jogo> findAll() {
        String sql = "SELECT j.nome, j.anoLanc, j.numMin, j.numMax, j.notamedia, c.identificador, c.descricao " +
                "FROM jogos j " +
                "LEFT JOIN catjogo cj ON j.nome = cj.jogoNome " +
                "LEFT JOIN categoria c ON cj.categoriaID = c.identificador " +
                "ORDER BY j.nome, c.descricao";

        Map<String, Jogo> jogosMap = new HashMap<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                String nomeJogo = rset.getString("nome");
                Jogo jogo = jogosMap.get(nomeJogo);

                if (jogo == null) {
                    jogo = new Jogo();
                    jogo.setNome(nomeJogo);
                    jogo.setAnoLanc(rset.getInt("anoLanc"));
                    jogo.setNumMin(rset.getInt("numMin"));
                    jogo.setNumMax(rset.getInt("numMax"));
                    jogo.setNotaMedia(rset.getFloat("notamedia"));
                    jogo.setCategorias(new ArrayList<>());
                    jogosMap.put(nomeJogo, jogo);
                }

                int idCategoria = rset.getInt("identificador");
                if (idCategoria > 0) {
                    Categoria categoria = new Categoria(idCategoria, rset.getString("descricao"));
                    jogo.getCategorias().add(categoria);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(jogosMap.values());
    }

    public List<Jogo> findForRanking() {
        String sql = "SELECT nome, notamedia FROM jogos ORDER BY notamedia DESC NULLS LAST, nome ASC";

        List<Jogo> ranking = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Jogo jogo = new Jogo();
                jogo.setNome(rset.getString("nome"));
                jogo.setNotaMedia(rset.getFloat("notamedia"));
                ranking.add(jogo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }

    public void updateNotaMedia(String nomeJogo) {
        String sqlCalc = "SELECT AVG(notaGeral) AS media FROM avaliacao WHERE jogoNome = ?";
        String sqlUpdate = "UPDATE jogos SET notamedia = ? WHERE nome = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            float media = 0;
            try (PreparedStatement pstmCalc = conn.prepareStatement(sqlCalc)) {
                pstmCalc.setString(1, nomeJogo);
                try (ResultSet rs = pstmCalc.executeQuery()) {
                    if (rs.next()) {
                        media = rs.getFloat("media");
                    }
                }
            }
            try (PreparedStatement pstmUpdate = conn.prepareStatement(sqlUpdate)) {
                pstmUpdate.setFloat(1, media);
                pstmUpdate.setString(2, nomeJogo);
                pstmUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
