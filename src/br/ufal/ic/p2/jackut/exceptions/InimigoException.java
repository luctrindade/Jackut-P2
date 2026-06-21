package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta realizar uma ação interativa
 * direcionada a outro usuário que o declarou previamente como inimigo.
 * <p>
 * Garante o cumprimento da regra de bloqueio mútuo exigida pelo sistema.
 * </p>
 */
public class InimigoException extends Exception {

  /**
   * Constrói a exceção formatando a mensagem de erro padrão do sistema
   * com o nome do usuário que aplicou o bloqueio de inimizade.
   *
   * @param nomeInimigo O nome de exibição do usuário alvo que bloqueou a ação.
   */
  public InimigoException(String nomeInimigo) {
    super("Função inválida: " + nomeInimigo + " é seu inimigo.");
  }
}