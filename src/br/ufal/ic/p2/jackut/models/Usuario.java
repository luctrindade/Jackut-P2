package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.relacionamentos.RelacionamentoStrategy;

import java.io.Serializable;
import java.util.*;

/**
 * Entidade central do sistema Jackut.
 * <p>
 * O {@code Usuario} atua como a raiz de agregação, sendo responsável por
 * encapsular e gerenciar o próprio estado, incluindo seu perfil descritivo,
 * lista de amizades, histórico de convites enviados e a caixa de recados.
 * </p>
 */
public class Usuario implements Serializable {
    /**
     * Identificador único de versão da classe utilizado na serialização.
     * Garante a compatibilidade estrutural do objeto durante o processo de desserialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * O login único de acesso do usuário.
     */
    private final String login;
    /**
     * A senha de autenticação da conta.
     */
    private String senha;

    /**
     * O perfil do usuário, contendo seus atributos e nome de exibição.
     */
    private final Perfil perfil;
    /**
     * Conjunto de logins para os quais o usuário enviou um convite de amizade pendente.
     */
    private final Set<String> envioConvites;
    /**
     * Lista de logins dos usuários que possuem vínculo de amizade consolidado com o usuário.
     */
    private final List<String> amigos;
    /**
     * Fila cronológica contendo os recados recebidos pelo usuário.
     */
    private final Queue<Recado> recados;

    /**
     * Lista com os nomes das comunidades das quais o usuário é membro.
     */
    private final List<String> comunidades;

    /**
     * Fila cronológica (FIFO) contendo as mensagens de comunidades recebidas.
     */
    private final Queue<Mensagem> mensagens;

    /** Conjunto de logins que o usuário declarou como ídolos. */
    private final Set<String> idolos;

    /** Conjunto de logins de usuários que são fãs do usuário. */
    private final Set<String> fas;

    /** Conjunto de logins que o usuário paquera. */
    private final Set<String> paqueras;

    /** Conjunto de logins que o usuário declarou como inimigos. */
    private final Set<String> inimigos;

    /**
     * Constrói e inicializa um novo Usuário no sistema.
     * Realiza a validação básica das credenciais antes de instanciar as coleções internas.
     *
     * @param login O login desejado para o usuário.
     * @param senha A senha de acesso da conta.
     * @param nome  O nome de exibição inicial do usuário.
     * @throws LoginInvalidoException Se o login fornecido for nulo ou estiver em branco.
     * @throws SenhaInvalidaException Se a senha fornecida for nula ou estiver em branco.
     */
    public Usuario(String login, String senha, String nome) throws LoginInvalidoException, SenhaInvalidaException {
        if(login == null || login.trim().isEmpty()){
            throw new LoginInvalidoException();
        }
        if(senha == null || senha.trim().isEmpty()){
            throw new SenhaInvalidaException();
        }

        this.login = login;
        this.senha = senha;
        this.perfil = new Perfil(nome);
        this.envioConvites = new HashSet<>();
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
        this.comunidades = new ArrayList<>();
        this.mensagens = new LinkedList<>();
        this.idolos = new HashSet<>();
        this.fas = new HashSet<>();
        this.paqueras = new HashSet<>();
        this.inimigos = new HashSet<>();
    }

    /**
     * Recupera o login único do usuário.
     *
     * @return O login do usuário.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Valida se uma tentativa de senha corresponde à senha real do usuário.
     *
     * @param senhaTentiva A senha inserida durante a tentativa de login.
     * @return {@code true} se a senha estiver correta, {@code false} caso contrário.
     */
    public boolean autenticar(String senhaTentiva){
        return this.senha.equals(senhaTentiva);
    }

    /**
     * Delega a adição ou atualização de um atributo descritivo para a classe Perfil.
     *
     * @param atributo O nome do atributo (ex: "estilo").
     * @param valor    O valor do atributo.
     */
    public void setAtributo(String atributo, String valor){
        this.perfil.setAtributo(atributo, valor);
    }

