
int sirkaHerniPlochy = 30;
int vyskaHerniPlochy = 30;

class Generace extends BitSet {

    boolean jeTamZivot(int x, int y) {
        return x >= 0 && y >= 0 && x < sirkaHerniPlochy && get(x + y * sirkaHerniPlochy);
    }

    void nastavHodnotu(int x, int y, boolean život) {
        set(x + y * sirkaHerniPlochy, život);
    }

    int spocitejSousedy(int x, int y) {
        int sousedi = 0;
        for (var sy = y - 1; sy <= y + 1; sy++) {
            for (var sx = x - 1; sx <= x + 1; sx++) {
                if (jeTamZivot(sx, sy)) sousedi++;
            }
        }
        if (jeTamZivot(x, y)) sousedi--;
        return sousedi;
    }
}

void main() throws InterruptedException {
    var novyZivot = "o ";
    var zivot = "O ";
    var smrt = "X ";
    var nic = ". ";

    var pocatecniStav =
    """
    OO
    OO   OO
        O  O
         OO      O
                  O
    OO          OOO
    O   O
    O  OOO
        O      O O
               O O
               O O
            OOO   OOO

            OOO   OOO
               O O
               O O
               O O
    """.split(System.lineSeparator());

    var staraGenerace = new Generace();
    for (var y = 0; y < pocatecniStav.length; y++) {
        var radek = pocatecniStav[y];
        for (var x = 0; x < radek.length(); x++) {
            staraGenerace.nastavHodnotu(x, y, radek.charAt(x) != ' ');
        }
    }

    var cisloGenerace = 1;
    while(true) {
        println("Generace " + cisloGenerace++);
        var novaGenerace = new Generace();
        for (var y = 0; y < sirkaHerniPlochy; y++) {
            for (var x = 0; x < vyskaHerniPlochy; x++) {
                var pocetSousedu = staraGenerace.spocitejSousedy(x, y);
                if (staraGenerace.jeTamZivot(x, y)) {
                    if (pocetSousedu < 2 || pocetSousedu > 3) {
                        //málo nebo naopak moc sousedů zahubí stávající život
                        novaGenerace.nastavHodnotu(x, y, false);
                        print(smrt);
                    } else {
                        //při optimálním počtu sousedů se přežívá do další generace
                        novaGenerace.nastavHodnotu(x, y, true);
                        print(zivot);
                    }
                } else {
                    if (pocetSousedu == 3) {
                        //ideální prázdné místo pro zrození nového života
                        novaGenerace.nastavHodnotu(x, y, true);
                        print(novyZivot);
                    } else {
                        //nic tu nebylo a ani v další generaci nebude
                        novaGenerace.nastavHodnotu(x, y, false);
                        print(nic);
                    }
                }
            }
            println("");
        }
        if (staraGenerace.equals(novaGenerace)) {
            println("Konec - žádná další změna.");
            return;
        }
        staraGenerace = novaGenerace;
        Thread.sleep(200);
    }
}