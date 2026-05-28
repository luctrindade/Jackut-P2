package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAdicionadoException extends RuntimeException {
    public UsuarioJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
