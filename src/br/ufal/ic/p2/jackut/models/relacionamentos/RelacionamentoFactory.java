package br.ufal.ic.p2.jackut.models.relacionamentos;

/**
 * Responsável por instanciar a estratégia de relacionamento correta
 * com base na requisição do sistema.
 * <p>
 * Aplicação do padrão de projeto Factory Method.
 * </p>
 */
public class RelacionamentoFactory {
    /**
     * Construtor privado para impedir a instanciação da fábrica.
     */
    private RelacionamentoFactory() {
    }

    public static RelacionamentoStrategy criar(String tipo){
        switch (tipo.toLowerCase()) {
            case "idolo":
                return new RelacaoFa();
            case "paquera":
                return new RelacaoPaquera();
            case "inimigo":
                return new RelacaoInimigo();
            default:
                throw new IllegalArgumentException("Tipo de relacionamento desconhecido: " + tipo);
        }
    }
}
