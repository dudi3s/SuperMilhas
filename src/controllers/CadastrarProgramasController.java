package controllers;

import auxiliares.Cliente;
import auxiliares.Data;
import auxiliares.Programa;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarProgramasController implements Initializable {

    private Cliente c;

    @FXML
    private ComboBox<String> CB_Programas;

    @FXML
    private TextField TF_Usuario, TF_Senha;

    @FXML
    private Button BT_Finalizar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String[] programas = {"SMILES", "AZUL", "AMIGO", "MULTIPLUS"};
        CB_Programas.getItems().addAll(programas);

        BT_Finalizar.setOnAction(new EventHandler<ActionEvent>() {
            boolean confirm = true;

            @Override
            public void handle(ActionEvent e) {
                Random gerador = new Random();
                String nomePrograma = CB_Programas.getValue().toUpperCase();
                String usuario = TF_Usuario.getText();
                String senha = TF_Senha.getText();

                if (nomePrograma.equals("") || usuario.equals("") || senha.equals("")) {
                    confirm = false;
                    //Mensagem de erro
                }

                if (confirm) {
                    HashMap<Data, Integer> vencimento_qtdMilhas = new HashMap<>();
                    //Quantos vencimentos um plano terá (até 5 datas de vencimento);
                    int numVenc = gerador.nextInt(20) + 1;
                    for (int j = 0; j < numVenc; j++) {

                        //Quantidade de Milhas;
                        int milhas = gerador.nextInt(10) + 1;

                        //Gera o mês;
                        //int mes = gerador.nextInt(12) + 1;
                        int mes = (int) Math.ceil(Math.random() * (12 - 7 + 1)) - 1 + 7;

                        //Gera o dia;
                        int dia = 0;

                        //Verificação de meses com 30 e 31 dias;
                        if (mes == 2) {
                            dia = gerador.nextInt(28) + 1;
                        } else if (mes == 1 || mes == 3 | mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                            dia = gerador.nextInt(31) + 1;
                        } else {
                            dia = gerador.nextInt(30) + 1;
                        }

                        //Data correspondente ao vencimento gerado;
                        Data d = new Data(dia, mes, 2018);

                        //Adição do vencimento e a qtd de milhas respectivas ao programa;
                        vencimento_qtdMilhas.put(d, milhas);
                    }

                    Programa p = new Programa(nomePrograma, senha, vencimento_qtdMilhas, usuario);
                    c.setPrograma(p);

                    Stage essaStage = (Stage) BT_Finalizar.getScene().getWindow();
                    essaStage.close();

                }
            }
        });
    }

    public void carregaCliente(Cliente c) {
        this.c = c;
    }
}
