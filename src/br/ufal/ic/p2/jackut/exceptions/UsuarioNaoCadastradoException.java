package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoCadastradoException extends RuntimeException {
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
