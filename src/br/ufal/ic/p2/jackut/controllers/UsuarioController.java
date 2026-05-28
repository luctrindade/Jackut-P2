package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

public class UsuarioController {
    private final JackutRepository repo = JackutRepository.getInstancia();

    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException {
        if(repo.getUsuarios().containsKey(login)){
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login,senha,nome);
        repo.getUsuarios().put(login,novoUsuario);
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getAtributo(atributo);
    }

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
