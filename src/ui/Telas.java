package ui;

import algoritmos.BubbleSort;
import algoritmos.InsertionSort;
import algoritmos.QuickSort;
import dados.Estacao;
import modelo.ResultadoOrdenacao;
import modelo.Tripulante;

import java.util.Scanner;

// Tudo o que aparece na tela e tudo o que o usuário digita.
public class Telas {

    private static final Scanner SC = new Scanner(System.in);

    private Telas() {
    }

    public static void mostrarCarregamento(Estacao estacao, boolean carregado, String nomeArquivo) {
        if (carregado) {
            System.out.println(estacao.getQuantidade() + " tripulantes chegaram à Estação Órbita-7!");
        } else {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado. A estação começa vazia.");
        }
    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║              MISSÃO GALÁCTICA             ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║ 1 - Registrar tripulante                  ║");
        System.out.println("║ 2 - Listar tripulantes                    ║");
        System.out.println("║ 3 - Organizar embarque na cápsula         ║");
        System.out.println("║ 4 - Modo passo a passo                    ║");
        System.out.println("║ 5 - A Grande Corrida                      ║");
        System.out.println("║ 0 - Encerrar a missão                     ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    // Lê um número inteiro do teclado (repete se a pessoa digitar letras)
    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!SC.hasNextInt()) {
            SC.nextLine();
            System.out.print("Digite apenas números: ");
        }
        int numero = SC.nextInt();
        SC.nextLine(); // limpa o Enter que sobrou
        return numero;
    }

    public static void registrarTripulante(Estacao estacao) {
        if (estacao.estaLotada()) {
            System.out.println("A estação está lotada!");
            return;
        }

        int codigo;
        boolean valido;
        do {
            codigo = lerInteiro("Código galáctico (8 dígitos): ");
            valido = true;
            if (codigo < 10000000 || codigo > 99999999) {
                System.out.println("O código precisa ter exatamente 8 dígitos!");
                valido = false;
            } else if (estacao.codigoExiste(codigo)) {
                System.out.println("Esse código já pertence a outro tripulante!");
                valido = false;
            }
        } while (!valido);

        System.out.print("Nome: ");
        String nome = SC.nextLine();
        System.out.print("Planeta natal: ");
        String planeta = SC.nextLine();
        System.out.print("Função a bordo: ");
        String funcao = SC.nextLine();

        estacao.adicionar(new Tripulante(codigo, nome, planeta, funcao));
        System.out.println(nome + " foi registrado(a) na estação!");
    }

    public static void listarTripulantes(Estacao estacao) {
        Formatador.listarTripulantes(estacao.getVetor(), estacao.getQuantidade());
    }

    // retorna 1 (Bubble), 2 (Insertion), 3 (Quick) ou 4 (os tres).
    public static int escolherAlgoritmo(boolean permitirTodos) {
        System.out.println("1 - Bubble Sort");
        System.out.println("2 - Insertion Sort");
        System.out.println("3 - Quick Sort");
        if (permitirTodos) {
            System.out.println("4 - Os três (comparar)");
        }

        int maximo = 3;
        if (permitirTodos) {
            maximo = 4;
        }

        int escolha;
        do {
            escolha = lerInteiro("Algoritmo: ");
            if (escolha < 1 || escolha > maximo) {
                System.out.println("Opção inválida!");
            }
        } while (escolha < 1 || escolha > maximo);

        return escolha;
    }

    // Opção 3 - Organizar embarque
    public static void organizarEmbarque(Estacao estacao) {
        int quantidade = estacao.getQuantidade();
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }

        int escolha = escolherAlgoritmo(true);
        int inicio = escolha;
        int fim = escolha;
        if (escolha == 4) {
            inicio = 1;
            fim = 3;
        }

        Tripulante[] ordenado = null;
        System.out.println();

        //Copia para cada algoritmo pra ser justa a comparação
        for (int alg = inicio; alg <= fim; alg++) {
            Tripulante[] copia = estacao.copiar(quantidade);
            ResultadoOrdenacao resultado;
            String nomeAlg;
            String nomeCont = "trocas";

            if (alg == 1) {
                nomeAlg = "Bubble Sort";
                resultado = BubbleSort.ordenar(copia, quantidade, false);
            } else if (alg == 2) {
                nomeAlg = "Insertion Sort";
                nomeCont = "deslocamentos";
                resultado = InsertionSort.ordenar(copia, quantidade, false);
            } else {
                nomeAlg = "Quick Sort";
                resultado = QuickSort.ordenar(copia, quantidade, false);
            }

            System.out.println(Formatador.completar(nomeAlg, 15) + " → comparações: "
                    + resultado.comparacoes + " | " + nomeCont + ": " + resultado.trocas);
            ordenado = copia;
        }

        System.out.println();
        System.out.println("Ordem de embarque na cápsula de retorno:");
        Formatador.listarTripulantes(ordenado, quantidade);
    }

    // Opção 4 - passo a passo (10 trip max)
    public static void modoPassoAPasso(Estacao estacao) {
        int quantidade = estacao.getQuantidade();
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }

        //10 tripulantes máximo
        int tamanho = quantidade;
        if (tamanho > 10) {
            tamanho = 10;
            System.out.println("A cápsula de treino tem 10 assentos: embarcam os 10 primeiros da lista.");
        }

        int escolha = escolherAlgoritmo(false);
        Tripulante[] copia = estacao.copiar(tamanho);

        System.out.println();
        System.out.println("Fila inicial:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + copia[i].descricao());
        }

        //true no ultimo parametro pra imprimir a comparação
        ResultadoOrdenacao resultado;
        String nomeAlg;
        if (escolha == 1) {
            nomeAlg = "Bubble Sort";
            resultado = BubbleSort.ordenar(copia, tamanho, true);
        } else if (escolha == 2) {
            nomeAlg = "Insertion Sort";
            resultado = InsertionSort.ordenar(copia, tamanho, true);
        } else {
            nomeAlg = "Quick Sort";
            resultado = QuickSort.ordenar(copia, tamanho, true);
        }

        System.out.println();
        System.out.println("=== " + nomeAlg + " terminou! ===");
        System.out.println("Total → comparações: " + resultado.comparacoes + " | trocas: " + resultado.trocas);
        System.out.println("Fila final da cápsula:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + copia[i].descricao());
        }
    }
}
