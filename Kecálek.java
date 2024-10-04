void main() throws Exception {

    //nejprve se te zeptame na jmeno, protoze vsichni pouzivame stejny program, ale kazdy se jmenuje jinak
    var jmeno = readln("jak se jmenujes?");

    //nezapomeneme slusne pozdravit a vypsat napovedu
    println("ahoj " + jmeno + ", pro ukonceni napis konec");

    //otevrem si sitovy komunikacni kanal
    try (var komunikacniKanal = new DatagramSocket(1234);) {

        //prijimani zprav je samostatny proces, ktery potrebuje vlastni vlakno
        Thread.ofVirtual().start(()-> {

            //vytvorime si objekt pro prijimane zpravy
            var prijataZprava = new DatagramPacket(new byte[Short.MAX_VALUE], Short.MAX_VALUE);

            //a dokud se neuzavre komunikacni kanal
            while (!komunikacniKanal.isClosed()) try {

                //tak prijimame zpravy
                komunikacniKanal.receive(prijataZprava);

                //a vypisujeme je na obrazovku
                System.out.println(new String(prijataZprava.getData(), prijataZprava.getOffset(), prijataZprava.getLength(), StandardCharsets.UTF_8));
            } catch (Exception e) {
                //tady bychom pripadne resily nejake chybove stavy, ale to nas ted nezajima
            }
        });

        //komunikacnimu kanalu rekneme, ze chceme vysilat vsem prijimacum v mistni siti
        komunikacniKanal.setBroadcast(true);

        //jako prvni zprava je odeslano oznameni kdo se pripojil do diskuse
        //a v tomto cyklu se pokracuje dokud nenapiseme konec
        for (var zprava = "jsem tady"; !zprava.equals("konec"); zprava = readln("")) {

            //na zacatek kazde odesilane zpravy jeste prilepime jmeno odesilatele
            zprava = jmeno + ": " + zprava;

            //a zpravu odesleme komunikcnim kanalem do cele mistni site, kde ostatni poslouchji
            komunikacniKanal.send(new DatagramPacket(zprava.getBytes(StandardCharsets.UTF_8), zprava.getBytes().length, InetAddress.getByName("255.255.255.255"), 1234));
        }
    }
}
