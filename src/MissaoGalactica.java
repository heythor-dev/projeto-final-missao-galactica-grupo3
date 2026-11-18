import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


public class MissaoGalactica {

    // Dados da estação
    static Tripulante[] tripulacao = new Tripulante[500]; // vetor de tripulantes
    static int quantidade = 0;                            // quantos estão registrados

    // Contadores dos algoritmos
    static int comparacoes = 0;
    static int trocas = 0;          // no Insertion Sort conta os deslocamentos

    // Quando for true, os algoritmos narram cada passo na tela
    static boolean passoAPasso = false;

    static Scanner sc = new Scanner(System.in);
    static Random sorteio = new Random();

    //  MAIN E MENU
    public static void main(String[] args) {
        carregarArquivo("tripulantes.txt");

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            if (opcao == 1) {
                registrarTripulante();
            } else if (opcao == 2) {
                listarTripulantes(tripulacao, quantidade);
            } else if (opcao == 3) {
                organizarEmbarque();
            } else if (opcao == 4) {
                modoPassoAPasso();
            } else if (opcao == 5) {
                grandeCorrida();
            } else if (opcao == 0) {
                System.out.println("Missão encerrada. Boa viagem de volta, cadetes!");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void mostrarMenu() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║             MISSÃO GALÁCTICA              ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║ 1 - Registrar tripulante                  ║");
        System.out.println("║ 2 - Listar tripulantes                    ║");
        System.out.println("║ 3 - Organizar embarque na cápsula         ║");
        System.out.println("║ 4 - Modo passo a passo                    ║");
        System.out.println("║ 5 - A Grande Corrida                      ║");
        System.out.println("║ 0 - Encerrar a missão                     ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    // Lê um número inteiro do teclado (repete se a pessoa digitar letras)
    static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Digite apenas números: ");
        }
        int numero = sc.nextInt();
        sc.nextLine(); // limpa o Enter que sobrou
        return numero;
    }

    //  DADOS: ARQUIVO, REGISTRO E LISTAGEM

    // Lê o arquivo no formato: codigo;nome;planeta;funcao
    static void carregarArquivo(String nomeArquivo) {
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
            System.out.println(quantidade + " tripulantes chegaram à Estação Órbita-7!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + nomeArquivo + " não encontrado. A estação começa vazia.");
        }
    }

    // Verifica se um código galáctico já está sendo usado
    static boolean codigoExiste(int codigo) {
        for (int i = 0; i < quantidade; i++) {
            if (tripulacao[i].codigo == codigo) {
                return true;
            }
        }
        return false;
    }

    static void registrarTripulante() {
        if (quantidade == tripulacao.length) {
            System.out.println("A estação está lotada!");
            return;
        }

        int codigo;
        boolean valido;
        do {
            codigo = lerInteiro("Código galáctico (8 dígitos): ");
            valido = true;
            if (codigo < 10000000 || codigo > 99999999) {
                System.out.println("O código precisa ter exatamente 8 dígitos!");
                valido = false;
            } else if (codigoExiste(codigo)) {
                System.out.println("Esse código já pertence a outro tripulante!");
                valido = false;
            }
        } while (!valido);

        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Planeta natal: ");
        String planeta = sc.nextLine();
        System.out.print("Função a bordo: ");
        String funcao = sc.nextLine();

        tripulacao[quantidade] = new Tripulante(codigo, nome, planeta, funcao);
        quantidade++;
        System.out.println(nome + " foi registrado(a) na estação!");
    }

    static void listarTripulantes(Tripulante[] vetor, int tamanho) {
        System.out.println();
        System.out.println(completar("Nº", 5) + completar("Código", 11) + completar("Nome", 25)
                + completar("Planeta", 13) + "Função");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < tamanho; i++) {
            System.out.println(completar("" + (i + 1), 5) + completar("" + vetor[i].codigo, 11)
                    + completar(vetor[i].nome, 25) + completar(vetor[i].planeta, 13) + vetor[i].funcao);
        }
        System.out.println("Total: " + tamanho + " tripulantes");
    }

    // Acrescenta espaços no fim do texto até ele ter "largura" letras.
    // Serve para deixar as colunas das tabelas alinhadas.
    static String completar(String texto, int largura) {
        while (texto.length() < largura) {
            texto = texto + " ";
        }
        return texto;
    }

    // Copia os primeiros "tamanho" tripulantes para um vetor novo,
    // assim cada algoritmo ordena a sua própria cópia dos mesmos dados
    static Tripulante[] copiarVetor(Tripulante[] origem, int tamanho) {
        Tripulante[] copia = new Tripulante[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = origem[i];
        }
        return copia;
    }

    // Texto curto de um tripulante: "Zibo Zumbido (50318842)"
    static String descrever(Tripulante t) {
        return t.nome + " (" + t.codigo + ")";
    }

