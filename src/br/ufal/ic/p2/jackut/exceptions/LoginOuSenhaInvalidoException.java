package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem caso login ou senha inválidos.
 */
public class LoginOuSenhaInvalidoException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Login ou senha inválidos.".
     */
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }
}
