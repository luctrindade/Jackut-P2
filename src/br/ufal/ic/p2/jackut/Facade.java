package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.AmizadeController;
import br.ufal.ic.p2.jackut.controllers.AutenticacaoController;
import br.ufal.ic.p2.jackut.controllers.UsuarioController;
import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

public class Facade {
    private final UsuarioController usuarioController = new UsuarioController();
    private final AutenticacaoController autenticacaoController = new AutenticacaoController();
    private final AmizadeController amizadeController = new AmizadeController();
    private final JackutRepository repo = JackutRepository.getInstancia();
    public void zerarSistema(){
        repo.zerarSistema();
    }

    public void criarUsuario(String login, String senha, String nome) throws ContaJaExisteException {
        usuarioController.criarUsuario(login, senha, nome);
    }

    public String getAtributoUsuario(String login, String atributo) throws UsuarioNaoCadastradoException {
        return usuarioController.getAtributoUsuario(login,atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) throws UsuarioNaoCadastradoException{
        usuarioController.editarPerfil(id,atributo,valor);
    }

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        return autenticacaoController.abrirSessao(login, senha);
    }

    public void adicionarAmigo(String login, String amigo) throws UsuarioNaoCadastradoException{
        amizadeController.adicionarAmigo(login,amigo);
    }

    public boolean ehAmigo(String login, String amigo){
        return amizadeController.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) throws UsuarioNaoCadastradoException{
        return amizadeController.getAmigos(login);
    }

    public void encerrarSistema(){

    }
}
