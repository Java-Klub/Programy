
String[] hriste = """
###############################################
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
#                                             #
###############################################
""".split("\n");

char znackaKarla = '>';
int poziceKarlaX = 1;
int poziceKarlaY = 20;

void nakresliHriste() {
    for (int y = 0; y < hriste.length; y++) {
        String radek = hriste[y];
        for (int x = 0; x < radek.length(); x++) {
            if (x == poziceKarlaX && y == poziceKarlaY) {
                IO.print(znackaKarla);
            } else {
                IO.print(radek.charAt(x));
            }
        }
        IO.println("");
    }
}

void main() {
    nakresliHriste();
}
