package br.com.avaliacaogames.model;

import java.time.LocalDate;
import java.util.Date; // Import necessário

/**
 * Classe que representa a entidade Avaliacao.
 */
public class Avaliacao {

    // Atributos da própria avaliação
    private int notaComplex;
    private int notaRejo;
    private int notaDiver;
    private int qualidadeComp;
    private float notaGeral; //atributo calculado
    private String comentario;
    private LocalDate dataAval;

    // Chaves estrangeiras
    private String userEmail;
    private String jogoNome;

    // Objetos de relacionamento
    private Usuario usuario;
    private Jogo jogo;

    public Avaliacao() {}

    // Getters e Setters

    public int getNotaComplex() {
        return notaComplex;
    }

    public void setNotaComplex(int notaComplex) {
        this.notaComplex = notaComplex;
    }

    public int getNotaRejo() {
        return notaRejo;
    }

    public void setNotaRejo(int notaRejo) {
        this.notaRejo = notaRejo;
    }

    public int getNotaDiver() {
        return notaDiver;
    }

    public void setNotaDiver(int notaDiver) {
        this.notaDiver = notaDiver;
    }

    public int getQualidadeComp() {
        return qualidadeComp;
    }

    public void setQualidadeComp(int qualidadeComp) {
        this.qualidadeComp = qualidadeComp;
    }

    public float getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(float notaGeral) {
        this.notaGeral = notaGeral;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getDataAval() {
        return dataAval;
    }

    public void setDataAval(LocalDate dataAval) {
        this.dataAval = dataAval;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJogoNome() {
        return jogoNome;
    }

    public void setJogoNome(String jogoNome) {
        this.jogoNome = jogoNome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Date getDataAvalAsDate() {
        if (this.dataAval == null) {
            return null;
        }
        return java.sql.Date.valueOf(this.dataAval);
    }
}
