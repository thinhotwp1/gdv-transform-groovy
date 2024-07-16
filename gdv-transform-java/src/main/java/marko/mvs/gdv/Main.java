package marko.mvs.gdv;

import groovy.lang.GroovyClassLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Main extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private JTextArea inputTextArea;
    private JTextArea logArea;
    private JButton importButton;
    private JFileChooser fileChooser;
    private String inputJson;

    public Main() {
        logger.info("==================| GDV Json Transform started |==================");
        setTitle("GDV JSON Transform Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inputTextArea = new JTextArea();
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.append("Welcome to GDV JSON Transformer.\n[Step 1] Import GDV Json File.\n");

        // Import GDV Json File
        importButton = new JButton("Import GDV Json File");
        importButton.addActionListener(e -> loadJsonFile());

        // Clear Log
        JButton clearLogButton = new JButton("Clear Log");
        clearLogButton.addActionListener(event -> logArea.replaceRange("Clear log success.\n", 0, logArea.getDocument().getLength()));

        fileChooser = new JFileChooser();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(inputTextArea), BorderLayout.NORTH);
        panel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadJsonFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                inputJson = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                logInfo("Input:\n" + inputJson);
                transformJson();
            } catch (IOException e) {
                logError(e.getMessage() + ", stack: " + Arrays.toString(e.getStackTrace()));
                JOptionPane.showMessageDialog(this, "Failed to import input.json", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void transformJson() {
        try {
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<?> groovyClass = classLoader.parseClass(new File("transform.groovy"));

            String result = (String) groovyClass.getMethod("convert", String.class).invoke(null, inputJson);
            saveJsonToFile(result, "result.json");
            logInfo("Result:\n" + result);
        } catch (Exception e) {
            logError(e.getMessage() + ", stack: " + Arrays.toString(e.getStackTrace()));
            JOptionPane.showMessageDialog(this, "Failed to transform JSON", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveJsonToFile(String jsonDocument, String nameFile) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save JSON File");
        fileChooser.setSelectedFile(new File(nameFile));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                fileWriter.write(jsonDocument);
                logInfo("JSON saved to file: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                logError(e.getMessage() + ", stack: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private void logInfo(String message) {
        logArea.append(message + "\n");
        logger.info(message);
    }

    private void logError(String message) {
        logArea.append("Error: " + message + "\n");
        logger.error(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}