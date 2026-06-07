package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa o perfil descritivo de um usuário no sistema Jackut.
 * <p>
 * Esta classe isola o gerenciamento de atributos dinâmicos,
 * garantindo que o dicionário de dados fique fortemente encapsulado.
 * </p>
 */
public class Perfil implements Serializable {
    /**
     * Identificador único de versão da classe utilizado na serialização.
     * Garante a compatibilidade estrutural do objeto durante o processo de desserialização.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Dicionário interno que mapeia o nome de um atributo para o seu respectivo valor.
     */
    private final Map<String, String> atributos;

    /**
     * Constrói um novo perfil de usuário e inicializa a estrutura de armazenamento.
     * O nome é um atributo obrigatório de fundação e já é salvo na criação.
     *
     * @param nome O nome de exibição inicial do dono do perfil.
     */
    public Perfil(String nome){
        this.atributos = new HashMap<>();
        this.atributos.put("nome", nome);
    }

    /**
     * Adiciona um novo atributo ao perfil ou atualiza o valor de um atributo existente.
     *
     * @param chave O nome do atributo.
     * @param valor O valor correspondente que será salvo.
     */
    public void setAtributo(String chave, String valor){
        this.atributos.put(chave,valor);
    }

    /**
     * Recupera o valor de um atributo previamente salvo no perfil.
     *
     * @param chave O nome do atributo que se deseja consultar.
     * @return Uma {@code String} contendo o valor do atributo solicitado.
     * @throws AtributoNaoPreenchidoException Se a chave fornecida não existir no dicionário do perfil.
     */
    public String getAtributo(String chave) throws AtributoNaoPreenchidoException{
        if(!this.atributos.containsKey(chave)){
            throw new AtributoNaoPreenchidoException();
        }
        return this.atributos.get(chave);
    }
}
