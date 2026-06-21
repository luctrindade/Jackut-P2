package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.InimigoException;
import br.ufal.ic.p2.jackut.exceptions.RelacionamentoException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.models.relacionamentos.RelacionamentoFactory;
import br.ufal.ic.p2.jackut.models.relacionamentos.RelacionamentoStrategy;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

import java.util.List;

/**
 * Controlador responsável por orquestrar a criação e a consulta de vínculos
 * entre os usuários do sistema Jackut.
 * <p>
 * Centraliza as regras para amizades, fãs, paqueras e inimizades, utilizando
 * o padrão de projeto Strategy para delegar comportamentos específicos sem inflar
 * as entidades principais.
 * </p>
 */
public class RelacionamentoController {

    /** Referência ao repositório central (Singleton) de acesso aos dados em memória. */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Construtor padrão do controlador de relacionamentos.
     */
    public RelacionamentoController() {
    }

    /**
     * Método utilitário privado que concentra as validações comuns a todos os relacionamentos
     * antes de acionar a Factory e delegar a execução para a estratégia correspondente.
     *
     * @param idSessao   O identificador da sessão ativa do usuário remetente.
     * @param loginAlvo  O login do usuário alvo do relacionamento.
     * @param tipo       O tipo de vínculo desejado ("idolo", "paquera", "inimigo").
     * @throws UsuarioNaoCadastradoException Se a sessão do remetente for inválida ou o alvo não existir.
     * @throws RelacionamentoException       Se houver tentativa de auto-relacionamento ou se o vínculo já existir.
     * @throws InimigoException              Se o usuário alvo tiver declarado o remetente como inimigo.
     */
    private void estabelecerRelacionamento(String idSessao, String loginAlvo, String tipo) throws UsuarioNaoCadastradoException, RelacionamentoException, InimigoException {
        String loginRemetente = repo.buscarLoginSessao(idSessao);
        if (loginRemetente == null) throw new UsuarioNaoCadastradoException();

        Usuario remetente = repo.buscarUsuario(loginRemetente);
        Usuario alvo = repo.buscarUsuario(loginAlvo);

        if (alvo == null) throw new UsuarioNaoCadastradoException();

        if (remetente.getLogin().equals(alvo.getLogin())) {
            throw new RelacionamentoException("Usuário não pode ser " + (tipo.equals("idolo") ? "fã" : tipo) + " de si mesmo.");
        }

        boolean jaExiste = false;
        if (tipo.equals("idolo")) jaExiste = remetente.temIdolo(alvo.getLogin());
        if (tipo.equals("paquera")) jaExiste = remetente.ehPaquera(alvo.getLogin());
        if (tipo.equals("inimigo")) jaExiste = remetente.ehInimigo(alvo.getLogin());

        if (jaExiste) {
            String tipoF = tipo.equals("idolo") ? "ídolo" : tipo;
            throw new RelacionamentoException("Usuário já está adicionado como " + tipoF + ".");
        }

        if (alvo.ehInimigo(remetente.getLogin())) {
            throw new InimigoException(alvo.getNome());
        }

        RelacionamentoStrategy estrategia = RelacionamentoFactory.criar(tipo);
        remetente.estabelecerRelacionamento(alvo, estrategia);
    }


    /**
     * Adiciona um usuário à lista de ídolos do usuário logado.
     *
     * @param idSessao O identificador da sessão ativa.
     * @param idolo    O login do usuário que será marcado como ídolo.
     * @throws RelacionamentoException       Se tentar adicionar a si mesmo ou se o ídolo já estiver na lista.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o ídolo não existir.
     * @throws InimigoException              Se o ídolo tiver bloqueado o remetente como inimigo.
     */
    public void adicionarIdolo(String idSessao, String idolo) throws RelacionamentoException, UsuarioNaoCadastradoException, InimigoException {
        estabelecerRelacionamento(idSessao, idolo, "idolo");
    }

