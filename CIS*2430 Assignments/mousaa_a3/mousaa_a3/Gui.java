package mousaa_a3;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author AhmedMousa
 */
public class Gui extends JFrame
{     
    //for functions later on.
    static EStoreSearch store = new EStoreSearch();
    
    //for all event handling
    Listeners listener = new Listeners();

    //main window
    private static JFrame mainWindow;
    private static JPanel mainPanel;

    //welcome message components
    private static JPanel wlcmPanel;
    private static JTextArea wlcmText;

    //menu components
    private static JMenu commandMenu;
    private static JMenuBar menuBar;
    private static JMenuItem addItem, searchItem, quitItem;

    //card panels components
    private static JPanel cards;

    //for add interface inside cardLayout
    String[] typeChoice =
    {
        "book", "electronics"
    };
    private static JComboBox<?> boxChoice;
    private static JPanel addPanel, buttonPanel, addParent;
    private static JLabel addHeader, typeLabel, idLabel, nameLabel, priceLabel, yearLabel, authorsLabel, publisherLabel, makerLabel;
    private static JTextField idField, nameField, priceField, yearField, authorsField, publisherField, makerField;
    private static JButton addButton, resetButton;
    private static JTextArea outText;
    private static JPanel addTextPanel;
    private static TitledBorder textBorder;
    private static JScrollPane scrollText;

    //for search interface inside cardLayout
    private static JPanel searchParent, searchPanel, searchButtonPanel, searchResultPanel;
    private static JLabel searchHeader, idSearchLabel, keySearchLabel, startYearLabel, endYearLabel;
    private static JTextField idSearchField, keySearchField, startYearSearchField, endYearSearchField;
    private static JButton searchButton, resetSearchButton;
    private static JTextArea searchResultText;
    private static TitledBorder searchTextBorder;
    private static JScrollPane seachScrollText;

    public Gui()
    {
        super();

        //functions to create the panels.
        menuBar();
        welcomeMsg();
        cardScreen();
        mainWindow();

    }

    private void menuBar()
    {
        commandMenu = new JMenu("Commands");
        menuBar = new JMenuBar();
        menuBar.add(commandMenu);
        menuBar.setForeground(Color.DARK_GRAY);

        //Menu items
        addItem = new JMenuItem("Add");
        searchItem = new JMenuItem("Search");
        quitItem = new JMenuItem("Quit");

        //adds to menu
        commandMenu.add(addItem);
        commandMenu.add(searchItem);
        commandMenu.add(quitItem);

        addItem.addActionListener(listener.new addScreen());
        searchItem.addActionListener(listener.new searchScreen());
        quitItem.addActionListener(listener.new quitProgram());
    }

    private void welcomeMsg()
    {
        //creates a panel and adds a textArea to it.
        wlcmPanel = new JPanel();
        wlcmText = new JTextArea(40, 55);
        wlcmText.setEditable(false);
        wlcmText.setText("\n\n  Welcome to eStore Search.\n\n");
        wlcmText.append("  Choose a command from the \"Commands\" menu above for\n"
                + "  adding a product, searching products, or quittng the program");

        wlcmText.setForeground(Color.GRAY);
        wlcmPanel.add(wlcmText);

    }

    private void cardScreen()
    {
        addParent = new JPanel();
        searchParent = new JPanel();

        searchParent.setLayout(new BorderLayout());
        addParent.setLayout(new BorderLayout());

        cards = new JPanel();
        cards.setLayout(new CardLayout());

        //add interface
        createAddPanel();
        addButtonPanel();
        addTextPanel();
        addParent.add(addPanel, BorderLayout.WEST);
        addParent.add(buttonPanel, BorderLayout.CENTER);
        addParent.add(addTextPanel, BorderLayout.SOUTH);

        //search interface
        createSearchPanel();
        createSearchButtons();
        createSearchTextPanel();
        searchParent.add(searchPanel, BorderLayout.WEST);
        searchParent.add(searchButtonPanel, BorderLayout.CENTER);
        searchParent.add(searchResultPanel, BorderLayout.SOUTH);

        cards.add(wlcmPanel, "welcome");
        cards.add(addParent, "Add Panel");
        cards.add(searchParent, "Search Panel");
    }
    
