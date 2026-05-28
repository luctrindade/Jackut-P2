package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.AutoAdicaoException;
import br.ufal.ic.p2.jackut.exceptions.ConvitePendenteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.List;

public class AmizadeController {
    private final JackutRepository repo = JackutRepository.getInstancia();

    public void adicionarAmigo(String idSessao, String amigoLogin) throws UsuarioNaoCadastradoException{
        String meuLogin = repo.getSessoesAtivas().get(idSessao);
        if(meuLogin == null) throw new UsuarioNaoCadastradoException();
        if (meuLogin.equals(amigoLogin)) throw new AutoAdicaoException();

        Usuario eu = repo.getUsuarios().get(meuLogin);
        Usuario ele = repo.getUsuarios().get(amigoLogin);

        if(ele == null) throw new UsuarioNaoCadastradoException();
        if(eu.ehAmigo(amigoLogin)) throw new UsuarioJaAdicionadoException();
        if(eu.jaEnviouConvitePara(amigoLogin)) throw new ConvitePendenteException();

        if(ele.jaEnviouConvitePara(meuLogin)){
            eu.adicionarAmigo(amigoLogin);
            ele.adicionarAmigo(meuLogin);
            ele.removerConviteEnviado(meuLogin);
        }
        else{
            eu.enviarConvite(amigoLogin);
        }
    }

    public boolean ehAmigo(String login, String amigoLogin){
        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null) return false;
        return usuario.ehAmigo(amigoLogin);
    }

    public String getAmigos(String login) throws UsuarioNaoCadastradoException{
        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        List<String> listaAmigos = usuario.getAmigos();
        return "{" + String.join(",", listaAmigos) + "}";
    }
}
