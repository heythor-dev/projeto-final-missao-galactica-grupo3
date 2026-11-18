// Representa um tripulante da Estação Espacial Órbita-7.
// É por "codigo" (código galáctico de 8 dígitos) que a tripulação é ordenada.
public class Tripulante {

    int codigo;      // código galáctico (8 dígitos, nunca se repete)
    String nome;     // nome do tripulante
    String planeta;  // planeta natal
    String funcao;   // função a bordo

    public Tripulante(int codigo, String nome, String planeta, String funcao) {
        this.codigo = codigo;
        this.nome = nome;
        this.planeta = planeta;
        this.funcao = funcao;
    }
}
