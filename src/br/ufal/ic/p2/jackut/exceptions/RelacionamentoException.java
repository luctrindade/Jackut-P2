package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando ocorre uma violação nas regras de negócio
 * durante a criação ou manipulação de um relacionamento entre usuários
 * (como ídolo, fã, paquera ou inimigo).
 * <p>
 * É utilizada para sinalizar falhas de domínio, como tentativas de
 * auto-relacionamento ou a duplicação de um vínculo já existente.
 * </p>
 */
public class RelacionamentoException extends Exception {
    /**
     * Constrói a exceção com uma mensagem de erro específica,
     * detalhando o motivo exato da falha na operação de relacionamento.
     *
     * @param message A mensagem detalhada explicando a regra de negócio violada.
     */
    public RelacionamentoException(String message) {
        super(message);
    }
}
