
int bububu(int a, int b) {
    return a + b;
}

void mojeMetodaNaVypis(int cislo) {
    println(cislo);
}

void main() {
    var vysledek = bububu(3, bububu(6, 4));
    mojeMetodaNaVypis(vysledek);
}
