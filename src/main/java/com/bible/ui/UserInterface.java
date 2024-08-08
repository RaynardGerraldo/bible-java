package com.bible.ui;

import com.bible.app.Application;
import java.io.InputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleConstants;

public class UserInterface {
    private JButton search;
    private JButton bookmark;
    private JFrame frame;
    private JLabel title;
    private JPanel panel;
    private JPanel widgetpanel;
    private JTextPane display;
    private JScrollPane scroller;
    private JComboBox<String> testamentBox;
    private JComboBox<String> bookBox;
    private JComboBox<Integer> chapterBox;
    private JComboBox<Integer> verseBox;
    private JComboBox<String> bookmarkBox;
    private Font font;
    private Font sizedFont;
    private SimpleAttributeSet bold;
    private StyledDocument doc;
    private boolean updatingBooks = false;

    DefaultComboBoxModel<String> testaments = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> books = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Integer> chapters = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Integer> verses = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> bookmarks = new DefaultComboBoxModel<>();

    Application backend = new Application();

    public void createUI(){
        frame = new JFrame("Bible");
        display = new JTextPane();
        doc = display.getStyledDocument();
        bold = new SimpleAttributeSet();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        widgetpanel = new JPanel();
        widgetpanel.setLayout(new FlowLayout());
        scroller = new JScrollPane(display);
        title = new JLabel();
        testamentBox = new JComboBox<>(testaments);
        bookBox = new JComboBox<>(books);
        chapterBox = new JComboBox<>(chapters);
        verseBox = new JComboBox<>(verses);
        bookmarkBox = new JComboBox<>(bookmarks);
        search = new JButton("Search");
        bookmark = new JButton("Bookmark");
        display.setSize(30,50);
        display.setEditable(false);

        StyleConstants.setBold(bold,true);
        StyleConstants.setBackground(bold, Color.YELLOW);

        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
 
        testaments.addElement("Old Testament");
        testaments.addElement("New Testament");

        widgetpanel.add(testamentBox);
        widgetpanel.add(bookBox);
        widgetpanel.add(chapterBox);
        widgetpanel.add(verseBox);
        widgetpanel.add(search);
        widgetpanel.add(bookmark);

        panel.add(scroller);
        panel.add(widgetpanel);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        
        // Set to Gen 1.1 by default
        updateBooks();
        updateChapters();
        updateVerses();
        searchVerse();

        testamentBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooks();
            }
        });

        bookBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!updatingBooks){
                   updateChapters();
                }
            }
        });

        chapterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                updateVerses();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                searchVerse();
            }
        });

        bookmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                bookmarkInsert();
            }
        });

        // Retrieve bookmark
        //bookmarkBox.addActionListener(new ActionListener() {
            //@Override
            //public void actionPerformed(ActionEvent e){
                //bookmarkGrab();
            //}
        //});
    }

    public void updateBooks(){
        if (testamentBox.getSelectedItem().equals("Old Testament")){
            String[] book_names = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Josue", "Judges", "Ruth", "1 Samuel", "2 Samuel", "3 Kings", "4 Kings", "1 Paralipomenon", "2 Paralipomenon", "1 Esdras", "2 Esdras", "Tobias", "Judith", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes", "Canticle of Canticles", "Wisdom", "Ecclesiasticus", "Isaiah", "Jeremiah", "Lamentations", "Baruch", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Abdias", "Jonas", "Micheas", "Nahum", "Habacuc", "Sophonias", "Aggeus", "Zacharias", "Malachias", "1 Machabees", "2 Machabees"}; 
            updateBooksModel(book_names);
        }
        else if (testamentBox.getSelectedItem().equals("New Testament")){
            String[] book_names = {"St. Matthew", "St. Mark", "St. Luke", "St. John", "The Acts of the Apostles", "St. Paul to the Romans", "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians", "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon", "To the Hebrews", "The Epistle of St. James", "1 St. Peter", "2 St. Peter", "1 St. John", "2 St. John", "3 St. John", "St. Jude", "Apocalypse of St. John the Apostle"};
            updateBooksModel(book_names);
        }
    }

    public void updateBooksModel(String[] book_names){
        updatingBooks = true;
        books.removeAllElements();
        for (String book_name : book_names) {
            books.addElement(book_name);
        }
        updatingBooks = false;
    }

    public void updateChapters(){
        chapters.removeAllElements();
        Object bookItem = bookBox.getSelectedItem();
        if (bookItem != null) {
            String bookName = (String) bookItem;
            int chapters_db = backend.chapterDB(bookName);
            for (int i = 1; i < chapters_db+1; i++){
                chapters.addElement(i);
            }
        }
    }

    public void updateVerses(){
       verses.removeAllElements();
       Object bookItem = bookBox.getSelectedItem();
       Object chapterItem = chapterBox.getSelectedItem();
       if (bookItem != null && chapterItem != null) {
            String bookName = (String) bookItem;
            int chapter_sel = (int) chapterItem;
            int verses_db = backend.verseDB(bookName,chapter_sel);
            for (int i = 1; i < verses_db+1; i++){
                verses.addElement(i);
            }
       }
    }

    public void searchVerse(){
        String book = (String) bookBox.getSelectedItem();
        int chapter = (int) chapterBox.getSelectedItem();
        int verse = (int) verseBox.getSelectedItem();
        String whole_verse = backend.bibleDB(book,chapter);
        String selected_verse = backend.selectedDB(book,chapter,verse,0);
        int selected_index = whole_verse.indexOf(selected_verse);
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/OLDSH.ttf")){
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            sizedFont = font.deriveFont(15f);
        } catch (FontFormatException | IOException e){
            sizedFont = new Font("Arial",Font.PLAIN,15);
        }
        display.setFont(sizedFont);
        display.setText(whole_verse);
        display.setCaretPosition(selected_index + selected_verse.length());
        try {
            doc.setCharacterAttributes(selected_index, selected_verse.length(), bold, false);
        } catch (Exception e) {
            display.setText(whole_verse);
            JOptionPane.showMessageDialog(frame, "Pick your book, chapter and verse and try again");
        }
    }

    public void bookmarkInsert(){
        String markname = JOptionPane.showInputDialog(frame,"Bookmark name?",null);
        String book = (String) bookBox.getSelectedItem();
        int chapter = (int) chapterBox.getSelectedItem();
        int verse = (int) verseBox.getSelectedItem();
        backend.bookmarkInsertDB(markname,book,chapter,verse);
        JOptionPane.showMessageDialog(frame, "Bookmarked!");
    }
}
