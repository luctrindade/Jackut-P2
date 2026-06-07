package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta ler um recado, mas sua caixa de entrada está vazia.
 */
public class NaoHaRecadosException extends RuntimeException {
    /**
     * Cria a exceção com a mensagem "Não há recados.".
     */
    public NaoHaRecadosException() {
        super("Não há recados.");
    }
}
