
String[] hriste = """
###############################################
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
###############################################
""".split(System.lineSeparator());

char znackaKarla = '>';
int domaciPoziceX = 1;
int domaciPoziceY = 20;
int poziceKarlaX = domaciPoziceX;
int poziceKarlaY = domaciPoziceY;
int pauza = 200;

void chvilkuPockej() {
    try {
        Thread.sleep(pauza);
    } catch (InterruptedException ex) {
    }
}

void nakresliHriste() {
    for (int y = 0; y < hriste.length; y++) {
        String radek = hriste[y];
        for (int x = 0; x < radek.length(); x++) {
            if (x == poziceKarlaX && y == poziceKarlaY) {
                IO.print(znackaKarla);
            } else {
                IO.print(radek.charAt(x));
            }
        }
        IO.println("");
    }
    IO.println("");
    chvilkuPockej();
}

boolean sever() {
    return znackaKarla == '^';
}

boolean jih() {
    return znackaKarla == 'v';
}

boolean vychod() {
    return znackaKarla == '>';
}

boolean zapad() {
    return znackaKarla == '<';
}

void vlevoVbok() {
    if (vychod()) {
        znackaKarla = '^';
    } else if (sever()) {
        znackaKarla = '<';
    } else if (zapad()) {
        znackaKarla = 'v';
    } else {
        znackaKarla = '>';
    }
    nakresliHriste();
}

boolean zed() {
    int novaPoziceX = poziceKarlaX;
    int novaPoziceY = poziceKarlaY;
    if (vychod()) {
        novaPoziceX++;
    } else if (sever()) {
        novaPoziceY--;
    } else if (zapad()) {
        novaPoziceX--;
    } else {
        novaPoziceY++;
    }
    return hriste[novaPoziceY].charAt(novaPoziceX) == '#';
}

void krok() {
    if (zed()) {
        throw new RuntimeException("Au, narazil jsem do zdi!");
    }
    if (vychod()) {
        poziceKarlaX++;
    } else if (sever()) {
        poziceKarlaY--;
    } else if (zapad()) {
        poziceKarlaX--;
    } else {
        poziceKarlaY++;
    }
    nakresliHriste();
}

void dojdiKeZdi() {
    while(!zed()) {
        krok();
    }
}

void vpravoVbok() {
    for (int i = 0; i < 3; i++) {
        vlevoVbok();
    }
}

void rychle() {
    pauza = 0;
}

void pomalu() {
    pauza = 200;
}

boolean doma() {
    return poziceKarlaX == domaciPoziceX && poziceKarlaY == domaciPoziceY;
}

void obejdiHriste() {
    do {
        krok();
        rychle();
        vpravoVbok();
        while(zed()) {
            vlevoVbok();
        }
        pomalu();
    } while(!doma());
}

void main() {
    nakresliHriste();
    obejdiHriste();
}
