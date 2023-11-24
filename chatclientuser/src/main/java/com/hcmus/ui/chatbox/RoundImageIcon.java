package com.hcmus.ui.chatbox;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class RoundImageIcon extends ImageIcon {
    private final int width;
    private final int height;

    public RoundImageIcon(String filename, int width, int height) {
        super(new ImageIcon(filename).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        this.width = width;
        this.height = height;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        int diameter = Math.min(getIconWidth(), getIconHeight());
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(clip);
        g2.drawImage(getImage(), 0, 0, diameter, diameter, c);

        g2.dispose();
        g.drawImage(image, x, y, c);
    }
}