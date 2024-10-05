void main() {

    // tady používáme textový blok přes více řádků, ten začíná a konċí """
    println("""
        Text v terminálu můžeme obarvit pomocí barevných kódů.

        Každŷ kód:
         - začíná specielním kódem \\33
         - pak je znak [
         - pokračuje číslem barvy písma 33 až 37
         - pak je znak ;
         - další je číslo barvy pozadí 40 až 47
         - a končí písmenem m

        Kód pro ukončení barvení je \\33[0m

        Kompletní tabulka barevných kódů tedy vypadá takto:
        """);

    for (var barvaPozadi = 40; barvaPozadi < 48; barvaPozadi++) {

        for (var barvaPisma = 30; barvaPisma < 38; barvaPisma++) {

            var barevnyKod = "[" + barvaPisma + ";" + barvaPozadi + "m";

            // \33 je specielní znak a pokud ho chci použít (obarvit text), tak ho napíšu s jedním zpětným lomítkem,
            // ale pokud ho chci jenom vypsat jak vypadá, musím zpětné lomítko zdvojit a napsat \\33
            // další použitý specielní znak je \t neboli tabulátor, který nám pomůže text zarovnat do sloupců
            print("\33" + barevnyKod + "\\33" + barevnyKod+ "\t");
        }

        // každá barva pozadí bude vypsaná na nový řádek
        println("");
    }
}