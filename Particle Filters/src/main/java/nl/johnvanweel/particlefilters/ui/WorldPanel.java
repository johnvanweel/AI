package nl.johnvanweel.particlefilters.ui;

import nl.johnvanweel.particlefilters.actor.Robot;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 2:16 PM
 */
public class WorldPanel extends JPanel implements ActionListener {
    @Autowired
    private WorldMap map;

    @Autowired
    private Robot[] robots;


    @PostConstruct
    public void postConstruct() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int dx = 0;
                int dy = 0;

                switch (e.getKeyCode()) {
                    case 38: //up
                        dy = -10;
                        break;
                    case 40: //down
                        dy = 10;
                        break;
                    case 37:   //left
                        dx = -10;
                        break;
                    case 39:   //right
                        dx = 10;
                        break;
                }

                for (int i = 0; i < robots.length; i++) {
                    robots[i].move(dx, dy);
                }

            }
        });

        Timer timer = new Timer(25, this);
        timer.start();
    }


    @Override
    public void paint(Graphics g) {
        int width = super.getWidth();
        int height = super.getHeight();

        g.clearRect(0, 0, width, height);

        for (Robot r : robots) {
            r.render(g);
        }

        map.render(g, width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        repaint();
    }
}
