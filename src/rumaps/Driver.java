package rumaps;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * **RUN THIS FILE TO RUN THE APPLICATION**
 * 
 * The Driver class represents the main entry point of the RUMaps application.
 * It creates the main window, initializes the map panel, and handles user interactions.
 *
 * @author Vian Miranda
 * @author Anna Lu
 */
public class Driver {
    /**
     * NOTE TO STUDENTS: This is the seed which the Driver uses when setting the 
     * traffic factor. Feel free to change the seed to your choice, but remember 
     * that changing the seed will result in different outputs.
     */
    private final static int SEED = 2024;
    private final String networkPath = "Coordinates.in";
    private final String[] imagePaths = {"assets/satellite.png", "assets/overlay.png"};
    private final double defaultOverlayTransparency = 0.6;
    private final String defaultStreetInfoString = "Hover over a block to see its street name and block number";

    private JFrame window;
    private MapPanel mapPanel;
    private RUMaps rumaps;

    private JLabel streetInfoLabel;
    private JLabel blockLengthLabel;
    private JLabel blockTrafficFactorLabel;
    private JLabel blockTrafficLabel;

    private String defaultBlockLengthValue;
    private String defaultBlockTrafficFactorValue;
    private String defaultBlockTrafficValue;

    private JLabel minIntersectionsLabel;
    private JLabel fastestPathLabel;

    private Driver() {
        /* Main Window */
        StdRandom.setSeed(SEED);

        window = new JFrame();
        window.setLayout(new BorderLayout());
        window.setTitle("RU Maps");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Map */
        mapPanel = new MapPanel(this, imagePaths, defaultOverlayTransparency);
        window.add(mapPanel, BorderLayout.CENTER);

        /* Control Panel */
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        JPanel controlPanel = makeControlPanel();
        window.add(controlPanel, BorderLayout.SOUTH);

        window.setResizable(false);
        window.setVisible(true);
        window.pack();
    }

    private JPanel makeControlPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        rumaps = new RUMaps(mapPanel, networkPath);

        JPanel streetInfo = makeStreetInfo();
        container.add(streetInfo, BorderLayout.NORTH);

        JPanel buttonPanel = makeMapOverlaySelect();
        container.add(buttonPanel, BorderLayout.SOUTH);

