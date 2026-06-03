package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
