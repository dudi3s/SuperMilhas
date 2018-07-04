package supermilhas;

import auxiliares.Cliente;
import auxiliares.Data;
import auxiliares.Programa;
import controllers.Chat_ClienteController;
import controllers.Chat_GerenteController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SuperMilhas extends Application {

    private static ArrayList<Cliente> clientes;

    private static Chat_ClienteController chat_cliController;
    private static Chat_GerenteController chat_gerController;

    @Override
    public void start(Stage stage) throws Exception {
        geraClientes();
        Parent root_pc = FXMLLoader.load(getClass().getResource("/fxml/pc/Gerenciamento.fxml"));
        Scene scene_pc = new Scene(root_pc);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/pc_icon.png")));
        stage.setTitle("SuperMilhas - Gerenciamento");
        stage.setScene(scene_pc);
        stage.show();

        double windowGap = 5;

        Stage stage_mb = new Stage();
        Parent root_mb = FXMLLoader.load(getClass().getResource("/fxml/mobile/LoginCliente.fxml"));
        Scene scene_mb = new Scene(root_mb);
        stage_mb.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));
        stage_mb.setTitle("SuperMilhas");
        stage_mb.setScene(scene_mb);
        stage_mb.setResizable(false);
        stage_mb.show();
    }

    public static Chat_ClienteController getControllerCliente() {
        return chat_cliController;
    }

    public static Chat_GerenteController getControllerGerente() {
        return chat_gerController;
    }

    public static void setControllerCliente(Chat_ClienteController cCli) {
        chat_cliController = cCli;
    }

    public static void setControllerGerente(Chat_GerenteController cGer) {
        chat_gerController = cGer;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void geraClientes() {
        clientes = new ArrayList<>();

        String[] fonte_nomes = {"Sophia", "Valentina", "Heloísa", "Beatriz", "Cecília", "Arthur", "Bernardo", "Lucas", "Vicente", "Evandro"};
        for (String nome : fonte_nomes) {
            Cliente c = new Cliente(nome, nome.toLowerCase() + "@gmail.com", geraSenha());
            c.setProgramas(geraProgramas());
            clientes.add(c);
        }
    }

    public static ArrayList<Cliente> getClientes() {
        return clientes;
    }

    //Métodos Auxiliares para gerar os dados dos Clientes/Programas;
    //Enumeração dos Programas Disponíveis;
    private static enum ProgramasEnum {

        SMILES, MULTIPLUS, AMIGO, AZUL;
    }

    //Dado um valor aleatório, devolve o nome de programa atrelado a ele;
    private static String nomePrograma(int valor) {
        String nome = "";
        switch (valor) {
            case 1:
                nome = ProgramasEnum.SMILES.toString();
                break;
            case 2:
                nome = ProgramasEnum.AMIGO.toString();
                break;
            case 3:
                nome = ProgramasEnum.AZUL.toString();
                break;
            case 4:
                nome = ProgramasEnum.MULTIPLUS.toString();
                break;
            default:
                nome = "SUPERMILHAS";
                break;
        }

        return nome;
    }

    //Verifica se o nome de programa sorteado já está cadastrado pelo Cliente;
    private static boolean programaJaCadastrado(ArrayList<Programa> arr, String targetValue) {
        for (Programa s : arr) {
            if (s.getNome().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    //Gera a lista contendo todos os programas de um cliente;
    private static ArrayList<Programa> geraProgramas() {
        ArrayList<Programa> programas = new ArrayList<>();

        Random gerador = new Random();

        //Número de programas que um usuário terá (até 4 programas)
        int numDeProgramas = gerador.nextInt(4) + 1;

        //Para cada programa gera datas de vencimentos e qtd de milhas aleatórias;
        for (int i = 0; i < numDeProgramas; i++) {
            HashMap<Data, Integer> vencimento_qtdMilhas = new HashMap<>();

            //Identifica o número do programa que o usuário terá
            int numPrograma = gerador.nextInt(4) + 1;
            //Pega o nome do programa correspondente ao número dele;
            String nomePrograma = nomePrograma(numPrograma);

            //Caso o programa já esteja cadastrado, sorteia um novo;
            while (programaJaCadastrado(programas, nomePrograma)) {
                numPrograma = gerador.nextInt(4) + 1;
                nomePrograma = nomePrograma(numPrograma);
            }

            //Quantos vencimentos um plano terá (até 5 datas de vencimento);
            int numVenc = gerador.nextInt(20) + 1;
            for (int j = 0; j < numVenc; j++) {

                //Quantidade de Milhas;
                int milhas = gerador.nextInt(10) + 1;

                //Gera o mês;
                //int mes = gerador.nextInt(12) + 1;
                int mes = (int) Math.ceil(Math.random() * (12 - 6 + 1)) - 1 + 6;

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

            //Gera o programa com sua senha e suas respectivas datas de vencimentos;
            Programa p = new Programa(nomePrograma, geraSenha(), vencimento_qtdMilhas, geraUser());
            programas.add(p);
        }

        return programas;
    }

    //Gera a senha de um susuário;
    private static String geraUser() {
        // Determia as letras que poderão estar presente nas chaves
        String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ123456789";

        Random random = new Random();

        String armazenaChaves = "";
        int index = -1;
        for (int i = 0; i < 9; i++) {
            index = random.nextInt(letras.length());
            armazenaChaves += letras.substring(index, index + 1);
        }

        return armazenaChaves;
    }

    //Gera a senha de um susuário;
    private static String geraSenha() {
        // Determia as letras que poderão estar presente nas chaves
        String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ123456789/*-+.!?_";

        Random random = new Random();

        String armazenaChaves = "";
        int index = -1;
        for (int i = 0; i < 9; i++) {
            index = random.nextInt(letras.length());
            armazenaChaves += letras.substring(index, index + 1);
        }

        return armazenaChaves;
    }
}
