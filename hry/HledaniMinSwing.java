import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

int pocetZivotu = 3;
int pocetMin = 6;
int velikost = 6;
int pocetOdhalenychPolicek = 0;

JFrame okno;
JButton[][] tlacitka;
boolean[][] miny;
boolean[][] odhalene;

void main() {
    okno = new JFrame("Hledání min");
    okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    okno.setSize(400, 400);
    okno.setLayout(new GridLayout(velikost, velikost));

    tlacitka = new JButton[velikost][velikost];
    miny = new boolean[velikost][velikost];
    odhalene = new boolean[velikost][velikost];

    // vyrobime okno s tlacitky
    for (int i = 0; i < velikost; i++) {
        for (int j = 0; j < velikost; j++) {
            tlacitka[i][j] = new JButton("");
            tlacitka[i][j].addActionListener(new PosluchacNaTlacitku(i, j));
            okno.add(tlacitka[i][j]);
        }
    }

    // polozime miny
    Random nahoda = new Random();
    var pocet = 0;
    while (pocet < pocetMin) {
        int x = nahoda.nextInt(velikost);
        int y = nahoda.nextInt(velikost);
        if (!miny[x][y]) {
            miny[x][y] = true;
            pocet++;
        }
    }

    // zobrazime okno
    okno.setVisible(true);
}

void odhalPolicko(int x, int y) {
    if (x < 0 || y < 0 || x >= velikost || y >= velikost || odhalene[x][y]) return;

    // odhalime policko
    odhalene[x][y] = true;
    tlacitka[x][y].setEnabled(false);

    // spocitame miny v okoli
    var pocetMinOkolo = 0;
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            int nx = x + i;
            int ny = y + j;
            if (nx >= 0 && ny >= 0 && nx < velikost && ny < velikost && miny[nx][ny]) {
                pocetMinOkolo++;
            }
        }
    }

    if (pocetMinOkolo > 0) {
        // zobrazime pocet min
        tlacitka[x][y].setText(String.valueOf(pocetMinOkolo));
    } else {
        tlacitka[x][y].setText("");
        // prazdne policko odhali sve okoli
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                odhalPolicko(x + i, y + j);
            }
        }
    }

    pocetOdhalenychPolicek++;
    if (pocetMin + pocetOdhalenychPolicek == velikost * velikost) {
        for (int i = 0; i < velikost; i++) {
            for (int j = 0; j < velikost; j++) {
                if (!odhalene[i][j] && miny[i][j])
                tlacitka[i][j].setText("\uD83D\uDCA3");
                tlacitka[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(okno, "Konec hry, všechny miny jsou odkryty!");
        okno.dispose();
    }
}

class PosluchacNaTlacitku implements ActionListener {
    private int x, y;

    public PosluchacNaTlacitku(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (miny[x][y]) {
            odhalene[x][y] = true;
            tlacitka[x][y].setText("\uD83D\uDCA5");
            if (--pocetZivotu == 0) {
                JOptionPane.showMessageDialog(okno, "Konec hry, došli ti životy!");
                okno.dispose();
            } else {
                JOptionPane.showMessageDialog(okno, "Boooom! Tady byla mina, zbýva ti " + pocetZivotu + " životů!");
            }
        } else {
            odhalPolicko(x, y);
        }
    }
}
