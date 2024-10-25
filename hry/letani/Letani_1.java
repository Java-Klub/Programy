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

    var x = 700;
    var y = 580;

    var grafika = hra.getGraphics();
    while(hra.isVisible()) {
        if (klavesy.get(KeyEvent.VK_UP)) y--;
        if (klavesy.get(KeyEvent.VK_DOWN)) y++;
        if (klavesy.get(KeyEvent.VK_LEFT)) x--;
        if (klavesy.get(KeyEvent.VK_RIGHT)) x++;

        grafika.setColor(Color.LIGHT_GRAY);
        grafika.fillRect(0, 0, 1600, 1000);
        grafika.setColor(Color.WHITE);
        grafika.fillOval(x, y, 50, 50);

        Thread.sleep(10);
    }
}
