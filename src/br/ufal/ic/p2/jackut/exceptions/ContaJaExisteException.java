package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class ContaJaExisteException extends RuntimeException {
    public ContaJaExisteException() {
        super("Conta com esse nome já existe.");
    }
}
