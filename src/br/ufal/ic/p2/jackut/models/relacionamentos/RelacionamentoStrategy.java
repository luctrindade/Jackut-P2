package br.ufal.ic.p2.jackut.models.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.InimigoException;
import br.ufal.ic.p2.jackut.exceptions.RelacionamentoException;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Interface que define o contrato para a criação de novos vínculos entre usuários.
 * <p>
 * Aplicação do padrão de projeto Strategy para encapsular as diferentes regras
 * de negócio (Fã, Paquera, Inimigo) sem sobrecarregar a classe Usuario.
 * </p>
 */
public interface RelacionamentoStrategy {
    /**
     * Retorna a string identificadora do tipo de relacionamento.
     *
     * @return O tipo do vínculo como "idolo", "paquera", "inimigo").
     */
    String getTipo();

    /**
     * Executa as regras de negócio específicas para estabelecer o vínculo entre os usuários.
     *
     * @param remetente    O usuário que está iniciando a ação de relacionamento.
     * @param destinatario O usuário alvo do relacionamento.
     */
    void estabelecerVinculo(Usuario remetente, Usuario destinatario);
}
