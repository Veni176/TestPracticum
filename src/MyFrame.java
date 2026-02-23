import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    JPanel topPanel = new JPanel();
    JPanel midPanel = new JPanel();
    JPanel bottomPanel = new JPanel();

    JLabel fNameL = new JLabel("Име:");
    JLabel lNameL = new JLabel("Фамилия:");
    JLabel genderL = new JLabel("Пол:");
    JLabel ageL = new JLabel("Години:");
    JLabel salaryL = new JLabel("Заплата:");

    JTextField fNameTF = new JTextField();
    JTextField lNameTF = new JTextField();
    JTextField ageTF = new JTextField();
    JTextField salaryTF = new JTextField();

    String[] item = {"Мъж", "Жена"};
    JComboBox<String> genderCombo = new JComboBox<>(item);

    JButton insertBTN = new JButton("Доабвяне");
    JButton updateBTN = new JButton("Редактиране");
    JButton deleteBTN = new JButton("Изтриване");

    JTable table = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    public MyFrame() {
        this.setSize(400,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3,1));

        //topPanel
        topPanel.setLayout(new GridLayout(5,2));
        topPanel.add(fNameL);
        topPanel.add(fNameTF);
        topPanel.add(lNameL);
        topPanel.add(lNameTF);
        topPanel.add(genderL);
        topPanel.add(genderCombo);
        topPanel.add(ageL);
        topPanel.add(ageTF);
        topPanel.add(salaryL);
        topPanel.add(salaryTF);
        this.add(topPanel);

        //midPanel
        midPanel.add(insertBTN);
        midPanel.add(updateBTN);
        midPanel.add(deleteBTN);
        this.add(midPanel);

        insertBTN.addActionListener(new AddAction());

        //bottomPanel
        scroll.setPreferredSize(new Dimension(350,150));
        bottomPanel.add(scroll);
        this.add(bottomPanel);

        this.setVisible(true);
    }

    class AddAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(fNameTF.getText() + " " + lNameTF.getText() + " " +
                    genderCombo.getSelectedItem().toString() + " " + ageTF.getText() + " " +
                    salaryTF.getText());
        }
    }
}
