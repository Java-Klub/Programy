
int šířkaHerníPlochy = 30;
int výškaHerníPlochy = 30;

class Generace extends BitSet {

    boolean jeTamŽivot(int x, int y) {
        return x >= 0 && y >= 0 && x < šířkaHerníPlochy && get(x + y * šířkaHerníPlochy);
    }

    void nastavHodnotu(int x, int y, boolean život) {
        set(x + y * šířkaHerníPlochy, život);
    }

    int spočítejSousedy(int x, int y) {
        int sousedi = 0;
        for (var sy = y - 1; sy <= y + 1; sy++) {
            for (var sx = x - 1; sx <= x + 1; sx++) {
                if (jeTamŽivot(sx, sy)) sousedi++;
            }
        }
        if (jeTamŽivot(x, y)) sousedi--;
        return sousedi;
    }
}

String smajlík(int kód) {
    return new String(new int[]{kód, ' '}, 0, 2);
}

void main() throws InterruptedException {
    var smajlíkNovýŽivot = smajlík(0x1F970);
    var smajlíkŽivot = smajlík(0x1F600);
    var smajlíkSmrt = smajlík(0x1F922);
    var smajlíkNic = smajlík(0x1FAE5);

    var počátečníStav = 
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

    var staráGenerace = new Generace();
    for (var y = 0; y < počátečníStav.length; y++) {
        var řádek = počátečníStav[y];
        for (var x = 0; x < řádek.length(); x++) {
            staráGenerace.nastavHodnotu(x, y, řádek.charAt(x) != ' ');
        }
    }

    var čísloGenerace = 1;
    while(true) {
        println("Generace " + čísloGenerace++);
        var nováGenerace = new Generace();
        for (var y = 0; y < šířkaHerníPlochy; y++) {
            for (var x = 0; x < výškaHerníPlochy; x++) {
                var početSousedů = staráGenerace.spočítejSousedy(x, y);
                if (staráGenerace.jeTamŽivot(x, y)) {
                    if (početSousedů < 2 || početSousedů > 3) {
                        //málo nebo naopak moc sousedů zahubí stávající život
                        nováGenerace.nastavHodnotu(x, y, false);
                        print(smajlíkSmrt);
                    } else {
                        //při optimálním počtu sousedů se přežívá do další generace
                        nováGenerace.nastavHodnotu(x, y, true);
                        print(smajlíkŽivot);
                    }
                } else {
                    if (početSousedů == 3) {
                        //ideální prázdné místo pro zrození nového života
                        nováGenerace.nastavHodnotu(x, y, true);
                        print(smajlíkNovýŽivot);
                    } else {
                        //nic tu nebylo a ani v další generaci nebude
                        nováGenerace.nastavHodnotu(x, y, false);
                        print(smajlíkNic);
                    }
                }
            }
            println("");
        }
        if (staráGenerace.equals(nováGenerace)) {
            println("Konec - žádná další změna.");
            return;
        }
        staráGenerace = nováGenerace;
        Thread.sleep(200);
    }
}