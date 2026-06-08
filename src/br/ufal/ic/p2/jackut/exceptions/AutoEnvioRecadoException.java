package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem se tentar enviar recado a si mesmo.
 */
public class AutoEnvioRecadoException extends RuntimeException {
    /**
     * Exceção com a mensagem "Usuário não pode enviar recado para si mesmo.".
     */
    public AutoEnvioRecadoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
