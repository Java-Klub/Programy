
void zahraj(String melodie) throws Exception {
    //nastavime si MIDI system
    var midiSystem = javax.sound.midi.MidiSystem.getSynthesizer();
    midiSystem.open();
    int i = 0;
    var kanal = midiSystem.getChannels()[0];
    Thread.sleep(100);

    //rozebereme noty pomoci regularniho vyrazu
    var noty = java.util.regex.Pattern.compile("([a-iA-IsSpP]+)(['\"]?)([1-9])").matcher(melodie);
    while (noty.find()) {
        var zakladniTon = noty.group(1);
        var pocetCarek = noty.group(2).length();
        if (pocetCarek == 1 && noty.group(2).charAt(0) == '"') {
            pocetCarek++;
        }
        var delka = Integer.parseInt(noty.group(3));
        var midiKod = switch (zakladniTon.toLowerCase()) {
            case "c" -> 0;
            case "cis", "des" -> 1;
            case "d" -> 2;
            case "dis", "es" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "fis", "ges" -> 6;
            case "g" -> 7;
            case "gis", "as" -> 8;
            case "a" -> 9;
            case "ais", "b" -> 10;
            case "h" -> 11;
            default -> -1;
        };
        if (midiKod >= 0) {
            if (Character.isLowerCase(zakladniTon.charAt(0))) {
                //mala oktava carkovana smerem nahoru + transpozice o oktavu nahoru
                midiKod += 60 + pocetCarek * 12;
            } else {
                //velka oktava carkovana smerem dolu + transpozice o oktavu nahoru
                midiKod += 48 - pocetCarek * 12;
            }
        }
        if (midiKod >= 0 && midiKod < 128) {
            //hrajeme pouze povolene noty, ostatni jsou pauzy
            kanal.noteOn(midiKod, 100);
        }
        print(noty.group() + " ");
        // 1/8 notu hrajeme po dobu 200ms
        Thread.sleep(delka * 200);
        kanal.allNotesOff();
    }
    println("");
}

void main() throws Exception {

    var forrestGump = """
        e1 f1 g1 g2 e2 g2 c'2 g2 e3 f1 g1 a1 a2 f2 a9
        f1 g1 a1 a2 f2 a2 d'2 h2 g3 e1 f1 g1 g2 c'2 g9
        a1 h1 c'1 c'2 a2 c'3 a1 c'2 a3 f1 g1 a1 a2 f2 a9
        f1 g1 a1 a2 f2 d3 e1 f2 d3 c8
        """;

    var vanoceVanoce = """
        c'4 c'2 a2 g4 g2 e2 g2 g4 d2 g8 g2 h4 a2 g4 f4 e8 p2 g2 a2 h2
        c'4 c'2 a2 g4 g2 e2 g2 g4 d2 g8 g2 h4 a2 g4 h4 c'8
        d2 d2 d2 d2 h2 h2 g2 g2 a2 a2 fis2 fis2 g2 g2 d4
        d2 d2 d2 d2 h2 h2 g2 g2 a2 a2 fis2 fis2 g8
        d2 d2 d2 d2 h2 h2 g2 g2 a2 a2 fis2 fis2 g2 g2 d4
        d2 d2 d2 d2 h2 h2 g2 g2 a2 a2 fis2 fis2 g2 p6
        """;

    var rolnicky = """
        d2 h2 a2 g2 d4 p4 d2 h2 a2 g2 e4 p4
        e2 c'2 h2 a2 d'2 d'2 d'4 d'2 c'2 h2 a2 g4 p4
        d2 h2 a2 g2 d4 p4 d2 h2 a2 g2 e4 p4
        e2 c'2 h2 a2 d'2 d'2 d'4 e'2 d'2 c'2 a2 g4 p4
        h2 h2 h2 p2 h2 h2 h2 p2 h2 d'2 g2 a2 h4 p4
        c'2 c'2 c'2 p2 c'2 h2 h4 h2 a2 a2 h2 a4 p4
        h2 h2 h2 p2 h2 h2 h2 p2 h2 d'2 g2 a2 h4 p4
        c'2 c'2 c'2 c'2 c'2 h2 h2 p2 d'2 c'2 h2 a2 g4 p4
        """;

    var pasliOvceValasi = "c'2 g2 g2 f2 e2 f2 g4 c'2 g2 g2 f2 e2 d2 c4 c2 e2 e2 f2 e2 d2 e4 c2 e2 e2 f2 e2 d2 c4";

    var piratiZKaribiku = """
        d2 d1 d2 d1 d2 d1 d1 d1 d1 d2 d1 d2 d1 d2 d1 d1 ,
        A1 c1 d2 d2 d1 e1 f2 f2 f1 g1 e2 e2 d1 c1 c1 d2 p1 ,
        A1 c1 d2 d2 d1 e1 f2 f2 f1 g1 e2 e2 d1 c1 d3 p1 ,
        A1 c1 d2 d2 d1 f1 g2 g2 g1 a1 b2 b2 a1 g1 a1 d2 p1 ,
        d1 e1 f2 f2 g2 a1 d2 p1 d1 f1 e2 e2 f1 d1 e3 p1
        a1 c'1 d'2 d'2 d'1 e'1 f'2 f'2 f'1 g'1 e'2 e'2 d'1 c'1 c'1 d'2 p1
        a1 c'1 d'2 d'2 d'1 e'1 f'2 f'2 f'1 g'1 e'2 e'2 d'1 c'1 d'3 p1
        a1 c'1 d'2 d'2 d'1 f'1 g'2 g'2 g'1 a'1 b'2 b'2 a'1 g'1 a'1 d'2 p1
        d'1 e'1 f'2 f'2 g'2 a'1 d'2 p1
        d'1 f'1 e'2 e'2 d'1 c'1 d'2 d'2 e'2 f'2 f'1 f'1 g'2 a'1 f'1 p2 f'1 d'1 a1 p5
        b'1 p2 g'1 d'1 b1 p5 e1 e2 d3 f3 p1 f1 g1 a2 a2 a2 b1 a1 p4
        g2 g2 g2 g1 a1 p4 a2 a2 a2 b1 a1 p4 g2 f2 e2 d2 p2
        d1 e1 f4 g1 a1 g2 f2 e2 f2 g2 a2 g2
        p2 f1 g1 a2 p2 g1 f1 e2 f2 e2 d2 p2 e1 c1 d1 p3
        d'1 e'1 f'2 p2 e'1 f'1 g'2 f'2 g'2 a'2 g'2 f'2 d'2 p2
        d'1 e'1 f'2 g'2 a'2 b'2 d'2 g'2 f'2 p2 g'1 e'1 d'2 p2 e'1 cis'1 a'2 p4
        b'2 p4 a'2 a'2 a'2 a'1 g'1 p4 g'2 p4 f'2 p4 f'2 g'2 e'2 d'3 d'1 e'1 f'1 a'3 d'1 e'1 f'1 b'3 d'1 e'1 f'1 a'2 a'2
        c"2 a'1 g'1 p4 g'2 p4 f'2 p4 f'2 g'2 e'2 d'3 p3 d6
        """;

    var stupnice = """
        C"1 D"1 E"1 F"1 G"1 A"1 H"1
        C'1 D'1 E'1 F'1 G'1 A'1 H'1
        C1 D1 E1 F1 G1 A1 H1
        c1 d1 e1 f1 g1 a1 h1
        c'1 d'1 e'1 f'1 g'1 a'1 h'1
        c"1 d"1 e"1 f"1 g"1 a"1 h"1
        """;

    zahraj(forrestGump);
}
