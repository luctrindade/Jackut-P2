package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class AutoAdicaoException extends RuntimeException {
    public AutoAdicaoException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
