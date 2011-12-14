package nl.johnvanweel.particlefilters.ui;

import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:14 AM
 *
 */
public class MainWindow extends JFrame {
    @Autowired
    private WorldPanel panel;

    @PostConstruct
    public void postConstruct() {
        setSize(640, 480);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Particle Filters Example");

        setVisible(true);

        add(panel, BorderLayout.CENTER);
    }
}
