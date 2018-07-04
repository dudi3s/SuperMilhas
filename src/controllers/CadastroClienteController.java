/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import auxiliares.Cliente;
import auxiliares.Programa;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author eduar
 */
public class CadastroClienteController implements Initializable {

    private ArrayList<Cliente> clientes;

    @FXML
    private TextField TF_Nome, TF_Email, TF_Senha;

    @FXML
    private Button BT_Finalizar;

    @FXML
    private CheckBox CB_Ofertas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientes = controllers.GerenciamentoController.clientes;

        BT_Finalizar.setOnAction(new EventHandler<ActionEvent>() {
            boolean confirm = true;

            @Override
            public void handle(ActionEvent e) {
                String nome = TF_Nome.getText();
                String email = TF_Email.getText();
                String senha = TF_Senha.getText();

                boolean ofertas = CB_Ofertas.isSelected();

                if (nome.equals("") || email.equals("") || senha.equals("")) {
                    confirm = false;
                    alertDialog("Todos os campos são Obrigatórios", Alert.AlertType.WARNING, "Campos não preenchidos", "Preencha todos os campos.");
                }

                if (confirm) {
                    Cliente c = new Cliente(nome, email, senha);
                    c.setReceberOferta(ofertas);
                    clientes.add(c);

                    try {
                        URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/GerenciamentoProgramas.fxml");
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(location1);
                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                        Parent root1 = fxmlLoader.load(location1.openStream());

                        GerenciamentoProgramasController ctrl1 = (GerenciamentoProgramasController) fxmlLoader.getController();
                        ctrl1.carregaCliente(c);

                        Stage stage = new Stage();
                        stage.setTitle("Informação de Programas");
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                        stage.setScene(new Scene(root1));
                        stage.show();

                        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                if (newValue) {
                                    for (Programa p : c.getProgramas()) {
                                        switch (p.getNome()) {
                                            case "SMILES":
                                                ctrl1.setBotaoSmiles();
                                                break;
                                            case "AZUL":
                                                ctrl1.setBotaoAzul();
                                                break;
                                            case "AMIGO":
                                                ctrl1.setBotaoAmigo();
                                                break;
                                            case "MULTIPLUS":
                                                ctrl1.setBotaoMultiplus();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        });

                        Stage stageActual = (Stage) BT_Finalizar.getScene().getWindow();
                        stageActual.close();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
    }

    public ArrayList<Cliente> getClientes() {
        return this.clientes;
    }

    //Métodos Auxiliares para o gerenciamento
    //Método de alerta de Mensagens;
    public void alertDialog(String title, Alert.AlertType alert, String header, String message) {
        Alert dialogoInfo = new Alert(alert);
        dialogoInfo.setTitle(title);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(message);
        dialogoInfo.showAndWait();
    }
}
