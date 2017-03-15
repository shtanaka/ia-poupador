package algoritmo;

public class Ladrao extends ProgramaLadrao {

    private int decisao;
    private int countNaoAchaNinguem;
    private int id;
    protected static int idGenerator = 0;

    public Ladrao() {
        super();
        id = idGenerator++;
        id = id - 4;
    }

    public int acao() {

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
                if (id == 0)
                    System.out.println(id + ":UM POUPADOR PASSOU NA CASA "
                            + i + " HA " + ambienteOlfato[i] + " RODADAS!");
                return i;
            }
        }
        return -1;
    }

    private int seesPoupador() {
        int[] visao = sensor.getVisaoIdentificacao();
        for (int i = 0; i < visao.length; i++) {
            if (visao[i] == 100) {
                countNaoAchaNinguem = 0;
                if (id == 0)
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
                    if (id == 0)
                        System.out.println(id + ":CONTORNAR PAREDE!");
                    return true;
                }
        }
        return false;
    }


    private int contornarParede() {
        return 0;
    }

    private int seguirPoupador() {
        return 0;
    }

    private int procurarPoupador() {
        return 0;
    }

    private int procurarAleatoriamente() {
        return 0;

    }
}