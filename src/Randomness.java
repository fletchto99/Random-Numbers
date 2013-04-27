import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Randomness extends JApplet {

	private static final long serialVersionUID = -1737807640424560213L;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JTextPane input;
	private JTable table;
	private JTextArea information;
	private RandomNumberArray numbers = null;

	@Override
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JFrame.setDefaultLookAndFeelDecorated(true);
				setSize(720, 770);

				scrollPane1 = new JScrollPane();
				scrollPane2 = new JScrollPane();
				scrollPane3 = new JScrollPane();
				input = new JTextPane();
				table = new JTable();
				information = new JTextArea();

				Container contentPane = getContentPane();
				contentPane.setLayout(null);

				input.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent arg0) {
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						if (input.getText().length() > 0) {
							numbers = new RandomNumberArray(input.getText());
						} else {
							numbers = null;
						}
						populateTable();
						updateInformation();
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
					}

				});
				scrollPane1.setViewportView(input);
				contentPane.add(scrollPane1);
				scrollPane1.setBounds(15, 20, 675, 185);

				((DefaultTableModel) table.getModel()).addColumn("Number");
				((DefaultTableModel) table.getModel()).addColumn("Amount");
				scrollPane2.setViewportView(table);
				contentPane.add(scrollPane2);
				scrollPane2.setBounds(15, 210, 675, 285);

				information.setLineWrap(true);
				information.setWrapStyleWord(true);
				information.setEditable(false);
				information.setFont(new Font("Tahoma", 0, 20));
				information
						.setText("Please insert some values in the text box above. To seprate values use a single space.");
				scrollPane3.setViewportView(information);
				contentPane.add(scrollPane3);
				scrollPane3.setBounds(15, 505, 675, 220);

			}
		});
	}

	public void populateTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		if (numbers != null) {
			for (int i : numbers.getNumberArray()) {
				model.addRow(new Object[] { String.valueOf(i),
						String.valueOf(numbers.getAmount(i)) });
			}
		}
	}

	private void updateInformation() {
		if (numbers != null) {
			information
					.setText(formatMode()
							+ ". The average number is "
							+ new DecimalFormat("#,##0.0").format(numbers
									.findAverage())
							+ ". The ratio of evens to odds is "
							+ numbers.evenCount()
							+ ":"
							+ numbers.oddCount()
							+ " where even numbers make up "
							+ new DecimalFormat("#,##0.0").format(percentage(
									numbers.evenCount(), numbers.total()))
							+ "% and odds make up "
							+ new DecimalFormat("#,##0.0").format(percentage(
									numbers.oddCount(), numbers.total()))
							+ "%. The ratio of prime numbers to non-prime numbers is "
							+ numbers.primeCount()
							+ ":"
							+ (numbers.total() - numbers.primeCount())
							+ " where prime numbers make up "
							+ new DecimalFormat("#,##0.0").format(percentage(
									numbers.primeCount(), numbers.total()))
							+ "% and non-prime numbers make up "
							+ new DecimalFormat("#,##0.0").format(percentage(
									(numbers.total() - numbers.primeCount()),
									numbers.total()))
							+ "%. The total amount of numbers tested is "
							+ numbers.total()
							+ " which has a range of "
							+ numbers.range()
							+ " numbers with "
							+ numbers.getNumberArray().get(0)
							+ " being the lowest number and "
							+ numbers.getNumberArray().get(
									numbers.getNumberArray().size() - 1)
							+ " being the highest number.");
		} else {
			information
					.setText("Please insert some values in the text box above. To seprate values use a single space.");
		}
	}

	private String formatMode() {
		String mode = "The number"
				+ (numbers.findMode().length > 2 ? "s " : "") + " that "
				+ (numbers.findMode().length > 2 ? "have" : "has")
				+ " the most "
				+ (numbers.findMode().length > 2 ? "are " : "is ");
		for (int i = 1; i < numbers.findMode().length; i++) {
			mode += String.valueOf(numbers.findMode()[i]);
			if (i < numbers.findMode().length - 1) {
				mode += ", ";
			}
		}
		mode += " with "
				+ (numbers.findMode().length > 2 ? "each" : "it")
				+ " being selected "
				+ String.valueOf(numbers.findMode()[0])
				+ " time"
				+ (numbers.findMode()[0] > 1 ? "s " : "")
				+ " which is worth "
				+ new DecimalFormat("#,##0.0")
						.format(percentage(
								numbers.findMode()[0]
										* (numbers.findMode().length - 1),
								numbers.total())) + "%";
		return mode;
	}

	private double percentage(double number, double total) {
		return number / total * 100;
	}
}
