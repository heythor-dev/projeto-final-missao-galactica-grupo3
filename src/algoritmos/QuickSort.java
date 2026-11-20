package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Ordena a tripulação pelo código galáctico usando Quick Sort.
// O pivô continua sendo sempre o primeiro elemento, como no original.
public class QuickSort {

    private int comparacoes = 0;
    private int trocas = 0;
    private final int tamanho;
    private final boolean passoAPasso;

    private QuickSort(int tamanho, boolean passoAPasso) {
        this.tamanho = tamanho;
        this.passoAPasso = passoAPasso;
    }

    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        QuickSort ordenador = new QuickSort(tamanho, passoAPasso);
        ordenador.quickSort(vetor, 0, tamanho - 1);
        return new ResultadoOrdenacao(ordenador.comparacoes, ordenador.trocas);
    }

    private void quickSort(Tripulante[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo - 1);
            quickSort(vetor, posicaoPivo + 1, fim);
        }
    }

    private int separar(Tripulante[] vetor, int inicio, int fim) {
        Tripulante pivo = vetor[inicio];
        int i = inicio + 1;
        int f = fim;

        if (passoAPasso) {
            System.out.println();
            System.out.println("★ Quick Sort · quicksort(vetor, " + inicio + ", " + fim + ") · pivô: "
                    + pivo.descricao());
        }

        while (i <= f) {
            // mesmo que: while (i <= fim && vetor[i].codigo <= pivo.codigo) i++
            boolean continuar = true;
            while (i <= fim && continuar) {
                comparacoes++;
                if (vetor[i].getCodigo() <= pivo.getCodigo()) {
                    i = i + 1;
                } else {
                    continuar = false;
                }
            }

            // mesmo que: while (f > inicio && vetor[f].codigo > pivo.codigo) f--
            continuar = true;
            while (f > inicio && continuar) {
                comparacoes++;
                if (vetor[f].getCodigo() > pivo.getCodigo()) {
                    f = f - 1;
                } else {
                    continuar = false;
                }
            }

            if (i < f) {
                if (passoAPasso) {
                    System.out.println(vetor[i].descricao() + " e " + vetor[f].descricao()
                            + " estão do lado errado do pivô → trocam de assento!");
                }
                Tripulante troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                trocas++;
                i = i + 1;
                f = f - 1;
            }
        }

        // coloca o pivô na posição correta
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        if (f != inicio) {
            trocas++; // só conta se o pivô realmente mudou de lugar
            if (passoAPasso) {
                System.out.println("Pivô " + pivo.getNome() + " troca de assento com " + vetor[inicio].getNome()
                        + " e senta na posição " + f + ".");
            }
        } else {
            if (passoAPasso) {
                System.out.println("Pivô " + pivo.getNome() + " já está no lugar certo (posição " + f + ").");
            }
        }

        if (passoAPasso) {
            System.out.println("   comparações: " + comparacoes + " | trocas: " + trocas);
            Formatador.mostrarFila(vetor, tamanho);
        }
        return f;
    }
}
