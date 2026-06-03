package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class LoginOuSenhaInvalidoException extends RuntimeException {
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }
}
