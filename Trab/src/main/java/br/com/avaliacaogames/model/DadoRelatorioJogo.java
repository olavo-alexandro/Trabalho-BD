package br.com.avaliacaogames.model;


public class DadoRelatorioJogo {
    private String nome;
    private double media;

    public DadoRelatorioJogo(String nome, double media) {
        this.nome = nome;
        this.media = media;
    }


    public String getNome() {
        return nome;
    }

    public double getMedia() {
        return media;
    }
}
