/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliares;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author eduar
 */
public class Vencimento_Geral {

    private SimpleStringProperty nome = new SimpleStringProperty("");
    private SimpleStringProperty programa = new SimpleStringProperty("");
    private SimpleStringProperty data = new SimpleStringProperty("");
    private SimpleStringProperty qtd = new SimpleStringProperty("");

    public Vencimento_Geral(String nome, String programa, String data, String qtd) {
        this.nome.set(nome);
        this.programa.set(programa);
        this.data.set(data);
        this.qtd.set(qtd);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getPrograma() {
        return programa.get();
    }

    public void setPrograma(String programa) {
        this.programa.set(programa);
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getQtd() {
        return qtd.get();
    }

    public void setQtd(String qtd) {
        this.qtd.set(qtd);
    }

    @Override
    public String toString() {
        return "Vencimento_Geral[" + "nome_cliente=" + nome.get() + ", nome_programa=" + programa.get() + ", data=" + data.get() + ", qtd_milhas=" + qtd.get() + ']';
    }
}
