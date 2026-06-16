package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginOuSenhaInvalidoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.UUID;

/**
 * Controlador responsável por gerenciar o processo de autenticação de usuários
 * e a criação de sessões ativas no sistema Jackut.
 */
public class AutenticacaoController {

    /**
     * Referência ao repositório central para acesso aos dados de usuários cadastrados
     * e armazenamento das sessões ativas.
     */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Autentica um usuário no sistema utilizando seu login e senha.
     * Em caso de sucesso, gera um identificador único (UUID) para a sessão e a registra no sistema.
     *
     * @param login O login de acesso do usuário.
     * @param senha A senha correspondente à conta do usuário.
     * @return Uma {@code String} contendo o UUID gerado para a sessão ativa.
     * @throws LoginOuSenhaInvalidoException Se o usuário não for encontrado no repositório ou a senha estiver incorreta.
     * @throws LoginInvalidoException        Se as credenciais fornecidas (login ou senha) forem nulas ou estiverem vazias.
     */
    public String abrirSessao(String login, String senha) throws LoginOuSenhaInvalidoException, LoginInvalidoException {
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null || !usuario.autenticar(senha)){
            throw new LoginOuSenhaInvalidoException();
        }

        if(login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()){
            throw new LoginInvalidoException();
        }

        String idSessao = UUID.randomUUID().toString();
        repo.adicionarSessao(idSessao,login);
        return idSessao;
    }
}
