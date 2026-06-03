package br.ufal.ic.p2.jackut.exceptions;

/**
 * Constrói a exceção com a mensagem padrão de erro definida pelo sistema.
 */
public class AutoEnvioRecadoException extends RuntimeException {
    public AutoEnvioRecadoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
