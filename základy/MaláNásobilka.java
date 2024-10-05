void main() {

    println("Malá násobilka:");

    // pro výpis tabulky využijeme dvou vnořných příkazů for a dvou proménných i a j
    for (int i = 1; i <= 10; i++) {

        for (int j = 1; j <= 10; j++) {

            // print je příkaz pro výpis to terminálu, ale pokračuje se na stejném řádku
            // \t je specielní znak nazývaný tabulátor, a ten nám pomůže text zarovnat pěkně do sloupců
            print(i * j + "\t");
        }

        // aż tady zakončíme řádek a pokračujeme na novém
        println("");
    }
}

