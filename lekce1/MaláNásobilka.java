void main() {

    println("Mala nasobilka:");

    for (int a = 1; a < 10; a++) {
        for (int b = 1; b < 10; b++) {
            print("%4d".formatted(a * b));
        }
        println("");
    }
}
