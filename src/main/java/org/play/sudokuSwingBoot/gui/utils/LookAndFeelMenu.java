package org.play.sudokuSwingBoot.gui.utils;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


// this class is adapted from llm provided code
@Component
@Scope("singleton")
public class LookAndFeelMenu extends JMenu {
    private static final String MENU_TEXT = "Look and Feel";
    private static final String SYSTEM_DEFAULT_TEXT =
		"System Default";

    /**
     * Creates a JMenu with all available Look and Feel options
     * that automatically updates the parent window when selected
     * @return Configured JMenu with L&F options
     */
    public LookAndFeelMenu() {
        super(MENU_TEXT);
        
        // Get all available Look and Feels
        List<LookAndFeelInfo> availableLookAndFeels =
			getAvailableLookAndFeels();
        
        // Add System Default option
        JMenuItem systemDefaultItem =
			new JMenuItem(SYSTEM_DEFAULT_TEXT);

		systemDefaultItem.addActionListener(
			new LookAndFeelAction(UIManager
				.getSystemLookAndFeelClassName()));
        this.add(systemDefaultItem);
        
        this.addSeparator();
        
        // Add all available L&F options
        for (LookAndFeelInfo info : availableLookAndFeels) {
            JMenuItem item = new JMenuItem(info.getName());
            item.addActionListener(
				new LookAndFeelAction(info.getClassName())
			);
            this.add(item);
        }
    }

    /**
     * Gets all available Look and Feels that can be instantiated
     * @return List of available LookAndFeelInfo objects
     */
    private static List<LookAndFeelInfo> getAvailableLookAndFeels() {
        return Arrays.stream(UIManager.getInstalledLookAndFeels())
			.filter(info ->
				isLookAndFeelAvailable(info.getClassName()))
			.collect(Collectors.toList());
    }

    /**
     * Checks if a Look and Feel is available (can be instantiated)
     * @param className The class name of the Look and Feel to check
     * @return true if available, false otherwise
     */
    private static boolean isLookAndFeelAvailable(String className) {
        try {
            LookAndFeel laf = (LookAndFeel) Class.forName(className)
				.getDeclaredConstructors()[0].newInstance();
            return laf.isSupportedLookAndFeel();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ActionListener implementation for Look and Feel changes
     */
    private static class LookAndFeelAction implements ActionListener {
        private final String lookAndFeelClassName;

        public LookAndFeelAction(String lookAndFeelClassName) {
            this.lookAndFeelClassName = lookAndFeelClassName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Change the Look and Feel
                UIManager.setLookAndFeel(lookAndFeelClassName);
                
                // Update all top-level windows
                for (Window window : Window.getWindows()) {
                    if (window.isDisplayable()) {
                        SwingUtilities.updateComponentTreeUI(window);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
					"Failed to set Look and Feel: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sets the default Look and Feel to the system default
     */
    public static void setSystemDefaultLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager
				.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to cross-platform if system LAF fails
            try {
                UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
