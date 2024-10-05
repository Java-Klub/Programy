
void main() throws Exception {
    var adresa = new InetSocketAddress("255.255.255.255", 1234);
    
    try (var komunikačníKanál = new DatagramSocket(adresa.getPort())) {
        komunikačníKanál.setBroadcast(true);
        
        var jméno = readln("Jak se jmenuješ: ");
        
        println("\033[30m0 černá \033[31m1 červená \033[32m2 zelená \033[33m3 žlutá \033[34m4 modrá \033[35m5 fialová \033[36m6 tyrkisová \033[37m7 šedá");
        var barevnýKód = "\033[3" + readln("Jakou barvu si vybereš (zadej číslo): ") + ";40m";
        
        println("Pro ukončení napiš konec");
        
        Thread.ofVirtual().start(()-> {
            var přijatáZpráva = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
            while (!komunikačníKanál.isClosed()) try {
                komunikačníKanál.receive(přijatáZpráva);
                System.out.println(new String(přijatáZpráva.getData(), přijatáZpráva.getOffset(), přijatáZpráva.getLength()) + "\033[0m");
            } catch (IOException chyba) {}
        });
        
        for (var zpráva = "jsem tady"; !zpráva.equalsIgnoreCase("konec"); zpráva = readln("")) {
            var dataZprávy = (barevnýKód + jméno + ": " + zpráva).getBytes();
            komunikačníKanál.send(new DatagramPacket(dataZprávy, dataZprávy.length, adresa));
        }
    }
}
