package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.models.Usuario;

import java.util.HashMap;
import java.util.Map;

public class JackutRepository {
    private static JackutRepository instancia;

    private final Map<String, Usuario> usuarios;
    private final Map<String, String> sessoesAtivas;

    private JackutRepository(){
        this.usuarios = new HashMap<>();
        this.sessoesAtivas = new HashMap<>();
    }

    public static synchronized JackutRepository getInstancia(){
        if(instancia == null){
            instancia = new JackutRepository();
        }
        return instancia;
    }

    public void zerarSistema(){
        this.usuarios.clear();
        this.sessoesAtivas.clear();
    }

    public Map<String, Usuario> getUsuarios(){
        return usuarios;
    }

    public Map<String, String> getSessoesAtivas(){
        return sessoesAtivas;
    }
}
