package com.mawasource.pdfscan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScanPDFCmd {

	private static File chosenFile;
	
	public static void main(String[] args) {
		startUI();
	}

	private static void startUI() {
		JFrame dialog = new JFrame();
		dialog.setTitle(Constants.UI_TITLE);
		dialog.setSize(Constants.FRAME_SIZE_WIDTH, Constants.FRAME_SIZE_HEIGHT);
		dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JPanel panel = new JPanel();
		JButton sortButton = new JButton(Constants.BTN_SORT);
		JButton fileChooseButton = new JButton(Constants.BTN_CHOOSE);
		final JLabel label = new JLabel(Constants.BTN_NOFILE, JLabel.LEFT);
		fileChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();

				int choice = chooser.showOpenDialog(panel);

				if (choice != JFileChooser.APPROVE_OPTION)
					return;

				chosenFile = chooser.getSelectedFile();
				label.setText(chosenFile.getAbsolutePath());
			}
		});

		sortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chosenFile != null) {
					new PDFProcessor().process(chosenFile.getAbsolutePath());
				}
			}
		});


		panel.add(label);
		panel.add(fileChooseButton);
		panel.add(sortButton);
		
		dialog.add(panel);
		dialog.setVisible(true);
	}


}
