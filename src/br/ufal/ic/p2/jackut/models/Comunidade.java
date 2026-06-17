package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comunidade implements Serializable {

    /** Identificador único de versão da classe para serialização. */
    private static final long serialVersionUID = 1L;

    /** Nome único da comunidade, atuando como chave primária. */
    private String nome ;

    /** Descrição do propósito ou tema da comunidade. */
    private final String descricao;

    /** Login do usuário criador e administrador da comunidade. */
    private final String dono;

    /** Lista contendo os logins dos usuários que fazem parte desta comunidade. */
    private final List<String> membros;

    /**
     * Constrói uma nova comunidade.
     * O criador é automaticamente adicionado como o primeiro membro.
     *
     * @param nome      O nome único da comunidade.
     * @param descricao O texto descritivo da comunidade.
     * @param dono      O login do usuário que a criou.
     */
    public Comunidade(String nome, String descricao, String dono){
        this.descricao = descricao;
        this.dono = dono;
        this.nome = nome;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
    }

    /**
     * Recupera a descrição da comunidade.
     *
     * @return O texto descritivo.
     */
    public String getDescricao(){
        return descricao;
    }

    /**
     * Recupera o nome da comunidade.
     *
     * @return O nome.
     */
    public String getNome(){
        return nome;
    }
    /**
     * Recupera o login do dono da comunidade.
     *
     * @return O login do proprietário.
     */
    public String getDono(){
        return dono;
    }

    /**
     * Retorna a lista de membros de forma segura.
     *
     * @return Uma lista não modificável com os logins dos membros.
     */
    public List<String> getMembros(){
        return Collections.unmodifiableList(this.membros);
    }

    /**
     * Adiciona um novo membro à lista da comunidade.
     *
     * @param login O login do usuário a ser adicionado.
     */
    public void adicionarMembro(String login){
        this.membros.add(login);
    }
}