    /**
     * Delega a busca de um atributo descritivo para a classe Perfil.
     *
     * @param atributo O nome do atributo consultado.
     * @return O valor armazenado no atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo não existir no perfil.
     */
    public String getAtributo(String atributo) throws AtributoNaoPreenchidoException{
        return this.perfil.getAtributo(atributo);
    }

    /**
     * Verifica se o usuário atual já é amigo do usuário informado.
     *
     * @param amigoLogin O login do usuário a ser verificado.
     * @return {@code true} se a amizade já estiver consolidada, {@code false} caso contrário.
     */
    public boolean ehAmigo(String amigoLogin){
        return this.amigos.contains(amigoLogin);
    }

    /**
     * Verifica se o usuário atual já enviou um convite pendente para o destino.
     *
     * @param amigoLogin O login do usuário de destino.
     * @return {@code true} se o convite já foi enviado, {@code false} caso contrário.
     */
    public boolean jaEnviouConvitePara(String amigoLogin){
        return this.envioConvites.contains(amigoLogin);
    }

    /**
     * Registra o envio de um novo convite de amizade.
     *
     * @param amigoLogin O login do destinatário do convite.
     */
    public void enviarConvite(String amigoLogin){
        this.envioConvites.add(amigoLogin);
    }

    /**
     * Remove um convite pendente da lista de enviados (utilizado quando a amizade é aceita).
     *
     * @param amigoLogin O login do destinatário cujo convite será removido.
     */
    public void removerConviteEnviado(String amigoLogin){
        this.envioConvites.remove(amigoLogin);
    }

    /**
     * Adiciona um novo usuário à lista de amizades consolidadas.
     *
     * @param amigoLogin O login do novo amigo.
     */
    public void adicionarAmigo(String amigoLogin){
        this.amigos.add(amigoLogin);
    }

    /**
     * Recupera a lista de amigos do usuário de forma segura (imutável).
     *
     * @return Uma {@code List} não modificável contendo os logins dos amigos.
     */
    public List<String> getAmigos(){
        return Collections.unmodifiableList(this.amigos);
    }

    /**
     * Insere um novo recado no final da fila de leitura do usuário.
     *
     * @param recado O objeto Recado recebido.
     */
    public void adicionarRecado(Recado recado){
        this.recados.add(recado);
    }

    /**
     * Lê o recado mais antigo da caixa de entrada, removendo-o da fila de espera.
     *
     * @return O conteúdo em texto do recado.
     * @throws NaoHaRecadosException Se o usuário não possuir nenhum recado na fila.
     */
    public String lerRecado() throws NaoHaRecadosException{
        if(this.recados.isEmpty()){
            throw new NaoHaRecadosException();
        }
        return this.recados.poll().getTexto();
    }

    /**
     * Adiciona o nome de uma comunidade à lista de participações do usuário.
     *
     * @param nome O nome da comunidade a ser adicionada.
     */
    public void adicionarComunidade(String nome){
        this.comunidades.add(nome);
    }

    /**
     * Recupera a lista de comunidades do usuário de forma segura.
     *
     * @return Uma {@code List} não modificável contendo os nomes das comunidades.
     */
    public List<String> getComunidades(){
        return Collections.unmodifiableList(this.comunidades);
    }

    /**
     * Insere uma nova mensagem de comunidade no final da fila de leitura do usuário.
     *
     * @param mensagem O objeto Mensagem recebido.
     */
    public void adicionarMensagem(Mensagem mensagem){
        this.mensagens.add(mensagem);
    }

    /**
     * Lê a mensagem mais antiga da caixa de entrada, removendo-a da fila.
     *
     * @return O conteúdo em texto da mensagem.
     * @throws NaoHaMensagensException Se o usuário não possuir nenhuma mensagem na fila.
     */
    public String lerMensagem() throws NaoHaMensagensException {
        if(this.mensagens.isEmpty()){
            throw new NaoHaMensagensException();
        }
        return this.mensagens.poll().getTexto();
    }

