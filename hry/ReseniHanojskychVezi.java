int pocetPater;
List<LinkedList<Integer>> veze;

int prectiCislo(String výzva, int max) {
    while (true) try {
        var cislo = Integer.parseInt(readln(výzva));
        if (cislo < 1 || cislo > max) {
            println("Chybné zadání!");
        } else {
            return cislo;
        }
    } catch (NumberFormatException chyba) {
        println("Chybné zadání!");
    }
}

void zahajeniHry() {
    pocetPater = prectiCislo("Kolik pater má mít věž (max 14): ", 14);
    veze = List.of(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    var prvniVez = veze.get(0);
    for (var velikostKamene = 1; velikostKamene <= pocetPater; velikostKamene++) {
        prvniVez.addFirst(velikostKamene);
    }
}

void vykresliVeze() {
    var sb = new StringBuilder();
    for (var patro = pocetPater - 1; patro >= 0; patro--) {
        for (var vez : veze) {
            if (patro < vez.size()) {
                var velikostKamene = vez.get(patro);
                sb.append(" ".repeat(pocetPater - velikostKamene)).append("\033[46m").append("\u25d8".repeat(2 * velikostKamene - 1)).append("\033[0m").append(" ".repeat(pocetPater - velikostKamene));
            } else {
                sb.append(" ".repeat(2 * pocetPater - 1));
            }
        }
        sb.append('\n');
    }
    sb.append(" ".repeat(pocetPater - 1)).append(1).append(" ".repeat(2 * pocetPater - 2)).append(2).append(" ".repeat(2 * pocetPater - 2)).append(3);
    println(sb.toString());
}

void presun(int pocetKamenu, LinkedList<Integer> zVeze, LinkedList<Integer> naVez, LinkedList<Integer> odkladiste) {
    if (pocetKamenu == 1) {
        var presouvanyKamen = zVeze.removeLast();
        naVez.add(presouvanyKamen);
        vykresliVeze();
    } else {
        presun(pocetKamenu - 1, zVeze, odkladiste, naVez);
        presun(1, zVeze, naVez, odkladiste);
        presun(pocetKamenu - 1, odkladiste, naVez, zVeze);
    }
}


void main() {
    zahajeniHry();
    presun(pocetPater, veze.get(0), veze.get(2), veze.get(1));
}
