package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;

// Ordena a tripulação pelo código galáctico usando Bubble Sort.
public class BubbleSort {

    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        int comparacoes = 0;
        int trocas = 0;
        Tripulante temp;

        for (int i = 0; i < tamanho - 1; i++) {
            boolean houveTroca = false;

            if (passoAPasso) {
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
                    houveTroca = true;
                } else {
                    if (passoAPasso) {
                        System.out.println(vetor[j - 1].descricao() + " > " + vetor[j].descricao()
                                + "? NÃO → " + vetor[j - 1].getNome() + " fica onde está.");
                    }
                }
            }

            if (passoAPasso) {
                System.out.println("fim da passagem " + (i + 1) + " → comparações: " + comparacoes
                        + " | trocas: " + trocas);
                mostrarFila(vetor, tamanho);
            }

            // critério de parada
            if (!houveTroca) {
                if (passoAPasso) {
                    System.out.println("Nenhuma troca nesta passagem → todos já estão em ordem!");
                }
                break;
            }
        }

        return new ResultadoOrdenacao(comparacoes, trocas);
    }

    // Mostra a fila da cápsula (só os códigos), usada no passo a passo.
    private static void mostrarFila(Tripulante[] vetor, int tamanho) {
        System.out.print("   Fila da cápsula: ");
        for (int i = 0; i < tamanho; i++) {
            System.out.print(vetor[i].getCodigo() + " ");
        }
        System.out.println();
    }
}
