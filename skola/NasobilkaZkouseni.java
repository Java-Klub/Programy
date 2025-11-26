void main() {

    var pocet = Integer.parseInt(IO.readln("Z kolika příkladů chceš vyzkoušet?"));
    var spravne = 0;
    var spatne = 0;
    var nahoda = new Random();

    for (var i = 1; i <= pocet; i++) {
        IO.println(i + ". příklad:");
        var a = nahoda.nextInt(2, 10);
        var b = nahoda.nextInt(2, 10);
        var vysledek = Integer.parseInt(IO.readln(a + " * " + b + " = "));
        if (vysledek == a * b) {
            spravne++;
            IO.println("Správně!");
        } else {
            spatne++;
            IO.println("Špatně, " + a + " * " + b + " = " + a * b);
        }
    }

    IO.println("Správných výsledků: " + spravne);
    IO.println("Špatných výsledků: " + spatne);
    IO.println("Výsledná známka: " + (1 + 4 * spatne / pocet));
}
