package org.madmeg.impl.gui;

import org.madmeg.api.obfuscator.Loader;
import org.madmeg.impl.Core;
import org.madmeg.impl.config.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public final class Gui extends JFrame {

    public Gui(){
        super("Python Obfuscator");
    }

    public File configFile;
    public File file;
    public JButton startButton;
    public static JTextArea logBox;

    public void render(){
        addElements();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setResizable(false);
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
                    this.configFile = fileChooser.getSelectedFile();
                    configIndicator.setText("Config File: " + configFile.getName());
                    Core.LOGGER.printSuccess("Loading config");
                    Core.PLUGIN_LOADER.onConfig();
                    Core.CONFIG_LOADER = new ConfigLoader(configFile);
                    Core.CONFIG = Core.CONFIG_LOADER.config;
                    Core.LOGGER.printSuccess("Loaded config");
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
                    fileIndicator.setText("File: " + file.getName());
                    Core.PLUGIN_LOADER.onLoad();
                    Core.LOADER = new Loader(file);
                }
            }
        });

        filePanel.add(fileSelectButton);

        startButton = new JButton("Obfuscate");
        startButton.addActionListener(e -> {
            if(this.file == null || this.configFile == null){
                Core.LOGGER.printError("Please select a file or config file.");
                return;
            }
            Core.LOGGER.printSuccess("Pooling Obfuscation tasks");
            Core.PLUGIN_LOADER.onPoolTasks();
            Core.TASK_FACTORY.poolTasks();
            Core.LOGGER.printSuccess("Pooled Obfuscation tasks");

            Core.LOGGER.printSuccess("Executing Obfuscation tasks");
            Core.PLUGIN_LOADER.onExecute();
            Core.TASK_FACTORY.runTasks();
            Core.LOGGER.printSuccess("Completed all Obfuscation tasks");
            Core.LOADER.save("Output.py");
        });
        filePanel.add(startButton);



        header.add(filePanel);
        this.add(header, BorderLayout.NORTH);

        logBox = new JTextArea();
        logBox.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(logBox);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setAutoscrolls(true);

        final JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

        logPanel.add(scrollPane, BorderLayout.CENTER);
        add(logPanel, BorderLayout.CENTER);
    }

    public static void log(String message){
        logBox.setText(logBox.getText() + "\n" + message);
    }

}
