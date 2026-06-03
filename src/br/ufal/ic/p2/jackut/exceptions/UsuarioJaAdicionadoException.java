package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class UsuarioJaAdicionadoException extends RuntimeException {
    public UsuarioJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
