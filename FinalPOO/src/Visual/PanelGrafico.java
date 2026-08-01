package Visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelGrafico extends JPanel {

    private String[] nombres;
    private int[] valores;
    private Color[] colores = {new Color(59, 130, 246), new Color(16, 185, 129), new Color(245, 158, 11)}; // Azul, Verde, Naranja

    public PanelGrafico(String[] nombres, int[] valores) {
        this.nombres = nombres;
        this.valores = valores;
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (valores == null || valores.length == 0) return;

        int maxValor = 0;
        for (int v : valores) {
            if (v > maxValor) maxValor = v;
        }
        if (maxValor == 0) maxValor = 1;

        int anchoPanel = getWidth();
        int altoPanel = getHeight();
        int margenAbajo = 40;
        int margenArriba = 30;
        int altoMaximoBarra = altoPanel - margenAbajo - margenArriba;
        
        int anchoBarra = 60;

        int separacion = (anchoPanel - (anchoBarra * valores.length)) / (valores.length + 1);

        for (int i = 0; i < valores.length; i++) {

            int alturaBarra = (int) (((double) valores[i] / maxValor) * altoMaximoBarra);
            
            int x = separacion + (i * (anchoBarra + separacion));
            int y = altoPanel - margenAbajo - alturaBarra;

            g2.setColor(colores[i % colores.length]);
            g2.fillRect(x, y, anchoBarra, alturaBarra);

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            FontMetrics metrics = g2.getFontMetrics();
            int xTexto = x + (anchoBarra - metrics.stringWidth(nombres[i])) / 2;
            g2.drawString(nombres[i], xTexto, altoPanel - 15);

            String valorString = String.valueOf(valores[i]);
            int xNumero = x + (anchoBarra - metrics.stringWidth(valorString)) / 2;
            g2.drawString(valorString, xNumero, y - 5);
        }
        
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(20, altoPanel - margenAbajo, anchoPanel - 20, altoPanel - margenAbajo);
    }
}
