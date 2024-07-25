package marko.mvs.gdv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import marko.mvs.gdv.config.ConfigManager;
import marko.mvs.gdv.entity.ContractsData;
import marko.mvs.gdv.process.CamelProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

public class Main extends JFrame {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static JTextArea logArea;
    boolean loadConfig = ConfigManager.getInstance().isConfigLoaded();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
    public Main() {
        initGui();
    }

    private void initGui() {
        logger.info("==================| Gdv Transform Apache Camel Started |==================");

        // Khởi tạo các thành phần GUI
        logArea = new JTextArea();
        JButton selectFileButton = new JButton("Select File");

        // Cài đặt thuộc tính cho JTextArea
        logArea.setEditable(false);
        logArea.setRows(10);
        logArea.setColumns(40);
        logArea.append("Welcome to Gdv Transform Apache Camel.\n[Step 1] Import Gdv Json file.\n");


        JScrollPane scrollPane = new JScrollPane(logArea);
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Import GDV to JSON
        JButton importGdvButton = new JButton("Import Gdv Json File");
        importGdvButton.addActionListener(event -> importGdvToJson());

        // Clear Log
        JButton clearLogButton = new JButton("Clear Log");
        clearLogButton.addActionListener(event -> logArea.replaceRange("Clear log success.\n", 0, logArea.getDocument().getLength()));

        // Cài đặt khung chính và các thành phần
        setTitle("Gdv Transform Apache Camel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonPanel.add(importGdvButton);
        buttonPanel.add(clearLogButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        if (loadConfig) {
            add(buttonPanel, BorderLayout.SOUTH);
        } else {
            logArea.append("Error loading config file, please contact to admin to add config file: ./config/config.txt");
        }

        // Hiển thị khung chính
        setVisible(true);
    }

    private void importGdvToJson() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            logger.info("Selected file: " + selectedFile.getAbsolutePath());
            logArea.append("Selected file: " + selectedFile.getAbsolutePath() + "\n");

            String outputFilePath = selectedFile.getParent() + File.separator + "output.json";
            processJson(selectedFile.getAbsolutePath(), outputFilePath);
        }
    }


    public static void processJson(String inputFilePath, String outputFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Đọc file với mã hóa ISO-8859-1
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath), StandardCharsets.ISO_8859_1))) {
                JsonNode rootNode = mapper.readTree(reader);

                if (!rootNode.isMissingNode()) {
                    // Create output structure
                    JsonNode outputNode = createOutputNode(rootNode, mapper);

                    // Chuyển đổi JsonNode output thành các object Java
                    ContractsData contractsData = mapper.treeToValue(outputNode, ContractsData.class);

                    // Write output to file
                    try (FileWriter file = new FileWriter(outputFilePath)) {
                        logger.info("Waiting write to file: " + outputFilePath + "...");
                        logArea.append("Waiting write to file: " + outputFilePath + "...\n");
                        mapper.writerWithDefaultPrettyPrinter().writeValue(file, contractsData);
                        logger.info("Processing complete. Output saved to: " + outputFilePath);
                        logArea.append("Processing complete. Output saved to: " + outputFilePath + "\n");
                    }

                } else {
                    logger.warning("Contracts node is missing.");
                }
            }
        } catch (IOException e) {
            logArea.append(e.getMessage() + "\nStack: " + Arrays.toString(e.getStackTrace()) + "\n");
            logger.severe("Error processing JSON: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode createOutputNode(JsonNode contractsNode, ObjectMapper mapper) throws Exception {
        // Conver ContractsNode to JSON
        String inputJson = mapper.writeValueAsString(contractsNode);

        // Sử dụng CamelProcessor để xử lý dữ liệu JSON
        CamelProcessor camelProcessor = new CamelProcessor();
        String outputJson = camelProcessor.processJson(inputJson);

        // Chuyển đổi chuỗi JSON đã xử lý thành JsonNode
        return mapper.readTree(outputJson);
    }
}
