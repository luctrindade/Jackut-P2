package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.UUID;

public class AutenticacaoController {
    private final JackutRepository repo = JackutRepository.getInstancia();

    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException {
        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null || !usuario.autenticar(senha)){
            throw new LoginOuSenhaInvalidoException();
        }

        if(login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()){
            throw new LoginInvalidoException();
        }

        String idSessao = UUID.randomUUID().toString();
        repo.getSessoesAtivas().put(idSessao,login);
        return idSessao;
    }
}
