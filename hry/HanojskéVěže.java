
int početPater;
List<LinkedList<Integer>> věže;

int přečtiČíslo(String výzva, int max) {
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

void zahájeníHry() {
    početPater = přečtiČíslo("Kolik pater má mít věž (max 8): ", 8);
    věže = List.of(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    var prvníVěž = věže.get(0);
    for (var velikostKamene = 1; velikostKamene <= početPater; velikostKamene++) {
        prvníVěž.addFirst(velikostKamene);
    }
}

boolean ještěNeníKonec() {
    return věže.get(2).size() < početPater;
}

void vykresliVěže() {
    for (var patro = početPater - 1; patro >= 0; patro--) {
        for (var věž : věže) {
            if (patro < věž.size()) {
                var velikostKamene = věž.get(patro);
                print(" ".repeat(početPater - velikostKamene) + "\033[46m" + "\u25d8".repeat(2 * velikostKamene - 1) + "\033[0m" + " ".repeat(početPater - velikostKamene));
            } else {
                print(" ".repeat(2 * početPater - 1));
            }
        }
        println("");
    }        
    println(" ".repeat(početPater - 1) + 1 + " ".repeat(2 * početPater - 2) + 2 + " ".repeat(2 * početPater - 2) + 3);
}

void main() {
    zahájeníHry();
    while (ještěNeníKonec()) {        
        vykresliVěže();        
        var zdrojováVěž = věže.get(přečtiČíslo("Vzít kámen z věže: ", 3) - 1);
        var cílováVěž = věže.get(přečtiČíslo("a přesunout ho na věž: ", 3) - 1);
        if (zdrojováVěž.isEmpty()) {
            println("Není co přesouvat!");
        } else if (!cílováVěž.isEmpty() && cílováVěž.getLast() < zdrojováVěž.getLast()) {
            println("Nelze dát větší kámen na menší!");
        } else {
            var přesouvanýKámen = zdrojováVěž.removeLast();
            cílováVěž.add(přesouvanýKámen);
        }
    }
    vykresliVěže();
    println("Gratuluji, úspěšně vyřešený hlavolam :)");
    println("Konec hry.");
}
