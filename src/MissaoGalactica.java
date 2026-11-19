import dados.Estacao;
import relatorios.GrandeCorrida;
import ui.Telas;

// Ponto de entrada do programa: monta a estação e encaminha as opções do menu.
public class MissaoGalactica {

    private static final String ARQUIVO_TRIPULANTES = "tripulantes.txt";

    public static void main(String[] args) {
        Estacao estacao = new Estacao();
        boolean carregado = estacao.carregar(ARQUIVO_TRIPULANTES);
        Telas.mostrarCarregamento(estacao, carregado, ARQUIVO_TRIPULANTES);

        int opcao;
        do {
            Telas.mostrarMenu();
            opcao = Telas.lerInteiro("Escolha uma opção: ");

            if (opcao == 1) {
                Telas.registrarTripulante(estacao);
            } else if (opcao == 2) {
                Telas.listarTripulantes(estacao);
            } else if (opcao == 3) {
                Telas.organizarEmbarque(estacao);
            } else if (opcao == 4) {
                Telas.modoPassoAPasso(estacao);
            } else if (opcao == 5) {
                GrandeCorrida.executar();
            } else if (opcao == 0) {
                System.out.println("Missão encerrada. Boa viagem de volta, cadetes!");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
