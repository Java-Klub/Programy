void main() {

    var pocet = Integer.parseInt(readln("Z kolika příkladů chceš vyzkoušet?"));
    var spravne = 0;
    var spatne = 0;
    var nahoda = new Random();

    for (var i = 1; i <= pocet; i++) {
        println(i + ". příklad:");
        var a = nahoda.nextInt(2, 10);
        var b = nahoda.nextInt(2, 10);
        var vysledek = Integer.parseInt(readln(a + " * " + b + " = "));
        if (vysledek == a * b) {
            spravne++;
            println("Správně!");
        } else {
            spatne++;
            println("Špatně, " + a + " * " + b + " = " + a * b);
        }
    }

    println("Správných výsledků: " + spravne);
    println("Špatných výsledků: " + spatne);
    println("Výsledná známka: " + (1 + 4 * spatne / pocet));
}
