package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
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
     * Cria uma nova conta de usuário no sistema e a persiste em memória.
     *
     * @param login O login de acesso único desejado para a nova conta.
     * @param senha A senha de autenticação do usuário.
     * @param nome  O nome de exibição do usuário.
     * @throws ContaJaExisteException Se o login fornecido já estiver registrado no sistema.
     */
    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException {
        if(repo.getUsuarios().containsKey(login)){
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login,senha,nome);
        repo.getUsuarios().put(login,novoUsuario);
    }

    /**
     * Recupera o valor de um atributo específico do perfil de um usuário.
     *
     * @param login    O login do usuário cujos dados serão consultados.
     * @param atributo O nome do atributo desejado.
     * @return Uma {@code String} contendo o valor armazenado no atributo solicitado.
     * @throws UsuarioNaoCadastradoException  Se o login não corresponder a um usuário válido.
     */
    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = repo.getUsuarios().get(login);
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
        String login = repo.getSessoesAtivas().get(idSessao);
        if(login == null){
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        usuario.setAtributo(atributo,valor);
    }
}
