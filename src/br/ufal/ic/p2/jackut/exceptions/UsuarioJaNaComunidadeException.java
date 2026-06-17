package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta se adicionar a uma comunidade
 * da qual ele já é membro ativo.
 */
public class UsuarioJaNaComunidadeException extends Exception {
    /**
     * Exceção com a mensagem "Usuario já faz parte dessa comunidade.".
     */
    public UsuarioJaNaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
