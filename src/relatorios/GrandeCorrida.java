package relatorios;

import algoritmos.Algoritmo;
import dados.GeradorTripulantes;
import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

import java.util.Arrays;

// Compara os três algoritmos em três tamanhos e três situações.
public class GrandeCorrida {

    private static final int[] TAMANHOS = {50, 1000, 10000};
    private static final String[] SITUACOES = {"Aleatório", "Já ordenado", "Ordem inversa"};
    private static final int[] CODIGOS_SITUACAO = {
            GeradorTripulantes.ALEATORIO,
            GeradorTripulantes.JA_ORDENADO,
            GeradorTripulantes.ORDEM_INVERSA
    };

    private GrandeCorrida() {
    }

    public static void executar() {
        // linha = algoritmo * 3 + situação (9 linhas, como na tabela do enunciado)
        // coluna = tamanho (n = 50, 1.000, 10.000)
        double[][] tempos = new double[9][3];
        int[][] tabelaComparacoes = new int[9][3];
        int[][] tabelaTrocas = new int[9][3];
        Algoritmo[] algoritmos = Algoritmo.values();

        System.out.println();
        System.out.println("A Grande Corrida vai começar!");

        for (int t = 0; t < TAMANHOS.length; t++) {
            for (int s = 0; s < SITUACOES.length; s++) {
                // os três algoritmos correm com os MESMOS dados (cada um com sua cópia)
                Tripulante[] base = GeradorTripulantes.gerar(TAMANHOS[t], CODIGOS_SITUACAO[s]);

                for (Algoritmo algoritmo : algoritmos) {
                    Tripulante[] copia = Arrays.copyOf(base, TAMANHOS[t]);

                    long inicio = System.nanoTime();
                    ResultadoOrdenacao resultado = algoritmo.ordenar(copia, TAMANHOS[t], false);
                    long fim = System.nanoTime();

                    int linha = algoritmo.ordinal() * 3 + s;
                    tempos[linha][t] = (fim - inicio) / 1000000.0; // nanossegundos → milissegundos
                    tabelaComparacoes[linha][t] = resultado.comparacoes;
                    tabelaTrocas[linha][t] = resultado.trocas;
                }
            }
            System.out.println("  n = " + TAMANHOS[t] + " concluído!");
        }

        // Tabela de tempos
        System.out.println();
        System.out.println("TEMPO (ms)");
        imprimirTabelas(tempos, tabelaComparacoes, tabelaTrocas, algoritmos);
    }

    private static void imprimirTabelas(double[][] tempos, int[][] comparacoes, int[][] trocas,
                                        Algoritmo[] algoritmos) {
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(algoritmos[linha / 3].getNome(), 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                // arredonda para 3 casas depois da vírgula
                double tempoArredondado = Math.round(tempos[linha][t] * 1000) / 1000.0;
                System.out.print(Formatador.completar("" + tempoArredondado, 15));
            }
            System.out.println();
        }

        // Tabela de comparações
        System.out.println();
        System.out.println("COMPARAÇÕES");
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(algoritmos[linha / 3].getNome(), 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(Formatador.completar("" + comparacoes[linha][t], 15));
            }
            System.out.println();
        }

        // Tabela de trocas
        System.out.println();
        System.out.println("TROCAS (deslocamentos no Insertion Sort)");
        Formatador.imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(Formatador.completar(algoritmos[linha / 3].getNome(), 17)
                    + Formatador.completar(SITUACOES[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(Formatador.completar("" + trocas[linha][t], 15));
            }
            System.out.println();
        }
    }
}
