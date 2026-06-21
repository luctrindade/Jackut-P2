package br.ufal.ic.p2.jackut.models.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Estratégia concreta para o relacionamento de Fã e Ídolo.
 * <p>
 * Um usuário adiciona outro como ídolo. O alvo passa a ter o remetente em sua lista de fãs.
 * </p>
 */
public class RelacaoFa implements RelacionamentoStrategy{
    /** Construtor padrão. */
    public RelacaoFa() {
    }

    @Override
    public String getTipo() {
        return "idolo";
    }

    @Override
    public void estabelecerVinculo(Usuario remetente, Usuario destinatario) {
        remetente.registrarIdolo(destinatario.getLogin());

        destinatario.registrarFa(remetente.getLogin());
    }
}
