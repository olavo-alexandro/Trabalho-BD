package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Avaliacao;
import br.com.avaliacaogames.model.Jogo;
import br.com.avaliacaogames.model.Usuario;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    // Instância do JogoDAO para chamar o método de atualização da nota média
    private final JogoDAO jogoDAO = new JogoDAO();

    //salva ou atualiza uma avaliação
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

            // Após salvar/atualizar, recalcula a média
            jogoDAO.updateNotaMedia(avaliacao.getJogoNome());
            System.out.println("Avaliação salva/atualizada e média recalculada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //excluí uma avaliação
    public void delete(String userEmail, String jogoNome) {
        String sql = "DELETE FROM avaliacao WHERE userEmail = ? AND jogoNome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userEmail);
            pstm.setString(2, jogoNome);
            pstm.executeUpdate();

            // Após excluir, recalcula a média
            jogoDAO.updateNotaMedia(jogoNome);
            System.out.println("Avaliação excluída e média recalculada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //mostra todas as avaliações de um usuário
    public List<Avaliacao> findByUserEmail(String userEmail) {
        String sql = "SELECT a.*, j.nome as nome_jogo FROM avaliacao a " +
                "JOIN jogos j ON a.jogoNome = j.nome WHERE a.userEmail = ? ORDER BY a.dataAval DESC";
        List<Avaliacao> avaliacoes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userEmail);
            try (ResultSet rset = pstm.executeQuery()) {
                while (rset.next()) {
                    Avaliacao avaliacao = new Avaliacao();
                    Jogo jogo = new Jogo();
                    jogo.setNome(rset.getString("nome_jogo"));

                    avaliacao.setUserEmail(userEmail);
                    avaliacao.setJogoNome(rset.getString("jogoNome"));
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
                    avaliacao.setJogo(jogo);
                    avaliacoes.add(avaliacao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

    //encontra a avaliação de um usuário para um jogo
    public Avaliacao findUnique(String userEmail, String jogoNome) {
        String sql = "SELECT * FROM avaliacao WHERE userEmail = ? AND jogoNome = ?";
        Avaliacao avaliacao = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, userEmail);
            pstm.setString(2, jogoNome);
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    avaliacao = new Avaliacao();
                    avaliacao.setUserEmail(rset.getString("userEmail"));
                    avaliacao.setJogoNome(rset.getString("jogoNome"));
                    avaliacao.setNotaComplex(rset.getInt("notaComplex"));
                    avaliacao.setNotaRejo(rset.getInt("notaRejo"));
                    avaliacao.setNotaDiver(rset.getInt("notaDiver"));
                    avaliacao.setQualidadeComp(rset.getInt("qualidadeComp"));
                    avaliacao.setNotaGeral(rset.getFloat("notaGeral"));
                    avaliacao.setComentario(rset.getString("comentario"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avaliacao;
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

    public List<Avaliacao> findByJogoNome(String jogoNome) {
        String sql = "SELECT a.notaGeral, a.comentario, u.userName " +
                "FROM avaliacao a " +
                "JOIN usuario u ON a.userEmail = u.email " +
                "WHERE a.jogoNome = ? " +
                "ORDER BY a.dataAval DESC";
        List<Avaliacao> avaliacoes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, jogoNome);
            try (ResultSet rset = pstm.executeQuery()) {
                while (rset.next()) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setNotaGeral(rset.getFloat("notaGeral"));
                    avaliacao.setComentario(rset.getString("comentario"));

                    Usuario usuario = new Usuario();
                    usuario.setUserName(rset.getString("userName"));

                    avaliacao.setUsuario(usuario);
                    avaliacoes.add(avaliacao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }
}
