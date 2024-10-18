// obarvena verze programu Kecalek
void main() throws Exception {
    var adresa = new InetSocketAddress("255.255.255.255", 1234);

    try (var komunikacniKanal = new DatagramSocket(adresa.getPort())) {
        komunikacniKanal.setBroadcast(true);

        var jmeno = readln("Jak se jmenuješ: ");

        println("\033[30m0 černá \033[31m1 červená \033[32m2 zelená \033[33m3 žlutá \033[34m4 modrá \033[35m5 fialová \033[36m6 tyrkisová \033[37m7 šedá");
        var barevnyKod = "\033[3" + readln("Jakou barvu si vybereš (zadej číslo): ") + ";40m";

        println("Pro ukončení napiš konec");

        Thread.ofVirtual().start(()-> {
            var prijataZprava = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);
            while (!komunikacniKanal.isClosed()) try {
                komunikacniKanal.receive(prijataZprava);
                System.out.println(new String(prijataZprava.getData(), prijataZprava.getOffset(), prijataZprava.getLength()) + "\033[0m");
            } catch (IOException chyba) {}
        });

        for (var zprava = "jsem tady"; !zprava.equalsIgnoreCase("konec"); zprava = readln("")) {
            var dataZpravy = (barevnyKod + jmeno + ": " + zprava).getBytes();
            komunikacniKanal.send(new DatagramPacket(dataZpravy, dataZpravy.length, adresa));
            Thread.sleep(5000);
        }
    }
}
