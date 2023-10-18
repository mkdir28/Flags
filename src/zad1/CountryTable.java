package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class CountryTable {
    private JTable ctab;

    public CountryTable(String countriesFileName) {
        DefaultTableModel model = new DefaultTableModel(new Object[]
                {"Name", "Country", "Population", "Flag"}, 0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 3)
                    return ImageIcon.class;
                return Object.class;
            }
        };
        try (BufferedReader file = new BufferedReader(new FileReader
                (countriesFileName))) {
            String path;
            while ((path = file.readLine()) != null) {
                String[] row = path.split(" {4}");
                model.addRow(row);
                model.setValueAt(new ImageIcon("data/png100px/" +row[0]+ ".png"), model.getRowCount()-1,3);
                System.out.println(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctab = new JTable(model);
        ctab.setAutoCreateRowSorter(true);
        ctab.setPreferredScrollableViewportSize(
                new Dimension(500, 100));
        ctab.setFillsViewportHeight(true);

        DefaultTableCellRenderer Population = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                if (column == 2) {
                    int population = Integer.parseInt(value.toString());
                    if (population > 20000) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(Color.BLACK);
                    }
                }
                return super.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
            }
        };
        ctab.getColumnModel().getColumn(2).setCellRenderer(Population);
    }

    public JTable create() {
        return ctab;
    }
}