import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyFrame extends JFrame {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet result = null;
    int id = -1;

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
    JComboBox<String> personCombo = new JComboBox<String>();

    JButton insertBTN = new JButton("Доабвяне");
    JButton updateBTN = new JButton("Редактиране");
    JButton deleteBTN = new JButton("Изтриване");
    JButton searchBTN = new JButton("Търсене");
    JButton refreshBTN = new JButton("Обновяване");

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
        midPanel.add(searchBTN);
        midPanel.add(refreshBTN);
        midPanel.add(personCombo);
        this.add(midPanel);

        insertBTN.addActionListener(new AddAction());
        deleteBTN.addActionListener(new DeleteAction());
        updateBTN.addActionListener(new UpdateAction());
        searchBTN.addActionListener(new SearchByAgeAction());
        refreshBTN.addActionListener(new RefreshAction());

        //bottomPanel
        scroll.setPreferredSize(new Dimension(350,150));
        bottomPanel.add(scroll);
        this.add(bottomPanel);

        table.addMouseListener(new MouseAction());
        refreshTable();
        refreshComboPerson();
        this.setVisible(true);
    }

    public void refreshTable() {
        connection = DBConnection.getConnection();
        try {
            statement = connection.prepareStatement("select * from Person");
            result = statement.executeQuery();
            table.setModel(new MyModel(result));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void refreshComboPerson() {
        personCombo.removeAllItems();
        connection = DBConnection.getConnection();
        String sql = "select id,fname,lname from person";
        String item = "";
        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                item = result.getObject(1).toString() + ". " + result.getObject(2).toString() + " " + result.getObject(3).toString();
                personCombo.addItem(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearForm() {
        fNameTF.setText("");
        lNameTF.setText("");
        ageTF.setText("");
        salaryTF.setText("");
    }

    class AddAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            connection = DBConnection.getConnection();
            String sql = "insert into person (fname, lname, gender, age, salary) " +
                    "values(?, ?, ?, ?, ?)";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, fNameTF.getText());
                statement.setString(2, lNameTF.getText());
                statement.setString(3, genderCombo.getSelectedItem().toString());
                statement.setInt(4, Integer.parseInt(ageTF.getText()));
                statement.setFloat(5, Float.parseFloat(salaryTF.getText()));

                statement.execute();
                refreshTable();
                refreshComboPerson();
                clearForm();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            int row = table.getSelectedRow();
            id = Integer.parseInt(table.getValueAt(row, 0).toString());
            fNameTF.setText(table.getValueAt(row, 1).toString());
            lNameTF.setText(table.getValueAt(row, 2).toString());
            ageTF.setText(table.getValueAt(row, 4).toString());
            salaryTF.setText(table.getValueAt(row, 5).toString());

            if (table.getValueAt(row, 3).toString().equals("Мъж")) {
                genderCombo.setSelectedIndex(0);
            } else {
                genderCombo.setSelectedIndex(1);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class UpdateAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO
        }
    }

    class DeleteAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            connection = DBConnection.getConnection();
            String sql = "delete from person where id=?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                statement.execute();
                refreshTable();
                refreshComboPerson();
                clearForm();
                id = -1;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class SearchByAgeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            connection = DBConnection.getConnection();
            String sql = "select * from person where age=?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(ageTF.getText()));
                result = statement.executeQuery();
                table.setModel(new MyModel(result));
                clearForm();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class RefreshAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            refreshTable();
        }
    }
}
