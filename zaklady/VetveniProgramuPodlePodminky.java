void main() {

    var cislo = Integer.parseInt(IO.readln("Zadej číslo:"));

    //blok programu za podmímnou se provede jen když je podmínka splněna
    if (cislo > 10) {
        IO.println("číslo " + cislo + " je větší než 10");
        //podmínky můžeme do sebe zanořovat
        if (cislo > 100) {
            IO.println("číslo " + cislo + " je větší než 100");
            if (cislo > 1000) {
                IO.println("číslo " + cislo + " je větší než 1000");
            }
        }
    //pokud přidáme příkaz "else", tak následující blok programu se provede jen když podmínka není splněna
    } else {
        IO.println("číslo " + cislo + " je menší nebo rovno 10");
    }

    //matematická operace % v Javě spočítá co zbyde po dělení dvou celých čísel
    //a pokud nám nic nezbyde po dělení dvěma, tak je číslo sudé
    if (cislo % 2 == 0) {
        IO.println("číslo " + cislo + " je sudé");
    } else {
        IO.println("číslo " + cislo + " je liché");
    }

}
