package Midterm;
/*
@author Bowen Kruse
April 24th 2020
Tic Tac Toe Program using Swing
Player square choice done using digit system via input of 'row' and 'column' values
Player input control panel on same frame, as multiple frames would be very user unfriendly
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SwingTicTacToe extends JFrame implements ActionListener {
    SwingTicTacToe() {
        RandomIDAssign();
        setLayout();
        addComponents();
        addEvents();
        createButtons();
    }
    // grandparent container
    Container container = getContentPane();

    // top control pane
    JPanel controlPanel = new JPanel();

    // where the grid of game play will be
    JPanel gameSpace = new JPanel();
    // general use border
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
    // game buttons
    JButton [][] gameButtons = new JButton[4][4];

    // control panel items
    JPanel player1Panel = new JPanel();
    JPanel player2Panel = new JPanel();
    JLabel p1Label = new JLabel();
    JLabel p2Label = new JLabel();
    JLabel p1Row = new JLabel("Row");
    JLabel p1Column = new JLabel("Column");
    JLabel p2Row = new JLabel("Row");
    JLabel p2Column = new JLabel("Column");
    JTextField rowInput = new JTextField(10);
    JTextField rowInput2 = new JTextField(10);
    JTextField columnInput = new JTextField(10);
    JTextField columnInput2 = new JTextField(10);
    JButton confirm = new JButton("Confirm");
    JButton confirm2 = new JButton("Confirm");

    // player ID to be assigned by below function
    public String p1ID;
    public String p2ID;

    // assigns the ID 'X' or 'O' randomly to players
    public void RandomIDAssign() {
        Random random = new Random();
        int tofu = random.nextInt(10);
        if (tofu < 4) {
            p1ID = "X";
            p2ID = "O";
        }
        else {
            p1ID = "O";
            p2ID = "X";
        }
    }


    // sets layouts to respective containers
    public void setLayout() {
        container.setLayout(new BorderLayout());
        gameSpace.setLayout(new GridLayout(4,4,1,1));
        gameSpace.setBackground(Color.lightGray);
        controlPanel.setLayout(new BorderLayout());
        player1Panel.setLayout(new FlowLayout());
        player2Panel.setLayout(new FlowLayout());

    }

    public void createButtons() {
        //function adds components and event listener in one function for simplicity
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameButtons[i][j] = new JButton();
                gameButtons[i][j].putClientProperty("Index",new Integer[]{i,j});
                gameButtons[i][j].putClientProperty("Owner",null);
                gameButtons[i][j].setBorder(blackLine);
                gameSpace.add(gameButtons[i][j]);
            }
        }
    }

    // adds events to respective buttons and text fields
    public void addEvents() {
        confirm.addActionListener(this);
        confirm2.addActionListener(this);
    }


    // adds components to respective parent container
    public void addComponents() {
        container.add(gameSpace,BorderLayout.CENTER);
        container.add(controlPanel,BorderLayout.NORTH);
        controlPanel.add(player1Panel,BorderLayout.NORTH);
        controlPanel.add(player2Panel,BorderLayout.SOUTH);
        player1Panel.setBorder(blackLine);

        p1Label.setText("Player 1: "+p1ID);
        player1Panel.add(p1Label);
        player1Panel.add(p1Row);
        player1Panel.add(rowInput);
        player1Panel.add(p1Column);
        player1Panel.add(columnInput);
        player1Panel.add(confirm);

        player2Panel.setBorder(blackLine);
        p2Label.setText("Player 2: "+p2ID);
        player2Panel.add(p2Label);
        player2Panel.add(p2Row);
        player2Panel.add(rowInput2);
        player2Panel.add(p2Column);
        player2Panel.add(columnInput2);
        player2Panel.add(confirm2);
    }


    // function that resets game conditions
    public void gameReset() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameButtons[i][j].putClientProperty("Index",new Integer[]{i,j});
                gameButtons[i][j].putClientProperty("Owner",null);
                gameButtons[i][j].setBorder(blackLine);
                gameButtons[i][j].setText("");
            }
        }
    }

    // sets owner using client property
    public void setOwner(int x, int y, String owner) {
        gameButtons[x-1][y-1].setText(owner);
        gameButtons[x-1][y-1].putClientProperty("Owner",owner);
    }

    // returns client property 'owner'
    public String getOwner(JButton b) {
        return (String)b.getClientProperty("Owner");
    }

    // function to check if '4 in a row' has been achieved
    public boolean checkVictory(Integer[] index) {
        Integer a = index[0];
        Integer b = index[1];

        //check row
        int i;
        for (i = 0; i < 4; i++) {
            if(getOwner(gameButtons[a][i]) != getOwner(gameButtons[a][b]))
                break;
        }
        if (i == 4)
            return true;
        //check column
        for (i = 0; i < 4; i++) {
            if (getOwner(gameButtons[i][b])!=getOwner(gameButtons[a][b]))
                break;
        }
        if (i ==4)
            return true;


        // diagonal
        if((a==2&&b==2)||(a==0&&b==0)||(a==1&&b==1)||(a==0&&b==2)||(a==2&&b==0))
        {
            //left diagonal
            for(i=0;i < 4; i++)
                if(getOwner(gameButtons[i][i])!=getOwner(gameButtons[a][b]))
            break;
            if(i==4)
                return true;

            //right diagonal
            if((getOwner(gameButtons[0][2])==getOwner(gameButtons[a][b]))&&(getOwner(gameButtons[1][1])==
                    getOwner(gameButtons[a][b]))&&(getOwner(gameButtons[2][0])==getOwner(gameButtons[a][b])))
                return true;

        }
        return false;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        // player 1 confirm button
        if (actionEvent.getSource() == confirm) {
            int row = Integer.parseInt(rowInput.getText());
            int column = Integer.parseInt(columnInput.getText());
            setOwner(row,column,p1ID);

            //reset input text fields to be more user friendly
            rowInput.setText("");
            columnInput.setText("");

            // to be used for checking victory status
            Integer[] index = (Integer[]) gameButtons[row-1][column-1].getClientProperty("Index");

            // check if winner every time button is used
            boolean result = checkVictory(index);
            if(result)
                Victory(p1ID);
        }
        // confirm button for player 2
        else if (actionEvent.getSource() == confirm2) {
            int row = Integer.parseInt(rowInput2.getText());
            int column = Integer.parseInt(columnInput2.getText());
            setOwner(row,column,p2ID);

            //reset pl   ayer 2 text field
            rowInput2.setText("");
            columnInput2.setText("");

            // to be used for checking victory
            Integer[] index = (Integer[]) gameButtons[row-1][column-1].getClientProperty("Index");

            //check if winner every time button is used
            boolean result = checkVictory(index);
            if(result)
                Victory(p2ID);
        }
    }

    // function called when either player 1 or 2 has achieved four in a row
    public void Victory(String ID) {
        String message = ID + " Wins!\nPlay again?";
        int choice = JOptionPane.showConfirmDialog(container,message);

        // resets game if user wishes so or halts program with exit code o
        if (choice == JOptionPane.YES_OPTION) {
            gameReset();
        }
        else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingTicTacToe TicTacToeFrame = new SwingTicTacToe();
        TicTacToeFrame.setTitle("Tic Tac Toe");
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension(500,500);
        // center on the screen
        TicTacToeFrame.setBounds(ss.width/2-frameSize.width/2,
                ss.height/2-frameSize.height/2,frameSize.width,frameSize.height);
        TicTacToeFrame.setVisible(true);
        TicTacToeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
