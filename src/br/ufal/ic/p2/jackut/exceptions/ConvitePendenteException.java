package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem caso convite pendente.
 */
public class ConvitePendenteException extends Exception {
    /**
     * Exceção com a mensagem "Usuário já está adicionado como amigo, esperando aceitação do convite.".
     */
    public ConvitePendenteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
