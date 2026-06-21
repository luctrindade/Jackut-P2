package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.List;

/**
 * Controlador responsável por orquestrar as regras de negócio relacionadas
 * aos vínculos de amizade do sistema Jackut.
 * Garante as validações necessárias para o envio de convites, aceitação e
 * verificação de relacionamentos entre usuários.
 */
public class AmizadeController {
    /**
     * Referência ao repositório central para acesso e persistência em memória
     * das sessões e dos usuários.
     */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Adiciona um amigo ou envia um convite de amizade.
     * Se o usuário destino já houver enviado um convite para o remetente, a amizade
     * é consolidada imediatamente. Caso contrário, um convite pendente é registrado.
     *
     * @param idSessao   O identificador único da sessão do usuário logado que está enviando a solicitação.
     * @param amigoLogin O login do usuário que se deseja adicionar como amigo.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o login do amigo não existir no sistema.
     * @throws AutoAdicaoException           Se o usuário tentar adicionar a si próprio.
     * @throws UsuarioJaAdicionadoException  Se os usuários já possuírem um vínculo de amizade.
     * @throws ConvitePendenteException      Se um convite já tiver sido enviado anteriormente para este usuário.
     */
    public void adicionarAmigo(String idSessao, String amigoLogin) throws UsuarioNaoCadastradoException, ConvitePendenteException, UsuarioJaAdicionadoException, AutoAdicaoException, InimigoException {
        String meuLogin = repo.buscarLoginSessao(idSessao);
        if(meuLogin == null) throw new UsuarioNaoCadastradoException();
        if (meuLogin.equals(amigoLogin)) throw new AutoAdicaoException();

        Usuario eu = repo.buscarUsuario(meuLogin);
        Usuario ele = repo.buscarUsuario(amigoLogin);

        if(ele == null) throw new UsuarioNaoCadastradoException();
        if(eu.ehAmigo(amigoLogin)) throw new UsuarioJaAdicionadoException();
        if(eu.jaEnviouConvitePara(amigoLogin)) throw new ConvitePendenteException();

        if (ele.ehInimigo(meuLogin)) {
            throw new InimigoException(ele.getNome());
        }

        if(ele.jaEnviouConvitePara(meuLogin)){
            eu.adicionarAmigo(amigoLogin);
            ele.adicionarAmigo(meuLogin);
            ele.removerConviteEnviado(meuLogin);
        }
        else{
            eu.enviarConvite(amigoLogin);
        }
    }

    /**
     * Verifica se existe um vínculo de amizade consolidado entre dois usuários.
     *
     * @param login      O login do usuário base da consulta.
     * @param amigoLogin O login do possível amigo a ser verificado.
     * @return {@code true} se a amizade existir; {@code false} caso a amizade não exista ou o usuário base seja nulo.
     */
    public boolean ehAmigo(String login, String amigoLogin){
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null) return false;
        return usuario.ehAmigo(amigoLogin);
    }

    /**
     * Recupera a lista de amigos de um usuário e a formata como uma string delimitada por chaves.
     *
     * @param login O login do usuário cujos amigos serão listados.
     * @return Uma {@code String} contendo os logins dos amigos no formato "{amigo1,amigo2}".
     * @throws UsuarioNaoCadastradoException Se o login informado não estiver cadastrado no sistema.
     */
    public String getAmigos(String login) throws UsuarioNaoCadastradoException{
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        List<String> listaAmigos = usuario.getAmigos();
        return "{" + String.join(",", listaAmigos) + "}";
    }
}
