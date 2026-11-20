package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Ordena a tripulação pelo código galáctico usando Quick Sort (pivo continua sendo sempre o 1 elemento)
public class QuickSort {

    private int comparacoes = 0;  //comparacoes e trocas medem quanto trabalho o algoritmo teve para ordenar (n influencia o algotimo em si, so serve pra exibir)
    private int trocas = 0;
    private final int tamanho;          // usado só para mostrar a fila inteira na tela
    private final boolean passoAPasso;  // true = imprime cada etapa

    //a classe só pode ser usada pelo metodo ordenar
    private QuickSort(int tamanho, boolean passoAPasso) {
        this.tamanho = tamanho;
        this.passoAPasso = passoAPasso;
    }

    // porta de entrada: cria o objeto, ordena a fila inteira (0 até o ultimo) e devolve as estatisticas
    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        QuickSort ordenador = new QuickSort(tamanho, passoAPasso);
        ordenador.quickSort(vetor, 0, tamanho - 1);
        return new ResultadoOrdenacao(ordenador.comparacoes, ordenador.trocas);
    }

    // ordena o trecho do vetor entre inicio e fim
    private void quickSort(Tripulante[] vetor, int inicio, int fim) {
        // caso base: trecho com 0 ou 1 elemento já está ordenado
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);  // pivo vai para o lugar final
            quickSort(vetor, inicio, posicaoPivo - 1);      // ordena os menores (esquerda)
            quickSort(vetor, posicaoPivo + 1, fim);         // ordena os maiores (direita)
        }
    }

    // deixa os menores ou iguais ao pivô à esquerda e os maiores à direita
    // devolve a posição final do pivô
    private int separar(Tripulante[] vetor, int inicio, int fim) {
        Tripulante pivo = vetor[inicio];  // pivo = primeiro do trecho
        int i = inicio + 1;               // anda para a direita procurando um MAIOR que o pivô
        int f = fim;                      // anda para a esquerda procurando um MENOR OU IGUAL ao pivô

        if (passoAPasso) {
            System.out.println();
            System.out.println(" Quick Sort · quicksort(vetor, " + inicio + ", " + fim + ") · pivô: "
                    + pivo.descricao()); //metodo da classe tripulante
        }

        // repete até i e f se cruzarem
        while (i <= f) {
            // i avança enquanto o elemento pode ficar à esquerda; para no primeiro maior
            boolean continuar = true;
            while (i <= fim && continuar) {
                comparacoes++;
                if (vetor[i].getCodigo() <= pivo.getCodigo()) { //codigo metodo de tripulante
                    i = i + 1;
                } else {
                    continuar = false;
                }
            }
            // f volta enquanto o elemento pode ficar à direita; para no primeiro menor ou igual
            continuar = true;
            while (f > inicio && continuar) {
                comparacoes++;
                if (vetor[f].getCodigo() > pivo.getCodigo()) {
                    f = f - 1;
                } else {
                    continuar = false;
                }
            }
            // se ainda não se cruzaram, vetor[i] e vetor[f] estão do lado errado > troca
            if (i < f) {
                if (passoAPasso) {
                    System.out.println(vetor[i].descricao() + " e " + vetor[f].descricao()
                            + " estão do lado errado do pivô → trocam de assento!");
                }
                Tripulante troca = vetor[i];  // "troca" faz o papel do temp
                vetor[i] = vetor[f];
                vetor[f] = troca;
                trocas++;
                i = i + 1;   // os dois já estão certos, segue em frente
                f = f - 1;
            }
        }

        // coloca o pivô na posição correta
        // f parou no último menor ou igual: é a fronteira entre os dois lados
        vetor[inicio] = vetor[f];  // último menor vai para o começo
        vetor[f] = pivo;           // pivô senta na fronteira (pivo já guardava o valor, não precisa de temp)
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

        // mostra os contadores acumulados e a fila depois desta separação
        if (passoAPasso) {
            System.out.println("   comparações: " + comparacoes + " | trocas: " + trocas);
            Formatador.mostrarFila(vetor, tamanho);
        }
        return f;  // posição final do pivô, usada pelo quickSort para dividir
    }
}