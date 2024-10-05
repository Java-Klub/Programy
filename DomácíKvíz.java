void main() {

    var zprava = readln("napis mi nejakou zpravu:");

    var pocetPismen = zprava.length();

    //nasledujici program vypise zpravu po pismenech od prvniho po posledni
    println("puvodni text zpravy je:");
    //POZOR: v programovani vetsinou zaciname pocitat nulou a ne jednickou
    for (var i = 0; i < pocetPismen; i = i + 1) {
        var pismeno = zprava.charAt(i);
        //pismena vypisujeme bez odradkovani
        print(pismeno);
    }
    //a nakonec odradkujeme
    println("");


    //ukol c.1: ted program prepis tak, aby kazde druhe pismeno preskocil a vypsal pouze licha pismena zpravy
    println("ukol c.1 - zprava s vynechanymi pismeny:");
    for (var i = 0; i < pocetPismen; i = i + 1) {
        var pismeno = zprava.charAt(i);
        print(pismeno);
    }
    println("");


    //tady se te program zepta na text, se kterym budeme dal pracovat
    var prilepka = readln("napis text, ktery se ma prilepit ke kazdemu pismenu zpravy:");

    //v tomto prikazu najdes napovedu pro reseni ukolu c.2
    println("zadana prilepka je " + prilepka);


    //ukol c.2: prepis nasledujici program tak, aby ke kazdemu pismenu zpravy prilepil zadanou prilepku
    println("ukol c.2 - zprava s prilepky:");
    for (var i = 0; i < pocetPismen; i = i + 1) {
        var pismeno = zprava.charAt(i);
        print(pismeno   );
    }
    println("");


    //ukol c.3: na dalsi radky napis program, ktery zpravy vypise 10x
    println("ukol c.3 - zprava 10x :");






    //velmi obtizny ukol c.4: zkus nasledujici program prepsat tak, aby vypisoval pismena pozpatku
    println("ukol c.4 - zprava pozpatku:");
    for (var i = 0; i < pocetPismen; i = i + 1) {
        var pismeno = zprava.charAt(i);
        print(pismeno);
    }
    println("");

}