    //text area when search card is shown
    private void createSearchTextPanel()
    {
        searchResultPanel = new JPanel();
        searchResultText = new JTextArea(25, 50);
        searchResultText.setLineWrap(true);
        searchResultText.setEditable(false);
        searchResultText.setWrapStyleWord(true);
        searchTextBorder = new TitledBorder("Search Results");
        seachScrollText = new JScrollPane(searchResultText); //scroll bars
        seachScrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        seachScrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        searchResultPanel.add(seachScrollText);
        searchResultPanel.setBorder(searchTextBorder);
    }
    
    //buttons in search panel
    private void createSearchButtons()
    {
        searchButtonPanel = new JPanel();
        searchButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        resetSearchButton = new JButton("Reset");
        resetSearchButton.addActionListener(listener.new resetFields());
        searchButton = new JButton("Search");
        searchButton.addActionListener(listener.new searchMap());

        g.gridx = 0;
        g.gridy = 0;
        g.ipady = 4;
        g.weighty = 1;
        searchButtonPanel.add(resetSearchButton, g);

        g.gridx = 0;
        g.gridy = 2;
        searchButtonPanel.add(searchButton, g);
    }
    
    
    //creates serach panel using gridbag layout 
    private void createSearchPanel()
    {
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        searchHeader = new JLabel("\tSearching products");
        searchHeader.setForeground(Color.BLUE);

        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.NORTHWEST;
        g.weighty = 1;
        g.gridx = 0;
        g.gridy = 0;
        searchPanel.add(searchHeader, g);

        //id
        idSearchLabel = new JLabel(" ProductID:");
        g.gridx = 0;
        g.gridy = 1;
        searchPanel.add(idSearchLabel, g);

        idSearchField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 1;
        searchPanel.add(idSearchField, g);

        //keywords
        keySearchLabel = new JLabel(" Key:");
        g.gridx = 0;
        g.gridy = 2;
        searchPanel.add(keySearchLabel, g);

        keySearchField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 2;
        searchPanel.add(keySearchField, g);

        //start year
        startYearLabel = new JLabel(" Start Year");
        g.gridx = 0;
        g.gridy = 3;
        searchPanel.add(startYearLabel, g);

        startYearSearchField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 3;
        searchPanel.add(startYearSearchField, g);

        //end year
        endYearLabel = new JLabel(" End Year");
        g.gridx = 0;
        g.gridy = 4;
        searchPanel.add(endYearLabel, g);

        endYearSearchField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 4;
        searchPanel.add(endYearSearchField, g);
    }
    
    
    //creates add panel
    private void createAddPanel()
    {
        addPanel = new JPanel();
        addPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        addHeader = new JLabel("\tAdding a product");
        addHeader.setForeground(Color.BLUE);

        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.NORTHWEST;
        g.weighty = 1;
        g.gridx = 0;
        g.gridy = 0;
        addPanel.add(addHeader, g);

        //type
        typeLabel = new JLabel(" Type:");
        g.gridx = 0;
        g.gridy = 1;
        addPanel.add(typeLabel, g);

        //combobox
        boxChoice = new JComboBox<>(typeChoice);
        boxChoice.setSelectedIndex(0);
        boxChoice.addActionListener(listener.new comboChoice());
        g.gridx = 1;
        g.gridy = 1;
        addPanel.add(boxChoice, g);

        //ID
        idLabel = new JLabel(" ProductID:");
        g.gridx = 0;
        g.gridy = 2;
        addPanel.add(idLabel, g);

        //idfield
        idField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 2;
        addPanel.add(idField, g);

        //name 
        nameLabel = new JLabel(" Name:");
        g.gridx = 0;
        g.gridy = 3;
        addPanel.add(nameLabel, g);

        nameField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 3;
        addPanel.add(nameField, g);

        //price
        priceLabel = new JLabel(" Price:");
        g.gridx = 0;
        g.gridy = 4;
        addPanel.add(priceLabel, g);

        priceField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 4;
        addPanel.add(priceField, g);

        //year
        yearLabel = new JLabel(" Year:");
        g.gridx = 0;
        g.gridy = 5;
        addPanel.add(yearLabel, g);

        yearField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 5;
        addPanel.add(yearField, g);

        //authors
        authorsLabel = new JLabel(" Authors:");
        g.gridx = 0;
        g.gridy = 6;
        addPanel.add(authorsLabel, g);

        authorsField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 6;
        addPanel.add(authorsField, g);

        //publisher
        publisherLabel = new JLabel(" Publisher:");
        g.gridx = 0;
        g.gridy = 7;
        addPanel.add(publisherLabel, g);

        publisherField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 7;
        addPanel.add(publisherField, g);

        //maker. initially invisible.
        makerLabel = new JLabel(" Maker:");
        g.gridx = 0;
        g.gridy = 8;
        makerLabel.setVisible(false);
        addPanel.add(makerLabel, g);

        makerField = new JTextField(20);
        g.gridx = 1;
        g.gridy = 8;
        makerField.setVisible(false);
        addPanel.add(makerField, g);

    }
    
