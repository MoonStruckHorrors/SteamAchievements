import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class SteamAchGUI {
	JFrame window;
	JPanel fields, achPanel, bottomBar, achBar;
	JLabel lblUName, lblGName, achIcon;
	JTextField uName, gName;
	JTextArea achDesc;
	JButton doIt;
	JScrollPane sp;

	public static void main(String[] args) {
		SteamAchGUI instance = new SteamAchGUI();
		instance.createGUI();
	}
	public void createGUI() {
		window = new JFrame("Steam Achievements");
		fields = new JPanel();
		achPanel = new JPanel();
		bottomBar = new JPanel();
		uName = new JTextField("");
		gName = new JTextField("");
		doIt = new JButton("Show Achievements");
		lblUName = new JLabel("Username", SwingConstants.RIGHT);
		lblGName = new JLabel("Name/app ID of game", SwingConstants.RIGHT);
		sp = new JScrollPane(achPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		achPanel.setPreferredSize(new Dimension(800, 540));
		achPanel.setBackground(Color.BLACK);
		
		Dimension d = new Dimension(150, 30);
		
		lblUName.setPreferredSize(d);
		uName.setPreferredSize(d);
		lblGName.setPreferredSize(d);
		gName.setPreferredSize(d);
		doIt.setPreferredSize(d);
		doIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strUName = uName.getText();
				String strGName = gName.getText();
				System.out.println(strUName + " and " + strGName);
				SteamAchievements sa = new SteamAchievements(strUName, strGName);
				sa.print();
				showAchList(sa.getAchList());
			}
		});
		
		fields.add(lblUName);
		fields.add(uName);
		fields.add(lblGName);
		fields.add(gName);
		fields.add(doIt);
		
		window.setSize(800, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.getContentPane().add(BorderLayout.NORTH, fields);
		window.getContentPane().add(BorderLayout.CENTER, sp);
		window.getContentPane().add(BorderLayout.SOUTH, bottomBar);
		
		window.setVisible(true);
	}
	public void showAchList(ArrayList<Achievement> achList) {
		if(achList.size() != 0) {
			achPanel.setPreferredSize(new Dimension(800, 540));
			Iterator<Achievement> i = achList.iterator();
			achPanel.removeAll();
			while(i.hasNext()) {
				Achievement a = i.next();
				achBar = new JPanel(new BorderLayout());
				achBar.setPreferredSize(new Dimension(800, 80));
				try {
					achIcon = new JLabel(new ImageIcon(new URL(a.getIcon())));
					achIcon.setBackground(Color.GRAY);
					achIcon.setOpaque(true);
					achIcon.setPreferredSize(new Dimension(80, 80));
				} catch(Exception e) {
					e.printStackTrace();
				}
				achDesc = new JTextArea(a.getName() + "\n" + a.getDesc() + (a.isUnlocked() == true? "\n\n Unlocked on : " + a.getDateUnlocked().toString() : "\n\nLocked"));
				achDesc.setBackground(Color.BLACK);
				achDesc.setForeground(Color.WHITE);
				achDesc.setOpaque(true);
				achDesc.setEditable(false);
				achDesc.setPreferredSize(new Dimension(720, 80));
				achBar.add(achIcon, BorderLayout.WEST);
				achBar.add(achDesc, BorderLayout.EAST);
				achDesc = null;
				achIcon = null;
				achPanel.add(achBar);
				
				int newHeight = (int)achPanel.getPreferredSize().getHeight() + 80;
				achPanel.setPreferredSize(new Dimension(800, newHeight));
			}
		}
	}
}