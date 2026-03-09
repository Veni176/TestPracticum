import javax.swing.*;

public class NewFrame extends JFrame {

    JTabbedPane tab = new JTabbedPane();
    JPanel personPanel = new JPanel();
    JPanel carPanel = new JPanel();
    JPanel rentPanel = new JPanel();
    JPanel referencePanel = new JPanel();

    public NewFrame() {
        this.setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tab.add(personPanel,"Клиенти");
        tab.add(carPanel,"Коли");
        tab.add(rentPanel,"Наем");
        tab.add(referencePanel,"Справка по ID и име");

        this.add(tab);
        this.setVisible(true);
    }
}
