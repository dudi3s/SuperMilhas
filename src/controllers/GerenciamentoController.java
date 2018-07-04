package controllers;

import auxiliares.Cliente;
import auxiliares.Data;
import auxiliares.Programa;
import auxiliares.Vencimento_Geral;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GerenciamentoController implements Initializable {

    //Lista de Clientes Gerais;
    public static ArrayList<Cliente> clientes;

    //FXML referentes à Tabela da Esquerda e suas colunas;
    @FXML
    private TableView<Vencimento_Geral> TB_Mes_Vencimentos;

    @FXML
    private TableColumn<Vencimento_Geral, String> TB_Mes_Vencimentos_Prog, TB_Mes_Vencimentos_Nome, TB_Mes_Vencimentos_Data, TB_Mes_Vencimentos_Qtd;

    //FXML referentes ao gráfico da Esquerda;
    @FXML
    private StackedBarChart<String, Integer> SBC_Histograma;

    @FXML
    private CategoryAxis SBC_catA;

    @FXML
    private NumberAxis SBC_numbA;

    //FXML referente à Tabela da Direita e suas colunas;
    @FXML
    private TableView<Vencimento_Geral> TB_Projecao;

    @FXML
    private TableColumn<Vencimento_Geral, String> TB_Projecao_Prog, TB_Projecao_Nome, TB_Projecao_Data, TB_Projecao_Qtd;

    //FMXL referente aos campos de controle: Combobox e TextFields;
    @FXML
    private ComboBox CB_Periodo, CB_Programa;

    @FXML
    private TextField TF_Nome_Cliente_Projecao, TF_Nome_Cliente_Vencimento;

    //FXML referentes aos botões de Informação do Cliente e da Busca de Projecao
    @FXML
    private Button BT_Perfil_Cliente, BT_Busca_Projecao, BT_Geral_Vencimentos, BT_Busca_Vencimentos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            clientes = supermilhas.SuperMilhas.getClientes();

            inicializaTabelaVencMes(clientes, 7);
            inicializaTabelaPerspectiva(clientes, 7);
            inicializaHistograma(7, clientes);

            inicializaComboBox();
            ouveBotaoBuscaProjecao(clientes, 7);
            ouveBotaoPerfil(clientes);
            ouveBotaoGeral(clientes);
            ouveBotaoBuscaVencimento(clientes);

        } catch (IOException ex) {
            Logger.getLogger(GerenciamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Método para inicializar a tabela inferior esquerda com os dados de vencimento de um mês específico.

    private void inicializaTabelaVencMes(ArrayList<Cliente> fonte, int mes) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    if (aux.getKey().getMes() == mes) {
                        Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), aux.getValue().toString());
                        retorno.add(v);
                    }
                }
            }
        }

        TB_Mes_Vencimentos_Nome.setStyle("-fx-alignment: CENTER;");
        TB_Mes_Vencimentos_Prog.setStyle("-fx-alignment: CENTER;");
        TB_Mes_Vencimentos_Data.setStyle("-fx-alignment: CENTER;");
        TB_Mes_Vencimentos_Qtd.setStyle("-fx-alignment: CENTER;");

        TB_Mes_Vencimentos_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TB_Mes_Vencimentos_Prog.setCellValueFactory(new PropertyValueFactory<>("programa"));
        TB_Mes_Vencimentos_Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        TB_Mes_Vencimentos_Qtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        if (retorno.isEmpty()) {
            alertDialog("Vencimento de Milhas", AlertType.INFORMATION, "Sem vencimentos", "Não há vencimentos de milhas neste mês");
        }

        Collections.sort(retorno, new dataComparator());
        TB_Mes_Vencimentos.getItems().clear();
        TB_Mes_Vencimentos.getItems().addAll(FXCollections.observableArrayList(retorno));
    }

    //Método para inicializar a tabela inferior direita com a projeção de vencimentos a partir de um mês inicial;
    private void inicializaTabelaPerspectiva(ArrayList<Cliente> fonte, int mesAtual) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), aux.getValue().toString());
                    if (!retorno.contains(v) && aux.getKey().getMes() > mesAtual) {
                        retorno.add(v);
                    }
                }
            }
        }

        TB_Projecao_Nome.setStyle("-fx-alignment: CENTER;");
        TB_Projecao_Prog.setStyle("-fx-alignment: CENTER;");
        TB_Projecao_Data.setStyle("-fx-alignment: CENTER;");
        TB_Projecao_Qtd.setStyle("-fx-alignment: CENTER;");

        TB_Projecao_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TB_Projecao_Prog.setCellValueFactory(new PropertyValueFactory<>("programa"));
        TB_Projecao_Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        TB_Projecao_Qtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        Collections.sort(retorno, new dataComparator());
        TB_Projecao.getItems().clear();
        TB_Projecao.getItems().addAll(FXCollections.observableArrayList(retorno));
    }

    //Método que gera os dados utilizados pelo Histograma;
    public ArrayList<HashMap<String, Integer>> calculaHistograma(ArrayList<Cliente> fonte, int mesDeBusca, int anoDeBusca) {

        ArrayList<HashMap<String, Integer>> tProgrMes = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            HashMap<String, Integer> tProgrDia = new HashMap<>();
            tProgrDia.put("SMILES", 0);
            tProgrDia.put("MULTIPLUS", 0);
            tProgrDia.put("AMIGO", 0);
            tProgrDia.put("AZUL", 0);

            for (Cliente c : fonte) {
                for (Programa p : c.getProgramas()) {
                    String nPro = p.getNome();
                    HashMap<Data, Integer> data_qtd = p.getVencimentos();
                    for (Entry<Data, Integer> dq : data_qtd.entrySet()) {
                        int dia = dq.getKey().getDia();
                        int mes = dq.getKey().getMes();
                        int ano = dq.getKey().getAno();

                        if (dia == i) {
                            if (mes == mesDeBusca && ano == anoDeBusca) {
                                int vAnt = tProgrDia.get(nPro);
                                int vNov = vAnt + dq.getValue();
                                tProgrDia.put(nPro, vNov);
                            }
                        }
                    }
                }
            }

            tProgrMes.add(tProgrDia);

        }

        return tProgrMes;
    }

    //Método que inicializa o Histograma;
    private void inicializaHistograma(int mes, ArrayList<Cliente> fonte) {

        SBC_catA.setLabel("Dia (mês)");
        SBC_numbA.setLabel("Total Milhas (Programa)");
        SBC_Histograma.setTitle("Total de Milhas a vencer");

        final XYChart.Series<String, Integer> amigo = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> multiplus = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> smiles = new XYChart.Series<String, Integer>();
        final XYChart.Series<String, Integer> azul = new XYChart.Series<String, Integer>();

        amigo.setName("AMIGO");
        multiplus.setName("MULTIPLUS");
        smiles.setName("SMILES");
        azul.setName("AZUL");

        for (int dia = 1; dia <= 31; dia++) {
            String data = dia + "/" + mes;

            azul.getData().add(new XYChart.Data<String, Integer>(data, totalMilhasDia("AZUL", data, fonte)));
            amigo.getData().add(new XYChart.Data<String, Integer>(data, totalMilhasDia("AMIGO", data, fonte)));
            multiplus.getData().add(new XYChart.Data<String, Integer>(data, totalMilhasDia("MULTIPLUS", data, fonte)));
            smiles.getData().add(new XYChart.Data<String, Integer>(data, totalMilhasDia("SMILES", data, fonte)));
        }

        SBC_Histograma.getData().addAll(azul, amigo, multiplus, smiles);

        for (Series<String, Integer> serie : SBC_Histograma.getData()) {
            for (XYChart.Data<String, Integer> item : serie.getData()) {
                item.getNode().setOnMousePressed((MouseEvent event) -> {
                    atualizaTabelaMesVencimento(fonte, item.getXValue(), serie.getName());
                });
            }
        }
    }

    //Calcula o total de Milhas dada uma data específica;
    private Integer totalMilhasDia(String programa, String data, ArrayList<Cliente> fonte) {
        int total = 0;
        String diaBusca = data.split("/")[0];
        String mesBusca = data.split("/")[1];

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                if (p.getNome().equals(programa)) {
                    for (Entry<Data, Integer> venc_qtd : p.getVencimentos().entrySet()) {
                        String diaAtual = String.valueOf(venc_qtd.getKey().getDia());
                        String mesAtual = String.valueOf(venc_qtd.getKey().getMes());

                        if (diaAtual.equals(diaBusca) && mesAtual.equals(mesBusca)) {
                            total += venc_qtd.getValue();
                        }

                    }
                }
            }
        }

        return total;
    }

    //Método que dado um programa clicado no histograma atualiza a tabela inferior dos vencimentos de um mês com somente aquele plano;
    private void atualizaTabelaMesVencimento(ArrayList<Cliente> fonte, String data, String prog) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();

        String diaBusca = data.split("/")[0];
        String mesBusca = data.split("/")[1];

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    String diaAtual = String.valueOf(aux.getKey().getDia());
                    String mesAtual = String.valueOf(aux.getKey().getMes());

                    if (diaAtual.equals(diaBusca) && mesAtual.equals(mesBusca)) {
                        if (p.getNome().equals(prog)) {
                            Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), String.valueOf(aux.getValue()));
                            retorno.add(v);
                        }
                    }
                }
            }
        }

        TB_Mes_Vencimentos.getItems().clear();
        TB_Mes_Vencimentos.getItems().addAll(FXCollections.observableArrayList(retorno));

        return;
    }

    //Método para inicializar o conteúdo dos comboboxs;
    private void inicializaComboBox() {
        CB_Periodo.getItems().addAll("Nenhum", "+1 Mês", "+2 Meses", "+3 Meses", "+4 Meses", "+5 Meses", "+6 Meses", "+7 Meses", "+8 Meses", "+9 Meses",
                "+10 Meses", "+11 Meses", "+12 Meses");
        CB_Programa.getItems().addAll("Todos", "Amigo", "Azul", "Multiplus", "Smiles");

        CB_Periodo.setValue("Nenhum");
        CB_Programa.setValue("Todos");
    }

    //Método para ouvir o botão de Busca;
    private void ouveBotaoBuscaProjecao(ArrayList<Cliente> fonte, int mesAtual) {
        BT_Busca_Projecao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String periodo = CB_Periodo.getValue().toString();
                String programa = CB_Programa.getValue().toString();
                String nCliente = TF_Nome_Cliente_Projecao.getText();

                if (nCliente.equals("")) {
                    //Gera a projeção com todos os clientes;
                    if (!periodo.equals("Nenhum") && programa.equals("Todos")) {
                        geraPeriodo(fonte, periodo, mesAtual, nCliente);
                    } else if (periodo.equals("Nenhum") && !programa.equals("Todos")) {
                        geraPrograma(fonte, programa, nCliente);
                    } else if (!periodo.equals("Nenhum") && !programa.equals("Todos")) {
                        geraPerProg(fonte, periodo, programa, mesAtual, nCliente);
                    } else if (periodo.equals("Nenhum") && programa.equals("Todos")) {
                        geraTudo(fonte, mesAtual);
                    }

                } else {
                    //Gera a projeção com o nome dos clientes selecionados;
                    //Gerar Array de Clientes com somente o nome desejado;
                    ArrayList<Cliente> fonte2 = clienteBusca(fonte, nCliente);

                    if (!periodo.equals("Nenhum") && programa.equals("Todos")) {
                        geraPeriodo(fonte2, periodo, mesAtual, nCliente);
                    } else if (periodo.equals("Nenhum") && !programa.equals("Todos")) {
                        geraPrograma(fonte2, programa, nCliente);
                    } else if (!periodo.equals("Nenhum") && !programa.equals("Todos")) {
                        geraPerProg(fonte2, periodo, programa, mesAtual, nCliente);
                    } else if (periodo.equals("Nenhum") && programa.equals("Todos")) {
                        geraTudo(fonte2, mesAtual);
                    }
                }
            }
        });
    }

    //Gera uma Lista contendo somente os clientes com o nome determinado na busca;
    private ArrayList<Cliente> clienteBusca(ArrayList<Cliente> fonte, String nomeBusca) {
        ArrayList<Cliente> retorno = new ArrayList<>();
        for (Cliente c : fonte) {
            if (c.getNome().equals(nomeBusca)) {
                retorno.add(c);
            }
        }

        return retorno;
    }

    private void geraPeriodo(ArrayList<Cliente> fonte, String periodo, int mesAtual, String cNome) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();
        int periodoInt = Integer.parseInt(periodo.split(" ")[0]);

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    if (aux.getKey().getMes() > mesAtual && (aux.getKey().getMes() <= (mesAtual + periodoInt))) {
                        Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), String.valueOf(aux.getValue()));
                        retorno.add(v);
                    }
                }
            }
        }

        if (retorno.isEmpty()) {
            alertDialog("Vencimentos de Milhas", AlertType.INFORMATION, "Sem Vencimentos\n" + cNome, "Não há vencimentos de milhas no próximo período de: " + periodo);
        }

        Collections.sort(retorno, new dataComparator());
        TB_Projecao.getItems().clear();
        TB_Projecao.getItems().addAll(FXCollections.observableArrayList(retorno));

    }

    private void geraPerProg(ArrayList<Cliente> fonte, String periodo, String programa, int mesAtual, String cNome) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();
        int periodoInt = Integer.parseInt(periodo.split(" ")[0]);

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    if (aux.getKey().getMes() > mesAtual && (aux.getKey().getMes() <= (mesAtual + periodoInt))) {
                        if (p.getNome().equals(programa.toUpperCase())) {
                            Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), String.valueOf(aux.getValue()));
                            retorno.add(v);
                        }
                    }
                }
            }
        }

        if (retorno.isEmpty()) {
            alertDialog("Vencimentos de Milhas", AlertType.INFORMATION, "Sem Vencimentos\n" + cNome, "Não há vencimentos de milhas para o programa: " + programa
                    + " no próximo período de: " + periodo);
        }

        Collections.sort(retorno, new dataComparator());
        TB_Projecao.getItems().clear();
        TB_Projecao.getItems().addAll(FXCollections.observableArrayList(retorno));
    }

    private void geraPrograma(ArrayList<Cliente> fonte, String programa, String cNome) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();
        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                if (p.getNome().equals(programa.toUpperCase())) {
                    for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                        Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), String.valueOf(aux.getValue()));
                        if (!retorno.contains(v)) {
                            retorno.add(v);
                        }
                    }
                }
            }
        }

        if (retorno.isEmpty()) {
            alertDialog("Vencimentos de Milhas", AlertType.INFORMATION, "Sem Vencimentos\n" + cNome, "Não há vencimentos de milhas para o programa: " + programa);
        }

        Collections.sort(retorno, new dataComparator());
        TB_Projecao.getItems().clear();
        TB_Projecao.getItems().addAll(FXCollections.observableArrayList(retorno));

    }

    private void geraTudo(ArrayList<Cliente> fonte, int mesAtual) {
        ArrayList<Vencimento_Geral> retorno = new ArrayList<>();

        for (Cliente c : fonte) {
            for (Programa p : c.getProgramas()) {
                for (Entry<Data, Integer> aux : p.getVencimentos().entrySet()) {
                    int mesAux = aux.getKey().getMes();
                    if (mesAux > mesAtual) {
                        Vencimento_Geral v = new Vencimento_Geral(c.getNome(), p.getNome(), aux.getKey().toString(), String.valueOf(aux.getValue()));
                        if (!retorno.contains(v)) {
                            retorno.add(v);
                        }
                    }
                }
            }
        }

        Collections.sort(retorno, new dataComparator());
        TB_Projecao.getItems().clear();
        TB_Projecao.getItems().addAll(FXCollections.observableArrayList(retorno));
    }

    //Método para ouvir o botão de mais informações sobre o cliente;
    private void ouveBotaoPerfil(ArrayList<Cliente> fonte) throws IOException {
        BT_Perfil_Cliente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Vencimento_Geral venc = TB_Projecao.getSelectionModel().getSelectedItem();
                if (venc == null) {
                    venc = TB_Mes_Vencimentos.getSelectionModel().getSelectedItem();
                }

                if (venc == null) {
                    alertDialog("Usuário Não Selecionado", AlertType.ERROR, "Selecione um usuário", "Selecione um usuário das tabelas para visualizar seu perfil.");
                } else {
                    for (Cliente c : fonte) {
                        if (c.getNome().equals(venc.getNome())) {
                            try {
                                URL location1 = GerenciamentoController.class.getResource("/fxml/pc/ClienteInfo.fxml");
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(location1);
                                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                                Parent root1 = fxmlLoader.load(location1.openStream());

                                ClienteInfoController ctrl1 = (ClienteInfoController) fxmlLoader.getController();
                                ctrl1.carregaCliente(c);

                                Stage stage = new Stage();
                                stage.setTitle("Informação do Cliente");
                                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
                                stage.setScene(new Scene(root1));
                                stage.setResizable(false);
                                stage.show();

                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                            BT_Perfil_Cliente.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
                                Node source = evt.getPickResult().getIntersectedNode();

                                // move up through the node hierarchy until a TableRow or scene root is found 
                                while (source != null && !(source instanceof TableRow)) {
                                    source = source.getParent();
                                }

                                // clear selection on click anywhere but on a filled row
                                if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
                                    TB_Mes_Vencimentos.getSelectionModel().clearSelection();
                                    TB_Projecao.getSelectionModel().clearSelection();
                                }
                            });
                            break;
                        }
                    }
                }
            }
        });
    }

    //Método para ouvir o botão de resetar a tabela de Vencimentos para Todos os programas;
    public void ouveBotaoGeral(ArrayList<Cliente> fonte) {
        BT_Geral_Vencimentos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                inicializaTabelaVencMes(fonte, 7);

                for (Cliente ci : fonte) {
                    System.out.println(ci.toString());
                }

            }
        });

    }

    //Método para ouvir o botão de busca de vencimento do cliente na seção de vencimentos;
    public void ouveBotaoBuscaVencimento(ArrayList<Cliente> fonte) {
        BT_Busca_Vencimentos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String nCliente = TF_Nome_Cliente_Vencimento.getText();
                if (nCliente.equals("")) {
                    alertDialog("Nome vazio", AlertType.WARNING, "Campo Cliente Vazio!", "Insira o nome de um cliente para realizar a busca.");
                } else {
                    ArrayList<Cliente> nomeFiltrado = clienteBusca(fonte, nCliente);
                    inicializaTabelaVencMes(nomeFiltrado, 7);
                }
            }
        });

    }

    //Métodos Auxiliares para o gerenciamento
    //Método de alerta de Mensagens;
    public void alertDialog(String title, AlertType alert, String header, String message) {
        Alert dialogoInfo = new Alert(alert);
        dialogoInfo.setTitle(title);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(message);
        dialogoInfo.showAndWait();
    }

    //Método de comparação para datas;
    public class dataComparator implements Comparator<Vencimento_Geral> {

        @Override
        public int compare(Vencimento_Geral t, Vencimento_Geral t1) {
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
