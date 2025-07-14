package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class RelatorioDAO {

    public Map<String, Double> getMediaGeralPorCategoria() {
        Map<String, Double> dados = new LinkedHashMap<>();
        String sql = "SELECT c.descricao, AVG(a.notageral) as media " +
                "FROM avaliacao a " +
                "JOIN catjogo cj ON a.jogonome = cj.jogonome " +
                "JOIN categoria c ON cj.categoriaid = c.identificador " +
                "GROUP BY c.descricao " +
                "ORDER BY c.descricao ASC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                dados.put(rs.getString("descricao"), rs.getDouble("media"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dados;
    }
}
