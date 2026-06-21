package br.ufal.ic.p2.jackut.models.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Estratégia concreta para o relacionamento de Inimizade.
 * <p>
 * Um usuário declara o outro como inimigo. Isso causará
 * bloqueios futuros para amizades, recados e outras interações.
 * </p>
 */
public class RelacaoInimigo implements RelacionamentoStrategy {

    /** Construtor padrão. */
    public RelacaoInimigo() {
    }

    @Override
    public String getTipo() {
        return "inimigo";
    }

    @Override
    public void estabelecerVinculo(Usuario remetente, Usuario destinatario) {
        remetente.registrarInimigo(destinatario.getLogin());
    }
}