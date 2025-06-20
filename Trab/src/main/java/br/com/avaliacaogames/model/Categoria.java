package br.com.avaliacaogames.model;

/**
 * Classe que representa a entidade Categoria.
 * Seus atributos correspondem às colunas da tabela "categoria" no banco de dados.
 */
public class Categoria {

    private int identificador;
    private String descricao;

    // Construtor vazio
    public Categoria() {
    }

    // Construtor com todos os campos
    public Categoria(int identificador, String descricao) {
        this.identificador = identificador;
        this.descricao = descricao;
    }

    // Getters e Setters para todos os atributos

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}