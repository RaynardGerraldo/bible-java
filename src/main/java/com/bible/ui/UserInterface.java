package com.bible.ui;

import com.bible.app.Application;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class UserInterface {
    private JButton search;
    private JFrame frame;
    private JLabel title;
    private JPanel panel;
    private JComboBox<String> testamentBox;
    private JComboBox<String> bookBox;
    private JComboBox<Integer> chapterBox;
    private JComboBox<Integer> startverseBox;
    private GridBagConstraints gbc;
    private GridBagLayout layout;
    private boolean updatingBooks = false;
    private String book;
    private int chapter;
    private int verse;
    DefaultComboBoxModel<String> testaments = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> books = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Integer> chapters = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Integer> verses = new DefaultComboBoxModel<>();

    Application backend = new Application();
    public void createUI(){
        Template bible = new Template();
        frame = new JFrame("My App");
        panel = new JPanel();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        title = new JLabel();
        testamentBox = new JComboBox<>(testaments);
        testaments.addElement("Old Testament");
        testaments.addElement("New Testament");
        bookBox = new JComboBox<>(books);
        chapterBox = new JComboBox<>(chapters);
        startverseBox = new JComboBox<>(verses);
        search = new JButton("Search");
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(layout);
        title.setText("BIBLE");
        bible.add_obj(title,panel,layout,gbc,200,120);
        bible.add_obj(testamentBox,panel,layout,gbc,160,150);
        bible.add_obj(bookBox,panel,layout,gbc,180,150);
        bible.add_obj(chapterBox,panel,layout,gbc,220,150);
        bible.add_obj(startverseBox,panel,layout,gbc,240,150);
        bible.add_obj(search,panel,layout,gbc,200,220);

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

        frame.add(panel);
        frame.setVisible(true);
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
        book = (String) bookBox.getSelectedItem();
        chapter = (int) chapterBox.getSelectedItem();
        verse = (int) startverseBox.getSelectedItem();
        backend.bibleDB(book,chapter,verse,0);
    }
}
