package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;

// Ordena a tripulação pelo código galáctico usando Insertion Sort.
public class InsertionSort {

    public static ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        int comparacoes = 0;
        int trocas = 0;
        Tripulante chave;
        int j;

        for (int i = 1; i < tamanho; i++) {
            chave = vetor[i];
            j = i - 1;

            if (passoAPasso) {
                System.out.println();
                System.out.println("★ Insertion Sort · rodada " + i + " · chave: " + chave.descricao());
            }

            // mesmo que: while (j >= 0 && vetor[j].codigo > chave.codigo)
            // separado em um if para conseguir contar cada comparação
            boolean continuar = true;
            while (j >= 0 && continuar) {
                comparacoes++;
                if (vetor[j].getCodigo() > chave.getCodigo()) {
                    if (passoAPasso) {
                        System.out.println(vetor[j].descricao() + " > " + chave.descricao()
                                + "? SIM → " + vetor[j].getNome() + " anda uma poltrona para a direita!");
                    }
                    vetor[j + 1] = vetor[j];
                    trocas++; // deslocamento
                    j = j - 1;
                } else {
                    if (passoAPasso) {
                        System.out.println(vetor[j].descricao() + " > " + chave.descricao()
                                + "? NÃO → " + vetor[j].getNome() + " fica onde está.");
                    }
                    continuar = false;
                }
            }
            vetor[j + 1] = chave;

            if (passoAPasso) {
                System.out.println(chave.getNome() + " senta na poltrona " + (j + 2)
                        + " → comparações: " + comparacoes + " | deslocamentos: " + trocas);
                mostrarFila(vetor, tamanho);
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
