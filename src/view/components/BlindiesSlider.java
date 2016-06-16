package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class BlindiesSlider extends BasicSliderUI {

    public BlindiesSlider(JSlider b) {
        super(b);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(40, 40);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);
		g2d.fillOval(thumbRect.x,thumbRect.y,thumbRect.width,thumbRect.height);
    }

}
