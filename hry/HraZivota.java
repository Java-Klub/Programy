
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

String smajlik(int kód) {
    return new String(new int[]{kód, ' '}, 0, 2);
}

void main() throws InterruptedException {
    var smajlikNovyZivot = smajlik(0x1F970);
    var smajlikZivot = smajlik(0x1F600);
    var smajlikSmrt = smajlik(0x1F922);
    var smajlikNic = smajlik(0x1FAE5);

    var pocatecniStav =
    """
    XX
    XX   XX
        X  X
         XX      X
                  X
    XX          XXX
    X   X
    X  XXX
        X      X X
               X X
               X X
            XXX   XXX

            XXX   XXX
               X X
               X X
               X X
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
                        print(smajlikSmrt);
                    } else {
                        //při optimálním počtu sousedů se přežívá do další generace
                        novaGenerace.nastavHodnotu(x, y, true);
                        print(smajlikZivot);
                    }
                } else {
                    if (pocetSousedu == 3) {
                        //ideální prázdné místo pro zrození nového života
                        novaGenerace.nastavHodnotu(x, y, true);
                        print(smajlikNovyZivot);
                    } else {
                        //nic tu nebylo a ani v další generaci nebude
                        novaGenerace.nastavHodnotu(x, y, false);
                        print(smajlikNic);
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