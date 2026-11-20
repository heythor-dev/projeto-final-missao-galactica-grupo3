package dados;

import modelo.Tripulante;

import java.io.File;
import java.util.Scanner;

// Guarda a tripulação da Estação Órbita-7.
// Não conhece teclado nem tela: quem chama decide o que mostrar.
public class Estacao {

    private final Tripulante[] tripulacao = new Tripulante[500];
    private int quantidade = 0;

    // Lê o arquivo no formato: codigo;nome;planeta;funcao.
    // Devolve false se o arquivo não for encontrado ou houver erro de leitura.
    public boolean carregar(String nomeArquivo) {
        try {
            Scanner leitor = new Scanner(new File(nomeArquivo), "UTF-8");
            while (leitor.hasNextLine() && !estaLotada()) {
                String linha = leitor.nextLine().trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split(";");
                    // Ignora linhas fora do padrao para nao quebrar o programa.
                    if (partes.length == 4) {
                        int codigo = Integer.parseInt(partes[0].trim());
                        tripulacao[quantidade] = new Tripulante(
                                codigo, partes[1].trim(), partes[2].trim(), partes[3].trim());
                        quantidade++;
                    }
                }
            }
            leitor.close();
            return true;
        } catch (Exception e) {
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

    // Cópia manual com for para evitar Arrays.copyOf (fora da lista basica).
    public Tripulante[] copiar(int tamanho) {
        Tripulante[] copia = new Tripulante[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = tripulacao[i];
        }
        return copia;
    }
}