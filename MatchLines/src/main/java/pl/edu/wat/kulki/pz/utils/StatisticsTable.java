package pl.edu.wat.kulki.pz.utils;

import pl.edu.wat.kulki.pz.utils.database.GameResult;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class StatisticsTable {
    private static String[][] rowData;
    private static String[] columnNames = new String[2];

    public StatisticsTable() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        columnNames[0] = ResourceLoader.localeProperty("statisticsPlayer", Locale.getDefault().getLanguage());
        columnNames[1] = ResourceLoader.localeProperty("statisticsScore", Locale.getDefault().getLanguage());
        new GameResult().getScoreList(list -> {
            rowData = list.toArray();
            JTable table = new JTable(rowData, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setSize(Integer.parseInt(ResourceLoader.property("statWindow.width")), Integer.parseInt(ResourceLoader.property("statWindow.height")));
            frame.setVisible(true);
        });
    }
}
