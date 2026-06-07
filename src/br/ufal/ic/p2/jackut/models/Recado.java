package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Representa uma mensagem de texto (recado) enviada entre usuários no sistema Jackut.
 * <p>
 * Atua armazenando a origem do envio e o conteúdo da mensagem de forma imutável.
 * </p>
 */
public class Recado implements Serializable {
    /**
     * Identificador único de versão da classe utilizado na serialização.
     * Garante a compatibilidade estrutural do objeto durante o processo de desserialização.
     */
    private static final long serialVersionUID = 1L;
    /** O login do usuário que originou o envio do recado. */
    private final String remetente;

    /** O conteúdo em texto da mensagem enviada. */
    private final String texto;


    /**
     * Constrói um novo recado contendo a informação de quem o enviou e a sua mensagem.
     *
     * @param remetente O login do usuário remetente.
     * @param texto     O conteúdo da mensagem do recado.
     */
    public Recado(String remetente, String texto){
        this.texto = texto;
        this.remetente = remetente;
    }

    /**
     * Recupera o login de quem enviou o recado.
     *
     * @return O login do usuário remetente.
     */
    public String getRemetente(){
        return remetente;
    }

    /**
     * Recupera o conteúdo da mensagem do recado.
     *
     * @return A mensagem de texto do recado.
     */
    public String getTexto(){
        return texto;
    }

}
