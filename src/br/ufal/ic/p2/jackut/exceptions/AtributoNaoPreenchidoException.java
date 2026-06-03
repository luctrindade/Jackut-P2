package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class AtributoNaoPreenchidoException extends RuntimeException {
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
