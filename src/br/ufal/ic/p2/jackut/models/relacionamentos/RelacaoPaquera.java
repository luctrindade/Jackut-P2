package br.ufal.ic.p2.jackut.models.relacionamentos;

import br.ufal.ic.p2.jackut.models.Recado;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Estratégia concreta para o relacionamento de Paquera.
 * <p>
 * A adição é privada. Se houver reciprocidade mútua, o sistema
 * atua como Observer e dispara um recado automático para ambos.
 * </p>
 */
public class RelacaoPaquera implements RelacionamentoStrategy {

    /** Construtor padrão. */
    public RelacaoPaquera() {
    }

    @Override
    public String getTipo() {
        return "paquera";
    }

    @Override
    public void estabelecerVinculo(Usuario remetente, Usuario destinatario) {
        remetente.registrarPaquera(destinatario.getLogin());

        if (destinatario.ehPaquera(remetente.getLogin())) {

            String msgParaRemetente = destinatario.getNome() + " é seu paquera - Recado do Jackut.";
            String msgParaDestinatario = remetente.getNome() + " é seu paquera - Recado do Jackut.";

            remetente.adicionarRecado(new Recado("Jackut", msgParaRemetente));
            destinatario.adicionarRecado(new Recado("Jackut", msgParaDestinatario));
        }
    }
}