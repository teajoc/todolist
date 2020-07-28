package personal;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class ToDo extends JPanel {

    private static ArrayList<ListItem> listItems;

    /*I'm not a fan of using exceptions to handle expected events, as is apparently the case with
    ObjectInputStream. What I usually do when faced with this situation is put all my objects in
    a collection and serialize the collection.
    Of course, with large numbers of objects this method could cause performance problems. - JOE*/

    public ToDo(ArrayList<ListItem> lstItems) {
        super(new BorderLayout());
        ArrayList<ListItem> oldList = new ArrayList<>();
        fillOld(oldList); // if there are existing list items, pulls them from existing list_items file
        lstItems.addAll(oldList);
        Collections.sort(lstItems);
        listItems = lstItems;
        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        try {
            FileOutputStream fileOut = new FileOutputStream("./list_items.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (ListItem listIt : listItems) {
                checkPanel.add(listIt.box);
                out.writeObject(listIt);
            }
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in ./list_items.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        setMinimumSize(new Dimension(200, 400));
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 90));
        add(checkPanel, BorderLayout.LINE_START);

    }

    /**TODO = make collection serializable rather than individual items **/
    private ArrayList<ListItem> fillOld(ArrayList<ListItem> oldList) {
        try {
            FileInputStream fileIn = new FileInputStream("./list_items.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while (true) {
                try {
                    oldList.add((ListItem) in.readObject());
                }
                catch (EOFException e) {
                    in.close();
                    fileIn.close();
                    return oldList;
                }
            }
        } catch (IOException i) {
            //i.printStackTrace();
            return oldList;
        } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
            return oldList;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { runGUI(); } });
    }

    private static void runGUI() {
        JFrame frame = new JFrame("To Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Scanner input = new Scanner(System.in);
        ArrayList<ListItem> inList = new ArrayList<>();

        System.out.print("Do you have items to add to your to-do list? (y/n) ");
        String ans = input.nextLine();
        while (!ans.equals("y") && !ans.equals("n")) {
            System.out.print("Invalid input, please enter y or n: ");
            ans = input.nextLine();
        }
        while (ans.equals("y")) {
            System.out.print("What is your next item? ");
            String nextItem = input.nextLine();
            System.out.println("When is it due?");
            System.out.print("Month #: ");
            int month = input.nextInt();
            if (month > 12 || month < 1) {
                System.out.print("Invalid input, please enter an integer between 1 and 12: ");
                month = input.nextInt();
            }
            input.nextLine();
            System.out.print("Day: ");
            int day = input.nextInt();
            if (day > 31 || day < 1) {
                System.out.print("Invalid input, please enter an integer between 1 and 31: ");
                day = input.nextInt();
            }
            input.nextLine();
            inList.add(new ListItem(nextItem, month, day));
            System.out.print("Do you have more items to add to your to-do list? (y/n) ");
            ans = input.nextLine();
            while (!ans.equals("y") && !ans.equals("n")) {
                System.out.print("Invalid input, please enter y or n: ");
                ans = input.nextLine();
            }
        }
        
        JComponent newContentPane = new ToDo(inList);
        newContentPane.setOpaque(true); 
        frame.setContentPane(newContentPane);
        
        frame.pack();
        frame.setVisible(true);
    }
}
