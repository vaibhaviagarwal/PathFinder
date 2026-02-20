import api.CourseDatabase;
import java.awt.*;
import javax.swing.*;
import model.Course;

public class CourseFinderUI {

    private JFrame mainWindow;
    private JTextField courseCodeField;
    private JTextArea courseDetailsArea;
    private JButton searchCourseButton;
    private JButton refreshCoursesButton;
    private static final Color PINK_BG = new Color(255, 232, 244);
    private static final Color PINK_PANEL = new Color(255, 210, 232);
    private static final Color PINK_DARK = new Color(219, 87, 149);
    private static final Color PINK_ACCENT = new Color(255, 111, 181);

    public CourseFinderUI() {
        // Load course data when app starts
        CourseDatabase.load("waterloo_courses.json");

        // Main window setup
        mainWindow = new JFrame("Waterloo Course Finder");
        mainWindow.setSize(720, 500);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout(12, 12));
        mainWindow.getContentPane().setBackground(PINK_BG);

        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 28);
        Font bodyFont = new Font("Comic Sans MS", Font.PLAIN, 16);
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 16);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.setBackground(PINK_PANEL);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PINK_DARK, 3),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel titleLabel = new JLabel("✦ Waterloo Course Finder ✦", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(PINK_DARK);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        inputPanel.setOpaque(false);

        JLabel inputLabel = new JLabel("Enter course code (e.g., CS136):");
        inputLabel.setFont(bodyFont);
        inputLabel.setForeground(PINK_DARK);
        inputPanel.add(inputLabel);

        // Input field for course code
        courseCodeField = new JTextField(12);
        courseCodeField.setFont(bodyFont);
        courseCodeField.setBackground(Color.WHITE);
        courseCodeField.setForeground(new Color(90, 20, 60));
        courseCodeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PINK_DARK, 2),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        inputPanel.add(courseCodeField);

        // Search button
        searchCourseButton = new JButton("Search");
        styleActionButton(searchCourseButton, buttonFont);
        inputPanel.add(searchCourseButton);

        refreshCoursesButton = new JButton("Refresh");
        styleActionButton(refreshCoursesButton, buttonFont);
        inputPanel.add(refreshCoursesButton);

        topPanel.add(inputPanel, BorderLayout.CENTER);
        mainWindow.add(topPanel, BorderLayout.NORTH);

        // Text area to display course info
        courseDetailsArea = new JTextArea();
        courseDetailsArea.setFont(bodyFont);
        courseDetailsArea.setLineWrap(true);
        courseDetailsArea.setWrapStyleWord(true);
        courseDetailsArea.setEditable(false);
        courseDetailsArea.setBackground(new Color(255, 246, 252));
        courseDetailsArea.setForeground(new Color(90, 20, 60));
        courseDetailsArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(courseDetailsArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PINK_DARK, 3),
            BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        scrollPane.getViewport().setBackground(new Color(255, 246, 252));
        mainWindow.add(scrollPane, BorderLayout.CENTER);

        // Button actions
        searchCourseButton.addActionListener(e -> performSearch());

        refreshCoursesButton.addActionListener(e -> {
            CourseDatabase.load("waterloo_courses.json");
            courseDetailsArea.setText("Courses refreshed! Total loaded: " + CourseDatabase.getAllCourses().size());
        });

        courseCodeField.addActionListener(e -> searchCourseButton.doClick());

        // Show frame
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    private void performSearch() {
        String code = courseCodeField.getText().trim();
        Course course = CourseDatabase.getCourse(code);

        if (course != null) {
            courseDetailsArea.setText(
                    "Subject: " + course.getSubject() + "\n" +
                            "Number: " + course.getNumber() + "\n" +
                            "Title: " + course.getTitle() + "\n" +
                            "Prerequisites: " + String.join(", ", course.getPrereq()) + "\n" +
                            "Corequisites: " + String.join(", ", course.getCoreq()) + "\n" +
                            "Antirequisites: " + String.join(", ", course.getAntireq())
            );
        } else {
            courseDetailsArea.setText("Course not found!");
        }
    }

    private void styleActionButton(JButton button, Font font) {
        button.setFont(font);
        button.setBackground(PINK_ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PINK_DARK, 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        // Start the UI
        SwingUtilities.invokeLater(() -> new CourseFinderUI());
    }
}
