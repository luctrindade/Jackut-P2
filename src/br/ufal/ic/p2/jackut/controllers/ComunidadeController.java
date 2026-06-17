package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.ComunidadeJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaNaComunidadeException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.List;

/**
 * Controlador responsável por gerenciar o ciclo de vida das comunidades.
 * <p>
 * Centraliza as operações de criação, consulta de descrições, donos e
 * membros das comunidades presentes no sistema.
 * </p>
 */
public class ComunidadeController {
    /** Referência ao repositório central. */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Cria uma nova comunidade no sistema e define o usuário logado como dono e primeiro membro.
     * O criador é automaticamente vinculado à comunidade em seu perfil.
     *
     * @param idSessao  O identificador da sessão ativa do usuário criador.
     * @param nome      O nome desejado para a comunidade (deve ser único).
     * @param descricao A descrição da comunidade.
     * @throws UsuarioNaoCadastradoException Se a sessão do usuário for inválida.
     * @throws ComunidadeJaExisteException   Se já existir uma comunidade com o mesmo nome.
     */
    public void criarComunidade(String idSessao, String nome, String descricao) throws UsuarioNaoCadastradoException, ComunidadeJaExisteException{
        String login = repo.buscarLoginSessao(idSessao);
        if(login == null) throw new UsuarioNaoCadastradoException();
        if(repo.existeComunidade(nome)) throw new ComunidadeJaExisteException();

        Comunidade comunidade = new Comunidade(nome,descricao,login);
        repo.adicionarComunidade(comunidade);

        Usuario usuario = repo.buscarUsuario(login);
        if(usuario != null){
            usuario.adicionarComunidade(nome);
        }
    }

    /**
     * Adiciona o usuário logado como membro de uma comunidade já existente.
     *
     * @param idSessao O identificador da sessão ativa do usuário.
     * @param nome     O nome da comunidade alvo.
     * @throws UsuarioNaoCadastradoException  Se a sessão for inválida ou o usuário não existir.
     * @throws ComunidadeNaoExisteException   Se a comunidade não for encontrada no sistema.
     * @throws UsuarioJaNaComunidadeException Se o usuário já fizer parte da comunidade.
     */
    public void adicionarComunidade(String idSessao, String nome) throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException, UsuarioJaNaComunidadeException {
        String login = repo.buscarLoginSessao(idSessao);
        if(login == null) throw new UsuarioNaoCadastradoException();

        Comunidade comunidade = getComunidadeGeral(nome);

        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        if(comunidade.getMembros().contains(login)){
            throw new UsuarioJaNaComunidadeException();
        }
        comunidade.adicionarMembro(login);
        usuario.adicionarComunidade(nome);
    }

    public String getComunidades(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        List<String> comunidades = usuario.getComunidades();
        return "{" + String.join(",",comunidades) + "}";
    }
    /**
     * Método geral de verificação da comunidade
     *
     * @param nome O nome da comunidade consultada.
     * @return a comunidade se ela existir.
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada.
     */
    private Comunidade getComunidadeGeral(String nome) throws ComunidadeNaoExisteException{
        Comunidade comunidade = repo.buscarComunidade(nome);
        if(comunidade == null) throw new ComunidadeNaoExisteException();
        return comunidade;
    }
    /**
     * Recupera a descrição de uma comunidade específica.
     *
     * @param nome O nome da comunidade consultada.
     * @return O texto de descrição da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada.
     */
    public String getDescricaoComunidade(String nome) throws ComunidadeNaoExisteException{
        Comunidade comunidade = getComunidadeGeral(nome);
        return comunidade.getDescricao();
    }

    /**
     * Recupera o login do dono de uma comunidade específica.
     *
     * @param nome O nome da comunidade consultada.
     * @return O login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada.
     */
    public String getDonoComunidade(String nome) throws ComunidadeNaoExisteException{
        Comunidade comunidade = getComunidadeGeral(nome);
        return comunidade.getDono();
    }

    /**
     * Recupera a lista de membros de uma comunidade e a formata como string.
     *
     * @param nome O nome da comunidade consultada.
     * @return Uma {@code String} contendo os membros no formato "{membro1,membro2}".
     * @throws ComunidadeNaoExisteException Se a comunidade não for encontrada.
     */
    public String getMembrosComunidade(String nome) throws ComunidadeNaoExisteException{
        Comunidade comunidade = getComunidadeGeral(nome);
        List<String> membros = comunidade.getMembros();
        return "{" + String.join(",",membros) + "}";
    }
}
