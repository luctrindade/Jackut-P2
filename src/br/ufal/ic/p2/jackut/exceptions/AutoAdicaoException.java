package br.ufal.ic.p2.jackut.exceptions;

public class AutoAdicaoException extends RuntimeException {
    public AutoAdicaoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
