void main() {

    var pocetZivotu = 3;
    var pocetMin = 6;

    var velikost = 6;
    var pocetPolicek = velikost * velikost;
    var zbyvaOdkryt = pocetPolicek - pocetMin - 4 * velikost + 4; //okraje a miny hráč neodkrývá

    var minovePole = new Character[pocetPolicek];
    Arrays.fill(minovePole, '0');

    //umístíme miny náhodně do hracího pole (kromě okrajů)
    var nahoda = new Random();
    while (pocetMin > 0) {
        var i = velikost * nahoda.nextInt(1, velikost - 1) + nahoda.nextInt(1, velikost - 1);
        if (minovePole[i] != '*') {
            minovePole[i] = '*';
            pocetMin--;
            for (var okoli : new int[]{-velikost - 1, -velikost, -velikost + 1, -1, 1, velikost - 1, velikost, velikost + 1}) {
                if (minovePole[i + okoli] != '*') {
                    minovePole[i + okoli]++;
                }
            }
        }
    }
    //nahradíme 0 prázdnými políčky
    for (int i = 0; i < pocetPolicek; i++) {
        if (minovePole[i] == '0') {
            minovePole[i] = ' ';
        }
    }

    var herniPole = new Character[pocetPolicek];
    Arrays.fill(herniPole, '?');

    //před začátkem hry odkryjeme okraje
    for (int i = 0; i < velikost; i++) {
        //horní okraj
        herniPole[i] = minovePole[i];
        //dolní okraj
        herniPole[pocetPolicek - i - 1] = minovePole[pocetPolicek - i - 1];
        //levý okraj
        herniPole[velikost * i] = minovePole[velikost * i];
        //pravý okraj
        herniPole[velikost * i + velikost - 1] = minovePole[velikost * i + velikost - 1];
    }

    var klavesnice = new Scanner(System.in);

    //tady začíná hlavní herní cyklus
    while (true) {
        IO.println("zbyvající počet životů: " + pocetZivotu);

        //vykreslíme minové pole
        IO.print("""
             A   B   C   D   E   F
           +---+---+---+---+---+---+
         1 | %c | %c | %c | %c | %c | %c |  1
           +---+---+---+---+---+---+
         2 | %c | %c | %c | %c | %c | %c |  2
           +---+---+---+---+---+---+
         3 | %c | %c | %c | %c | %c | %c |  3
           +---+---+---+---+---+---+
         4 | %c | %c | %c | %c | %c | %c |  4
           +---+---+---+---+---+---+
         5 | %c | %c | %c | %c | %c | %c |  5
           +---+---+---+---+---+---+
         6 | %c | %c | %c | %c | %c | %c |  6
           +---+---+---+---+---+---+
             A   B   C   D   E   F
        """.formatted((Object[])herniPole));

        //zkontrolujeme, jestli už není vše hotovo
        if (zbyvaOdkryt == 0) {
            IO.println("konec hry, vše je odkryto");
            return;
        }
        if (pocetZivotu == 0) {
            IO.println("konec hry, došli ti životy");
            return;
        }

        //zeptáme se hráče na souřadnice
        IO.println("zadej souřadnice kam si stoupneš (písmeno a číslo):");
        try {
            var souradnice = klavesnice.next("[A-Fa-f][1-6]");
            var i = souradnice.toUpperCase().charAt(0) - 'A' + velikost * (souradnice.charAt(1) - '1');
            //odkryjeme souřadnice
            if (herniPole[i] == '?' || herniPole[i] == '*') {
                herniPole[i] = minovePole[i];
                if (minovePole[i] == '*') {
                    IO.println("boooom! tady byla mina");
                    pocetZivotu--;
                    if (pocetZivotu == 0) {
                        //konec hry, zobrazíme miny
                        herniPole = minovePole;
                    }
                } else {
                    zbyvaOdkryt--;
                    if (zbyvaOdkryt == 0) {
                        //pokud je vše odkryto, zobrazíme miny
                        herniPole = minovePole;
                    }
                }
            }
        } catch (Exception e) {
            IO.println("špatně zadané souřadnice, zkus to znovu");
            klavesnice.nextLine();
        }
    }
}
