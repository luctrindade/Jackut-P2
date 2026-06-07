package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem se a conta já existir.
 */
public class ContaJaExisteException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Conta com esse nome já existe.".
     */
    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
