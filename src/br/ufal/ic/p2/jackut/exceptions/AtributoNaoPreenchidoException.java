package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem caso o atributo não tenha sido definido.
 */
public class AtributoNaoPreenchidoException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Atributo não preenchido.".
     */
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
