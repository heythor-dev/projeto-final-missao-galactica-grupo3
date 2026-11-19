package algoritmos;

import modelo.ResultadoOrdenacao;
import modelo.Tripulante;

// Os três algoritmos disponíveis, com os nomes usados nos relatórios.
public enum Algoritmo {

    BUBBLE("Bubble Sort", "trocas"),
    INSERTION("Insertion Sort", "deslocamentos"),
    QUICK("Quick Sort", "trocas");

    private final String nome;
    private final String nomeContador;

    Algoritmo(String nome, String nomeContador) {
        this.nome = nome;
        this.nomeContador = nomeContador;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeContador() {
        return nomeContador;
    }

    public ResultadoOrdenacao ordenar(Tripulante[] vetor, int tamanho, boolean passoAPasso) {
        switch (this) {
            case BUBBLE:
                return BubbleSort.ordenar(vetor, tamanho, passoAPasso);
            case INSERTION:
                return InsertionSort.ordenar(vetor, tamanho, passoAPasso);
            default:
                return QuickSort.ordenar(vetor, tamanho, passoAPasso);
        }
    }
}
