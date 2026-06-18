package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.*;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

/**
 * Fachada principal do sistema Jackut.
 * <p>
 * Implementa o padrão de projeto Facade para fornecer uma interface unificada
 * e simplificada, isolando a complexidade dos controladores internos e
 * servindo como ponto de entrada para os testes do EasyAccept.
 * </p>
 */
public class Facade {
    private final UsuarioController usuarioController = new UsuarioController();
    private final AutenticacaoController autenticacaoController = new AutenticacaoController();
    private final AmizadeController amizadeController = new AmizadeController();
    private final ComunidadeController comunidadeController = new ComunidadeController();
    /** Repositório central (Singleton) de acesso aos dados em memória. */
    private final JackutRepository repo = JackutRepository.getInstancia();
    private final RecadoController recadoController = new RecadoController();
    private final MensagemController mensagemController = new MensagemController();

    /**
     * Construtor da Facade.
     * Tenta carregar o estado anterior do sistema salvo em disco (se existir)
     * logo na inicialização.
     */
    public Facade() {
        repo.carregarDados();
    }

    /**
     * Limpa completamente o estado do sistema, deletando todos os usuários
     * e encerrando todas as sessões ativas. Utilizado principalmente para
     * a redefinição de estado entre testes.
     */
    public void zerarSistema(){
        repo.zerarSistema();
    }

    /**
     * Delega a criação de um novo usuário para o controlador correspondente.
     *
     * @param login O login do novo usuário.
     * @param senha A senha do novo usuário.
     * @param nome  O nome de exibição.
     * @throws ContaJaExisteException Se o login já estiver cadastrado.
     * @throws SenhaInvalidaException Se a senha fornecida for nula ou estiver vazia.
     * @throws LoginInvalidoException Se o login fornecido for nulo ou estiver vazio.
     */
    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException, SenhaInvalidaException, LoginInvalidoException {
        usuarioController.criarUsuario(login, senha, nome);
    }

    /**
     * Consulta um atributo específico do perfil de um usuário.
     *
     * @param login    O login do usuário consultado.
     * @param atributo O nome do atributo desejado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não existir.
     * @throws AtributoNaoPreenchidoException Se o atributo não existir no perfil.
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        return usuarioController.getAtributoUsuario(login,atributo);
    }

    /**
     * Edita ou adiciona um atributo ao perfil do usuário logado.
     *
     * @param id       O identificador da sessão ativa.
     * @param atributo O nome do atributo a ser modificado.
     * @param valor    O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     */
    public void editarPerfil(String id, String atributo, String valor) throws UsuarioNaoCadastradoException{
        usuarioController.editarPerfil(id,atributo,valor);
    }

    /**
     * Autentica um usuário e cria uma nova sessão no sistema.
     *
     * @param login O login do usuário.
     * @param senha A senha de acesso.
     * @return O UUID (identificador único) da sessão gerada.
     * @throws LoginOuSenhaInvalidoException Se as credenciais estiverem incorretas.
     * @throws LoginInvalidoException Se as credenciais forem nulas ou vazias.
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException, LoginInvalidoException {
        return autenticacaoController.abrirSessao(login, senha);
    }

    /**
     * Envia um convite de amizade ou consolida uma amizade pendente.
     *
     * @param login O id da sessão do usuário remetente.
     * @param amigo O login do usuário destinatário.
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não for encontrado.
     * @throws ConvitePendenteException Se já houver um convite enviado para este usuário.
     * @throws UsuarioJaAdicionadoException Se os usuários já forem amigos.
     * @throws AutoAdicaoException Se o usuário tentar adicionar a si próprio.
     */
    public void adicionarAmigo(String login, String amigo) throws UsuarioNaoCadastradoException, ConvitePendenteException, UsuarioJaAdicionadoException, AutoAdicaoException {
        amizadeController.adicionarAmigo(login,amigo);
    }

