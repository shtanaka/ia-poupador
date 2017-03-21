package algoritmo;

import java.awt.*;

public class Ladrao extends ProgramaLadrao {

    private final int MUDAR_REGIAO = 0;
    private final int PROCURAR_POUPADOR = 1;
    private final int SEGUIR_POUPADOR = 2;
    private final int DEIXAR_DE_SEGUIR = 3;

    private Probabilidade probabilidades[];

    private static int idGenerator = 0;

    private int id;
    private Cell mapa[][];
    private MapView mapview;

    public Ladrao() {
        super();

        // Gera ID para print
        id = idGenerator++;
        id = id - 4;

        // Gera o mapa
        mapa = new Cell[30][30];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                mapa[i][j] = new Cell(-88, 0);
            }
        }

        // Gera as probabilidades
        probabilidades = new Probabilidade[4];
        for (int i = 0; i < 4; i++) {
            probabilidades[i] = new Probabilidade();
        }

        /*probabilidades[MUDAR_REGIAO] = new Probabilidade();
        probabilidades[PROCURAR_POUPADOR] = new Probabilidade();
        probabilidades[SEGUIR_POUPADOR] = new Probabilidade();
        probabilidades[DEIXAR_DE_SEGUIR] = new Probabilidade();
        */

        // Gera MapView apenas para 1 dos agentes
        if (id == 1)
            mapview = new MapView();

    }

    public int acao() {

        if (id == 1) {
            Point posicao = sensor.getPosicao();
            updateMapa();
            mapview.update(mapa, posicao.x, posicao.y, true, false);
        }

        int decisao = acharDecisao();
        if (id == 1) {
            if (decisao == 2) {
                System.err.println(decisao);
            } else
                System.out.println(decisao);
        }

        switch (decisao) {
            case 0:
                return procurarPoupador();
            case 1:
                return seguirPoupador();
            case 2:
                return mudarRegiao();
        }

        return (int) (Math.random() * 5);

    }

    private int acharDecisao() {

        analisarVisao();
        analisarOlfato();

        return sortearDecisao();

    }

    private void analisarVisao() {
        int[] visao = sensor.getVisaoIdentificacao();
        boolean hasPoupador = false;
        for (int i = 0; i < visao.length; i++) {
            if (visao[i] == 100 || visao[i] == 110) {
                probabilidades[SEGUIR_POUPADOR].somarProbabilidade(80);
                probabilidades[MUDAR_REGIAO].subtrairProbabilidade(50);
                probabilidades[PROCURAR_POUPADOR].subtrairProbabilidade(80);
                hasPoupador = true;
            }
        }
        if (!hasPoupador)
            probabilidades[MUDAR_REGIAO].somarProbabilidade(1);
    }

    private void analisarOlfato() {
        int[] olfato = sensor.getAmbienteOlfatoPoupador();
        boolean hasCheiro = false;
        for (int i = 0; i < olfato.length; i++) {
            if (olfato[i] > 0) {
                probabilidades[PROCURAR_POUPADOR].somarProbabilidade(20 * (6 - olfato[i]));
                probabilidades[MUDAR_REGIAO].subtrairProbabilidade(100);
                hasCheiro = true;
            }
        }
        if (!hasCheiro)
            probabilidades[MUDAR_REGIAO].somarProbabilidade(5);
    }

    private int sortearDecisao() {

        double totalProbabilidade = probabilidades[PROCURAR_POUPADOR].getProbabilidade()
                + probabilidades[SEGUIR_POUPADOR].getProbabilidade()
                + probabilidades[MUDAR_REGIAO].getProbabilidade();
        double probPP = probabilidades[PROCURAR_POUPADOR].getProbabilidade() / totalProbabilidade;
        double probSP = probabilidades[SEGUIR_POUPADOR].getProbabilidade() / totalProbabilidade;
        double probMR = probabilidades[MUDAR_REGIAO].getProbabilidade() / totalProbabilidade;

        double fatorProbabilidade = Math.random();

        if (fatorProbabilidade < probPP) return PROCURAR_POUPADOR;
        else if (fatorProbabilidade < probPP + probSP) return SEGUIR_POUPADOR;
        else if (fatorProbabilidade < probPP + probSP + probMR) return MUDAR_REGIAO;

        return (int) (Math.random() * 5);

    }

    private int mudarRegiao() {
        probabilidades[SEGUIR_POUPADOR].zerarProbabilidade();
        return (int) (Math.random() * 5);
    }

    private int seguirPoupador() {

        return (int) (Math.random() * 5);
    }

    private int procurarPoupador() {
        return (int) (Math.random() * 5);
    }

    private void updateMapa() {

        int[] visao = sensor.getVisaoIdentificacao();
        Point posicao = sensor.getPosicao();
        int x = posicao.x;
        int y = posicao.y;
        int aux = 0;
        for (int linha = 0; linha < 5; linha++) {
            for (int coluna = 0; coluna < 5; coluna++) {
                if (linha * 5 + coluna != 12) {
                    if (visao[linha * 5 + coluna - aux] >= 0)
                        mapa[y - 2 + linha][x - 2 + coluna].v = visao[linha * 5 + coluna - aux];
                } else {
                    aux++;
                }
            }
        }


    }
}