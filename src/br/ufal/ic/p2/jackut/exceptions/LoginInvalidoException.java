package br.ufal.ic.p2.jackut.exceptions;

public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
