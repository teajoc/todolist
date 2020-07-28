package personal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;

public class ListItem extends JComponent implements ItemListener, Comparable<ListItem>, Serializable {
    JCheckBox box;
    protected DueDate deadline;

    public ListItem(String myItem, int month, int day) {
        deadline = new DueDate(month, day);
        box = new JCheckBox(myItem + ", due " + deadline.toString());
        box.setSelected(false);
        box.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public int compareTo(ListItem other) {
        return deadline.compareTo(other.deadline);
    }
}