    /**
     * Delega a consolidação de um novo relacionamento para a estratégia específica.
     *
     * @param alvo       O usuário alvo do relacionamento.
     * @param estrategia A regra de negócio (Strategy) a ser aplicada.
     */
    public void estabelecerRelacionamento(Usuario alvo, RelacionamentoStrategy estrategia) {
        estrategia.estabelecerVinculo(this, alvo);
    }

    /**
     * Registra um novo ídolo para o usuário.
     * @param login O login do usuário que será o ídolo.
     */
    public void registrarIdolo(String login) {
        this.idolos.add(login);
    }

    /**
     * Verifica se o usuário é fã do login especificado.
     * @param login O login do ídolo procurado.
     * @return {@code true} se o usuário possuir este ídolo, {@code false} caso contrário.
     */
    public boolean temIdolo(String login) {
        return this.idolos.contains(login);
    }

    /**
     * Adiciona um novo fã à lista do usuário.
     * @param login O login do fã a ser adicionado.
     */
    public void registrarFa(String login) {
        this.fas.add(login);
    }

    /**
     * Retorna a lista de fãs formatada para leitura.
     * @return Uma lista contendo os logins dos fãs.
     */
    public List<String> getFas() {
        return new ArrayList<>(this.fas);
    }

    /**
     * Registro de um novo paquera de forma privada.
     * @param login O login do usuário paquerado.
     */
    public void registrarPaquera(String login) {
        this.paqueras.add(login);
    }

    /**
     * Verifica se o login especificado está na lista de paqueras do usuário.
     * @param login O login do possível paquera.
     * @return {@code true} se o usuário paquera o login informado, {@code false} caso contrário.
     */
    public boolean ehPaquera(String login) {
        return this.paqueras.contains(login);
    }

    /**
     * Retorna a lista contendo todos os paqueras do usuário.
     * @return Uma lista com os logins dos paqueras.
     */
    public List<String> getPaqueras() {
        return new ArrayList<>(this.paqueras);
    }

    /**
     * Registro de uma declaração de inimizade.
     * @param login O login do usuário declarado como inimigo.
     */
    public void registrarInimigo(String login) {
        this.inimigos.add(login);
    }

    /**
     * Verifica se o usuário declarou o login especificado como inimigo.
     * @param login O login do possível inimigo.
     * @return {@code true} se o login for considerado inimigo, {@code false} caso contrário.
     */
    public boolean ehInimigo(String login) {
        return this.inimigos.contains(login);
    }

    /**
     * Recupera o nome de exibição do usuário diretamente do perfil.
     *
     * @return O nome do usuário.
     */
    public String getNome() {
        try {
            return this.perfil.getAtributo("nome");
        } catch (AtributoNaoPreenchidoException e) {
            return this.login;
        }
    }

    /**
     * Varre todas as listas de relacionamento do usuário e apaga qualquer
     * registro associado ao login alvo.
     *
     * @param login O login do usuário que está sendo deletado do sistema.
     */
    public void apagarRegistrosDe(String login) {
        this.amigos.remove(login);
        this.idolos.remove(login);
        this.fas.remove(login);
        this.paqueras.remove(login);
        this.inimigos.remove(login);
    }

    /**
     * Remove da caixa de entrada todos os recados cujo remetente seja o usuário deletado.
     *
     * @param remetenteLogin O login do remetente a ser filtrado.
     */
    public void removerRecadosDe(String remetenteLogin) {
        this.recados.removeIf(recado -> recado.getRemetente().equals(remetenteLogin));
    }

    /**
     * Remove uma comunidade da lista de participações do usuário.
     *
     * @param nomeComunidade O nome da comunidade a ser removida.
     */
    public void removerComunidade(String nomeComunidade) {
        this.comunidades.remove(nomeComunidade);
    }
}
