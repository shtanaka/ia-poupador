package algoritmo;

import java.awt.*;

public class Ladrao extends ProgramaLadrao {

    private static int idGenerator = 0;

    private int decisao;
    private int countNaoAchaNinguem;
    private int id;
    private MapView.Cell mapa[][];
    private MapView mapview;

    public Ladrao() {
        super();
        id = idGenerator++;
        id = id - 4;

        if (id == 1) {
            mapa = new MapView.Cell[30][30];
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    mapa[i][j] = new MapView.Cell(-88, 0);
                }
            }
            mapview = new MapView();
        }

    }

    public int acao() {

        if (id == 1) {
            Point posicao = sensor.getPosicao();
            updateMapa();
            mapview.update(mapa, posicao.x, posicao.y, true, false);
        }

        acharDecisao();

        switch (decisao) {
            case 0:
                return procurarPoupador();
            case 1:
                return seguirPoupador();
            case 2:
                return contornarParede();
            default:
                return procurarAleatoriamente();
        }

    }

    private void acharDecisao() {

        if (smellsPoupador() != -1)
            decisao = 0;
        else if (seesPoupador() != -1)
            decisao = 1;
        else if (isHoraContornarParede())
            decisao = 2;
        else
            decisao = 3;

    }

    private int smellsPoupador() {
        int[] ambienteOlfato = sensor.getAmbienteOlfatoPoupador();
        for (int i = 0; i < ambienteOlfato.length; i++) {
            if (ambienteOlfato[i] > 0) {
                if (id == 1)
                    System.out.println(id + ":UM POUPADOR PASSOU NA CASA "
                            + i + " HA " + ambienteOlfato[i] + " RODADAS!");
                return i;
            }
        }
        return -1;
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

    private int seesPoupador() {
        int[] visao = sensor.getVisaoIdentificacao();
        for (int i = 0; i < visao.length; i++) {
            if (visao[i] == 100) {
                countNaoAchaNinguem = 0;
                if (id == 1)
                    System.out.println(id + ":EXISTE UM POUPADOR NA CASA " + i + "!");
                return i;
            }
        }
        countNaoAchaNinguem++;
        return -1;
    }

    private boolean isHoraContornarParede() {
        int[] visao = sensor.getVisaoIdentificacao();
        for (int valor : visao) {
            if (valor == 1 || valor == 3 || valor == 4)
                if ((Math.random() * 100) <= countNaoAchaNinguem) {
                    if (id == 1)
                        System.out.println(id + ":CONTORNAR PAREDE!");
                    return true;
                }
        }
        return false;
    }

    private int contornarParede() {
        return (int) (Math.random() * 5);
    }

    private int seguirPoupador() {
        return (int) (Math.random() * 5);
    }

    private int procurarPoupador() {
        return (int) (Math.random() * 5);
    }

    private int procurarAleatoriamente() {
        return (int) (Math.random() * 5);
    }
}