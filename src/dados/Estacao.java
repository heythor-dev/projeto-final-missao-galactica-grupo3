package dados;

import modelo.Tripulante;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// Guarda a tripulação da Estação Órbita-7.
// Não conhece teclado nem tela: quem chama decide o que mostrar.
public class Estacao {

    private final Tripulante[] tripulacao = new Tripulante[500];
    private int quantidade = 0;

    // Lê o arquivo no formato: codigo;nome;planeta;funcao.
    // Devolve false se o arquivo não for encontrado.
    public boolean carregar(String nomeArquivo) {
        try {
            Scanner leitor = new Scanner(new File(nomeArquivo), "UTF-8");
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split(";");
                    int codigo = Integer.parseInt(partes[0]);
                    tripulacao[quantidade] = new Tripulante(codigo, partes[1], partes[2], partes[3]);
                    quantidade++;
                }
            }
            leitor.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public boolean estaLotada() {
        return quantidade == tripulacao.length;
    }

    // Verifica se um código galáctico já está sendo usado
    public boolean codigoExiste(int codigo) {
        for (int i = 0; i < quantidade; i++) {
            if (tripulacao[i].getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    public void adicionar(Tripulante tripulante) {
        tripulacao[quantidade] = tripulante;
        quantidade++;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Tripulante[] getVetor() {
        return tripulacao;
    }

    // Cópia dos primeiros "tamanho" tripulantes para um vetor novo,
    // assim cada algoritmo ordena a sua própria cópia dos mesmos dados
    public Tripulante[] copiar(int tamanho) {
        return Arrays.copyOf(tripulacao, tamanho);
    }
}
