package org.jpui.about;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AboutPanel extends JPanel
{

	final static Logger logger = LoggerFactory.getLogger(AboutPanel.class);

	public AboutPanel()
	{
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;

		JScrollPane jsp = new JScrollPane();
		add(jsp, c);

		JEditorPane pane = new JEditorPane();
		jsp.setViewportView(pane);
		pane.setEditable(false);

		HTMLEditorKit kit = new HTMLEditorKit();
		pane.setEditorKit(kit);

		String filename = "res/about.html";
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(filename);
		try {
			logger.debug("url: " + url);
			pane.setPage(url);
		} catch (IOException e) {
			logger.debug("unable to set page: " + e.getMessage());
		}
	}
}
