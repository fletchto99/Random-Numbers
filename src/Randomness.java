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
                        .format(this.percentage(numbers.findMode()[0]
                                * (numbers.findMode().length - 1),
                                numbers.total())) + "%";
        return mode;
    }

    @Override
    public void init() {
        SwingUtilities
                .invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(UIManager
                                .getSystemLookAndFeelClassName());
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    Randomness.this.setSize(720, 770);

                    Randomness.this.scrollPane1 = new JScrollPane();
                    Randomness.this.scrollPane2 = new JScrollPane();
                    Randomness.this.scrollPane3 = new JScrollPane();
                    Randomness.this.input = new JTextPane();
                    Randomness.this.table = new JTable();
                    Randomness.this.information = new JTextArea();

                    final Container contentPane = Randomness.this
                            .getContentPane();
                    contentPane.setLayout(null);

                    Randomness.this.input.addKeyListener(new KeyListener() {

                        @Override
                        public void keyPressed(final KeyEvent arg0) {
                        }

                        @Override
                        public void keyReleased(final KeyEvent arg0) {
                            if (input.getText().length() > 0) {
                                numbers = new RandomNumberArray(input.getText());
                            } else {
                                numbers = null;
                            }
                            Randomness.this.populateTable();
                            Randomness.this.updateInformation();
                        }

                        @Override
                        public void keyTyped(final KeyEvent arg0) {
                        }

                    });
                    Randomness.this.scrollPane1
                            .setViewportView(Randomness.this.input);
                    contentPane.add(Randomness.this.scrollPane1);
                    Randomness.this.scrollPane1.setBounds(15, 20, 675, 185);

                    ((DefaultTableModel) Randomness.this.table.getModel())
                            .addColumn("Number");
                    ((DefaultTableModel) Randomness.this.table.getModel())
                            .addColumn("Amount");
                    Randomness.this.scrollPane2
                            .setViewportView(Randomness.this.table);
                    contentPane.add(Randomness.this.scrollPane2);
                    Randomness.this.scrollPane2.setBounds(15, 210, 675, 285);

                    Randomness.this.information.setLineWrap(true);
                    Randomness.this.information.setWrapStyleWord(true);
                    Randomness.this.information.setEditable(false);
                    Randomness.this.information.setFont(new Font("Tahoma", 0,
                            20));
                    Randomness.this.information
                            .setText("Please insert some values in the text box above. To seprate values use a single space.");
                    Randomness.this.scrollPane3
                            .setViewportView(Randomness.this.information);
                    contentPane.add(Randomness.this.scrollPane3);
                    Randomness.this.scrollPane3.setBounds(15, 505, 675, 220);

                });
    }

    private double percentage(final double number, final double total) {
        return number / total * 100;
    }

    public void populateTable() {
        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        if (numbers != null) {
            for (final int i : numbers.getNumberArray()) {
                model.addRow(new Object[] { String.valueOf(i),
                        String.valueOf(numbers.getAmount(i)) });
            }
        }
    }

    private void updateInformation() {
        if (numbers != null) {
            information
                    .setText(this.formatMode()
                            + ". The average number is "
                            + new DecimalFormat("#,##0.0").format(numbers
                                    .findAverage())
                            + ". The ratio of evens to odds is "
                            + numbers.evenCount()
                            + ":"
                            + numbers.oddCount()
                            + " where even numbers make up "
                            + new DecimalFormat("#,##0.0").format(this
                                    .percentage(numbers.evenCount(),
                                            numbers.total()))
                            + "% and odds make up "
                            + new DecimalFormat("#,##0.0").format(this
                                    .percentage(numbers.oddCount(),
                                            numbers.total()))
                            + "%. The ratio of prime numbers to non-prime numbers is "
                            + numbers.primeCount()
                            + ":"
                            + (numbers.total() - numbers.primeCount())
                            + " where prime numbers make up "
                            + new DecimalFormat("#,##0.0").format(this
                                    .percentage(numbers.primeCount(),
                                            numbers.total()))
                            + "% and non-prime numbers make up "
                            + new DecimalFormat("#,##0.0").format(this
                                    .percentage((numbers.total() - numbers
                                            .primeCount()), numbers.total()))
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
}
