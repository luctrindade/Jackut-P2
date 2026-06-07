package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção se o Usuário a adicionar ja é amigo
 */
public class UsuarioJaAdicionadoException extends RuntimeException {
    public UsuarioJaAdicionadoException() {
        /**
         * Cria a exceção com a mensagem "Usuário já está adicionado como amigo.".
         */
        super("Usuário já está adicionado como amigo.");
    }
}
