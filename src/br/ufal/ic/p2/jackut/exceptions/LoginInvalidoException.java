package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
