package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;

import java.util.*;

public class Usuario {
    private final String login;
    private String senha;

    private final Map<String, String> perfil;
    private final Set<String> envioConvites;
    private final List<String> amigos;

    public Usuario(String login, String senha, String nome) throws LoginInvalidoException{
        if(login == null || login.trim().isEmpty()){
            throw new LoginInvalidoException();
        }
        if(senha == null || senha.trim().isEmpty()){
            throw new SenhaInvalidaException();
        }

        this.login = login;
        this.senha = senha;
        this.perfil = new HashMap<>();
        this.perfil.put("nome", nome);
        this.envioConvites = new HashSet<>();
        this.amigos = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public boolean autenticar(String senhaTentiva){
        return this.senha.equals(senhaTentiva);
    }

    public void setAtributo(String atributo, String valor){
        this.perfil.put(atributo,valor);
    }

    public String getAtributo(String atributo) throws AtributoNaoPreenchidoException{
        if(!this.perfil.containsKey(atributo)){
            throw new AtributoNaoPreenchidoException();
        }
        return this.perfil.get(atributo);
    }

    public boolean ehAmigo(String amigoLogin){
        return this.amigos.contains(amigoLogin);
    }

    public boolean jaEnviouConvitePara(String amigoLogin){
        return this.envioConvites.contains(amigoLogin);
    }

    public void enviarConvite(String amigoLogin){
        this.envioConvites.add(amigoLogin);
    }

    public void removerConviteEnviado(String amigoLogin){
        this.envioConvites.remove(amigoLogin);
    }

    public void adicionarAmigo(String amigoLogin){
        this.amigos.add(amigoLogin);
    }

    public List<String> getAmigos(){
        return Collections.unmodifiableList(this.amigos);
    }
}
