package br.ufal.ic.p2.jackut.exceptions;

public class LoginOuSenhaInvalidoException extends RuntimeException {
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha inválidos.");
    }
}
