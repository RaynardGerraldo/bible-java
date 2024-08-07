package com.bible.app;

import com.bible.ui.UserInterface;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Application {
    String url = "jdbc:sqlite:db/bible_DB.db";
    private static final Map<String, String> book_dict = new HashMap<String, String>() {{
        put("Genesis", "GEN");
        put("Exodus", "EXO");
        put("Leviticus", "LEV");
        put("Numbers", "NUM");
        put("Deuteronomy", "DEU");
        put("Josue", "JOS");
        put("Judges", "JDG");
        put("Ruth", "RUT");
        put("1 Samuel", "1SA");
        put("2 Samuel", "2SA");
        put("3 Kings", "1KI");
        put("4 Kings", "2KI");
        put("1 Paralipomenon", "1CH");
        put("2 Paralipomenon", "2CH");
        put("1 Esdras", "EZR");
        put("2 Esdras", "NEH");
        put("Esther", "EST");
        put("Job", "JOB");
        put("Psalms", "PSA");
        put("Proverbs", "PRO");
        put("Ecclesiastes", "ECC");
        put("Canticle of Canticles", "SNG");
        put("Wisdom", "WIS");
        put("Ecclesiasticus", "SIR");
        put("Isaiah", "ISA");
        put("Jeremiah", "JER");
        put("Lamentations", "LAM");
        put("Baruch", "BAR");
        put("Ezekiel", "EZK");
        put("Daniel", "DAN");
        put("Hosea", "HOS");
        put("Joel", "JOL");
        put("Amos", "AMO");
        put("Abdias", "OBA");
        put("Jonas", "JON");
        put("Micheas", "MIC");
        put("Nahum", "NAM");
        put("Habacuc", "HAB");
        put("Sophonias", "ZEP");
        put("Aggeus", "HAG");
        put("Zacharias", "ZEC");
        put("Malachias", "MAL");
        put("Tobias", "TOB");
        put("Judith", "JDT");
        put("Wisdom", "WIS");
        put("Ecclesiasticus", "SIR");
        put("1 Machabees", "1MA");
        put("2 Machabees", "2MA");
        put("St. Matthew", "MAT");
        put("St. Mark", "MRK");
        put("St. Luke", "LUK");
        put("St. John", "JHN");
        put("The Acts of the Apostles", "ACT");
        put("St. Paul to the Romans", "ROM");
        put("1 Corinthians", "1CO");
        put("2 Corinthians", "2CO");
        put("Galatians", "GAL");
        put("Ephesians", "EPH");
        put("Philippians", "PHP");
        put("Colossians", "COL");
        put("1 Thessalonians", "1TH");
        put("2 Thessalonians", "2TH");
        put("1 Timothy", "1TI");
        put("2 Timothy", "2TI");
        put("Titus", "TIT");
        put("Philemon", "PHM");
        put("To the Hebrews", "HEB");
        put("The Epistle of St. James", "JAS");
        put("1 St. Peter", "1PE");
        put("2 St. Peter", "2PE");
        put("1 St. John", "1JN");
        put("2 St. John", "2JN");
        put("3 St. John", "3JN");
        put("St. Jude", "JUD");
        put("Apocalypse of St. John the Apostle", "REV");
    }};;

    public int chapterDB(String book){
        int chapters = 0;
        book = book_dict.get(book);
        String query = "SELECT COUNT(DISTINCT chapter) AS chapters FROM verse WHERE book=?";
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                chapters = rs.getInt("chapters");
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return chapters;
    }


    public int verseDB(String book,int chapters){
        int verses = 0;
        book = book_dict.get(book);
        String query = "SELECT COUNT(chapter) AS verses FROM verse WHERE book=? AND chapter=?";
        try (Connection conn = DriverManager.getConnection(url)){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book);
            pstmt.setInt(2,chapters);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                verses = rs.getInt("verses");
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return verses;
    }

    public String bibleDB(String book, int chapter, int start_verse, Integer end_verse) {
        String text = "";
        book = book_dict.get(book);
        String query = "SELECT text FROM verse WHERE book=? AND chapter=? AND start_verse=?";
        try (Connection conn = DriverManager.getConnection(url)){
           if (end_verse != null && end_verse != 0) {
               query += " AND end_verse=?";
           }

           PreparedStatement pstmt = conn.prepareStatement(query);

           pstmt.setString(1, book);
           pstmt.setInt(2, chapter);
           pstmt.setInt(3, start_verse);

           if (end_verse != null && end_verse != 0) {
               pstmt.setInt(4, end_verse);
           }
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()){
                text = rs.getString("text");
           }
           rs.close();
           pstmt.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return text;
    }
    public void run() {
        UserInterface ui = new UserInterface();
        ui.createUI();
    }
}