    //buttons in add panel
    private void addButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        resetButton = new JButton("Reset");
        resetButton.addActionListener(listener.new resetFields());
        addButton = new JButton("Add");
        addButton.addActionListener(listener.new addProduct());

        g.gridx = 0;
        g.gridy = 0;
        g.ipady = 4;
        g.weighty = 1;
        buttonPanel.add(resetButton, g);

        g.gridx = 0;
        g.gridy = 2;
        buttonPanel.add(addButton, g);

    }

    /*
     *  Output screen that displays 
     *  the information to the user.
     */
    private void addTextPanel()
    {
        addTextPanel = new JPanel();
        outText = new JTextArea(25, 50);
        outText.setLineWrap(true);
        outText.setEditable(false);
        outText.setWrapStyleWord(true);
        textBorder = new TitledBorder("Messages");
        scrollText = new JScrollPane(outText); //scroll bars
        scrollText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addTextPanel.add(scrollText);
        addTextPanel.setBorder(textBorder);
    }
    
    //main JFrame not resizable to avoid awkward component sizes
    private void mainWindow()
    {
        mainWindow = new JFrame("eStore Search");
        mainWindow.setSize(500, 680);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(cards, BorderLayout.CENTER);
        showCard("welcome");

        mainWindow.add(mainPanel);
        mainWindow.setJMenuBar(menuBar);
        mainWindow.setVisible(true);
        mainWindow.addWindowListener(listener.new closeWindow());
        mainWindow.pack();

    }

    
    // functions to be accessed through other classes/listeners
    
    protected static void setAddText(String textToSet)
    {
        outText.setText(textToSet);
    }
    

    protected static void appendToAddText(String textToAppend)
    {
        outText.append(textToAppend);
    }
    
    //deletes previous text in  text area
    protected static void setSearchText(String textToSet)
    {
        searchResultText.setText(textToSet);
    }

     //appends to whatever is on search panel
    protected static void appendToSearchText(String textToAppend)
    {
        searchResultText.append(textToAppend);
    }

    protected static void showCard(String key)
    {
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, key);
    }

    protected static void comboChoice(String key)
    {
        if (key == "book")
        {

            authorsLabel.setVisible(true);
            authorsField.setVisible(true);
            publisherField.setVisible(true);
            publisherLabel.setVisible(true);
            makerLabel.setVisible(false);
            makerField.setVisible(false);
        }
        else
        {
            authorsLabel.setVisible(false);
            authorsField.setVisible(false);
            publisherLabel.setVisible(false);
            publisherField.setVisible(false);
            makerLabel.setVisible(true);
            makerField.setVisible(true);
        }
    }

    protected static void resetFields()
    {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        yearField.setText("");
        authorsField.setText("");
        publisherField.setText("");
        makerField.setText("");
        idSearchField.setText("");
        keySearchField.setText("");
        startYearSearchField.setText("");
        endYearSearchField.setText("");
    }

    protected static void getSearchFields()
    {
        store.searchProduct(idSearchField.getText(), keySearchField.getText(), startYearSearchField.getText(), endYearSearchField.getText());
    }
    
    
    protected static void getAddFields(String book_or_elec)
    {
        
        //checkProduct in EStoreSearch will make sure input is correct then will add it using function from EStore
        if("book".equals(book_or_elec))
        {
            store.addProduct(idField.getText(),  nameField.getText(),  priceField.getText(),  yearField.getText(),  authorsField.getText(), publisherField.getText(), makerField.getText(), 1);
        }
        else
        {
            store.addProduct(idField.getText(),  nameField.getText(),  priceField.getText(),  yearField.getText(),  authorsField.getText(), publisherField.getText(), makerField.getText(), 2);
        }
    }
}
