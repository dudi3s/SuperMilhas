/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliares;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author eduar
 */
public class Cliente {

    private SimpleStringProperty nome = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleStringProperty senha = new SimpleStringProperty("");

    private ArrayList<Programa> programas;
    private boolean receberOfertas;

    public Cliente(String nome, String email, String senha) {
        this.nome.set(nome);
        this.email.set(email);
        this.senha.set(senha);
        this.programas = new ArrayList<>();
        this.receberOfertas = false;
    }

    public String getNome() {
        return this.nome.get();
    }

    public String getEmail() {
        return this.email.get();
    }

    public String getSenha() {
        return this.senha.get();
    }

    public ArrayList<Programa> getProgramas() {
        return this.programas;
    }

    public boolean isRecebeOfertas() {
        return this.receberOfertas;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public void setProgramas(ArrayList<Programa> programas) {
        this.programas = programas;
    }

    public void setReceberOferta(boolean value) {
        this.receberOfertas = value;
    }

    public void setPrograma(Programa p) {
        this.programas.add(p);
    }

    @Override
    public String toString() {
        return "Cliente[" + "nome=" + nome.get() + ", email=" + email.get() + ", senha=" + senha.get() + ", receberOfertas=" + receberOfertas + ", programas=" + programas + ']';
    }

}
