import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class WebSearch {
    JTextArea resultsArea = new JTextArea();
    TextField URLField = new TextField();
    TextField KeyWordField = new TextField();


    public WebSearch(){
        prepareGUI();
    }

    public static void main(String[] args) {
        WebSearch swingControlDemo = new WebSearch();
    }

    public void prepareGUI(){
        //mainframe
        JFrame mainframe = new JFrame("Web Search");
        mainframe.setBounds(0,0,500,800);
        mainframe.setLayout(new BorderLayout());
        JLabel topControlLabel = new JLabel(" ");
        mainframe.add(topControlLabel, BorderLayout.NORTH);
        JLabel leftControlLabel = new JLabel("       ");
        mainframe.add(leftControlLabel, BorderLayout.WEST);
        JLabel rightControlLabel = new JLabel("       ");
        mainframe.add(rightControlLabel, BorderLayout.EAST);
        JLabel bottomControlLabel = new JLabel(" ");
        mainframe.add(bottomControlLabel, BorderLayout.SOUTH);

        //top half of mainframe
        JPanel secondControlPanel = new JPanel();
        secondControlPanel.setLayout(new BorderLayout());

        JPanel upperControlPanel = new JPanel();
        upperControlPanel.setLayout(new GridLayout(3,1));
        secondControlPanel.add(upperControlPanel, BorderLayout.NORTH);

        //url panel
        JPanel URLControlPanel = new JPanel();
        URLControlPanel.setLayout(new GridLayout(2,1));
        JLabel URLLabel=new JLabel("Paste URL Here");
        URLControlPanel.add(URLLabel);
        URLControlPanel.add(URLField);
        upperControlPanel.add(URLControlPanel);

        //search panel
        JPanel keywordControlPanel = new JPanel();
        keywordControlPanel.setLayout(new GridLayout(2,1));
        JLabel KeyWordLabel=new JLabel("Enter Keyword Here");
        URLControlPanel.add(KeyWordLabel);
        URLControlPanel.add(KeyWordField);
        upperControlPanel.add(keywordControlPanel);

         //search button + the panel holding it
        JPanel searchButtonPanel = new JPanel();
        searchButtonPanel.setLayout(new BorderLayout());
        JLabel searchPanelTop = new JLabel(" ");
        JLabel searchPanelBottom = new JLabel(" ");
        JLabel searchPanelLeft = new JLabel("       ");
        JLabel searchPanelRight = new JLabel("       ");

        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(new ButtonClickListener());

        searchButtonPanel.add(searchPanelTop, BorderLayout.NORTH);
        searchButtonPanel.add(searchPanelBottom, BorderLayout.SOUTH);
        searchButtonPanel.add(searchPanelLeft, BorderLayout.WEST);
        searchButtonPanel.add(searchPanelRight, BorderLayout.EAST);
        searchButtonPanel.add(searchButton, BorderLayout.CENTER);
        upperControlPanel.add(searchButtonPanel);

        //results area
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        resultsArea.setEditable(false);

        //scroll bar
        JScrollPane scrollPanel = new JScrollPane(resultsArea);
        resultsPanel.add(scrollPanel, BorderLayout.CENTER);
        secondControlPanel.add(resultsPanel);
        mainframe.add(secondControlPanel, BorderLayout.CENTER);

        mainframe.setVisible(true);
    }

    public void LinkSearcher(){
        try{
            System.out.println();
            String URLString = URLField.getText();
            URL url = new URL(URLString);
            String KeyWordString = KeyWordField.getText();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;

            //pulls out the links from the website code and prints the link if the link has the keyword
            while ((line = reader.readLine()) != null) {
                if (line.contains("https")){
                    int start1 = line.indexOf("https:");
                    int end1 = line.indexOf("\"", start1);
                    int end2 = line.indexOf("\'", start1);
                    if(end1==-1){
                        System.out.println(line.substring(start1,end2));
                        //checking for keyword
                       if(line.substring(start1,end2).contains(KeyWordString)) {
                           resultsArea.append(line.substring(start1, end2) + "\n");
                       }

                    } else {
                        System.out.println(line.substring(start1, end1));
                        //checking for keyword
                        if(line.substring(start1,end1).contains(KeyWordString)) {
                            resultsArea.append(line.substring(start1, end1) + "\n");
                        }
                    }
                }
            }
            reader.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Search")) {
                resultsArea.selectAll();
                resultsArea.replaceSelection("");
                LinkSearcher();
            }
        }
    }
}