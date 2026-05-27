package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;

public class Usuario {
    private final String login;
    private String senha;
    private String nome;

    public Usuario(String login, String senha, String nome) throws LoginInvalidoException{
        if(login == null || login.trim().isEmpty()){
            throw new LoginInvalidoException();
        }
        if(senha == null || senha.trim().isEmpty()){
            throw new SenhaInvalidaException();
        }

        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public boolean autenticar(String senhaTentiva){
        return this.senha.equals(senhaTentiva);
    }
}
