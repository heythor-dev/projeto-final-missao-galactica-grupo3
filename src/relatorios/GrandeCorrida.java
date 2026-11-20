package relatorios;

import algoritmos.BubbleSort;
import algoritmos.InsertionSort;
import algoritmos.QuickSort;
import dados.GeradorTripulantes;
import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Compara os três algoritmos em três tamanhos e três situações.
public class GrandeCorrida {

    private static final int[] TAMANHOS = {50, 1000, 10000};
    private static final String[] SITUACOES = {"Aleatório", "Já ordenado", "Ordem inversa"};
    private static final String[] NOMES_ALGORITMOS = {"Bubble Sort", "Insertion Sort", "Quick Sort"};
    private static final int[] CODIGOS_SITUACAO = {
            GeradorTripulantes.ALEATORIO,
            GeradorTripulantes.JA_ORDENADO,
            GeradorTripulantes.ORDEM_INVERSA
    };

    private GrandeCorrida() {
    }

    // Roda os 3 algoritmos sobre cópias iguais para cada tamanho e situação,
    // medindo tempo (ms), comparações e trocas, e depois imprime as tabelas.
    public static void executar() {
        // Vetores 1D de 27 posições (9 linhas x 3 tamanhos) no lugar de matrizes 2D.
        double[] tempos = new double[27];
        int[] tabelaComparacoes = new int[27];
        int[] tabelaTrocas = new int[27];

        System.out.println();
        System.out.println("A Grande Corrida vai começar!");

        for (int t = 0; t < TAMANHOS.length; t++) {
            for (int s = 0; s < SITUACOES.length; s++) {
                // Os três algoritmos correm com os mesmos dados (cada um recebe sua cópia).
                Tripulante[] base = GeradorTripulantes.gerar(TAMANHOS[t], CODIGOS_SITUACAO[s]);

                // 0 = Bubble Sort, 1 = Insertion Sort, 2 = Quick Sort
                for (int a = 0; a < 3; a++) {
                    Tripulante[] copia = copiar(base, TAMANHOS[t]);

                    long inicio = System.nanoTime();
                    ResultadoOrdenacao resultado;
                    if (a == 0) {
                        resultado = BubbleSort.ordenar(copia, TAMANHOS[t], false);
                    } else if (a == 1) {
                        resultado = InsertionSort.ordenar(copia, TAMANHOS[t], false);
                    } else {
                        resultado = QuickSort.ordenar(copia, TAMANHOS[t], false);
                    }
                    long fim = System.nanoTime();

                    // Mapeia (algoritmo, situação, tamanho) para uma posição única de 0 a 26.
                    int linha = a * 3 + s;
                    int pos = linha * 3 + t;
                    tempos[pos] = (fim - inicio) / 1000000.0; // nanossegundos → milissegundos
                    tabelaComparacoes[pos] = resultado.comparacoes;
                    tabelaTrocas[pos] = resultado.trocas;
                }
            }
            System.out.println("  n = " + TAMANHOS[t] + " concluído!");
        }

        System.out.println();
        System.out.println("TEMPO (ms)");
        imprimirTabelas(tempos, tabelaComparacoes, tabelaTrocas);
    }

    // Cria um novo vetor com os primeiros "tamanho" elementos
    private static Tripulante[] copiar(Tripulante[] original, int tamanho) {
        Tripulante[] copia = new Tripulante[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // Formata e exibe no console as três tabelas finais: tempo, comparações e trocas.
    private static void imprimirTabelas(double[] tempos, int[] comparacoes, int[] trocas) {
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(NOMES_ALGORITMOS[linha / 3], 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                double tempoArredondado = Math.round(tempos[linha * 3 + t] * 1000) / 1000.0;
                System.out.print(Formatador.completar("" + tempoArredondado, 15));
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("COMPARAÇÕES");
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(NOMES_ALGORITMOS[linha / 3], 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(Formatador.completar("" + comparacoes[linha * 3 + t], 15));
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("TROCAS (deslocamentos no Insertion Sort)");
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(NOMES_ALGORITMOS[linha / 3], 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(Formatador.completar("" + trocas[linha * 3 + t], 15));
            }
            System.out.println();
        }
    }
}