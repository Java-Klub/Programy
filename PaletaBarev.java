import java.awt.*;

void main() {
    var okno = new javax.swing.JFrame("Barevná Paleta") {
        public void paint(Graphics grafika) {
            var ra = new Random(1);
            double c[] = new double[3];
            for (int x = 0; x < 256; x++) {
                c[ra.nextInt(3)] += 0.2;
                grafika.setColor(new Color(
                        Math.abs((float)Math.sin(c[0])), 
                        Math.abs((float)Math.sin(c[1])), 
                        Math.abs((float)Math.sin(c[2]))));
                grafika.fillRect(3 * x, 0 , 3 , 200);
            }
        }
    };
    okno.setSize(765, 200);
    okno.setLocationRelativeTo(null);
    okno.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    okno.setVisible(true);
}
