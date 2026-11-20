package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;
import ui.Formatador;

// Ordena a tripulação pelo código galáctico usando Bubble Sort (sem critério de parada).
public class BubbleSort {

    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        int comparacoes = 0;
        int trocas = 0;
        Tripulante temp;

        // Mantém i < tamanho conforme o slide 5; na última iteração o laço interno não executa.
        for (int i = 0; i < tamanho; i++) {
            if (passoAPasso && i < tamanho - 1) {
                System.out.println();
                System.out.println("★ Bubble Sort · passagem " + (i + 1));
            }

            for (int j = 1; j < tamanho - i; j++) {
                comparacoes++;
                if (vetor[j - 1].getCodigo() > vetor[j].getCodigo()) {
                    if (passoAPasso) {
                        System.out.println(vetor[j - 1].descricao() + " > " + vetor[j].descricao()
                                + "? SIM → trocam de assento!");
                    }
                    // trocar os elementos
                    temp = vetor[j - 1];
                    vetor[j - 1] = vetor[j];
                    vetor[j] = temp;
                    trocas++;
                } else {
                    if (passoAPasso) {
                        System.out.println(vetor[j - 1].descricao() + " > " + vetor[j].descricao()
                                + "? NÃO → " + vetor[j - 1].getNome() + " fica onde está.");
                    }
                }
            }

            if (passoAPasso && i < tamanho - 1) {
                System.out.println("fim da passagem " + (i + 1) + " → comparações: " + comparacoes
                        + " | trocas: " + trocas);
                Formatador.mostrarFila(vetor, tamanho);
            }
        }

        return new ResultadoOrdenacao(comparacoes, trocas);
    }
}