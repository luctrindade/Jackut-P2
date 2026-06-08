package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem caso usuário colocar senha inválida.
 */
public class SenhaInvalidaException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Senha inválida.".
     */
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