    /**
     * Adiciona um usuário à lista privada de paqueras do usuário logado.
     * Caso o 'sentimento' seja recíproco, um recado automático é disparado.
     *
     * @param idSessao O identificador da sessão ativa.
     * @param paquera  O login do usuário alvo da paquera.
     * @throws RelacionamentoException       Se tentar paquerar a si mesmo ou se já estiver na lista.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o alvo não existir.
     * @throws InimigoException              Se o alvo tiver bloqueado o remetente como inimigo.
     */
    public void adicionarPaquera(String idSessao, String paquera) throws RelacionamentoException, UsuarioNaoCadastradoException, InimigoException {
        estabelecerRelacionamento(idSessao, paquera, "paquera");
    }

    /**
     * Declara um usuário como inimigo, bloqueando futuras interações dele
     * direcionadas ao usuário logado.
     *
     * @param idSessao O identificador da sessão ativa.
     * @param inimigo  O login do usuário que será declarado inimigo.
     * @throws RelacionamentoException       Se tentar declarar a si mesmo como inimigo ou se já estiver na lista.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o alvo não existir.
     * @throws InimigoException              Se o alvo já tiver declarado o remetente como inimigo mutuamente.
     */
    public void adicionarInimigo(String idSessao, String inimigo) throws RelacionamentoException, UsuarioNaoCadastradoException, InimigoException {
        estabelecerRelacionamento(idSessao, inimigo, "inimigo");
    }

    /**
     * Verifica se um usuário possui determinado ídolo em sua lista.
     *
     * @param login O login do usuário 'fã' a ser consultado.
     * @param idolo O login do ídolo procurado.
     * @return {@code true} se o usuário for fã do ídolo informado, {@code false} caso contrário.
     * @throws UsuarioNaoCadastradoException Se o usuário consultado não for encontrado.
     */
    public boolean ehFa(String login, String idolo) throws UsuarioNaoCadastradoException {
        Usuario usuario = repo.buscarUsuario(login);
        if (usuario == null) throw new UsuarioNaoCadastradoException();

        return usuario.temIdolo(idolo);
    }

    /**
     * Recupera a lista de todos os fãs de um determinado usuário, formatada em texto.
     *
     * @param login O login do usuário consultado.
     * @return Uma {@code String} contendo a lista de fãs.
     * @throws UsuarioNaoCadastradoException Se o usuário não existir no sistema.
     */
    public String getFas(String login) throws UsuarioNaoCadastradoException {
        Usuario usuario = repo.buscarUsuario(login);
        if (usuario == null) throw new UsuarioNaoCadastradoException();

        List<String> fas = usuario.getFas();
        return "{" + String.join(",", fas) + "}";
    }

    /**
     * Verifica de forma autenticada se o usuário logado paquera um determinado login.
     *
     * @param idSessao O identificador da sessão ativa do usuário consultor.
     * @param paquera  O login do usuário procurado na lista de paqueras.
     * @return {@code true} se a paquera existir, {@code false} caso contrário.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     */
    public boolean ehPaquera(String idSessao, String paquera) throws UsuarioNaoCadastradoException {
        String login = repo.buscarLoginSessao(idSessao);
        if (login == null) throw new UsuarioNaoCadastradoException();

        Usuario usuario = repo.buscarUsuario(login);
        return usuario.ehPaquera(paquera);
    }

    /**
     * Recupera a lista privada de paqueras do usuário autenticado.
     *
     * @param idSessao O identificador da sessão ativa do usuário.
     * @return Uma {@code String} contendo a lista de paqueras.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     */
    public String getPaqueras(String idSessao) throws UsuarioNaoCadastradoException {
        String login = repo.buscarLoginSessao(idSessao);
        if (login == null) throw new UsuarioNaoCadastradoException();

        Usuario usuario = repo.buscarUsuario(login);

        List<String> paqueras = usuario.getPaqueras();
        return "{" + String.join(",", paqueras) + "}";
    }
}
