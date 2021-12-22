package org.madmeg.impl.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public final class Gui extends JFrame {

    public Gui(){
        super("Python Obfuscator");
    }

    public File file;

    public void render(){
        addElements();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addElements(){
        this.setLayout(new BorderLayout());
        final JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        final JLabel label = new JLabel(new ImageIcon(
                new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage().getScaledInstance(
                        280, 100, Image.SCALE_DEFAULT
                )
        ));
        header.add(label);


        final JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));

        final JLabel configIndicator = new JLabel("Config File: NONE");
        filePanel.add(configIndicator);

        final JButton configSelectButton = new JButton("Select Config File");
        configSelectButton.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            if(e.getSource() == configSelectButton){
                int rVal = fileChooser.showOpenDialog(this);

                if(rVal == JFileChooser.APPROVE_OPTION){
                    this.file = fileChooser.getSelectedFile();
                    configIndicator.setText("Config File: " + file.getName());
                }
            }
        });
        filePanel.add(configSelectButton);

        final JLabel fileIndicator = new JLabel("File: NONE");
        filePanel.add(fileIndicator);

        final JButton fileSelectButton = new JButton("Select File");
        fileSelectButton.addActionListener(e -> {
            final JFileChooser fileChooser = new JFileChooser();
            if(e.getSource() == fileSelectButton){
                int rVal = fileChooser.showOpenDialog(this);

                if(rVal == JFileChooser.APPROVE_OPTION){
                    this.file = fileChooser.getSelectedFile();
                    fileIndicator.setText("Config File: " + file.getName());
                }
            }
        });

        filePanel.add(fileSelectButton);


        header.add(filePanel);

        this.add(header, BorderLayout.NORTH);
    }

}
