import module java.desktop;

class Motyl implements Comparable<Motyl> {

    byte druh;
    AffineTransform pozice;

    Motyl() {}

    Motyl(DatagramPacket prichoziZprava) {
        var data = ByteBuffer.wrap(prichoziZprava.getData());
        druh = data.get();
        var matice = new double[6];
        data.asDoubleBuffer().get(matice);
        pozice = new AffineTransform(matice);
    }

    void zabalDoZpravy(DatagramPacket odchoziZprava) throws Exception {
        var data = ByteBuffer.wrap(odchoziZprava.getData());
        data.put(druh);
        var matice = new double[6];
        pozice.getMatrix(matice);
        data.asDoubleBuffer().put(matice);
    }

    @Override
    public int compareTo(Motyl d) {
        return Double.compare(pozice.getDeterminant(), d.pozice.getDeterminant());
    }
}

void main() throws Exception {
    var hra = new JFrame();
    hra.setTitle("Letani");
    hra.setSize(1200, 1000);
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

    var mapa = ImageIO.read(getClass().getResource("louka.jpg"));
    var motyly = new BufferedImage[6];
    for (var i = 1; i < 6; i++) {
        motyly[i] = ImageIO.read(getClass().getResource("motyl" + i + ".png"));
    }

    var vsechnyMotyli = new HashMap<SocketAddress, Motyl>();

    try (var komunikacniKanal = new DatagramSocket(1236);) {
        Thread.ofVirtual().start(() -> {

            var prijataZprava = new DatagramPacket(new byte[49], 49);
            while (!komunikacniKanal.isClosed()) {
                try {
                    komunikacniKanal.receive(prijataZprava);
                    vsechnyMotyli.put(prijataZprava.getSocketAddress(), new Motyl(prijataZprava));
                } catch (Exception e) {}
            }
        });
        komunikacniKanal.setBroadcast(true);

        var mujMotyl = new Motyl();
        mujMotyl.druh = 1;
        mujMotyl.pozice = AffineTransform.getTranslateInstance(500, 200);
        mujMotyl.pozice.scale(0.5, 0.5);

        var odchoziZprava = new DatagramPacket(new byte[49], 49, InetAddress.getByName("255.255.255.255"), 1236);

        hra.createBufferStrategy(2);

        while(hra.isVisible()) {
            if (klavesy.get(KeyEvent.VK_UP)) mujMotyl.pozice.translate(0, -1);
            if (klavesy.get(KeyEvent.VK_DOWN)) mujMotyl.pozice.translate(0, 1);
            if (klavesy.get(KeyEvent.VK_LEFT)) mujMotyl.pozice.rotate(-0.02, 155, 100);
            if (klavesy.get(KeyEvent.VK_RIGHT)) mujMotyl.pozice.rotate(0.02, 155, 100);
            if (klavesy.get(KeyEvent.VK_A)) mujMotyl.pozice.scale(1.01, 1.01);
            if (klavesy.get(KeyEvent.VK_Y)) mujMotyl.pozice.scale(0.99, 0.99);
            if (klavesy.get(KeyEvent.VK_1)) mujMotyl.druh = 1;
            if (klavesy.get(KeyEvent.VK_2)) mujMotyl.druh = 2;
            if (klavesy.get(KeyEvent.VK_3)) mujMotyl.druh = 3;
            if (klavesy.get(KeyEvent.VK_4)) mujMotyl.druh = 4;
            if (klavesy.get(KeyEvent.VK_5)) mujMotyl.druh = 5;

            mujMotyl.zabalDoZpravy(odchoziZprava);
            komunikacniKanal.send(odchoziZprava);

            var buffer = hra.getBufferStrategy();
            var grafika = (Graphics2D)buffer.getDrawGraphics();
            grafika.drawRenderedImage(mapa, null);
            vsechnyMotyli.values().stream().sorted().forEachOrdered(motyl ->
                grafika.drawRenderedImage(motyly[motyl.druh], mavani(motyl.pozice)));

            buffer.show();

            Thread.sleep(10);
        }
    }
}

AffineTransform mavani(AffineTransform pozice) {
    var faze = Math.sin(System.currentTimeMillis()/100);
    var t = (AffineTransform)pozice.clone();
    t.translate(112.5 * (0.7 - 0.3 * faze), 0.0);
    t.scale(0.7 + 0.3 * faze, 1.0);
    return t;
}
