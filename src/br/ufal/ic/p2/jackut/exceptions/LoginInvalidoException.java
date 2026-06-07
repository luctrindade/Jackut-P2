package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem caso login seja inválido.
 */
public class LoginInvalidoException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Login inválido".
     */
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
