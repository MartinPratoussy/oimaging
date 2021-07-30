/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/


package fr.jmmc.oimaging.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author martin
 */
public class SuccessCell extends JPanel implements TableCellRenderer {

    private BufferedImage greenCheckMark;
    private BufferedImage redXMark;
    private boolean success = false;

    public SuccessCell() {
        super.setOpaque(true);

        try {
            greenCheckMark = ImageIO.read(new File("src/main/resources/fr/jmmc/oimaging/resource/image/ok-16.png"));
            redXMark = ImageIO.read(new File("src/main/resources/fr/jmmc/oimaging/resource/image/x-mark-3-16.png"));
        } catch (IOException e) {
            System.out.println("Can't load images");
        }

    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        success = (boolean) value;
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage((success) ? greenCheckMark : redXMark, 0, 0, this);
    }
    
}
