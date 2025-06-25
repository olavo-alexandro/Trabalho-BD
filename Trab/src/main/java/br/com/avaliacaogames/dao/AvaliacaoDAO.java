package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Avaliacao;
import br.com.avaliacaogames.model.Jogo;
import br.com.avaliacaogames.model.Usuario;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public void saveOrUpdate(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacao (userEmail, jogoNome, notaComplex, notaRejo, notaDiver, qualidadeComp, notaGeral, comentario, dataAval) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE) " +
                "ON CONFLICT (userEmail, jogoNome) " +
                "DO UPDATE SET " +
                "  notaComplex = EXCLUDED.notaComplex, " +
                "  notaRejo = EXCLUDED.notaRejo, " +
                "  notaDiver = EXCLUDED.notaDiver, " +
                "  qualidadeComp = EXCLUDED.qualidadeComp, " +
                "  notaGeral = EXCLUDED.notaGeral, " +
                "  comentario = EXCLUDED.comentario, " +
                "  dataAval = CURRENT_DATE";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, avaliacao.getUserEmail());
            pstm.setString(2, avaliacao.getJogoNome());
            pstm.setInt(3, avaliacao.getNotaComplex());
            pstm.setInt(4, avaliacao.getNotaRejo());
            pstm.setInt(5, avaliacao.getNotaDiver());
            pstm.setInt(6, avaliacao.getQualidadeComp());
            pstm.setFloat(7, avaliacao.getNotaGeral());
            pstm.setString(8, avaliacao.getComentario());
            pstm.executeUpdate();
            System.out.println("Avaliação salva/atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * NOVO: Exclui uma avaliação específica do banco.
     * @param userEmail O email do utilizador.
     * @param jogoNome O nome do jogo.
     */
    public void delete(String userEmail, String jogoNome) {
        String sql = "DELETE FROM avaliacao WHERE userEmail = ? AND jogoNome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userEmail);
            pstm.setString(2, jogoNome);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Avaliacao> findAll() {
        String sql = "SELECT a.*, u.nome as nome_usuario, u.userName, j.anoLanc " +
                "FROM avaliacao a " +
                "JOIN usuario u ON a.userEmail = u.email " +
                "JOIN jogos j ON a.jogoNome = j.nome " +
                "ORDER BY a.notaGeral DESC";

        List<Avaliacao> avaliacoes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Avaliacao avaliacao = new Avaliacao();
                Usuario usuario = new Usuario();
                Jogo jogo = new Jogo();

                usuario.setEmail(rset.getString("userEmail"));
                usuario.setNome(rset.getString("nome_usuario"));
                usuario.setUserName(rset.getString("userName"));

                jogo.setNome(rset.getString("jogoNome"));
                jogo.setAnoLanc(rset.getInt("anoLanc"));

                avaliacao.setNotaComplex(rset.getInt("notaComplex"));
                avaliacao.setNotaRejo(rset.getInt("notaRejo"));
                avaliacao.setNotaDiver(rset.getInt("notaDiver"));
                avaliacao.setQualidadeComp(rset.getInt("qualidadeComp"));
                avaliacao.setNotaGeral(rset.getFloat("notaGeral"));
                avaliacao.setComentario(rset.getString("comentario"));
                Date dataSql = rset.getDate("dataAval");
                if (dataSql != null) {
                    avaliacao.setDataAval(dataSql.toLocalDate());
                }

                avaliacao.setUsuario(usuario);
                avaliacao.setJogo(jogo);

                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }
}
