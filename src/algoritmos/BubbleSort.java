package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Ordena a tripulação pelo código galáctico usando Bubble Sort (sem critério de parada)
public class BubbleSort {

    // vetor: a fila a ordenar (é alterada direto, não é cópia)
    // tamanho: quantos elementos ordenar
    // passoAPasso: true = imprime cada etapa na tela
    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        int comparacoes = 0;   // quantas vezes comparamos dois vizinhos
        int trocas = 0;        // quantas vezes trocamos dois de lugar
        Tripulante temp;       // auxiliar para a troca

        // i = número da passagem, acada passagem, o maior que sobrou vai p o fim
        // mantém i < tamanho, na última iteração o laço interno não executa
        for (int i = 0; i < tamanho; i++) {
            // só imprime se a passagem vai ter comparações
            if (passoAPasso && i < tamanho - 1) {
                System.out.println();
                System.out.println(" Bubble Sort · passagem " + (i + 1));
            }

            // j percorre os pares de vizinhos (j-1, j)
            // vai só até tamanho - i porque os i últimos já estão no lugar certo
            for (int j = 1; j < tamanho - i; j++) {
                comparacoes++; // conta toda comparação, troque ou não

                // o da esquerda tem código maior? então estão fora de ordem
                if (vetor[j - 1].getCodigo() > vetor[j].getCodigo()) {
                    if (passoAPasso) {
                        System.out.println(vetor[j - 1].descricao() + " > " + vetor[j].descricao()
                                + "? SIM → trocam de assento!");
                    }
                    // troca usando temp, senão o da esquerda se perderia
                    temp = vetor[j - 1];       // guarda o da esquerda
                    vetor[j - 1] = vetor[j];   // direita vai para a esquerda
                    vetor[j] = temp;           // o guardado vai para a direita
                    trocas++;
                } else {
                    // já estão em ordem, nada a fazer, só mostra na tela
                    if (passoAPasso) {
                        System.out.println(vetor[j - 1].descricao() + " > " + vetor[j].descricao()
                                + "? NÃO → " + vetor[j - 1].getNome() + " fica onde está.");
                    }
                }
            }

            // fim da passagem, mostra os contadores acumulados e a fila atual
            if (passoAPasso && i < tamanho - 1) {
                System.out.println("fim da passagem " + (i + 1) + " → comparações: " + comparacoes
                        + " | trocas: " + trocas);
                Formatador.mostrarFila(vetor, tamanho);
            }
        }

        // o vetor já está ordenado, devolve só as estatísticas
        return new ResultadoOrdenacao(comparacoes, trocas);
    }
}