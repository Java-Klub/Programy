void main() {
    
    var počet = Integer.parseInt(readln("Z kolika příkladů chceš vyzkoušet?"));
    var správně = 0;
    var špatně = 0;        
    var náhoda = new Random();

    for (var i = 1; i <= počet; i++) {
        println(i + ". příklad:");
        var a = náhoda.nextInt(2, 10);
        var b = náhoda.nextInt(2, 10);
        var vysledek = Integer.parseInt(readln(a + " * " + b + " = "));
        if (vysledek == a * b) {
            správně++;
            println("Správně!");
        } else {
            špatně++;
            println("Špatně, " + a + " * " + b + " = " + a * b);
        }
    }

    println("Správných výsledků: " + správně);
    println("Špatných výsledků: " + špatně);
    println("Výsledná známka: " + (1 + 4 * špatně / počet));
}    
