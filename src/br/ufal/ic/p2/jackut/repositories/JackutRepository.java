package br.ufal.ic.p2.jackut.repositories;


import br.ufal.ic.p2.jackut.models.Recado;
import br.ufal.ic.p2.jackut.models.Usuario;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Repositório central de dados do sistema Jackut.
 * <p>
 * Implementa o padrão de projeto <b>Singleton</b> para garantir que exista
 * apenas uma única instância (uma fonte única de verdade) armazenando os
 * dados em memória (usuários e sessões ativas) durante toda a execução do sistema.
 * </p>
 */
public class JackutRepository {

    /** * Instância estática e única da classe (Singleton).
     */
    private static JackutRepository instancia;

    private final Map<String, Usuario> usuarios;
    private final Map<String, String> sessoesAtivas;

    /**
     * Construtor privado para impedir a criação de instâncias externas via operador {@code new}.
     * Inicializa as estruturas de dados vazias.
     */
    private JackutRepository(){
        this.usuarios = new HashMap<>();
        this.sessoesAtivas = new HashMap<>();
    }

    /**
     * Exporta o estado atual do sistema para um arquivo binário.
     */
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(this.usuarios);
            oos.writeObject(this.sessoesAtivas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado do sistema a partir de um arquivo binário.
     */
    @SuppressWarnings("unchecked")
    public void carregarDados() {
        File arquivo = new File("database.dat");
        if (!arquivo.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {

            Map<String, Usuario> usuariosSalvos = (Map<String, Usuario>) ois.readObject();
            Map<String, String> sessoesSalvas = (Map<String, String>) ois.readObject();

            this.zerarSistema();

            this.usuarios.putAll(usuariosSalvos);
            this.sessoesAtivas.putAll(sessoesSalvas);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    /**
     * Recupera a instância única do repositório.
     * é sincronizado para ser seguro em ambientes com múltiplas threads (thread-safe).
     * @return A instância única e global de {@code JackutRepository}.
     */
    public static synchronized JackutRepository getInstancia(){
        if(instancia == null){
            instancia = new JackutRepository();
        }
        return instancia;
    }

    /**
     * Limpa completamente o "banco de dados" em memória.
     * Remove todos os usuários cadastrados e destrói todas as sessões ativas.
     */
    public void zerarSistema(){
        this.usuarios.clear();
        this.sessoesAtivas.clear();
    }

    /**
     * Recupera o mapa contendo todos os usuários cadastrados no sistema.
     *
     * @return Um {@code Map} contendo os logins como chaves e as entidades {@code Usuario} como valores.
     */
    public Map<String, Usuario> getUsuarios(){
        return usuarios;
    }

    /**
     * Recupera o mapa contendo as sessões ativas no momento.
     *
     * @return Um {@code Map} contendo os IDs das sessões como chaves e os logins autenticados como valores.
     */
    public Map<String, String> getSessoesAtivas(){
        return sessoesAtivas;
    }
}
