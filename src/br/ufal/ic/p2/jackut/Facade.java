package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.JackutSistema;
import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;

public class Facade {
    private final JackutSistema sistema = JackutSistema.getInstancia();

    public void zerarSistema(){
        sistema.zerarSistema();
    }

    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException {
        sistema.criarUsuario(login, senha, nome);
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        return sistema.getAtributoUsuario(login,atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) throws UsuarioNaoCadastradoException{
        sistema.editarPerfil(id,atributo,valor);
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        return sistema.abrirSessao(login, senha);
    }

    public void encerrarSistema(){
        sistema.encerrarSistema();
    }
}
