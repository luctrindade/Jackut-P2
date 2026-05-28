package br.ufal.ic.p2.jackut.exceptions;

public class AtributoNaoPreenchidoException extends RuntimeException {
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}
