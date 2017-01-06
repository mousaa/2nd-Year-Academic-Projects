package mousaa_a3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JComboBox;

/**
 *
 * @author AhmedMousa
 */
public class Listeners
{

    private static String choice = "book";

    protected class addScreen implements ActionListener
    {
        //Add press on menubar
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Adding a product page");
            Gui.showCard("Add Panel");
        }
    }

    protected class searchScreen implements ActionListener
    {
        //choosing search from menu
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Search Page");
            Gui.showCard("Search Panel");
        }
    }

    protected class comboChoice implements ActionListener
    {
        //combobox in add screen
        public void actionPerformed(ActionEvent e)
        {
            JComboBox cb = (JComboBox)e.getSource();
            choice = (String) cb.getSelectedItem();
            if (choice == "book")
            {
                System.out.println("Book page");
                Gui.comboChoice("book");
                Gui.setAddText("");

            }
            else if (choice == "electronics")
            {
                System.out.println("Electronics page");
                Gui.comboChoice("electronics");
                Gui.setAddText("");

            }
            else
            {
                System.out.println("Unknown error");
            }

        }
    }

    protected class resetFields implements ActionListener
    {
        //reset all text fields
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Reset All Text Fields");
            Gui.resetFields();
        }
    }

    //writes to file when u choose quit from menu or close from window.
    protected class quitProgram implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Saving Data to file . . .");
            EStoreSearch.updateFile();
            System.out.println("Data successfully saved");
            System.out.println("Program Exiting . . .");
            System.exit(0);
        }
    }

    protected class closeWindow implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e)
        {
        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            System.out.println("Saving Data to file . . .");
            EStoreSearch.updateFile();
            System.out.println("Data successfully saved");
            System.out.println("Program Exiting . . .");
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e)
        {
        }

        @Override
        public void windowIconified(WindowEvent e)
        {
        }

        @Override
        public void windowDeiconified(WindowEvent e)
        {
        }

        @Override
        public void windowActivated(WindowEvent e)
        {
        }

        @Override
        public void windowDeactivated(WindowEvent e)
        {
        }
    }

    //when u press search button
    protected class searchMap implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Search HashMap");
            Gui.getSearchFields();
        }
    }

    //when u press add button 
    protected class addProduct implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Add Button");
            Gui.getAddFields(choice); //choice is either book or electronic.
        } 
    }
}
