
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

void nakresliHriste() {
    for (int y = 0; y < hriste.length; y++) {
        String radek = hriste[y];
        for (int x = 0; x < radek.length(); x++) {
            IO.print(radek.charAt(x));
        }
        IO.println("");
    }
}

void main() {
    nakresliHriste();
}
