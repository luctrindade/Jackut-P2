package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

/**
 * Controlador responsável pelo gerenciamento do ciclo de vida dos usuários no sistema Jackut.
 * <p>
 * Centraliza as operações de criação de novas contas, recuperação de informações
 * e edição dos atributos do perfil do usuário.
 * </p>
 */
public class UsuarioController {
    /**
     * Referência ao repositório central para acesso aos dados de usuários cadastrados
     * e armazenamento das sessões ativas.
     */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Construtor padrão do controlador do usuário.
     */
    public UsuarioController(){
    }

    /**
     * Cria uma nova conta de usuário no sistema e a persiste em memória.
     *
     * @param login O login de acesso único desejado para a nova conta.
     * @param senha A senha de autenticação do usuário.
     * @param nome  O nome de exibição do usuário.
     * @throws ContaJaExisteException Se o login fornecido já estiver registrado no sistema.
     * @throws SenhaInvalidaException Se a senha fornecida não atender aos critérios de segurança ou estiver vazia.
     * @throws LoginInvalidoException Se o login fornecido for nulo ou vazio.
     */
    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException, SenhaInvalidaException, LoginInvalidoException {
        if(repo.existeUsuario(login)){
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login,senha,nome);
        repo.adicionarUsuario(novoUsuario);
    }

    /**
     * Recupera o valor de um atributo específico do perfil de um usuário.
     *
     * @param login    O login do usuário cujos dados serão consultados.
     * @param atributo O nome do atributo desejado.
     * @return Uma {@code String} contendo o valor armazenado no atributo solicitado.
     * @throws UsuarioNaoCadastradoException  Se o login não corresponder a um usuário válido.
     * @throws AtributoNaoPreenchidoException Se o atributo solicitado não existir no perfil do usuário.
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException, AtributoNaoPreenchidoException {
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getAtributo(atributo);
    }

    /**
     * Atualiza ou adiciona um novo atributo ao perfil do usuário atualmente logado.
     *
     * @param idSessao O identificador único da sessão do usuário que está editando o perfil.
     * @param atributo O nome do atributo a ser modificado ou criado.
     * @param valor    O novo valor que será atribuído a este campo no perfil.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o usuário não existir.
     */
    public void editarPerfil(String idSessao, String atributo, String valor) throws UsuarioNaoCadastradoException{
        String login = repo.buscarLoginSessao(idSessao);
        if(login == null){
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        usuario.setAtributo(atributo,valor);
    }

    /**
     * Remove permanentemente a conta de um usuário do sistema.
     * Limpar relacionamentos, recados enviados
     * e destruir as comunidades geridas pelo usuário.
     *
     * @param idSessao O identificador da sessão ativa do usuário a ser deletado.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     */
    public void removerUsuario(String idSessao) throws UsuarioNaoCadastradoException {
        String login = repo.buscarLoginSessao(idSessao);
        if (login == null) throw new UsuarioNaoCadastradoException();

        for (Usuario outroUsuario : repo.getTodosUsuarios()) {
            if (!outroUsuario.getLogin().equals(login)) {
                outroUsuario.apagarRegistrosDe(login);
                outroUsuario.removerRecadosDe(login);
            }
        }

        java.util.List<Comunidade> todasComunidades =
                new java.util.ArrayList<>(repo.getTodasComunidades());

        for (Comunidade comunidade : todasComunidades) {
            if (comunidade.getDono().equals(login)) {
                for (String membroLogin : comunidade.getMembros()) {
                    br.ufal.ic.p2.jackut.models.Usuario membro = repo.buscarUsuario(membroLogin);
                    if (membro != null) {
                        membro.removerComunidade(comunidade.getNome());
                    }
                }
                repo.removerComunidade(comunidade.getNome());
            } else {
                comunidade.removerMembro(login);
            }
        }

        repo.removerUsuario(login);
    }
}
