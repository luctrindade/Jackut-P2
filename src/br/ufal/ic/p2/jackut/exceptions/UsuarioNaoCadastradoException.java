package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção se o Usuário não estiver cadastrado.
 */
public class UsuarioNaoCadastradoException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Usuário não cadastrado.".
     */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
