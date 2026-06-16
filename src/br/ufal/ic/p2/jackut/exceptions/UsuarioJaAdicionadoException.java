package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção se o Usuário a adicionar ja é amigo
 */
public class UsuarioJaAdicionadoException extends Exception {
    /**
     * Exceção com a mensagem "Usuário já está adicionado como amigo.".
     */
    public UsuarioJaAdicionadoException() {
        super("Usuário já está adicionado como amigo.");
    }
}
