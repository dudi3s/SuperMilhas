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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eduar
 */
public class DepositoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BT_Finalizar;

    @FXML
    private TextField TF_Nome, TF_Agencia, TF_Conta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BT_Finalizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String nome = TF_Nome.getText();
                String agencia = TF_Agencia.getText();
                String conta = TF_Conta.getText();

                if (nome.equals("") || agencia.equals("") || conta.equals("")) {
                    alertDialog("Campos vazios", Alert.AlertType.ERROR, "Algum campo não foi preenchido.", "Todos os campos são obrigatórios!");
                } else {
                    String mensagem = "Nome: " + nome + "\nAgência: " + agencia + "\nConta: " + conta;
                    alertDialog("Confirmação de Dados", Alert.AlertType.WARNING, "Os dados inseridos estão corretos?", mensagem);
                    Stage essaStage = (Stage) BT_Finalizar.getScene().getWindow();
                    essaStage.close();
                }
            }
        });
    }

    //Método de alerta de Mensagens;
    public void alertDialog(String title, Alert.AlertType alert, String header, String message) {
        Alert dialogoInfo = new Alert(alert);
        dialogoInfo.setTitle(title);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(message);
        dialogoInfo.showAndWait();
    }
}
