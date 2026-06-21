package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Representa uma mensagem enviada para uma comunidade no sistema Jackut.
 */
public class Mensagem implements Serializable {
    /** Identificador único de versão da classe para serialização. */
    private static final long serialVersionUID = 1L;

    /** O conteúdo em texto da mensagem. */
    private final String texto;

    /**
     * Constrói uma nova mensagem.
     *
     * @param texto O conteúdo da mensagem.
     */
    public Mensagem(String texto){
        this.texto = texto;
    }

    /**
     * Recupera o conteúdo da mensagem.
     *
     * @return A mensagem de texto.
     */
    public String getTexto(){
        return texto;
    }
}
