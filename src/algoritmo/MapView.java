package algoritmo;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class MapView extends JFrame {


    public static class Cell {
        int v;
        int f;

        Cell(int v, int f) {
            this.v = v;
            this.f = f;
        }
    }


    JLabel[][] map;
    JLabel coordinates;

    public MapView() {

        super("Map View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setBackground(Color.WHITE);
        int x = 0;
        int y = 0;
        map = new JLabel[30][30];
        int size = 15;

        for (int i = 0; i < 30; i++) {

            for (int j = 0; j < 30; j++) {
                JLabel l = new JLabel("");
                l.setBounds(x, y, 15, 15);
                l.setOpaque(true);
                x += size;
                l.setForeground(Color.MAGENTA);
                map[i][j] = l;
                this.add(l);
            }
            x = 0;
            y += size;
        }
        coordinates = new JLabel();
        coordinates.setBounds(x, y, 100,30);
        coordinates.setForeground(Color.black);
        coordinates.setBackground(Color.white);
        this.add(coordinates);
        setSize(size * 33 , size * 35 );
        this.setVisible(true);
    }


    public void update(Cell map[][], int pX, int pY, boolean isLadrao, boolean showFrequencias) {

        coordinates.setText(pX + ", " + pY);

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map.length; j++) {

                int v = map[j][i].v;

                if (v == -88) this.map[j][i].setBackground(Color.gray);
                if (v == 0) this.map[j][i].setBackground(Color.BLACK);
                if (v == 4) this.map[j][i].setBackground(Color.YELLOW);
                if (v == 1) this.map[j][i].setBackground(Color.BLUE);
                if (v == 3) this.map[j][i].setBackground(Color.green);
                if (v == 5) this.map[j][i].setBackground(Color.CYAN);
                if (v == 100 || v == 110) this.map[j][i].setBackground(Color.RED);
                if (v == 200 || v == 210 || v == 220 || v == 230) this.map[j][i].setBackground(Color.WHITE);

                if (showFrequencias) {
                    this.map[j][i].setText(map[j][i].f + "");
                } else {
                    this.map[j][i].setText("");
                }
            }
        }

        if (isLadrao) {
            this.map[pY][pX].setBackground(Color.white);
        } else {
            this.map[pY][pX].setBackground(Color.red);
        }
    }


}
