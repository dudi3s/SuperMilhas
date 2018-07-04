/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import auxiliares.Cliente;
import auxiliares.Programa;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author eduar
 */
public class GerenciamentoProgramasController implements Initializable {

    public static Cliente c;
    public static Programa p;
    public static Stage thisStage;

    @FXML
    private Button BT_Adicionar, BT_Chat;

    @FXML
    public Button BT_Amigo, BT_Azul, BT_Multiplus, BT_Smiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ouveBotaoAdicionar();
        ouveBotaoAmigo();
        ouveBotaoAzul();
        ouveBotaoMultiplus();
        ouveBotaoSmiles();
        ouveBotaoChat();
    }

    public void ouveBotaoChat() {
        BT_Chat.setOnAction(new EventHandler<ActionEvent>() {
            boolean confirm = true;

            @Override
            public void handle(ActionEvent e) {
                try {
                    URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/Chat_Cliente.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location1);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root1 = fxmlLoader.load(location1.openStream());

                    Chat_ClienteController chat_cli = (Chat_ClienteController) fxmlLoader.getController();
                    supermilhas.SuperMilhas.setControllerCliente(chat_cli);

                    Stage stage = new Stage();
                    stage.setTitle("SuperMilhas");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    private void ouveBotaoAdicionar() {
        BT_Adicionar.setOnAction(new EventHandler<ActionEvent>() {
            boolean confirm = true;

            @Override
            public void handle(ActionEvent e) {
                try {
                    URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/CadastrarProgramas.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location1);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root1 = fxmlLoader.load(location1.openStream());

                    CadastrarProgramasController ctrl1 = (CadastrarProgramasController) fxmlLoader.getController();
                    ctrl1.carregaCliente(c);

                    Stage stage = new Stage();
                    stage.setTitle("SuperMilhas");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    public void ouveBotaoAmigo() {
        BT_Amigo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (Programa p : c.getProgramas()) {
                    if (p.getNome().equals("AMIGO")) {
                        loadProgramaInfo(p);
                    }
                    break;
                }
            }
        });
    }

    public void ouveBotaoAzul() {
        BT_Azul.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (Programa p : c.getProgramas()) {
                    if (p.getNome().equals("AZUL")) {
                        loadProgramaInfo(p);
                    }
                    break;
                }
            }
        });
    }

    public void ouveBotaoSmiles() {
        BT_Smiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (Programa p : c.getProgramas()) {
                    if (p.getNome().equals("SMILES")) {
                        loadProgramaInfo(p);
                    }
                    break;
                }
            }
        });
    }

    public void ouveBotaoMultiplus() {
        BT_Multiplus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                for (Programa p : c.getProgramas()) {
                    if (p.getNome().equals("MULTIPLUS")) {
                        loadProgramaInfo(p);
                    }
                    break;
                }
            }
        });
    }

    public void loadProgramaInfo(Programa p) {
        this.p = p;

        try {
            URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/InfoPrograma.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location1);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root1 = fxmlLoader.load(location1.openStream());

            Stage stage = new Stage();
            stage.setTitle("SuperMilhas");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GerenciamentoProgramasController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setBotaoAmigo() {
        this.BT_Amigo.setVisible(true);

    }

    public void setBotaoAzul() {
        this.BT_Azul.setVisible(true);
    }

    public void setBotaoSmiles() {
        this.BT_Smiles.setVisible(true);
    }

    public void setBotaoMultiplus() {
        this.BT_Multiplus.setVisible(true);
    }

    public void carregaCliente(Cliente c) {
        this.c = c;
    }
}
