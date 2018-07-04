/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliares;

import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author eduar
 */
public class Programa {

    private SimpleStringProperty nome = new SimpleStringProperty("");
    private SimpleStringProperty senha = new SimpleStringProperty("");
    private SimpleStringProperty usuario = new SimpleStringProperty("");

    private HashMap<Data, Integer> vencimentos;

    public Programa(String nome, String senha, HashMap<Data, Integer> vencimentos, String usuario) {
        this.nome.set(nome);
        this.senha.set(senha);
        this.vencimentos = vencimentos;
        this.usuario.set(usuario);
    }

    public String getUsuario() {
        return this.usuario.get();
    }

    public String getNome() {
        return this.nome.get();
    }

    public String getSenha() {
        return this.senha.get();
    }

    public HashMap<Data, Integer> getVencimentos() {
        return this.vencimentos;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public void setVencitmentos(HashMap<Data, Integer> vencimentos) {
        this.vencimentos = vencimentos;
    }

    @Override
    public String toString() {
        return "Programa[" + "nome=" + nome.get() + ", senha=" + senha.get() + ", vencimentos=" + vencimentos + ']';
    }

}
