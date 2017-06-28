package app.ui;

import app.entity.Round;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dion
 */
public class TableActiveMatches extends JTable {

    public static TableActiveMatches self;

    public TableActiveMatches() {
        self = this;
    }

    public static void update(Collection<Round> rounds) {

        DefaultTableModel model = (DefaultTableModel) self.getModel();
        int selected = self.getSelectedRow();
        model.setRowCount(0);

        for (Round round : rounds) {
            String[] strings = new String[3];
            strings[0] = "R" + round.getId() + ":" + round.getMap().getWidth() + "x" + round.getMap().getHeight();
            strings[1] = round.getTurn() + "";
            strings[2] = round.getPlayerIds().stream()
                    .map(player -> {
                        return player+"";
                    })
                    .collect(Collectors.joining("-", "[", "]"));
            model.addRow(strings);
        }

        if (selected >= model.getRowCount()) {
            selected = model.getRowCount() - 1;
        }

        if (selected != -1) {
            self.setRowSelectionInterval(selected, selected);
        }
    }

}
