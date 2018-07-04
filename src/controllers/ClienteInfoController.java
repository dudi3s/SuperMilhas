package controllers;

import auxiliares.Cliente;
import auxiliares.Data;
import auxiliares.Programa;
import auxiliares.Vencimento_Cliente;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ClienteInfoController implements Initializable {

    @FXML
    private Label LB_nome, LB_email, LB_Total_Milhas, LB_Senha_Programa, LB_Usuario_Programa;

    @FXML
    private ComboBox<String> CB_Cliente_Programas;

    @FXML
    private ImageView IV_Imagem_Programa, IV_Oferta;

    @FXML
    private TableView<Vencimento_Cliente> TV_Programas_Venc;

    @FXML
    private TableColumn<Vencimento_Cliente, String> TV_Programas_Venc_Data, TV_Programas_Venc_Qtd;

    @FXML
    private Button BT_Chat;

    @FXML
    private StackedBarChart<String, Integer> BC_Comportamento_Milhas;

    @FXML
    private CategoryAxis BC_CatA;

    @FXML
    private NumberAxis BC_NumA;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ouveBotaoChat();
    }

    public void carregaCliente(Cliente c) {

        TV_Programas_Venc_Data.setStyle("-fx-alignment: CENTER;");
        TV_Programas_Venc_Qtd.setStyle("-fx-alignment: CENTER;");

        TV_Programas_Venc_Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        TV_Programas_Venc_Qtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        LB_nome.setText(c.getNome());
        LB_email.setText(c.getEmail());

        if (c.isRecebeOfertas()) {
            IV_Oferta.setVisible(true);
        } else {
            IV_Oferta.setVisible(false);
        }

        for (Programa p : c.getProgramas()) {
            CB_Cliente_Programas.getItems().add(p.getNome());
        }

        CB_Cliente_Programas.setOnAction((event) -> {
            TV_Programas_Venc.getItems().clear();
            String programa = CB_Cliente_Programas.getSelectionModel().getSelectedItem();
            switch (programa) {
                case "AZUL":
                    IV_Imagem_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/azul_logo.png")));
                    break;
                case "AMIGO":
                    IV_Imagem_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/amigo_logo.png")));
                    break;
                case "MULTIPLUS":
                    IV_Imagem_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/multiplus_logo.png")));
                    break;
                case "SMILES":
                    IV_Imagem_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/smiles_logo.png")));
                default:
                    break;
            }

            for (Programa p : c.getProgramas()) {
                int totalMilhas = 0;
                if (p.getNome().equals(programa)) {
                    LB_Usuario_Programa.setText("Usuário: " + p.getUsuario());
                    LB_Senha_Programa.setText("Senha: " + p.getSenha());
                    for (Entry<Data, Integer> dQ : p.getVencimentos().entrySet()) {
                        Vencimento_Cliente vu = new Vencimento_Cliente(dQ.getKey().toString(), dQ.getValue().toString());
                        TV_Programas_Venc.getItems().add(vu);
                        totalMilhas += dQ.getValue();
                    }
                    LB_Total_Milhas.setText("Total de Milhas: " + String.valueOf(totalMilhas));
                    break;
                }
            }
        });

        inicializaGrafico(c);
    }

    private void inicializaGrafico(Cliente c) {
        BC_CatA.setLabel("Mês");
        BC_NumA.setLabel("Total Milhas (Programa)");
        BC_Comportamento_Milhas.setTitle("Total de Milhas a vencer por Mês");

        final XYChart.Series<String, Integer> amigo = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> multiplus = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> smiles = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> azul = new XYChart.Series<String, Integer>();

        amigo.setName("AMIGO");
        multiplus.setName("MULTIPLUS");
        smiles.setName("SMILES");
        azul.setName("AZUL");

        for (int mes = 1; mes <= 12; mes++) {
            String wMes = "";
            switch (mes) {
                case 1:
                    wMes = "Jan";
                    break;
                case 2:
                    wMes = "Fev";
                    break;
                case 3:
                    wMes = "Mar";
                    break;
                case 4:
                    wMes = "Abr";
                    break;

                case 5:
                    wMes = "Mai";
                    break;
                case 6:
                    wMes = "Jun";
                    break;
                case 7:
                    wMes = "Jul";
                    break;
                case 8:
                    wMes = "Ago";
                    break;
                case 9:
                    wMes = "Set";
                    break;
                case 10:
                    wMes = "Ou";
                    break;
                case 11:
                    wMes = "Nov";
                    break;
                case 12:
                    wMes = "Dez";
                    break;
            }

            azul.getData().add(new XYChart.Data<String, Integer>(wMes, totalMilhasMes("AZUL", c.getProgramas(), mes)));
            amigo.getData().add(new XYChart.Data<String, Integer>(wMes, totalMilhasMes("AMIGO", c.getProgramas(), mes)));
            multiplus.getData().add(new XYChart.Data<String, Integer>(wMes, totalMilhasMes("MULTIPLUS", c.getProgramas(), mes)));
            smiles.getData().add(new XYChart.Data<String, Integer>(wMes, totalMilhasMes("SMILES", c.getProgramas(), mes)));
        }

        BC_Comportamento_Milhas.getData().addAll(azul, amigo, multiplus, smiles);
    }

    private Integer totalMilhasMes(String programa, ArrayList<Programa> cli_progs, int mes) {
        int total = 0;

        for (Programa p : cli_progs) {
            if (p.getNome().equals(programa)) {
                for (Entry<Data, Integer> venc_qtd : p.getVencimentos().entrySet()) {

                    if (mes == venc_qtd.getKey().getMes()) {
                        total += venc_qtd.getValue();
                    }
                }
            }
        }

        return total;
    }

    public void ouveBotaoChat() {
        BT_Chat.setOnAction(new EventHandler<ActionEvent>() {
            boolean confirm = true;

            @Override
            public void handle(ActionEvent e) {
                try {
                    URL location1 = GerenciamentoController.class.getResource("/fxml/pc/Chat_Gerente.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location1);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root1 = fxmlLoader.load(location1.openStream());

                    Chat_GerenteController chat_ger = (Chat_GerenteController) fxmlLoader.getController();
                    supermilhas.SuperMilhas.setControllerGerente(chat_ger);
                    
                    Stage stage = new Stage();
                    stage.setTitle("SuperMilhas");
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                    stage.setScene(new Scene(root1));
                    stage.show();

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
    }
}
