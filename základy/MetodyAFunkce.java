
// program muze mit vic metod, nez jen metodu main
// a pojmenovat si ji muzeme temer jak chceme, i kdyz vse ma sva pravidla
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
