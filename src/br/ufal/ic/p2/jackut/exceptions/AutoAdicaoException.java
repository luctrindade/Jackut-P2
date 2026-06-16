package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem se houver auto adição.
 */
public class AutoAdicaoException extends Exception {
    /**
     * Exceção com a mensagem "Usuário não pode adicionar a si mesmo como amigo.".
     */
    public AutoAdicaoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
