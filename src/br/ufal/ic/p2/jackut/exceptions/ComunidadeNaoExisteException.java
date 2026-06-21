package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando ocorre uma busca por uma comunidade
 * cujo nome não está registrado no sistema.
 */
public class ComunidadeNaoExisteException extends Exception {
    /**
     * Exceção com a mensagem "Comunidade não existe.".
     */
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
}
