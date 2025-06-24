package br.com.avaliacaogames.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe que representa a entidade Usuario.
 * Seus atributos correspondem às colunas da tabela "usuario" no banco de dados.
 */
public class Usuario {

    private String email;
    private String nome;
    private String userName;
    private String senha;
    private LocalDate dataNasc;
    private int idade; // atributo calculado

    // Construtor vazio
    public Usuario() {
    }

    // Construtor com os campos principais do formulário
    public Usuario(String email, String nome, String userName, String senha, LocalDate dataNasc) {
        this.email = email;
        this.nome = nome;
        this.userName = userName;
        this.senha = senha;
        // Ao usar o construtor, também definimos a data de nascimento e calculamos a idade
        this.setDataNasc(dataNasc);
    }

    // Getters e Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }


    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
        if (dataNasc != null) {
            this.idade = Period.between(dataNasc, LocalDate.now()).getYears();
        }
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}