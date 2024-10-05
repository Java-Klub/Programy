import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

        
double velikostMnožiny = 3;
double xStředu = -0.5;
double yStředu = 0;
int maxBarev = 256;
BufferedImage obrázek = null;

void main()  {
    var náhoda = new Random(1);
    var složkyJednéBarvy = new double[3];
    var barevnáPaleta = new byte[3 * maxBarev];
    for (int x = 0; x < maxBarev; x++) {
        složkyJednéBarvy[náhoda.nextInt(3)] += 0.2;
        for (int i = 0; i < 3; i++) {
            barevnáPaleta[3 * x + i] = (byte)(255 * Math.sin(složkyJednéBarvy[i]));
        }
    }    
    var barevnýModel = new IndexColorModel(8, maxBarev, barevnáPaleta, 0, false);    
    var okno = new JFrame("Mandelbrotova Množina") {
        public void paint(Graphics grafika) {
            var rozměryOkna = grafika.getClipBounds();
            if (obrázek == null || obrázek.getWidth() != rozměryOkna.width || obrázek.getHeight() != rozměryOkna.height) {
                obrázek = new BufferedImage(rozměryOkna.width, rozměryOkna.height, BufferedImage.TYPE_BYTE_INDEXED, barevnýModel);
            }
            var raster = obrázek.getRaster();
            var x0 = xStředu - velikostMnožiny/2;
            var y0 = yStředu - velikostMnožiny/2;
            var dx = velikostMnožiny / rozměryOkna.width;
            var dy = velikostMnožiny / rozměryOkna.height;
            try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < rozměryOkna.width; i++) {
                    var ii = i;
                    var x = x0;
                    es.submit(() -> {
                        var y = y0;
                        for (int j = 0; j < rozměryOkna.height; j++) {
                            raster.setPixel(ii, j, new int[]{vypočtiMandelbrota(x, y)});
                            y += dy;
                        }
                    });
                    x0 += dx;
                }
            }
            ((Graphics2D)grafika).drawImage(obrázek, null, 0, 0);
        }
    };
    okno.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                xStředu += velikostMnožiny * (e.getX() - obrázek.getWidth() / 2)  / obrázek.getWidth();
                yStředu += velikostMnožiny * (e.getY() - obrázek.getHeight() / 2) / obrázek.getHeight();
                velikostMnožiny /= 1.5;
            } else {
                velikostMnožiny *= 1.5;
            }
            SwingUtilities.invokeLater(() -> okno.repaint());
        }        
    });
    okno.setSize(800, 600);
    okno.setLocationRelativeTo(null);
    okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    okno.setVisible(true);
}

int vypočtiMandelbrota(double x, double y) {
    var z0poloměr = Math.sqrt(x * x + y * y);
    var z0úhel = Math.atan2(y, x);
    var přírůstekX = z0poloměr * Math.cos(z0úhel);
    var přírůstekY = z0poloměr * Math.sin(z0úhel);
    var zPoloměr = z0poloměr;
    var zÚhel = z0úhel;
    for (int iterace = 1; iterace < maxBarev; iterace++) {
        if (zPoloměr > 2.0) return maxBarev - iterace;
        zPoloměr *= zPoloměr;
        zÚhel += zÚhel;
        x = zPoloměr * Math.cos(zÚhel) + přírůstekX;
        y = zPoloměr * Math.sin(zÚhel) + přírůstekY;
        zPoloměr = Math.sqrt(x * x + y * y);
        zÚhel = Math.atan2(y, x);
    }
    return 0;
}
