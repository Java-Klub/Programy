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
    početPater = přečtiČíslo("Kolik pater má mít věž (max 14): ", 14);
    věže = List.of(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    var prvníVěž = věže.get(0);
    for (var velikostKamene = 1; velikostKamene <= početPater; velikostKamene++) {
        prvníVěž.addFirst(velikostKamene);
    }
}

void vykresliVěže() {
    var sb = new StringBuilder();
    for (var patro = početPater - 1; patro >= 0; patro--) {
        for (var věž : věže) {
            if (patro < věž.size()) {
                var velikostKamene = věž.get(patro);
                sb.append(" ".repeat(početPater - velikostKamene)).append("\033[46m").append("\u25d8".repeat(2 * velikostKamene - 1)).append("\033[0m").append(" ".repeat(početPater - velikostKamene));
            } else {
                sb.append(" ".repeat(2 * početPater - 1));
            }
        }
        sb.append('\n');
    }        
    sb.append(" ".repeat(početPater - 1)).append(1).append(" ".repeat(2 * početPater - 2)).append(2).append(" ".repeat(2 * početPater - 2)).append(3);
    println(sb.toString());
}

void přesuň(int početKamenů, LinkedList<Integer> zVěže, LinkedList<Integer> naVěž, LinkedList<Integer> odkladiště) {
    if (početKamenů == 1) {
        var přesouvanýKámen = zVěže.removeLast();
        naVěž.add(přesouvanýKámen);
        vykresliVěže();
    } else {
        přesuň(početKamenů - 1, zVěže, odkladiště, naVěž);
        přesuň(1, zVěže, naVěž, odkladiště);
        přesuň(početKamenů - 1, odkladiště, naVěž, zVěže);
    }
}


void main() {
    zahájeníHry();
    přesuň(početPater, věže.get(0), věže.get(2), věže.get(1));
}
