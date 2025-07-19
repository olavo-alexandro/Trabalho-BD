package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.DadoRelatorioJogo;
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


    public Map<String, DadoRelatorioJogo> getTopJogoPorFaixaEtaria() {
        Map<String, DadoRelatorioJogo> dados = new LinkedHashMap<>();
        String sql = "WITH JogoMediasPorFaixa AS ( " +
                "    SELECT " +
                "        a.jogonome, " +
                "        AVG(a.notageral) as media_jogo, " +
                "        CASE " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 10 AND 14 THEN '10-14 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 15 AND 19 THEN '15-19 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 20 AND 24 THEN '20-24 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 25 AND 29 THEN '25-29 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 30 AND 34 THEN '30-34 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 35 AND 39 THEN '35-39 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 40 AND 44 THEN '40-44 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 45 AND 49 THEN '45-49 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 50 AND 54 THEN '50-54 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 55 AND 59 THEN '55-59 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) BETWEEN 60 AND 64 THEN '60-64 anos' " +
                "            WHEN DATE_PART('year', AGE(u.datanasc)) >= 65 THEN '65+ anos' " +
                "        END AS faixa_etaria " +
                "    FROM avaliacao a " +
                "    JOIN usuario u ON a.useremail = u.email " +
                "    WHERE u.datanasc IS NOT NULL " +
                "    GROUP BY a.jogonome, faixa_etaria " +
                "), RankedJogos AS ( " +
                "    SELECT " +
                "        j.jogonome, " +
                "        j.media_jogo, " +
                "        j.faixa_etaria, " +
                "        ROW_NUMBER() OVER(PARTITION BY j.faixa_etaria ORDER BY j.media_jogo DESC, j.jogonome ASC) as rn " +
                "    FROM JogoMediasPorFaixa j WHERE j.faixa_etaria IS NOT NULL" +
                ") " +
                "SELECT r.faixa_etaria, r.jogonome, r.media_jogo FROM RankedJogos r WHERE r.rn = 1 ORDER BY " +
                "    CASE " +
                "        WHEN r.faixa_etaria = '10-14 anos' THEN 1 WHEN r.faixa_etaria = '15-19 anos' THEN 2 " +
                "        WHEN r.faixa_etaria = '20-24 anos' THEN 3 WHEN r.faixa_etaria = '25-29 anos' THEN 4 " +
                "        WHEN r.faixa_etaria = '30-34 anos' THEN 5 WHEN r.faixa_etaria = '35-39 anos' THEN 6 " +
                "        WHEN r.faixa_etaria = '40-44 anos' THEN 7 WHEN r.faixa_etaria = '45-49 anos' THEN 8 " +
                "        WHEN r.faixa_etaria = '50-54 anos' THEN 9 WHEN r.faixa_etaria = '55-59 anos' THEN 10 " +
                "        WHEN r.faixa_etaria = '60-64 anos' THEN 11 WHEN r.faixa_etaria = '65+ anos' THEN 12 " +
                "    END";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String faixaEtaria = rs.getString("faixa_etaria");
                String nomeJogo = rs.getString("jogonome");
                double media = rs.getDouble("media_jogo");
                dados.put(faixaEtaria, new DadoRelatorioJogo(nomeJogo, media));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dados;
    }

    public Map<String, Double> getMediaPorNumJogadores() {
        Map<String, Double> dados = new LinkedHashMap<>();
        String sql = "SELECT " +
                "    CASE " +
                "        WHEN j.nummax = 1 THEN 'Solo (1 jogador)' " +
                "        WHEN j.nummax = 2 THEN '2 jogadores' " +
                "        WHEN j.nummax BETWEEN 3 AND 4 THEN '3-4 jogadores' " +
                "        WHEN j.nummax BETWEEN 5 AND 6 THEN '5-6 jogadores' " +
                "        WHEN j.nummax > 6 THEN 'Mais de 6 jogadores' " +
                "    END as grupo_jogadores, " +
                "    AVG(a.notageral) as media " +
                "FROM avaliacao a " +
                "JOIN jogos j ON a.jogonome = j.nome " +
                "WHERE j.nummax IS NOT NULL " +
                "GROUP BY grupo_jogadores " +
                "ORDER BY MIN(j.nummax)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                String grupo = rs.getString("grupo_jogadores");
                if (grupo != null) {
                    dados.put(grupo, rs.getDouble("media"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dados;
    }
}
