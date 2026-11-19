package modelo;

// Contadores de uma execução de algoritmo de ordenação.
// Cada algoritmo devolve um destes, em vez de alterar variáveis globais.
public class ResultadoOrdenacao {

    public final int comparacoes;
    public final int trocas; // no Insertion Sort conta os deslocamentos

    public ResultadoOrdenacao(int comparacoes, int trocas) {
        this.comparacoes = comparacoes;
        this.trocas = trocas;
    }
}
