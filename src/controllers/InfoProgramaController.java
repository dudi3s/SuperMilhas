/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import auxiliares.Data;
import auxiliares.Programa;
import auxiliares.Vencimento_Cliente;
import auxiliares.Vencimento_Geral;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author eduar
 */
public class InfoProgramaController implements Initializable {

    private static Programa p;

    @FXML
    private ImageView IV_Programa;

    @FXML
    private Label LB_Usuario, LB_Senha, LB_Total;

    @FXML
    private TableView<Vencimento_Cliente> TB_Venc_Progama;

    @FXML
    private TableColumn<Vencimento_Cliente, String> TB_Venc_Progama_Vencimento, TB_Venc_Progama_Qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p = controllers.GerenciamentoProgramasController.p;
        inicializaInfo();
        inicializaListaVencimentos();
    }

    private void inicializaInfo() {
        switch (p.getNome()) {
            case "AMIGO":
                IV_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/amigo_logo.png")));
                break;
            case "AZUL":
                IV_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/azul_logo.png")));
                break;
            case "SMILES":
                IV_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/smiles_logo.png")));
                break;
            case "MULTIPLUS":
                IV_Programa.setImage(new Image(getClass().getResourceAsStream("/images/logos/multiplus_logo.png")));
                break;
            default:
                break;
        }

        LB_Usuario.setText("Usuário: " + p.getUsuario());
        LB_Senha.setText("Senha: " + p.getSenha());
        LB_Total.setText("Total de Milhas: " + totalMilhas(p.getVencimentos()));
    }

    private String totalMilhas(HashMap<Data, Integer> fonte) {
        int total = 0;
        for (Entry<Data, Integer> dv : fonte.entrySet()) {
            total += dv.getValue();
        }

        return String.valueOf(total);
    }

    public void inicializaListaVencimentos() {
        ArrayList<Vencimento_Cliente> vencimentos = new ArrayList<>();
        for (Entry<Data, Integer> dv : p.getVencimentos().entrySet()) {
            Vencimento_Cliente venc = new Vencimento_Cliente(dv.getKey().toString(), dv.getValue().toString());
            vencimentos.add(venc);
        }

        Collections.sort(vencimentos, new dataComparator());

        TB_Venc_Progama_Vencimento.setStyle("-fx-alignment: CENTER;");
        TB_Venc_Progama_Qtd.setStyle("-fx-alignment: CENTER;");

        TB_Venc_Progama_Vencimento.setCellValueFactory(new PropertyValueFactory<>("data"));
        TB_Venc_Progama_Qtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        TB_Venc_Progama.getItems().addAll(FXCollections.observableArrayList(vencimentos));
    }

    public void carregaPrograma(Programa p) {
        this.p = p;
    }

    //Método de comparação para datas;
    public class dataComparator implements Comparator<Vencimento_Cliente> {

        public int compare(Vencimento_Cliente t, Vencimento_Cliente t1) {
            String dataAString = t.getData();
            String dataBString = t1.getData();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            Date dataA;
            Date dataB;
            int c = 0;

            try {
                dataA = formato.parse(dataAString);
                dataB = formato.parse(dataBString);

                c = dataA.compareTo(dataB);
            } catch (ParseException ex) {
                System.out.println("Não foi possível converter data para compará-la");
            }

            return c;
        }

    }
}
