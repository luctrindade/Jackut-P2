package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.AmizadeController;
import br.ufal.ic.p2.jackut.controllers.AutenticacaoController;
import br.ufal.ic.p2.jackut.controllers.RecadoController;
import br.ufal.ic.p2.jackut.controllers.UsuarioController;
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
    private final JackutRepository repo = JackutRepository.getInstancia();
    private final RecadoController recadoController = new RecadoController();

    /**
     * Construtor da Facade.
     * Tenta carregar o estado anterior do sistema salvo em disco (se existir)
     * logo na inicialização.
     */
    public Facade() {
        repo.carregarDados();
    }

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
     */
    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException {
        usuarioController.criarUsuario(login, senha, nome);
    }

    /**
     * Consulta um atributo específico do perfil de um usuário.
     *
     * @param login    O login do usuário consultado.
     * @param atributo O nome do atributo desejado.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não existir.
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
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
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        return autenticacaoController.abrirSessao(login, senha);
    }

    /**
     * Envia um convite de amizade ou consolida uma amizade pendente.
     *
     * @param login O id da sessão do usuário remetente.
     * @param amigo O login do usuário destinatário.
     * @throws UsuarioNaoCadastradoException Se algum dos usuários não for encontrado.
     */
    public void adicionarAmigo(String login, String amigo) throws UsuarioNaoCadastradoException{
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
     * Método acionado pelo EasyAccept no final da execução dos testes.
     * Garante que todo o estado da memória seja persistido no arquivo físico.
     */
    public void encerrarSistema(){
        repo.salvarDados();
    }
}