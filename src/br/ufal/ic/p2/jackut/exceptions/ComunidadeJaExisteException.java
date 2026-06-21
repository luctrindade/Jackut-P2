package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando há uma tentativa de criar uma comunidade
 * com um nome que já está registrado no sistema.
 */
public class ComunidadeJaExisteException extends Exception {
    /**
     * Exceção com a mensagem "Comunidade com esse nome já existe.".
     */
    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }
}
