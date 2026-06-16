package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção se o Usuário não estiver cadastrado.
 */
public class UsuarioNaoCadastradoException extends Exception {
    /**
     * Exceção com a mensagem "Usuário não cadastrado.".
     */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
