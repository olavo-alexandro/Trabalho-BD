package br.com.avaliacaogames.model;

import java.util.List;

/**
 * Classe que representa a entidade Jogo.
 * Seus atributos correspondem às colunas da tabela "jogos" no banco de dados.
 */
public class Jogo {

    private String nome;
    private int anoLanc;
    private int numMin;
    private int numMax;
    private List<Categoria> categorias; // Para armazenar as categorias do jogo
    private float notaMedia; //atributo calculado

    // Construtor vazio
    public Jogo() {
    }

    // Construtor com os campos principais
    public Jogo(String nome, int anoLanc, int numMin, int numMax) {
        this.nome = nome;
        this.anoLanc = anoLanc;
        this.numMin = numMin;
        this.numMax = numMax;
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoLanc() {
        return anoLanc;
    }

    public void setAnoLanc(int anoLanc) {
        this.anoLanc = anoLanc;
    }

    public int getNumMin() {
        return numMin;
    }

    public void setNumMin(int numMin) {
        this.numMin = numMin;
    }

    public int getNumMax() {
        return numMax;
    }

    public void setNumMax(int numMax) {
        this.numMax = numMax;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public float getNotaMedia(){
        return notaMedia;
    }

    public void setNotaMedia(float notaMedia) {
        this.notaMedia = notaMedia;
    }
}