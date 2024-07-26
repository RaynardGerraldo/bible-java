import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
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
    private JComboBox<String> testament;
    private JComboBox<String> book;
    private JComboBox<String> chapter;
    private JComboBox<String> verse;
    private GridBagConstraints gbc;
    private GridBagLayout layout;
    DefaultComboBoxModel<String> testaments = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> books = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> chapters = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> verses = new DefaultComboBoxModel<>();
    public void createUI(){
        Template bible = new Template();
        frame = new JFrame("My App");
        panel = new JPanel();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        title = new JLabel();
        testament = new JComboBox<>(testaments);
        testaments.addElement("Old Testament");
        testaments.addElement("New Testament");
        book = new JComboBox<>(books);
        chapter = new JComboBox<>(chapters);
        verse = new JComboBox<>(verses);
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(layout);
        title.setText("BIBLE");
        bible.add_obj(title,panel,layout,gbc,200,120);
        bible.add_obj(testament,panel,layout,gbc,160,150);
        bible.add_obj(book,panel,layout,gbc,180,150);
        bible.add_obj(chapter,panel,layout,gbc,220,150);
        bible.add_obj(verse,panel,layout,gbc,240,150);

        testament.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooks();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    
    public void updateBooks(){
        if (testament.getSelectedItem().equals("Old Testament")){
            String[] book_names = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Josue", "Judges", "Ruth", "1 Kings (First Samuel)", "2 Kings (Second Samuel)", "3 Kings", "4 Kings", "1 Paralipomenon", "2 Paralipomenon", "1 Esdras", "2 Esdras, alias Nehemias", "Tobias", "Judith", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Canticle of Canticles", "Wisdom", "Ecclesiasticus", "iSAIAS (Isaiah)", "Jeremias (Jeremiah)", "Lamentations", "Baruch", "Ezechiel (Ezekiel)", "Daniel", "Osee (Hosea)", "Joel", "Amos", "Abdias", "Jonas", "Micheas", "Nahum", "Habacuc", "Sophonias", "Aggeus", "Zacharias", "Malachias", "1 Machabees", "2 Machabees"}; 
            updateBooksModel(book_names);
        }
        else if (testament.getSelectedItem().equals("New Testament")){
            String[] book_names = {"St. Matthew", "St. Mark", "St. Luke", "St. John", "The Acts of the Apostles", "St. Paul to the Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "To the Hebrews", "The Epistle of St. James", "1 St. Peter", "2 St. Peter", "1 St. John", "2 St. John", "3 St. John", "St. Jude", "Apocalypse of St. John the Apostle"};
            updateBooksModel(book_names);
        }
    }

    public void updateBooksModel(String[] book_names){
         books.removeAllElements();
         for (String book_name : book_names) {
             books.addElement(book_name);
         }
    }
}
