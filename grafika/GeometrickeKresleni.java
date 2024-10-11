import java.awt.*;

void noveOkno(String titulekOkna, int sirkaOkna, int vyskaOkna, Consumer<GeometrickeKresleni> coMamNakreslit) {
    var okno = new javax.swing.JFrame(titulekOkna) {
        public void paint(Graphics grafika) {
            coMamNakreslit.accept(new GeometrickeKresleni(grafika));
        }
    };
    okno.setSize(sirkaOkna, vyskaOkna);
    okno.setLocationRelativeTo(null);
    okno.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    okno.setVisible(true);
}

record GeometrickeKresleni(Graphics grafika) {

    void usecka(Color barva, int x1, int y1, int x2, int y2) {
        grafika.setColor(barva);
        grafika.drawLine(x1, y1, x2, y2);
    }

    void kruznice(Color barva, int x, int y, int polomer) {
        grafika.setColor(barva);
        grafika.drawOval(x - polomer, y - polomer, 2 * polomer, 2 * polomer);
    }

    void kruh(Color barva, int x, int y, int polomer) {
        grafika.setColor(barva);
        grafika.fillOval(x - polomer, y - polomer, 2 * polomer, 2 * polomer);
    }

    void obdelnik(Color barva, int x1, int y1, int x2, int y2) {
        grafika.setColor(barva);
        grafika.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    void plnyObdelnik(Color barva, int x1, int y1, int x2, int y2) {
        grafika.setColor(barva);
        grafika.fillRect(x1, y1, x2 - x1, y2 - y1);
    }

    void trojuhelnik(Color barva, int x1, int y1, int x2, int y2, int x3, int y3) {
        grafika.setColor(barva);
        grafika.drawPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    void plnyTrojuhelnik(Color barva, int x1, int y1, int x2, int y2, int x3, int y3) {
        grafika.setColor(barva);
        grafika.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    void vybarviCelouPlochu(Color barva) {
        var hranice = grafika.getClipBounds();
        plnyObdelnik(barva, hranice.x, hranice.y, hranice.width, hranice.height);
    }

    void snehulak(int x, int y) {
        //hlava
        kruh(Color.WHITE, x, y, 20);
        //trup
        kruh(Color.WHITE, x, y + 45, 30);
        //spodní koule
        kruh(Color.WHITE, x, y + 105, 40);
        //levé oko
        kruh(Color.BLACK, x - 7, y - 7, 3);
        //pravé oko
        kruh(Color.BLACK, x + 7, y - 7, 3);
        //nos
        plnyTrojuhelnik(Color.ORANGE, x, y - 3, x - 15, y, x, y + 3);
        //pusa
        usecka(Color.BLACK, x - 5, y + 7, x + 5, y +7);
        //levá ruka
        kruh(Color.WHITE, x - 30, y + 35, 12);
        //pravá ruka
        kruh(Color.WHITE, x + 30, y + 35, 12);
        //knoflíky
        for (int i = 25; i < 105; i += 15) {
            kruh(Color.BLACK, x, y + i, 2);
        }
        //hrnec
        plnyObdelnik(Color.DARK_GRAY, x - 15, y - 35, x + 15, y - 15);
        //ucho od hrnce
        kruznice(Color.DARK_GRAY, x + 15, y - 25, 5);
    }

    void dum(int x, int y) {
        //dům
        plnyObdelnik(new Color(200, 0, 255), x - 50, y + 70, x + 50, y + 150);
        //komín
        plnyObdelnik(Color.LIGHT_GRAY, x + 20, y + 20, x + 30, y + 50);
        //střecha
        plnyTrojuhelnik(Color.RED, x - 55, y + 70, x, y + 20, x + 55, y + 70);
        //okno
        plnyObdelnik(Color.WHITE, x - 40, y + 80, x - 4, y + 110);
        //rámecky v okně
        usecka(Color.LIGHT_GRAY, x - 40, y + 95, x - 4, y + 95);
        usecka(Color.LIGHT_GRAY, x - 22, y + 80, x - 22, y + 110);
        //dveře
        plnyObdelnik(Color.DARK_GRAY, x + 10, y + 95, x + 35, y + 148);
        //klika u dveří
        kruh(Color.LIGHT_GRAY, x + 15, y + 120, 3);
        //kouř z komína
        for (int i = 25; i < 80; i += 15) {
            kruh(Color.WHITE, x + i, y + 30 - i, 12);
        }
    }

    void strom(int x, int y) {
        //větve
        for (int i = 20; i < 120; i += 30) {
            var odstinZelene = new Color(0, 200 - i, 0);
            plnyTrojuhelnik(odstinZelene, x - i, y + i, x, y + i - 40, x + i, y + i);
        }
        //kmen
        plnyObdelnik(Color.DARK_GRAY, x - 10, y + 110, x + 10, y + 140);
    }
}

void main() {
    noveOkno("Můj obrázek", 600, 500, kresleni -> {
        kresleni.vybarviCelouPlochu(Color.CYAN);
        var nahoda = new Random(1);
        for (int y = 100; y < 350; y += 5) {
            kresleni.strom(nahoda.nextInt(600), y);
        }
        kresleni.snehulak(100, 380);
        kresleni.snehulak(500, 380);
        kresleni.dum(300, 340);
    });
}
