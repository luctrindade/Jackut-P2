package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.exceptions.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Mensagem;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

/**
 * Controlador responsável por orquestrar a lógica de envio e leitura
 * de mensagens vinculadas às comunidades.
 */
public class MensagemController {

    /** Referência ao repositório central. */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Construtor padrão do controlador de mensagem.
     */
    public MensagemController(){
    }
    /**
     * Envia uma mensagem para uma comunidade. Todos os membros atuais da comunidade
     * recebem uma cópia desta mensagem em suas caixas de entrada.
     *
     * @param idSessao       O identificador da sessão ativa do usuário remetente.
     * @param nomeComunidade O nome da comunidade destino.
     * @param textoMensagem  O conteúdo da mensagem.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     * @throws ComunidadeNaoExisteException  Se a comunidade não for encontrada.
     */
    public void enviarMensagem(String idSessao, String nomeComunidade, String textoMensagem) throws UsuarioNaoCadastradoException, ComunidadeNaoExisteException {
        String remetente = repo.buscarLoginSessao(idSessao);
        if(remetente == null) throw new UsuarioNaoCadastradoException();
        Comunidade comunidade = repo.buscarComunidade(nomeComunidade);
        if(comunidade == null) throw new ComunidadeNaoExisteException();

        Mensagem novaMensagem = new Mensagem(textoMensagem);

        for (String loginMembro : comunidade.getMembros()){
            Usuario membro = repo.buscarUsuario(loginMembro);
            if(membro != null){
                membro.adicionarMensagem(novaMensagem);
            }
        }
    }

    /**
     * Lê a mensagem de comunidade mais antiga da caixa de entrada do usuário logado e a remove da fila.
     *
     * @param idSessao O identificador da sessão ativa do usuário.
     * @return O conteúdo em texto da mensagem lida.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida.
     * @throws NaoHaMensagensException       Se a fila de mensagens estiver vazia.
     */
    public String lerMensagem(String idSessao) throws UsuarioNaoCadastradoException, NaoHaMensagensException {
        String login = repo.buscarLoginSessao(idSessao);
        if(login == null) throw new UsuarioNaoCadastradoException();
        Usuario usuario = repo.buscarUsuario(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        return usuario.lerMensagem();
    }
}
