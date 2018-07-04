/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author Renata
 */
public class Chat_GerenteController implements Initializable {

    @FXML
    public Button BT_Enviar;

    @FXML
    public TextArea TA_Mensagem;

    @FXML
    private TextArea TA_Historico_Gerente;

    private static Chat_ClienteController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        liberado();

        BT_Enviar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(TA_Mensagem.getText().equals(""))) {
                    if (controller == null) {
                        controller = supermilhas.SuperMilhas.getControllerCliente();
                    }
                    String mensParaEnviar = TA_Mensagem.getText();
                    String historico = TA_Historico_Gerente.getText();
                    TA_Historico_Gerente.setText(historico + "\n Eu: " + mensParaEnviar);
                    controller.setMensagem(mensParaEnviar);
                    TA_Mensagem.setText("");
                }
            }
        });
    }

    public static void liberado() {
        controller = supermilhas.SuperMilhas.getControllerCliente();
        System.out.println(controller);
    }

    public void setMensagem(String mString) {
        String historico = TA_Historico_Gerente.getText();
        TA_Historico_Gerente.setText(historico + "\n Cliente: " + mString);
    }
}
