package algoritmo;

public class Probabilidade {

    private int probabilidade;

    public Probabilidade() {
        this.probabilidade = 0;
    }

    public int getProbabilidade() {
        return probabilidade;
    }

    public void somarProbabilidade(int probabilidade) {
        this.probabilidade += probabilidade;
    }

    public void subtrairProbabilidade(int probabilidade) {
        if(this.probabilidade - probabilidade > 0)
            this.probabilidade -= probabilidade;
        else
            this.probabilidade = 0;
    }

    public void zerarProbabilidade() {
        this.probabilidade = 0;
    }
}
