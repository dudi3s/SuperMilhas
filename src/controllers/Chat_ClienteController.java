/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Renata
 */
public class Chat_ClienteController implements Initializable {

    private static Chat_GerenteController controller;

    @FXML
    private Button BT_Enviar, BT_Deposito;

    @FXML
    private TextArea TA_Mensagem;

    @FXML
    private TextArea TA_Historico_Cliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.controller = supermilhas.SuperMilhas.getControllerGerente();
        controller.liberado();

        BT_Enviar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(TA_Mensagem.getText().equals(""))) {
                    String mensParaEnviar = TA_Mensagem.getText();
                    String historico = TA_Historico_Cliente.getText();
                    TA_Historico_Cliente.setText(historico + "\n Eu: " + mensParaEnviar);
                    controller.setMensagem(mensParaEnviar);
                    TA_Mensagem.setText("");
                }
            }
        });

        BT_Deposito.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/Deposito.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location1);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root1 = fxmlLoader.load(location1.openStream());

                    Stage stage = new Stage();
                    stage.setTitle("SuperMilhas");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Chat_ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void setMensagem(String mString) {
        String historico = TA_Historico_Cliente.getText();
        TA_Historico_Cliente.setText(historico + "\n Tanara: " + mString);
    }
}
