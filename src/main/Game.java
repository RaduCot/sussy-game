package main;

import generation.Cell;
import generation.Generator;
import generation.TypeCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;

public class Game
{
    //facut de rc174

    private static int size;

    private JFrame frame;
    private LPanel panel;

    private Generator generator;
    private Cell[][] labyrinth;
    private Player player;

    private int width;
    private int height;

    private int xLocation;
    private int yLocation;

    private int level;

    private GridBagLayout layout;
    private GridBagConstraints c;

    //rc174

    public Game() {
        
        File soundFile = new File("src/theme.wav");
        try
        {
        AudioInputStream inputStream=AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.loop(1);
        }
        catch (UnsupportedAudioFileException e)
                {
                e.printStackTrace();
                }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (LineUnavailableException e)
                {
                    e.printStackTrace();
                }
        player = new Player();
        level = 1;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        xLocation = dim.width/2 - (width/2);
        yLocation = dim.height/2 - (height/2);

        width = size * 10 + 8*64;
        height  = size * 10 + 8* 64;

        frame = new JFrame();
        frame.setLocation(xLocation, yLocation);
        frame.setSize(width, height);
        frame.setResizable(false);
        //799399

        layout = new GridBagLayout();
        frame.setLayout(layout);

        c =  new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill   = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        generator = new Generator();

        newLevel();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                makePlayerMove(code);
            }
        });

        frame.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent e) {

                width = frame.getWidth();
                height = frame.getHeight();

            }

            public void componentMoved(ComponentEvent e) {

                xLocation = frame.getX();
                yLocation = frame.getY();

            }

        });

    }

    public static void main(String [] args) {

        size = 11;
        new Game();

    }


    public void newLevel() {

        player = new Player();
        labyrinth = generator.generateSimpleTable(size);

        panel = new LPanel(labyrinth, size);
        layout.setConstraints(panel, c);
        frame.getContentPane().add(panel);

        frame.revalidate();
        frame.repaint();
        frame.setTitle("Sussy-game level "+level);

    }

    public void makePlayerMove(int code) {

        boolean isMoved = false;
        int xOffset = 0, yOffset = 0;
        TypeCell symbolOver, symbolUnder,symbolLeft, symbolRight;

        if(this.player.yPosition == 0) {

            symbolOver = TypeCell.WALL;

        } else {

            symbolOver = labyrinth[player.yPosition - 1][player.xPosition].typeCell;

        }

        if(this.player.yPosition == size - 1) {

            symbolUnder = TypeCell.WALL;

        } else {

            symbolUnder = labyrinth[player.yPosition + 1][player.xPosition].typeCell;

        }

        symbolLeft =  labyrinth[player.yPosition][player.xPosition - 1].typeCell;
        symbolRight =  labyrinth[player.yPosition][player.xPosition + 1].typeCell;

        switch (code) {
        case 87 :
        case 38: {
            isMoved = this.player.goUp(symbolOver);
            yOffset = -1;
            }break;
        case 65:
        case 37: {
            isMoved = this.player.goLeft(symbolLeft);
            xOffset = -1;
            }break;
        case 83:
        case 40: {
            isMoved = this.player.goDown(symbolUnder);
            yOffset = 1;
            }break;
        case 68:
        case 39: {
            isMoved = this.player.goRight(symbolRight);
            xOffset = 1;
            }break;
        }

        if(isMoved) {

            labyrinth[player.yPosition][player.xPosition].typeCell = TypeCell.VISITED;

            this.player.xPosition += xOffset;
            this.player.yPosition += yOffset;

            labyrinth[player.yPosition][player.xPosition].typeCell = TypeCell.PLAYER;

            this.panel.repaint();

            if(this.player.yPosition == size - 1) {

                JOptionPane.showMessageDialog(frame, "Ai castigat!\nVei trece la urmatorul nivel.", "Victorie!",
                        JOptionPane.INFORMATION_MESSAGE);
                size=size+2;
                level++;
                frame.getContentPane().remove(panel);
                newLevel();

            }

        }

    }

}
