import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
public class UserInterface {
    private JButton button;
    private JFrame frame;
    private JLabel title;
    private JPanel panel;
    private JComboBox<String> book;
    public void createUI(){
        String[] books = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy"};
        panel = new JPanel();
        frame = new JFrame("My App");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        title = new JLabel();
        title.setText("BIBLE");
        book = new JComboBox<>(books);
        panel.add(title);
        panel.add(book);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    //public JButton getbutton(){
        //return button;
    //}
}
