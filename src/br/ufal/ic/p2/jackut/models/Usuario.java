package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecadosException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;

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
    private static final long serialVersionUID = 1L;
    private final String login;
    private String senha;

    private final Perfil perfil;
    private final Set<String> envioConvites;
    private final List<String> amigos;
    private final Queue<Recado> recados;

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
    public Usuario(String login, String senha, String nome) throws LoginInvalidoException{
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
}
