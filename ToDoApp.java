import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ToDoApp extends JFrame {
    private JTextField taskInput;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private ArrayList<String> secretCode = new ArrayList<>();
    private final String[] KONAMI_CODE = {"UP", "UP", "DOWN", "DOWN", "LEFT", "RIGHT", "LEFT", "RIGHT"};
    private int konamiIndex = 0;

    public ToDoApp() {
        setTitle("To-Do App");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set color scheme
        Color primaryColor = new Color(102, 126, 234);
        Color bgColor = new Color(245, 247, 250);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(bgColor);

        // Title
        JLabel titleLabel = new JLabel("📝 My To-Do List", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 0));
        inputPanel.setBackground(bgColor);

        taskInput = new JTextField();
        taskInput.setFont(new Font("Arial", Font.PLAIN, 16));
        taskInput.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JButton addButton = new JButton("Add Task");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(primaryColor);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskCellRenderer());

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(bgColor);

        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 107, 107));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton completeButton = new JButton("Mark Complete");
        completeButton.setFont(new Font("Arial", Font.BOLD, 14));
        completeButton.setBackground(new Color(72, 187, 120));
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.setBorderPainted(false);
        completeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        // Add button panel between scroll pane and input
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(bgColor);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Event listeners
        addButton.addActionListener(e -> addTask());
        taskInput.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        completeButton.addActionListener(e -> markComplete());

        // Easter Egg: Arrow key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                checkKonamiCode(e);
            }
        });

        setFocusable(true);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskInput.setText("");
            taskInput.requestFocus();
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void markComplete() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String task = taskListModel.get(selectedIndex);
            if (!task.startsWith("✓ ")) {
                taskListModel.set(selectedIndex, "✓ " + task);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark complete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Easter Egg: Konami Code checker
    private void checkKonamiCode(KeyEvent e) {
        String key = "";
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: key = "UP"; break;
            case KeyEvent.VK_DOWN: key = "DOWN"; break;
            case KeyEvent.VK_LEFT: key = "LEFT"; break;
            case KeyEvent.VK_RIGHT: key = "RIGHT"; break;
            default:
                konamiIndex = 0;
                return;
        }

        if (key.equals(KONAMI_CODE[konamiIndex])) {
            konamiIndex++;
            if (konamiIndex == KONAMI_CODE.length) {
                triggerEasterEgg();
                konamiIndex = 0;
            }
        } else {
            konamiIndex = 0;
        }
    }

    // Easter Egg activation
    private void triggerEasterEgg() {
        JDialog easterEggDialog = new JDialog(this, "🎉 Secret Unlocked!", true);
        easterEggDialog.setSize(500, 400);
        easterEggDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(26, 26, 26));

        JLabel title = new JLabel("🚀 DEVELOPER MODE ACTIVATED 🚀", JLabel.CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 20));
        title.setForeground(new Color(0, 255, 0));

        JTextArea message = new JTextArea();
        message.setText(
                "═══════════════════════════════════\n" +
                        "      CONGRATULATIONS HACKER!\n" +
                        "═══════════════════════════════════\n\n" +
                        "You've discovered the secret Konami Code!\n\n" +
                        "Fun facts about this app:\n" +
                        "• 99 little bugs in the code\n" +
                        "• 99 little bugs\n" +
                        "• Take one down, patch it around\n" +
                        "• 127 little bugs in the code...\n\n" +
                        "Pro tip: The best debugger is still\n" +
                        "System.out.println() 😄\n\n" +
                        "Keep coding and stay awesome!\n" +
                        "- Your Friendly To-Do App"
        );
        message.setFont(new Font("Courier New", Font.PLAIN, 14));
        message.setForeground(new Color(0, 255, 0));
        message.setBackground(new Color(26, 26, 26));
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);

        JButton closeButton = new JButton("Close (I feel special now)");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(0, 255, 0));
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> easterEggDialog.dispose());

        panel.add(title, BorderLayout.NORTH);
        panel.add(message, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        easterEggDialog.add(panel);
        easterEggDialog.setVisible(true);

        // Add confetti to task list
        for (int i = 0; i < 3; i++) {
            taskListModel.addElement("🎉 You found the Easter Egg! 🎉");
        }
    }

    // Custom cell renderer for completed tasks
    class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            String task = value.toString();
            if (task.startsWith("✓ ")) {
                label.setForeground(new Color(150, 150, 150));
                label.setFont(label.getFont().deriveFont(Font.ITALIC));
            }

            label.setBorder(new EmptyBorder(8, 10, 8, 10));
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ToDoApp app = new ToDoApp();
            app.setVisible(true);

            // Show hint about Easter egg
            JOptionPane.showMessageDialog(app,
                    "💡 Psst... Try pressing arrow keys in a special sequence!\n" +
                            "Hint: ↑↑↓↓←→←→",
                    "Secret Tip",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}