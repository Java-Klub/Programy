void main() {
    println("tabulka barev:");
    for (var barvaPozadi = 40; barvaPozadi < 48; barvaPozadi++) {
        for (var barvaPisma = 30; barvaPisma < 38; barvaPisma++) {
            print("\033[" + barvaPisma + ";" + barvaPozadi + "m\\033[" + barvaPisma + ";" + barvaPozadi + "m\033[0m ");
        }
        println("");
    }
}