package ui;

import modelo.Tripulante;

// Formatação das tabelas impressas no console.
public class Formatador {

    private Formatador() {
    }

    // Acrescenta espaços no fim do texto até ele ter "largura" letras.
    // Serve para deixar as colunas das tabelas alinhadas.
    public static String completar(String texto, int largura) {
        while (texto.length() < largura) {
            texto = texto + " ";
        }
        return texto;
    }

    // Mostra a fila da cápsula (só os códigos), usada no passo a passo.
    public static void mostrarFila(Tripulante[] vetor, int tamanho) {
        System.out.print("   Fila da cápsula: ");
        for (int i = 0; i < tamanho; i++) {
            System.out.print(vetor[i].getCodigo() + " ");
        }
        System.out.println();
    }

    public static void listarTripulantes(Tripulante[] vetor, int tamanho) {
        System.out.println();
        System.out.println(completar("Nº", 5) + completar("Código", 11) + completar("Nome", 25)
                + completar("Planeta", 13) + "Função");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < tamanho; i++) {
            System.out.println(completar("" + (i + 1), 5) + completar("" + vetor[i].getCodigo(), 11)
                    + completar(vetor[i].getNome(), 25) + completar(vetor[i].getPlaneta(), 13) + vetor[i].getFuncao());
        }
        System.out.println("Total: " + tamanho + " tripulantes");
    }

    public static void imprimirCabecalho() {
        System.out.println(completar("Algoritmo", 17) + completar("Situação", 16) + completar("n = 50", 15)
                + completar("n = 1.000", 15) + "n = 10.000");
        System.out.println("------------------------------------------------------------------------------");
    }
}
