package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.AutoEnvioRecadoException;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecadosException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Recado;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repositories.JackutRepository;

/**
 * Controlador responsável por orquestrar a lógica de envio e leitura de recados.
 * <p>
 * Intermedia a comunicação entre a Facade e os modelos, validando regras de
 * negócio antes de delegar a manipulação dos dados para a entidade Usuário.
 * </p>
 */
public class RecadoController {
    /**
     * Referência ao repositório central de dados do sistema.
     */
    private final JackutRepository repo = JackutRepository.getInstancia();

    /**
     * Envia um recado de um usuário logado para um destinatário especificado.
     *
     * @param idSessao O identificador único da sessão ativa do usuário remetente.
     * @param destLogin O login do usuário que receberá o recado.
     * @param recado O conteúdo em texto do recado a ser enviado.
     * @throws UsuarioNaoCadastradoException Se o remetente ou o destinatário não existirem no sistema.
     * @throws AutoEnvioRecadoException Se o usuário tentar enviar um recado para o seu próprio login.
     */
    public void enviarRecado(String idSessao, String destLogin, String recado) throws UsuarioNaoCadastradoException, AutoEnvioRecadoException{
        String remetLogin = repo.getSessoesAtivas().get(idSessao);

        if(remetLogin == null) {
            throw new UsuarioNaoCadastradoException();
        }
        if(remetLogin.equals(destLogin)){
            throw new AutoEnvioRecadoException();
        }

        Usuario dest = repo.getUsuarios().get(destLogin);
        if(dest == null) throw  new UsuarioNaoCadastradoException();
        Recado newRecado = new Recado(remetLogin,recado);
        dest.adicionarRecado(newRecado);
    }
    /**
     * Lê o recado mais antigo da caixa de entrada do usuário logado e o remove da fila.
     *
     * @param idSessao O identificador único da sessão ativa do usuário.
     * @return Uma {@code String} contendo o texto do recado lido.
     * @throws UsuarioNaoCadastradoException Se a sessão for inválida ou o usuário não existir.
     * @throws NaoHaRecadosException Se a fila de recados do usuário estiver vazia.
     */
    public String lerRecado(String idSessao) throws UsuarioNaoCadastradoException, NaoHaRecadosException{
        String login = repo.getSessoesAtivas().get(idSessao);
        if(login == null) throw new UsuarioNaoCadastradoException();

        Usuario usuario = repo.getUsuarios().get(login);
        if(usuario == null) throw new UsuarioNaoCadastradoException();
        return usuario.lerRecado();
    }
}
