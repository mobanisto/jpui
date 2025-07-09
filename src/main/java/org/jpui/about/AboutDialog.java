package org.jpui.about;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

public class AboutDialog extends JDialog
{

	public final static int PAGE_ABOUT = 0;
	public final static int PAGE_LICENSE = 1;

	private AboutPanel aboutPanel;
	private LicensePanel licensePanel;

	public AboutDialog(int page)
	{
		setTitle("About JPUI");

		aboutPanel = new AboutPanel();
		licensePanel = new LicensePanel();

		JTabbedPane tabs = new JTabbedPane();
		tabs.add("About", aboutPanel);
		tabs.add("License", licensePanel);

		if (page == PAGE_ABOUT) {
			tabs.setSelectedIndex(0);
		} else if (page == PAGE_LICENSE) {
			tabs.setSelectedIndex(1);
		}

		setContentPane(tabs);
	}

	public static void showDialog(int page)
	{
		AboutDialog dialog = new AboutDialog(page);
		dialog.setLocationByPlatform(true);
		dialog.setSize(400, 400);
		dialog.setVisible(true);
	}
}
