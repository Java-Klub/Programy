
int pocetPater;
List<LinkedList<Integer>> veze;

int prectiCislo(String vyzva, int max) {
    while (true) try {
        var cislo = Integer.parseInt(IO.readln(vyzva));
        if (cislo < 1 || cislo > max) {
            IO.println("Chybné zadání!");
        } else {
            return cislo;
        }
    } catch (NumberFormatException chyba) {
        IO.println("Chybné zadání!");
    }
}

void zahajeniHry() {
    pocetPater = prectiCislo("Kolik pater má mít věž (max 8): ", 8);
    veze = List.of(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    var prvniVez = veze.get(0);
    for (var velikostKamene = 1; velikostKamene <= pocetPater; velikostKamene++) {
        prvniVez.addFirst(velikostKamene);
    }
}

boolean jesteNeniKonec() {
    return veze.get(2).size() < pocetPater;
}

void vykresliVeze() {
    for (var patro = pocetPater - 1; patro >= 0; patro--) {
        for (var vez : veze) {
            if (patro < vez.size()) {
                var velikostKamene = vez.get(patro);
                IO.print(" ".repeat(pocetPater - velikostKamene) + "\033[46m" + "=".repeat(2 * velikostKamene - 1) + "\033[0m" + " ".repeat(pocetPater - velikostKamene));
            } else {
                IO.print(" ".repeat(2 * pocetPater - 1));
            }
        }
        IO.println("");
    }
    IO.println(" ".repeat(pocetPater - 1) + 1 + " ".repeat(2 * pocetPater - 2) + 2 + " ".repeat(2 * pocetPater - 2) + 3);
}

void main() {
    zahajeniHry();
    while (jesteNeniKonec()) {
        vykresliVeze();
        var zdrojovaVez = veze.get(prectiCislo("Vzít kámen z veze: ", 3) - 1);
        var cilovaVez = veze.get(prectiCislo("a přesunout ho na věž: ", 3) - 1);
        if (zdrojovaVez.isEmpty()) {
            IO.println("Není co přesouvat!");
        } else if (!cilovaVez.isEmpty() && cilovaVez.getLast() < zdrojovaVez.getLast()) {
            IO.println("Nelze dát větší kámen na menší!");
        } else {
            var presouvanyKamen = zdrojovaVez.removeLast();
            cilovaVez.add(presouvanyKamen);
        }
    }
    vykresliVeze();
    IO.println("Gratuluji, úspěšně vyřešený hlavolam :)");
    IO.println("Konec hry.");
}
