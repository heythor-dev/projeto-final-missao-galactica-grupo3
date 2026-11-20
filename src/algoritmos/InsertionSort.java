package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Ordena a tripulação pelo código galáctico usando Insertion Sort
public class InsertionSort {

    // vetor é cópia dos tripulantes e tamanho é quantidade de posições válidas
    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        int comparacoes = 0;
        int trocas = 0; // aqui conta deslocamentos (elemento anda uma posição para a direita)
        Tripulante chave; // tripulante que está sendo inserido na rodada
        int j;            // percorre a parte ordenada da direita para a esquerda

        // começa em 1: a posição 0 sozinha já está ordenada
        for (int i = 1; i < tamanho; i++) {
            chave = vetor[i]; // guarda a chave, pois vetor[i] pode ser sobrescrito no deslocamento
            j = i - 1;        // último elemento da parte ordenada

            if (passoAPasso) {
                System.out.println();
                System.out.println("★ Insertion Sort · rodada " + i + " · chave: " + chave.descricao());
            }

            // separado em um if para contar também a comparação que encerra o laço
            // o laço para quando j < 0 (chave é a menor) ou quando acha um código menor
            boolean continuar = true;
            while (j >= 0 && continuar) {
                comparacoes++; // conta a comparação de códigos do if abaixo
                if (vetor[j].getCodigo() > chave.getCodigo()) {
                    if (passoAPasso) {
                        System.out.println(vetor[j].descricao() + " > " + chave.descricao()
                                + "? SIM → " + vetor[j].getNome() + " anda uma poltrona para a direita!");
                    }
                    vetor[j + 1] = vetor[j]; // desloca o maior para a direita
                    trocas++;
                    j = j - 1; // passa para o próximo à esquerda
                } else {
                    if (passoAPasso) {
                        System.out.println(vetor[j].descricao() + " > " + chave.descricao()
                                + "? NÃO → " + vetor[j].getNome() + " fica onde está.");
                    }
                    // como a esquerda já está ordenada, os anteriores também são menores
                    continuar = false; // a chave fica logo depois de j
                }
            }
            // insere a chave no espaço aberto (se não houve deslocamento, j + 1 == i)
            vetor[j + 1] = chave;

            if (passoAPasso) {
                System.out.println(chave.getNome() + " senta na poltrona " + (j + 2) // j + 2 pq as poltronas são numeradas a partir de 1
                        + " → comparações: " + comparacoes + " | deslocamentos: " + trocas);
                Formatador.mostrarFila(vetor, tamanho);
            }
            // fim da rodada: posições 0 até i estão em ordem crescente
        }

        return new ResultadoOrdenacao(comparacoes, trocas);
    }
}