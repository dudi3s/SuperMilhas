package controllers;

import java.net.URL;
import java.util.ResourceBundle;
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

public class LoginClienteController implements Initializable {

    @FXML
    private Button BT_Cadastro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BT_Cadastro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    URL location1 = GerenciamentoController.class.getResource("/fxml/mobile/CadastroCliente.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location1);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root1 = fxmlLoader.load(location1.openStream());

                    Stage stage = new Stage();
                    stage.setTitle("SuperMilhas");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                    stage.setScene(new Scene(root1));
                    stage.show();
                    stage.setResizable(false);

                    Stage actualStage = (Stage) BT_Cadastro.getScene().getWindow();
                    actualStage.close();

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }
}
