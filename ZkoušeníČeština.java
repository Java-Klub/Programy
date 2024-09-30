void main() {
    
    var text  = new Scanner("""
            Elza bydlela v domku blízko u silnice. S oblibou si vyskočila na betonovou zídku a pozorovala, co se děje na ulici.
            Elza byla totiž kočka. Nebyla to jen obyčejná kočka, byla mimořádně krásná. Měla hebkou černou srst, velké, krásně zelené oči a pohybovala se ladně jako baletka. Není divu, že se za ní každý otáčel.
            Když seskočila z plotu dolů a prošla se po chodníku, nebo dokonce přešla přes cestu, vždycky nějaké auto nabouralo, protože řidič měl oči jen pro tu nádhernou kočku. Chodci zase při pohledu na Elzu vráželi do sloupů nebo pouličních lamp.
            „Nestalo se vám nic, pane?“ zeptala se jedna stará paní, která se dívala z okna zrovna ve chvíli, kdy nějaký pán narazil hlavou do pouliční lampy.
            „To nic,“ odpověděl pán. Zvedl ze země klobouk, který mu z hlavy sletěl, a pak si hned začal zatlačovat bouli, která mu na čele okamžitě naskočila.
            „Za to může ta černá kočka!“ věděla hned paní a prstem na Elzu zle zahrozila.
            Pán se zastyděl, že se díval na krásnou kočku místo pod nohy, ale paní jeho svědomí ulevila.
            „Já už to tady nějakou dobu pozoruju. Ta černá kočka nosí smůlu. Hlavně když přeběhne přes cestu. Jednou jsem ji viděla přejít přes cestu a řízla jsem se do prstu. Podruhé jsem si vymkla kotník. Teď jsem ji viděla stejně jako vy, tak už se hrozím toho, co bude!“ Paní na chvíli zmlkla. Nasála vzduch do nosu a pak vykřikla: „Pro pána Jána, vždyť se mi pálí oběd!“
            Po těch slovech zmizela. Pán zíral na dým valící se z okna a přemýšlel, jestli nemá pro jistotu zavolat hasiče. Když se paní znovu objevila v okně, aby na vzduch vystrčila kouřící pánvičku, věděl, že to nebude potřeba. Rozloučil se a s boulí na čele odkráčel domů.
            """);

    text.useDelimiter("[.]");
    var zkouseneZnaky = Map.of(
            'i', 'i',
            'y', 'y',
            'I', 'i',
            'Y', 'y',
            'í', 'i',
            'ý', 'y',
            'Í', 'i',
            'Ý', 'y');

    var spravne = 0;
    var chyby = 0;
    while (text.hasNext()) {
        var veta = text.next();
        for (var znak : veta.toCharArray()) {
            print((zkouseneZnaky.keySet().contains(znak) ? '_' : znak));
        }
        println(".");
        println("--------------------------------------");
        for (var znak : veta.toCharArray()) {
            if (zkouseneZnaky.keySet().contains(znak)) {
                var vstup = readln("");
                if (!vstup.isEmpty() && zkouseneZnaky.get(vstup.charAt(0)).equals(zkouseneZnaky.get(znak))) {
                    spravne++;
                } else {
                    chyby++;
                    print("!" + znak + "!");
                }
            } else {
                print(znak);
            }
        }
        println(".");
        println("--------------------------------------");
    }
    println("počet správných odpovědí: " + spravne + ", počet chyb: " + chyby);
}
