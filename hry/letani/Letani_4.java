import module java.desktop;

class Dron implements Comparable<Dron> {

    byte typ;
    AffineTransform pozice;

    Dron() {}

    Dron(DatagramPacket prichoziZprava) {
        var data = ByteBuffer.wrap(prichoziZprava.getData());
        typ = data.get();
        var matice = new double[6];
        data.asDoubleBuffer().get(matice);
        pozice = new AffineTransform(matice);
    }

    void zabalDoZpravy(DatagramPacket odchoziZprava) throws Exception {
        var data = ByteBuffer.wrap(odchoziZprava.getData());
        data.put(typ);
        var matice = new double[6];
        pozice.getMatrix(matice);
        data.asDoubleBuffer().put(matice);
    }

    @Override
    public int compareTo(Dron d) {
        return Double.compare(pozice.getDeterminant(), d.pozice.getDeterminant());
    }
}

void main() throws Exception {
    var hra = new JFrame();
    hra.setTitle("Letani");
    hra.setSize(1600, 1000);
    hra.setLocationRelativeTo(null);
    hra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    hra.setResizable(false);
    hra.setVisible(true);

    var klavesy = new BitSet();
    hra.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            klavesy.set(e.getKeyCode());
        }
        public void keyReleased(KeyEvent e) {
            klavesy.clear(e.getKeyCode());
        }
    });

    var mapa = ImageIO.read(getClass().getResource("mapa.png"));
    var drony = new BufferedImage[6];
    for (var i = 1; i < 6; i++) {
        drony[i] = ImageIO.read(getClass().getResource("dron" + i + ".png"));
    }

    try (var komunikacniKanal = new DatagramSocket(1234);) {
        Thread.ofVirtual().start(() -> {

            var prijataZprava = new DatagramPacket(new byte[49], 49);
            var vsechnyDrony = new HashMap<SocketAddress, Dron>();
            var grafika = (Graphics2D)hra.getGraphics();
            while (!komunikacniKanal.isClosed()) {
                try {
                    komunikacniKanal.receive(prijataZprava);
                    vsechnyDrony.put(prijataZprava.getSocketAddress(), new Dron(prijataZprava));
                    grafika.drawRenderedImage(mapa, null);
                    vsechnyDrony.values().stream().sorted().forEachOrdered(dron ->
                        grafika.drawRenderedImage(drony[dron.typ], dron.pozice));
                } catch (Exception e) {}
            }
        });
        komunikacniKanal.setBroadcast(true);

        var mujDron = new Dron();
        mujDron.typ = 1;
        mujDron.pozice = AffineTransform.getTranslateInstance(700, 580);
        mujDron.pozice.scale(0.5, 0.5);

        var odchoziZprava = new DatagramPacket(new byte[49], 49, InetAddress.getByName("255.255.255.255"), 1234);

        while(hra.isVisible()) {
            if (klavesy.get(KeyEvent.VK_UP)) mujDron.pozice.translate(0, -1);
            if (klavesy.get(KeyEvent.VK_DOWN)) mujDron.pozice.translate(0, 1);
            if (klavesy.get(KeyEvent.VK_LEFT)) mujDron.pozice.rotate(-0.02, 75, 75);
            if (klavesy.get(KeyEvent.VK_RIGHT)) mujDron.pozice.rotate(0.02, 75, 75);
            if (klavesy.get(KeyEvent.VK_A)) mujDron.pozice.scale(1.01, 1.01);
            if (klavesy.get(KeyEvent.VK_Y)) mujDron.pozice.scale(0.99, 0.99);
            if (klavesy.get(KeyEvent.VK_1)) mujDron.typ = 1;
            if (klavesy.get(KeyEvent.VK_2)) mujDron.typ = 2;
            if (klavesy.get(KeyEvent.VK_3)) mujDron.typ = 3;
            if (klavesy.get(KeyEvent.VK_4)) mujDron.typ = 4;
            if (klavesy.get(KeyEvent.VK_5)) mujDron.typ = 5;

            mujDron.zabalDoZpravy(odchoziZprava);
            komunikacniKanal.send(odchoziZprava);

            Thread.sleep(10);
        }
    }
}
