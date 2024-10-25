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

    var dron = ImageIO.read(getClass().getResource("dron1.png"));

    var poziceDronu = AffineTransform.getTranslateInstance(700, 580);

    var grafika = (Graphics2D)hra.getGraphics();
    while(hra.isVisible()) {
        if (klavesy.get(KeyEvent.VK_UP)) poziceDronu.translate(0, -1);
        if (klavesy.get(KeyEvent.VK_DOWN)) poziceDronu.translate(0, 1);
        if (klavesy.get(KeyEvent.VK_LEFT)) poziceDronu.rotate(-0.02, 75, 75);
        if (klavesy.get(KeyEvent.VK_RIGHT)) poziceDronu.rotate(0.02, 75, 75);

        grafika.setColor(Color.LIGHT_GRAY);
        grafika.fillRect(0, 0, 1600, 1000);
        grafika.drawRenderedImage(dron, poziceDronu);

        Thread.sleep(10);
    }
}
