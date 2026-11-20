package dados;

import modelo.Tripulante;

import java.util.Random;

// Gera os tripulantes usados na Grande Corrida.
public class GeradorTripulantes {

    private static final Random SORTEIO = new Random(1990);

    // Partes para montar nomes aleatórios
    private static final String[] PRIMEIROS_NOMES = {"Zibo", "Quark", "Lumi", "Gloop", "Pixel", "Plim", "Fafá", "Orbi",
            "Tico", "Kiko", "Zuzu", "Mimi", "Gugu", "Pipoca", "Xuxu", "Bóris", "Lelé", "Tetê", "Dodó", "Pingo"};
    private static final String[] SOBRENOMES = {"Zumbido", "Lunar", "Gelatina", "Pulsar", "Vortex", "Foguete", "Orbital",
            "Meteoro", "Galáxia", "Supernova", "Quasar", "Cratera", "Nebular", "Binário", "Cósmico"};
    private static final String[] PLANETAS = {"Marte", "Júpiter", "Plutão", "Vênus", "Mercúrio", "Vega", "Netuno",
            "Saturno", "Urano", "Órion", "Sírius", "Andrômeda", "Kepler"};
    private static final String[] FUNCOES = {"Piloto", "Navegador", "Cozinheiro", "Mecânico", "Cientista", "Médico",
            "Astrônomo", "Comunicações", "Botânico", "Faxineiro estelar"};

    // Situações dos dados gerados
    public static final int ALEATORIO = 0;
    public static final int JA_ORDENADO = 1;
    public static final int ORDEM_INVERSA = 2;

    private GeradorTripulantes() {
    }

    // Gera n tripulantes com códigos únicos.
    public static Tripulante[] gerar(int n, int situacao) {
        int[] codigos = new int[n];

        if (situacao == ALEATORIO) {
            // códigos sorteados, sem repetir
            for (int i = 0; i < n; i++) {
                int novo;
                boolean repetido;
                do {
                    novo = 10000000 + SORTEIO.nextInt(90000000); // de 10000000 a 99999999
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
                atual = atual + 1 + SORTEIO.nextInt(8000);
                if (situacao == JA_ORDENADO) {
                    codigos[i] = atual;          // já ordenado (crescente)
                } else {
                    codigos[n - 1 - i] = atual;  // ordem inversa (decrescente)
                }
            }
        }

        Tripulante[] vetor = new Tripulante[n];
        for (int i = 0; i < n; i++) {
            String nome = PRIMEIROS_NOMES[SORTEIO.nextInt(PRIMEIROS_NOMES.length)] + " "
                    + SOBRENOMES[SORTEIO.nextInt(SOBRENOMES.length)];
            String planeta = PLANETAS[SORTEIO.nextInt(PLANETAS.length)];
            String funcao = FUNCOES[SORTEIO.nextInt(FUNCOES.length)];
            vetor[i] = new Tripulante(codigos[i], nome, planeta, funcao);
        }
        return vetor;
    }
}