    /**
     * Verifica se existe uma amizade entre dois usuários.
     *
     * @param login O login do usuário base.
     * @param amigo O login do possível amigo.
     * @return {@code true} se forem amigos, {@code false} caso contrário.
     */
    public boolean ehAmigo(String login, String amigo){
        return amizadeController.ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário formatada em string.
     *
     * @param login O login do usuário consultado.
     * @return Uma string contendo os amigos no formato {amigo1,amigo2}.
     * @throws UsuarioNaoCadastradoException Se o usuário não existir.
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException{
        return amizadeController.getAmigos(login);
    }

    /**
     * Envia um recado para a caixa de entrada de outro usuário.
     *
     * @param id           O id da sessão do remetente.
     * @param destinatario O login do usuário que receberá o recado.
     * @param recado       O texto da mensagem.
     * @throws UsuarioNaoCadastradoException Se a sessão ou o destinatário forem inválidos.
     * @throws AutoEnvioRecadoException      Se o usuário tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws UsuarioNaoCadastradoException, AutoEnvioRecadoException {
        recadoController.enviarRecado(id, destinatario, recado);
    }

    /**
     * Lê e remove o recado mais antigo da fila do usuário logado.
     *
     * @param id O id da sessão do usuário.
     * @return O texto do recado lido.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     * @throws NaoHaRecadosException         Se a fila de recados estiver vazia.
     */
    public String lerRecado(String id) throws UsuarioNaoCadastradoException, NaoHaRecadosException{
        return recadoController.lerRecado(id);
    }

    /**
     * Delega a criação de uma nova comunidade no sistema.
     *
     * @param idSessao  O identificador da sessão ativa do usuário criador.
     * @param nome      O nome desejado para a comunidade.
     * @param descricao A descrição da comunidade.
     * @throws UsuarioNaoCadastradoException Se a sessão do usuário for inválida.
     * @throws ComunidadeJaExisteException   Se já existir uma comunidade com o mesmo nome.
     */
    public void criarComunidade(String idSessao, String nome, String descricao) throws UsuarioNaoCadastradoException, ComunidadeJaExisteException {
        comunidadeController.criarComunidade(idSessao,nome,descricao);
    }

    /**
     * Delega a busca pela descrição de uma comunidade específica.
     *
     * @param nome O nome da comunidade consultada.
     * @return O texto de descrição da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada no sistema.
     */
    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeController.getDescricaoComunidade(nome);
    }

    /**
     * Delega a busca pelo dono de uma comunidade específica.
     *
     * @param nome O nome da comunidade consultada.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada no sistema.
     */
    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeController.getDonoComunidade(nome);
    }

    /**
     * Delega a busca pela lista de membros de uma comunidade.
     *
     * @param nome O nome da comunidade consultada.
     * @return Uma {@code String} contendo os membros no formato "{membro1,membro2}".
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada no sistema.
     */
    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException {
        return comunidadeController.getMembrosComunidade(nome);
    }

    /**
     * Delega a adição de um usuário a uma comunidade existente.
     *
     * @param idSessao O identificador da sessão ativa do usuário.
     * @param nome     O nome da comunidade.
     * @throws UsuarioNaoCadastradoException  Se a sessão for inválida.
     * @throws ComunidadeNaoExisteException   Se a comunidade não for encontrada.
     * @throws UsuarioJaNaComunidadeException Se o usuário já pertencer à comunidade.
     */
    public void adicionarComunidade(String idSessao, String nome) throws UsuarioJaNaComunidadeException, UsuarioNaoCadastradoException, ComunidadeNaoExisteException {
        comunidadeController.adicionarComunidade(idSessao,nome);
    }

    /**
     * Delega a busca pelas comunidades das quais um usuário participa.
     *
     * @param login O login do usuário a ser consultado.
     * @return A string de comunidades formatada "{comunidade1,comunidade2}".
     * @throws UsuarioNaoCadastradoException Se o usuário não existir no sistema.
     */
    public String getComunidades(String login) throws UsuarioNaoCadastradoException{
        return comunidadeController.getComunidades(login);
    }

    /**
     * Delega o envio de uma mensagem para uma comunidade.
     *
     * @param idSessao       O identificador da sessão do remetente.
     * @param nomeComunidade O nome da comunidade destino.
     * @param mensagem       O texto da mensagem.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     * @throws ComunidadeNaoExisteException  Se a comunidade não for encontrada.
     */
    public void enviarMensagem(String idSessao, String nomeComunidade, String mensagem) throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException {
        mensagemController.enviarMensagem(idSessao,nomeComunidade,mensagem);
    }

    public String lerMensagem(String idSessao) throws NaoHaMensagensException, UsuarioNaoCadastradoException {
        return mensagemController.lerMensagem(idSessao);
    }

    /**
     * Método acionado pelo EasyAccept no final da execução dos testes.
     * Garante que todo o estado da memória seja persistido no arquivo físico.
     */
    public void encerrarSistema(){
        repo.salvarDados();
    }
}