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
public class Vencimento_Cliente {

    private SimpleStringProperty data = new SimpleStringProperty("");
    private SimpleStringProperty qtd = new SimpleStringProperty("");

    public Vencimento_Cliente(String data, String qtd_milhas) {
        this.data.set(data);
        this.qtd.set(qtd_milhas);
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

    public void setQtd(String qtd_milhas) {
        this.qtd.set(qtd_milhas);
    }

    @Override
    public String toString() {
        return "Vencimento_Cliente[" + "vencimento=" + data.get() + ", qtd_milhas=" + qtd.get() + ']';
    }

}
