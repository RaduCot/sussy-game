package main;

import generation.Cell;
import generation.TypeCell;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;

public class LPanel extends JPanel {

    Cell[][] map;
    int size;

    public LPanel(Cell[][] map, int size) {

        this.map = map;
        this.size = size;

    }

    public void paintComponent(Graphics g) {
        
        Color c1 = new Color(0,0,0);
        g.setColor(c1);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //g.setColor(Color.BLACK);
        int width = this.getWidth();
        int height = this.getHeight();

        int xBlock = (width / (size + 2));
        int yBlock = (height / (size + 2));

        int xStart = xBlock;
        int yStart = yBlock;

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                Cell cell = map[i][j];

                if (1==1) {

                    int x = xStart + (j * xBlock);
                    int y = yStart + (i * yBlock);
                    //g.fillRect(x, y, xBlock, yBlock);
                    ImageIcon TileIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\tile.png");
                    Image TileImage = TileIcon.getImage();
                    g.drawImage(TileImage, x, y, xBlock, yBlock, this);

                }
                if (cell.typeCell == TypeCell.WALL) {

                    int x = xStart + (j * xBlock);
                    int y = yStart + (i * yBlock);
                    //g.fillRect(x, y, xBlock, yBlock);
                    ImageIcon WallIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\wall.png");
                    Image WallImage = WallIcon.getImage();
                    g.drawImage(WallImage, x, y, xBlock, yBlock, this);

                }

                if (cell.typeCell == TypeCell.PLAYER) {

                    //ImageIcon beeIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\lb2\\icon.png");
                    //this.getClass().getResource("/resources/package_obj.png"))
                    //getImage()
                    //g.setColor(Color.RED);
                    int x = xStart + (j * xBlock);
                    int y = yStart + (i * yBlock);
                    ImageIcon AmogusIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icon.png");
                    Image AmogusImage = AmogusIcon.getImage();
                    g.drawImage(AmogusImage, x, y, xBlock, yBlock, this);
                    /*g.fillRect(x,y,xBlock, yBlock);
                    g.setColor(Color.BLACK);*/

                }

            }

        }

    }

}
