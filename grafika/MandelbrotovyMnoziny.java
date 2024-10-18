import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;


double velikostMnoziny = 3;
double zStredu = -0.5;
double yStredu = 0;
int maxBarev = 256;
BufferedImage obrazek = null;

void main()  {
    var nahoda = new Random(1);
    var slozkyJedneBarvy = new double[3];
    var barevnaPaleta = new byte[3 * maxBarev];
    for (int x = 0; x < maxBarev; x++) {
        slozkyJedneBarvy[nahoda.nextInt(3)] += 0.2;
        for (int i = 0; i < 3; i++) {
            barevnaPaleta[3 * x + i] = (byte)(255 * Math.sin(slozkyJedneBarvy[i]));
        }
    }
    var barevnyModel = new IndexColorModel(8, maxBarev, barevnaPaleta, 0, false);
    var okno = new JFrame("Mandelbrotova Množina") {
        public void paint(Graphics grafika) {
            var rozmeryOkna = grafika.getClipBounds();
            if (obrazek == null || obrazek.getWidth() != rozmeryOkna.width || obrazek.getHeight() != rozmeryOkna.height) {
                obrazek = new BufferedImage(rozmeryOkna.width, rozmeryOkna.height, BufferedImage.TYPE_BYTE_INDEXED, barevnyModel);
            }
            var raster = obrazek.getRaster();
            var x0 = zStredu - velikostMnoziny/2;
            var y0 = yStredu - velikostMnoziny/2;
            var dx = velikostMnoziny / rozmeryOkna.width;
            var dy = velikostMnoziny / rozmeryOkna.height;
            try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < rozmeryOkna.width; i++) {
                    var ii = i;
                    var x = x0;
                    es.submit(() -> {
                        var y = y0;
                        for (int j = 0; j < rozmeryOkna.height; j++) {
                            raster.setPixel(ii, j, new int[]{vypoctiMandelbrota(x, y)});
                            y += dy;
                        }
                    });
                    x0 += dx;
                }
            }
            ((Graphics2D)grafika).drawImage(obrazek, null, 0, 0);
        }
    };
    okno.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                zStredu += velikostMnoziny * (e.getX() - obrazek.getWidth() / 2)  / obrazek.getWidth();
                yStredu += velikostMnoziny * (e.getY() - obrazek.getHeight() / 2) / obrazek.getHeight();
                velikostMnoziny /= 1.5;
            } else {
                velikostMnoziny *= 1.5;
            }
            SwingUtilities.invokeLater(() -> okno.repaint());
        }
    });
    okno.setSize(800, 600);
    okno.setLocationRelativeTo(null);
    okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    okno.setVisible(true);
}

int vypoctiMandelbrota(double x, double y) {
    var z0polomer = Math.sqrt(x * x + y * y);
    var z0uhel = Math.atan2(y, x);
    var prirustekX = z0polomer * Math.cos(z0uhel);
    var prirustekY = z0polomer * Math.sin(z0uhel);
    var zPolomer = z0polomer;
    var zUhel = z0uhel;
    for (int iterace = 1; iterace < maxBarev; iterace++) {
        if (zPolomer > 2.0) return maxBarev - iterace;
        zPolomer *= zPolomer;
        zUhel += zUhel;
        x = zPolomer * Math.cos(zUhel) + prirustekX;
        y = zPolomer * Math.sin(zUhel) + prirustekY;
        zPolomer = Math.sqrt(x * x + y * y);
        zUhel = Math.atan2(y, x);
    }
    return 0;
}
