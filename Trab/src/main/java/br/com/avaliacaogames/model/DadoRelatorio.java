package br.com.avaliacaogames.model;

public class DadoRelatorio {
    private int ano;
    private double media;

    public DadoRelatorio(int ano, double media) {
        this.ano = ano;
        this.media = media;
    }

    public int getAno() {
        return ano;
    }

    public double getMedia() {
        return media;
    }
}
