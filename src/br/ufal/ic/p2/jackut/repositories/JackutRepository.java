package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.models.Comunidade;
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

    /**
     * Mapa em memória que armazena todos os usuários cadastrados.
     * A chave é o login do usuário e o valor é a entidade Usuario correspondente.
     */
    private final Map<String, Usuario> usuarios;
    /**
     * Mapa em memória que gerencia as sessões ativas do sistema.
     * A chave é o UUID da sessão e o valor é o login do usuário autenticado.
     */
    private final Map<String, String> sessoesAtivas;

    /**
     * Mapa em memória que armazena todas as comunidades criadas.
     * A chave é o nome da comunidade e o valor é a entidade Comunidade correspondente.
     */
    private final Map<String, Comunidade> comunidades;

    /**
     * Construtor privado para impedir a criação de instâncias externas via operador {@code new}.
     * Inicializa as estruturas de dados vazias.
     */
    private JackutRepository(){
        this.usuarios = new HashMap<>();
        this.sessoesAtivas = new HashMap<>();
        this.comunidades = new HashMap<>();
    }

    /**
     * Exporta o estado atual do sistema para um arquivo binário.
     */
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(this.usuarios);
            oos.writeObject(this.sessoesAtivas);
            oos.writeObject(this.comunidades);
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
            Map<String, Comunidade> comunidadesSalvas = (Map<String,Comunidade>) ois.readObject();
            this.zerarSistema();

            this.usuarios.putAll(usuariosSalvos);
            this.sessoesAtivas.putAll(sessoesSalvas);
            this.comunidades.putAll(comunidadesSalvas);

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
        this.comunidades.clear();
    }

    /**
     * Verifica se um usuário com o login especificado já está cadastrado no sistema.
     *
     * @param login O login único a ser verificado.
     * @return {@code true} se o usuário existir no repositório, {@code false} caso contrário.
     */
    public boolean existeUsuario(String login) {
        return this.usuarios.containsKey(login);
    }

    /**
     * Adiciona um novo usuário ao repositório de dados em memória,
     * utilizando o login do usuário como chave de armazenamento e busca.
     *
     * @param usuario O objeto {@code Usuario} instanciado a ser guardado no sistema.
     */
    public void adicionarUsuario(Usuario usuario) {
        this.usuarios.put(usuario.getLogin(), usuario);
    }

    /**
     * Recupera um objeto de usuário diretamente do repositório com base no seu login.
     *
     * @param login O login único do usuário a ser consultado.
     * @return A instância de {@code Usuario} correspondente ao login informado,
     * ou {@code null} se o usuário não for encontrado.
     */
    public Usuario buscarUsuario(String login) {
        return this.usuarios.get(login);
    }

    /**
     * Consulta o registro de sessões ativas para recuperar o login associado
     * a um determinado identificador de sessão.
     *
     * @param idSessao O identificador único da sessão.
     * @return O login do usuário autenticado dono da sessão, ou {@code null} se a sessão não existir.
     */
    public String buscarLoginSessao(String idSessao) {
        return this.sessoesAtivas.get(idSessao);
    }

    /**
     * Registra uma nova sessão ativa no sistema, criando um vínculo entre
     * o identificador da sessão e o login do usuário autenticado.
     *
     * @param idSessao O identificador único gerado para esta sessão.
     * @param login    O login do usuário que acabou de se autenticar.
     */
    public void adicionarSessao(String idSessao, String login) {
        this.sessoesAtivas.put(idSessao, login);
    }

    /**
     * Verifica se uma comunidade com o nome especificado já existe no sistema.
     *
     * @param nome O nome da comunidade a ser verificado.
     * @return {@code true} se a comunidade existir, {@code false} caso contrário.
     */
    public boolean existeComunidade(String nome){
        return this.comunidades.containsKey(nome);
    }

    /**
     * Adiciona uma nova comunidade ao repositório de dados.
     *
     * @param comunidade O objeto {@code Comunidade} instanciado a ser salvo.
     */
    public void adicionarComunidade(Comunidade comunidade){
        this.comunidades.put(comunidade.getNome(),comunidade);
    }

    /**
     * Recupera uma comunidade do repositório com base no seu nome.
     *
     * @param nome O nome da comunidade.
     * @return A instância da {@code Comunidade}, ou {@code null} se não for encontrada.
     */
    public Comunidade buscarComunidade(String nome){
        return this.comunidades.get(nome);
    }
}
