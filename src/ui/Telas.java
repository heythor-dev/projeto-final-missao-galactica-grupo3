package ui;

import algoritmos.Algoritmo;
import dados.Estacao;
import modelo.ResultadoOrdenacao;
import modelo.Tripulante;

import java.util.Arrays;
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
        System.out.println("║             MISSÃO GALÁCTICA              ║");
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

    // Devolve um algoritmo só, ou os três quando "Os três (comparar)" é escolhido.
    public static Algoritmo[] escolherAlgoritmos(boolean permitirTodos) {
        Algoritmo[] todos = Algoritmo.values();
        for (int i = 0; i < todos.length; i++) {
            System.out.println((i + 1) + " - " + todos[i].getNome());
        }
        if (permitirTodos) {
            System.out.println("4 - Os três (comparar)");
        }
        int maximo = permitirTodos ? todos.length + 1 : todos.length;

        int escolha;
        do {
            escolha = lerInteiro("Algoritmo: ");
            if (escolha < 1 || escolha > maximo) {
                System.out.println("Opção inválida!");
            }
        } while (escolha < 1 || escolha > maximo);

        if (permitirTodos && escolha == maximo) {
            return todos;
        }
        return new Algoritmo[]{todos[escolha - 1]};
    }

    // OPÇÃO 3 - ORGANIZAR EMBARQUE NA CÁPSULA
    public static void organizarEmbarque(Estacao estacao) {
        int quantidade = estacao.getQuantidade();
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }
        Algoritmo[] algoritmos = escolherAlgoritmos(true);
        Tripulante[] ordenado = null;

        System.out.println();
        for (Algoritmo algoritmo : algoritmos) {
            Tripulante[] copia = estacao.copiar(quantidade);
            ResultadoOrdenacao resultado = algoritmo.ordenar(copia, quantidade, false);
            System.out.println(Formatador.completar(algoritmo.getNome(), 15) + " → comparações: "
                    + resultado.comparacoes + " | " + algoritmo.getNomeContador() + ": " + resultado.trocas);
            ordenado = copia;
        }

        System.out.println();
        System.out.println("Ordem de embarque na cápsula de retorno:");
        Formatador.listarTripulantes(ordenado, quantidade);
    }

    // OPÇÃO 4 - MODO PASSO A PASSO (até 10 tripulantes)
    public static void modoPassoAPasso(Estacao estacao) {
        int quantidade = estacao.getQuantidade();
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }
        int tamanho = quantidade;
        if (tamanho > 10) {
            tamanho = 10;
            System.out.println("A cápsula de treino tem 10 assentos: embarcam os 10 primeiros da lista.");
        }

        Algoritmo algoritmo = escolherAlgoritmos(false)[0];
        Tripulante[] copia = estacao.copiar(tamanho);

        System.out.println();
        System.out.println("Fila inicial:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + copia[i].descricao());
        }

        ResultadoOrdenacao resultado = algoritmo.ordenar(copia, tamanho, true);

        System.out.println();
        System.out.println("=== " + algoritmo.getNome() + " terminou! ===");
        System.out.println("Total → comparações: " + resultado.comparacoes + " | trocas: " + resultado.trocas);
        System.out.println("Fila final da cápsula:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + copia[i].descricao());
        }
    }
}
