package modelo;

// Representa um tripulante da Estação Espacial Órbita-7.
// É por "codigo" (código galáctico de 8 dígitos) que a tripulação é ordenada.
public class Tripulante {

    private final int codigo;      // código galáctico (8 dígitos, nunca se repete)
    private final String nome;     // nome do tripulante
    private final String planeta;  // planeta natal
    private final String funcao;   // função a bordo

    public Tripulante(int codigo, String nome, String planeta, String funcao) {
        this.codigo = codigo;
        this.nome = nome;
        this.planeta = planeta;
        this.funcao = funcao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getPlaneta() {
        return planeta;
    }

    public String getFuncao() {
        return funcao;
    }

    // Texto curto de um tripulante: "Zibo Zumbido (50318842)"
    public String descricao() {
        return nome + " (" + codigo + ")";
    }
}
