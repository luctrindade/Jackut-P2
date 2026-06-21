package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta ler uma mensagem de comunidade,
 * mas a sua caixa de entrada de mensagens está vazia.
 */
public class NaoHaMensagensException extends Exception {
    /**
     * Exceção com a mensagem "Não há mensagens.".
     */
    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }
}
