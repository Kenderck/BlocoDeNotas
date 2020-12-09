package br.com.kenderck.blocodenotas.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private final String titulo;
    private final String descricao;

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Nota(String nota, String descricao) {
        this.titulo = nota;
        this.descricao = descricao;
    }
}
