package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class ConvitePendenteException extends RuntimeException {
    public ConvitePendenteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
