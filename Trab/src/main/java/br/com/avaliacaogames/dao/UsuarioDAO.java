package br.com.avaliacaogames.dao;

import br.com.avaliacaogames.model.Usuario;
import br.com.avaliacaogames.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void save(Usuario usuario) {
        String sql = "INSERT INTO usuario (email, nome, userName, senha, dataNasc, idade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.getEmail());
            pstm.setString(2, usuario.getNome());
            pstm.setString(3, usuario.getUserName());
            pstm.setString(4, usuario.getSenha());
            pstm.setDate(5, Date.valueOf(usuario.getDataNasc()));
            pstm.setInt(6, usuario.getIdade());
            pstm.execute();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                if (e.getMessage().toLowerCase().contains("pk_user") || e.getMessage().toLowerCase().contains("uk_user_email")) {
                    throw new RuntimeException("Erro ao salvar: O Email informado já está em uso.");
                } else if (e.getMessage().toLowerCase().contains("uk_user_username")) {
                    throw new RuntimeException("Erro ao salvar: O Username informado já está em uso.");
                }
            }
            e.printStackTrace();
        }
    }

    /**
     * NOVO: Atualiza um utilizador existente no banco de dados.
     * O email não é atualizável por ser a chave primária.
     */
    public void update(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, userName = ?, senha = ?, dataNasc = ?, idade = ? WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getUserName());
            pstm.setString(3, usuario.getSenha());
            pstm.setDate(4, Date.valueOf(usuario.getDataNasc()));
            pstm.setInt(5, usuario.getIdade());
            pstm.setString(6, usuario.getEmail());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * NOVO: Exclui um utilizador do banco de dados pelo seu email.
     */
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM usuario WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * NOVO: Busca um único utilizador pelo seu email.
     */
    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        Usuario usuario = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    usuario = new Usuario();
                    usuario.setEmail(rset.getString("email"));
                    usuario.setNome(rset.getString("nome"));
                    usuario.setUserName(rset.getString("userName"));
                    usuario.setSenha(rset.getString("senha"));
                    usuario.setDataNasc(rset.getDate("dataNasc").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(1) FROM usuario WHERE userName = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            try (ResultSet rset = pstm.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rset = pstm.executeQuery()) {
            while (rset.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(rset.getString("email"));
                usuario.setNome(rset.getString("nome"));
                usuario.setUserName(rset.getString("userName"));
                usuario.setSenha(rset.getString("senha"));
                usuario.setDataNasc(rset.getDate("dataNasc").toLocalDate());
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
