import module java.desktop;

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

    var typDronu = 1;
    var poziceDronu = AffineTransform.getTranslateInstance(700, 580);
    poziceDronu.scale(0.5, 0.5);

    var grafika = (Graphics2D)hra.getGraphics();
    while(hra.isVisible()) {
        if (klavesy.get(KeyEvent.VK_UP)) poziceDronu.translate(0, -1);
        if (klavesy.get(KeyEvent.VK_DOWN)) poziceDronu.translate(0, 1);
        if (klavesy.get(KeyEvent.VK_LEFT)) poziceDronu.rotate(-0.02, 75, 75);
        if (klavesy.get(KeyEvent.VK_RIGHT)) poziceDronu.rotate(0.02, 75, 75);
        if (klavesy.get(KeyEvent.VK_A)) poziceDronu.scale(1.01, 1.01);
        if (klavesy.get(KeyEvent.VK_Z)) poziceDronu.scale(0.99, 0.99);
        if (klavesy.get(KeyEvent.VK_1)) typDronu = 1;
        if (klavesy.get(KeyEvent.VK_2)) typDronu = 2;
        if (klavesy.get(KeyEvent.VK_3)) typDronu = 3;
        if (klavesy.get(KeyEvent.VK_4)) typDronu = 4;
        if (klavesy.get(KeyEvent.VK_5)) typDronu = 5;

        grafika.drawRenderedImage(mapa, null);
        grafika.drawRenderedImage(drony[typDronu], poziceDronu);

        Thread.sleep(10);
    }
}