        return container;
    }

    private JPanel makeStreetInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        streetInfoLabel = new JLabel(defaultStreetInfoString, SwingConstants.CENTER);
        JPanel blockInfoPanel = makeBlockInfo();
        JPanel pathInfoPanel = makePathInfo();

        panel.add(streetInfoLabel);
        panel.add(blockInfoPanel);
        panel.add(pathInfoPanel);

        return panel;
    }

    private JPanel makeBlockInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        blockLengthLabel = new JLabel();
        blockLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        blockTrafficFactorLabel = new JLabel();
        blockTrafficFactorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        blockTrafficLabel = new JLabel();
        blockTrafficLabel.setHorizontalAlignment(SwingConstants.CENTER);

        defaultBlockLengthValue = "N/A";
        defaultBlockTrafficFactorValue = "N/A";
        defaultBlockTrafficValue = "N/A";

        updateStreetInfo(null);

        panel.add(blockLengthLabel);
        panel.add(blockTrafficFactorLabel);
        panel.add(blockTrafficLabel);

        return panel;
    }

    private JPanel makePathInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        minIntersectionsLabel = new JLabel();
        minIntersectionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fastestPathLabel = new JLabel();
        fastestPathLabel.setHorizontalAlignment(SwingConstants.CENTER);

        updatePathInfo(null, null);

        panel.add(minIntersectionsLabel);
        panel.add(fastestPathLabel);
        panel.add(new JLabel());

        return panel;
    }

    private JPanel makeMapOverlaySelect() {
        JPanel completePanel = new JPanel();
        completePanel.setLayout(new GridLayout(3, 1));
        JLabel overlayLabel = new JLabel("Map Settings", SwingConstants.CENTER);
        overlayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        completePanel.add(overlayLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JCheckBox satellite = new JCheckBox("Satellite", true);
        JCheckBox network = new JCheckBox("Street Network", true);
        JCheckBox minimize = new JCheckBox("Minimize Intersections", true);
        minimize.setForeground(mapPanel.minimizeIntersectionPathColor);
        JCheckBox fast = new JCheckBox("Fastest Path", true);
        fast.setForeground(mapPanel.fastestPathColor);
        JPanel transparencyPanel = makeTransparencySlider();

        satellite.addActionListener(e -> {
            if (satellite.isSelected()) {
                mapPanel.enableSatellite();
            } else {
                mapPanel.disableSatellite();
            }
        });

        network.addActionListener(e -> mapPanel.toggleNetworkVisuals(network.isSelected()));
        minimize.addActionListener(e -> mapPanel.toggleMinimizeIntersectionPath(minimize.isSelected()));
        fast.addActionListener(e -> mapPanel.toggleFastestPath(fast.isSelected()));

        panel.add(satellite);
        panel.add(network);
        panel.add(minimize);
        panel.add(fast);

        completePanel.add(panel);
        completePanel.add(transparencyPanel);

        return completePanel;
    }

    private JPanel makeTransparencySlider() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Overlay: " + (int) (defaultOverlayTransparency * 100) + "%");
        JSlider slider = new JSlider(0, 100, (int) (defaultOverlayTransparency * 100));
        slider.addChangeListener(e -> {
            label.setText("Overlay: " + slider.getValue() + "%");
            double transparency = slider.getValue() / 100.0;
            mapPanel.changeTransparency(transparency);
        });

        panel.add(label);
        panel.add(slider);
        return panel;
    }

    public void updateStreetInfo(Block block) {
        if (block == null) {
            streetInfoLabel.setText(defaultStreetInfoString);
            streetInfoLabel.setForeground(new Color(128, 128, 128, 150));
            streetInfoLabel.setFont(new Font("Dialog", Font.ITALIC, 20));

            blockLengthLabel.setText("<html><b>Length:</b> " + defaultBlockLengthValue);
            blockTrafficFactorLabel.setText("<html><b>Traffic Factor:</b> " + defaultBlockTrafficFactorValue);
            blockTrafficLabel.setText("<html><b>Traffic:</b> " + defaultBlockTrafficValue);
        } else {
            streetInfoLabel.setText(String.format("%s (Block %s)", block.getStreetName(), block.getBlockNumber()));
            streetInfoLabel.setForeground(new Color(31, 31, 31, 200));
            streetInfoLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            blockLengthLabel.setText(String.format("<html><b>Length:</b> %.2f", block.getLength()));
            blockTrafficFactorLabel.setText(String.format("<html><b>Traffic Factor:</b> %.3f", block.getTrafficFactor()));
            blockTrafficLabel.setText(String.format("<html><b>Traffic:</b> %.2f", block.getTraffic()));
        }
    }

    public void updatePathInfo(Intersection start, Intersection end) {
        if (start == null || end == null) {
            defaultBlockLengthValue = "N/A";
            defaultBlockTrafficFactorValue = "N/A";
            defaultBlockTrafficValue = "N/A";
            updateStreetInfo(null);

            minIntersectionsLabel.setText("<html><b>Minimize Intersections:</b>  N/A");
            minIntersectionsLabel.setToolTipText(null);

            fastestPathLabel.setText("<html><b>Fastest Path:</b>  N/A");
            fastestPathLabel.setToolTipText(null);

            mapPanel.hightlightMinimizeIntersectionPath(new ArrayList<>());
            mapPanel.highlightedFastestPath(new ArrayList<>());
        } else {
            ArrayList<Block> minPath = rumaps.minimizeIntersections(start, end);
            ArrayList<Block> fastPath = rumaps.fastestPath(start, end);

            double[] minPathInfo = rumaps.pathInformation(minPath);
            double[] fastPathInfo = rumaps.pathInformation(fastPath);
            defaultBlockLengthValue = String.format(
                "<span style=\"color:rgb(%s,%s,%s);\">%.2f</span>, <span style=\"color:rgb(%s,%s,%s);\">%.2f</span>", 
                mapPanel.minimizeIntersectionPathColor.getRed(), mapPanel.minimizeIntersectionPathColor.getGreen(), 
                mapPanel.minimizeIntersectionPathColor.getBlue(), minPathInfo[0], mapPanel.fastestPathColor.getRed(), 
                mapPanel.fastestPathColor.getGreen(), mapPanel.fastestPathColor.getBlue(), fastPathInfo[0]);
            defaultBlockTrafficFactorValue = String.format(
                "<span style=\"color:rgb(%s,%s,%s);\">%.3f</span>, <span style=\"color:rgb(%s,%s,%s);\">%.3f</span>", 
                mapPanel.minimizeIntersectionPathColor.getRed(), mapPanel.minimizeIntersectionPathColor.getGreen(), 
                mapPanel.minimizeIntersectionPathColor.getBlue(), minPathInfo[1], mapPanel.fastestPathColor.getRed(), 
                mapPanel.fastestPathColor.getGreen(), mapPanel.fastestPathColor.getBlue(), fastPathInfo[1]);
            defaultBlockTrafficValue = String.format(
                "<span style=\"color:rgb(%s,%s,%s);\">%.2f</span>, <span style=\"color:rgb(%s,%s,%s);\">%.2f</span>", 
                mapPanel.minimizeIntersectionPathColor.getRed(), mapPanel.minimizeIntersectionPathColor.getGreen(), 
                mapPanel.minimizeIntersectionPathColor.getBlue(), minPathInfo[2], mapPanel.fastestPathColor.getRed(), 
                mapPanel.fastestPathColor.getGreen(), mapPanel.fastestPathColor.getBlue(), fastPathInfo[2]);

            updateStreetInfo(null);
            
            String[] formattedMinPaths = formatPath(minPath);
            String[] formattedFastPaths = formatPath(fastPath);

            minIntersectionsLabel.setText("<html><b>Minimize Intersections:</b>  " + formattedMinPaths[0]);
            minIntersectionsLabel.setToolTipText(String.format("<html><span style=\"color:rgb(%s, %s, %s)\"> %s",
                mapPanel.minimizeIntersectionPathColor.getRed(), mapPanel.minimizeIntersectionPathColor.getGreen(), 
                mapPanel.minimizeIntersectionPathColor.getBlue(), formattedMinPaths[1]));

            fastestPathLabel.setText("<html><b>Fastest Path:</b>  " + formattedFastPaths[0]);
            fastestPathLabel.setToolTipText(String.format("<html><span style=\"color:rgb(%s, %s, %s)\"> %s",
                mapPanel.fastestPathColor.getRed(), mapPanel.fastestPathColor.getGreen(), 
                mapPanel.fastestPathColor.getBlue(),formattedFastPaths[1]));

            mapPanel.hightlightMinimizeIntersectionPath(minPath);
            mapPanel.highlightedFastestPath(fastPath);
        }
    }

    private String[] formatPath(ArrayList<Block> path) {
        if (path == null) {
            return new String[] {"N/A", null};
        }

        StringBuilder sb = new StringBuilder();
        int lineCount = 0, index = 0;
        for (Block block : path) {
            sb.append(block.getStreetName()).append(" (Block ").append(block.getBlockNumber()).append(") ");
            index++;
            if (index < path.size()) {
                sb.append("-> ");
            } else break;
            if (sb.length() / 120 > lineCount) {
                sb.append("<br>");
                lineCount = sb.length() / 120;
            }
        }
        int displayLen = Math.min(50, sb.length());
        sb.append("<br><em>total ").append(index).append(index == 1 ? " block</em>" :" blocks</em>");

        StringBuilder display = new StringBuilder();
        display.append(sb.substring(0, displayLen)).append("...");

        return new String[] {display.toString(), sb.toString()};
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Cannot set look and feel; falling back on default.");
            }
            new Driver();
        });
    }
}
