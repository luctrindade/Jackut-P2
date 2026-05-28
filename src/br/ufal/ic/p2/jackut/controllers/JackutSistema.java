package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//PADRAO SINGLETON
public class JackutSistema {
    private static JackutSistema instancia;
    private final Map<String, Usuario> usuarios;
    private final Map<String, String> sessoesAtivas;

    private JackutSistema(){
        this.usuarios = new HashMap<>();
        this.sessoesAtivas = new HashMap<>();
    }

    public static synchronized JackutSistema getInstancia(){
        if(instancia == null){
            instancia = new JackutSistema();
        }
        return instancia;
    }

    public void zerarSistema(){
        this.usuarios.clear();
        this.sessoesAtivas.clear();
    }

    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException{
        if(usuarios.containsKey(login)){
            throw new ContaJaExisteException();
        }
        Usuario novoUsuario = new Usuario(login,senha,nome);
        usuarios.put(login,novoUsuario);
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException{
        Usuario usuario = usuarios.get(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        return usuario.getAtributo(atributo);
    }

    public void editarPerfil(String idSessao, String atributo, String valor) throws UsuarioNaoCadastradoException{
        String login = sessoesAtivas.get(idSessao);
        if(login == null){
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = usuarios.get(login);
        if(usuario == null){
            throw new UsuarioNaoCadastradoException();
        }
        usuario.setAtributo(atributo,valor);
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException{
        Usuario usuario = usuarios.get(login);
        if(usuario == null || !usuario.autenticar(senha)){
            throw new LoginOuSenhaInvalidoException();
        }

        if(login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()){
            throw new LoginInvalidoException();
        }

        String idSessao = UUID.randomUUID().toString();
        sessoesAtivas.put(idSessao,login);
        return idSessao;
    }

    public void encerrarSistema(){

    }
}