    // Mostra a fila da cápsula (só os códigos), usada no passo a passo
    static void mostrarFila(Tripulante[] vetor, int tamanho) {
        System.out.print("   Fila da cápsula: ");
        for (int i = 0; i < tamanho; i++) {
            System.out.print(vetor[i].codigo + " ");
        }
        System.out.println();
    }

    //  PARTE 1 - ALGORITMOS DE ORDENAÇÃO
    // BUBBLE SORT
    static void bubbleSort(Tripulante[] vetor, int tamanho) {
        Tripulante temp;
        for (int i = 0; i < tamanho - 1; i++) {
            boolean houveTroca = false;

            if (passoAPasso) {
                System.out.println();
                System.out.println("★ Bubble Sort · passagem " + (i + 1));
            }

            for (int j = 1; j < tamanho - i; j++) {
                comparacoes++;
                if (vetor[j - 1].codigo > vetor[j].codigo) {
                    if (passoAPasso) {
                        System.out.println(descrever(vetor[j - 1]) + " > " + descrever(vetor[j])
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
                        System.out.println(descrever(vetor[j - 1]) + " > " + descrever(vetor[j])
                                + "? NÃO → " + vetor[j - 1].nome + " fica onde está.");
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
    }

    // INSERTION SORT
    static void insertionSort(Tripulante[] vetor, int tamanho) {
        Tripulante chave;
        int j;
        for (int i = 1; i < tamanho; i++) {
            chave = vetor[i];
            j = i - 1;

            if (passoAPasso) {
                System.out.println();
                System.out.println("★ Insertion Sort · rodada " + i + " · chave: " + descrever(chave));
            }

            // mesmo que: while (j >= 0 && vetor[j].codigo > chave.codigo)
            // separado em um if para conseguir contar cada comparação
            boolean continuar = true;
            while (j >= 0 && continuar) {
                comparacoes++;
                if (vetor[j].codigo > chave.codigo) {
                    if (passoAPasso) {
                        System.out.println(descrever(vetor[j]) + " > " + descrever(chave)
                                + "? SIM → " + vetor[j].nome + " anda uma poltrona para a direita!");
                    }
                    vetor[j + 1] = vetor[j];
                    trocas++; // deslocamento
                    j = j - 1;
                } else {
                    if (passoAPasso) {
                        System.out.println(descrever(vetor[j]) + " > " + descrever(chave)
                                + "? NÃO → " + vetor[j].nome + " fica onde está.");
                    }
                    continuar = false;
                }
            }
            vetor[j + 1] = chave;

            if (passoAPasso) {
                System.out.println(chave.nome + " senta na poltrona " + (j + 2)
                        + " → comparações: " + comparacoes + " | deslocamentos: " + trocas);
                mostrarFila(vetor, tamanho);
            }
        }
    }

    // QUICK SORT
    static void quickSort(Tripulante[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo - 1);
            quickSort(vetor, posicaoPivo + 1, fim);
        }
    }

    static int separar(Tripulante[] vetor, int inicio, int fim) {
        Tripulante pivo = vetor[inicio];
        int i = inicio + 1;
        int f = fim;

        if (passoAPasso) {
            System.out.println();
            System.out.println("★ Quick Sort · quicksort(vetor, " + inicio + ", " + fim + ") · pivô: "
                    + descrever(pivo));
        }

        while (i <= f) {
            // mesmo que: while (i <= fim && vetor[i].codigo <= pivo.codigo) i++
            boolean continuar = true;
            while (i <= fim && continuar) {
                comparacoes++;
                if (vetor[i].codigo <= pivo.codigo) {
                    i = i + 1;
                } else {
                    continuar = false;
                }
            }

            // mesmo que: while (f > inicio && vetor[f].codigo > pivo.codigo) f--
            continuar = true;
            while (f > inicio && continuar) {
                comparacoes++;
                if (vetor[f].codigo > pivo.codigo) {
                    f = f - 1;
                } else {
                    continuar = false;
                }
            }

            if (i < f) {
                if (passoAPasso) {
                    System.out.println(descrever(vetor[i]) + " e " + descrever(vetor[f])
                            + " estão do lado errado do pivô → trocam de assento!");
                }
                Tripulante troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                trocas++;
                i = i + 1;
                f = f - 1;
            }
        }

        // coloca o pivô na posição correta
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        if (f != inicio) {
            trocas++; // só conta se o pivô realmente mudou de lugar
            if (passoAPasso) {
                System.out.println("Pivô " + pivo.nome + " troca de assento com " + vetor[inicio].nome
                        + " e senta na posição " + f + ".");
            }
        } else {
            if (passoAPasso) {
                System.out.println("Pivô " + pivo.nome + " já está no lugar certo (posição " + f + ").");
            }
        }

        if (passoAPasso) {
            System.out.println("   comparações: " + comparacoes + " | trocas: " + trocas);
            mostrarFila(vetor, vetor.length);
        }
        return f;
    }

    // Zera os contadores e roda o algoritmo escolhido (1, 2 ou 3)
    static void executarAlgoritmo(int algoritmo, Tripulante[] vetor, int tamanho) {
        comparacoes = 0;
        trocas = 0;
        if (algoritmo == 1) {
            bubbleSort(vetor, tamanho);
        } else if (algoritmo == 2) {
            insertionSort(vetor, tamanho);
        } else {
            quickSort(vetor, 0, tamanho - 1);
        }
    }

    static String nomeAlgoritmo(int algoritmo) {
        if (algoritmo == 1) {
            return "Bubble Sort";
        } else if (algoritmo == 2) {
            return "Insertion Sort";
        } else {
            return "Quick Sort";
        }
    }

    static int escolherAlgoritmo(boolean permitirTodos) {
        System.out.println("1 - Bubble Sort");
        System.out.println("2 - Insertion Sort");
        System.out.println("3 - Quick Sort");
        if (permitirTodos) {
            System.out.println("4 - Os três (comparar)");
        }
        int maximo = 3;
        if (permitirTodos) {
            maximo = 4;
        }
        int escolha;
        do {
            escolha = lerInteiro("Algoritmo: ");
            if (escolha < 1 || escolha > maximo) {
                System.out.println("Opção inválida!");
            }
        } while (escolha < 1 || escolha > maximo);
        return escolha;
    }

    //  OPÇÃO 3 - ORGANIZAR EMBARQUE NA CÁPSULA
    static void organizarEmbarque() {
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }
        int escolha = escolherAlgoritmo(true);
        Tripulante[] ordenado = null;

        System.out.println();
        for (int alg = 1; alg <= 3; alg++) {
            if (escolha == alg || escolha == 4) {
                Tripulante[] copia = copiarVetor(tripulacao, quantidade);
                executarAlgoritmo(alg, copia, quantidade);
                String nomeTroca = "trocas";
                if (alg == 2) {
                    nomeTroca = "deslocamentos";
                }
                System.out.println(completar(nomeAlgoritmo(alg), 15) + " → comparações: " + comparacoes
                        + " | " + nomeTroca + ": " + trocas);
                ordenado = copia;
            }
        }

        System.out.println();
        System.out.println("Ordem de embarque na cápsula de retorno:");
        listarTripulantes(ordenado, quantidade);
    }

    //  OPÇÃO 4 - MODO PASSO A PASSO (até 10 tripulantes)
    static void modoPassoAPasso() {
        if (quantidade == 0) {
            System.out.println("Nenhum tripulante registrado!");
            return;
        }
        int tamanho = quantidade;
        if (tamanho > 10) {
            tamanho = 10;
            System.out.println("A cápsula de treino tem 10 assentos: embarcam os 10 primeiros da lista.");
        }

        int alg = escolherAlgoritmo(false);
        Tripulante[] copia = copiarVetor(tripulacao, tamanho);

        System.out.println();
        System.out.println("Fila inicial:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + descrever(copia[i]));
        }

        passoAPasso = true;
        executarAlgoritmo(alg, copia, tamanho);
        passoAPasso = false;

        System.out.println();
        System.out.println("=== " + nomeAlgoritmo(alg) + " terminou! ===");
        System.out.println("Total → comparações: " + comparacoes + " | trocas: " + trocas);
        System.out.println("Fila final da cápsula:");
        for (int i = 0; i < tamanho; i++) {
            System.out.println("  " + (i + 1) + ". " + descrever(copia[i]));
        }
    }

    //  PARTE 2 - A GRANDE CORRIDA
    // Partes para montar nomes aleatórios
    static String[] primeirosNomes = {"Zibo", "Quark", "Lumi", "Gloop", "Pixel", "Plim", "Fafá", "Orbi",
            "Tico", "Kiko", "Zuzu", "Mimi", "Gugu", "Pipoca", "Xuxu", "Bóris", "Lelé", "Tetê", "Dodó", "Pingo"};
    static String[] sobrenomes = {"Zumbido", "Lunar", "Gelatina", "Pulsar", "Vortex", "Foguete", "Orbital",
            "Meteoro", "Galáxia", "Supernova", "Quasar", "Cratera", "Nebular", "Binário", "Cósmico"};
    static String[] planetas = {"Marte", "Júpiter", "Plutão", "Vênus", "Mercúrio", "Vega", "Netuno",
            "Saturno", "Urano", "Órion", "Sírius", "Andrômeda", "Kepler"};
    static String[] funcoes = {"Piloto", "Navegador", "Cozinheiro", "Mecânico", "Cientista", "Médico",
            "Astrônomo", "Comunicações", "Botânico", "Faxineiro estelar"};

    // Gera n tripulantes aleatórios com códigos únicos.
    // situacao: 0 = aleatório, 1 = já ordenado, 2 = ordem inversa
    static Tripulante[] gerarTripulantes(int n, int situacao) {
        int[] codigos = new int[n];

        if (situacao == 0) {
            // códigos sorteados, sem repetir
            for (int i = 0; i < n; i++) {
                int novo;
                boolean repetido;
                do {
                    novo = 10000000 + sorteio.nextInt(90000000); // de 10000000 a 99999999
                    repetido = false;
                    for (int k = 0; k < i; k++) {
                        if (codigos[k] == novo) {
                            repetido = true;
                        }
                    }
                } while (repetido);
                codigos[i] = novo;
            }
        } else {
            // cada código é o anterior + um salto de 1 a 8000 → sempre crescente e sem repetir
            int atual = 10000000;
            for (int i = 0; i < n; i++) {
                atual = atual + 1 + sorteio.nextInt(8000);
                if (situacao == 1) {
                    codigos[i] = atual;          // já ordenado (crescente)
                } else {
                    codigos[n - 1 - i] = atual;  // ordem inversa (decrescente)
                }
            }
        }

        Tripulante[] vetor = new Tripulante[n];
        for (int i = 0; i < n; i++) {
            String nome = primeirosNomes[sorteio.nextInt(primeirosNomes.length)] + " "
                    + sobrenomes[sorteio.nextInt(sobrenomes.length)];
            String planeta = planetas[sorteio.nextInt(planetas.length)];
            String funcao = funcoes[sorteio.nextInt(funcoes.length)];
            vetor[i] = new Tripulante(codigos[i], nome, planeta, funcao);
        }
        return vetor;
    }

    static void grandeCorrida() {
        int[] tamanhos = {50, 1000, 10000};
        String[] situacoes = {"Aleatório", "Já ordenado", "Ordem inversa"};

        // linha = algoritmo * 3 + situação (9 linhas, como na tabela do enunciado)
        // coluna = tamanho (n = 50, 1.000, 10.000)
        double[][] tempos = new double[9][3];
        int[][] tabelaComparacoes = new int[9][3];
        int[][] tabelaTrocas = new int[9][3];

        System.out.println();
        System.out.println("A Grande Corrida vai começar!");

        for (int t = 0; t < 3; t++) {
            for (int s = 0; s < 3; s++) {
                // os três algoritmos correm com os MESMOS dados (cada um com sua cópia)
                Tripulante[] base = gerarTripulantes(tamanhos[t], s);

                for (int alg = 1; alg <= 3; alg++) {
                    Tripulante[] copia = copiarVetor(base, tamanhos[t]);

                    long inicio = System.nanoTime();
                    executarAlgoritmo(alg, copia, tamanhos[t]);
                    long fim = System.nanoTime();

                    int linha = (alg - 1) * 3 + s;
                    tempos[linha][t] = (fim - inicio) / 1000000.0; // nanossegundos → milissegundos
                    tabelaComparacoes[linha][t] = comparacoes;
                    tabelaTrocas[linha][t] = trocas;
                }
            }
            System.out.println("  n = " + tamanhos[t] + " concluído!");
        }

        // Tabela de tempos
        System.out.println();
        System.out.println("TEMPO (ms)");
        imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(completar(nomeAlgoritmo(linha / 3 + 1), 17) + completar(situacoes[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                // arredonda para 3 casas depois da vírgula
                double tempoArredondado = Math.round(tempos[linha][t] * 1000) / 1000.0;
                System.out.print(completar("" + tempoArredondado, 15));
            }
            System.out.println();
        }

        // Tabela de comparações
        System.out.println();
        System.out.println("COMPARAÇÕES");
        imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(completar(nomeAlgoritmo(linha / 3 + 1), 17) + completar(situacoes[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(completar("" + tabelaComparacoes[linha][t], 15));
            }
            System.out.println();
        }

        // Tabela de trocas
        System.out.println();
        System.out.println("TROCAS (deslocamentos no Insertion Sort)");
        imprimirCabecalho();
        for (int linha = 0; linha < 9; linha++) {
            System.out.print(completar(nomeAlgoritmo(linha / 3 + 1), 17) + completar(situacoes[linha % 3], 16));
            for (int t = 0; t < 3; t++) {
                System.out.print(completar("" + tabelaTrocas[linha][t], 15));
            }
            System.out.println();
        }
    }

    static void imprimirCabecalho() {
        System.out.println(completar("Algoritmo", 17) + completar("Situação", 16) + completar("n = 50", 15)
                + completar("n = 1.000", 15) + "n = 10.000");
        System.out.println("------------------------------------------------------------------------------");
    }
}
