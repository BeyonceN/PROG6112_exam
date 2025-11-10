/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lab_services_student
 */
public class MainForm {
       private JButton btnLoad;
    private JButton btnSave;
    private JButton btnClear;
    private JTextArea outputArea;
    private JFileChooser fileChooser;

    // holds last loaded product names (for saving) and numeric sales
    private List<String> productNames = new ArrayList<>();
    private List<int[]> productSalesList = new ArrayList<>();

    public MainForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Product Sales Application");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Buttons
        btnLoad = new JButton("Load Product Data");
        btnSave = new JButton("Save Product Data");
        btnClear = new JButton("Clear");

        // Text area (read-only) to display results
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // File chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "csv"));

        // Layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnLoad);
        topPanel.add(btnSave);
        topPanel.add(btnClear);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnLoad.addActionListener(e -> loadProductData());
        btnSave.addActionListener(e -> saveProductData());
        btnClear.addActionListener(e -> clearData());
    }

    private void loadProductData() {
        int rc = fileChooser.showOpenDialog(this);
        if (rc != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        productNames.clear();
        productSalesList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty()) continue;
                // Expect CSV-like: name, val1, val2, ...
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    // Not enough columns: skip line and show warning
                    JOptionPane.showMessageDialog(this,
                            "Skipping invalid line " + lineNo + ": " + line,
                            "Parse Warning", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                String name = parts[0].trim();
                List<Integer> values = new ArrayList<>();
                boolean parseError = false;
                for (int i = 1; i < parts.length; i++) {
                    String p = parts[i].trim();
                    if (p.isEmpty()) continue;
                    try {
                        values.add(Integer.parseInt(p));
                    } catch (NumberFormatException ex) {
                        parseError = true;
                        break;
                    }
                }
                if (parseError || values.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Skipping invalid numeric values on line " + lineNo + ": " + line,
                            "Parse Warning", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                productNames.add(name);
                // convert values to int[]
                int[] arr = new int[values.size()];
                for (int i = 0; i < values.size(); i++) arr[i] = values.get(i);
                productSalesList.add(arr);
            }
            if (productSalesList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No valid product sales found in the file.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Build int[][] and compute using ProductSales
            int[][] salesArray = new int[productSalesList.size()][];
            for (int i = 0; i < productSalesList.size(); i++) {
                salesArray[i] = productSalesList.get(i);
            }
            ProductSales ps = new ProductSales(salesArray);
            displayProductSalesResults(ps);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProductSalesResults(ProductSales ps) {
        StringBuilder sb = new StringBuilder();
        sb.append("Total sales: ").append(ps.GetTotalSales()).append(System.lineSeparator());
        sb.append(String.format("Average sales: %.2f%n", ps.GetAverageSales()));
        sb.append("Sales over limit (" + ProductSales.SALES_LIMIT + "): ").append(ps.GetSalesOverLimit()).append(System.lineSeparator());
        sb.append("Sales under or equal to limit (" + ProductSales.SALES_LIMIT + "): ").append(ps.GetSalesUnderLimit()).append(System.lineSeparator());
        sb.append("Products processed: ").append(ps.GetProductsProcessed()).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("Detailed data (product rows):").append(System.lineSeparator());

        // Show the raw data rows and product names (if available)
        for (int i = 0; i < productSalesList.size(); i++) {
            String name = (i < productNames.size()) ? productNames.get(i) : ("Product " + (i + 1));
            sb.append(name).append(": ");
            int[] row = productSalesList.get(i);
            for (int j = 0; j < row.length; j++) {
                sb.append(row[j]);
                if (j < row.length - 1) sb.append(", ");
            }
            sb.append(System.lineSeparator());
        }

        outputArea.setText(sb.toString());
    }

    private void saveProductData() {
        if (productSalesList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to save. Load a file first.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int rc = fileChooser.showSaveDialog(this);
        if (rc != JFileChooser.APPROVE_OPTION) return;

        File file = fileChooser.getSelectedFile();
        // Ensure extension
        if (!file.getName().contains(".")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < productSalesList.size(); i++) {
                String name = (i < productNames.size()) ? productNames.get(i) : ("Product" + (i + 1));
                bw.write(name);
                int[] row = productSalesList.get(i);
                for (int v : row) {
                    bw.write("," + v);
                }
                bw.newLine();
            }
            bw.flush();
            JOptionPane.showMessageDialog(this, "Saved to " + file.getAbsolutePath(), "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearData() {
        productNames.clear();
        productSalesList.clear();
        outputArea.setText("");
    }

    public static void main(String[] args) {
        // Run Swing GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainForm f = new MainForm();
            f.setVisible(true);
        });
    }

}
