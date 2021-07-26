import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class HangmanGame extends JFrame{

    private final int WIDTH;
    private final int HEIGHT;
    private final int MAX_INCORRECT;
    private final int MAX_PLAYER_LENGTH;
    private final int MAX_PASSWORD_LENGTH;
    private final String HANGMAN_IMAGE_DIRECTORY;
    private final String HANGMAN_IMAGE_TYPE;
    private final String HANGMAN_IMAGE_BASE_NAME;
    private final String LETTER_IMAGE_DIRECTORY;
    private final String LETTER_IMAGE_TYPE;
    private LetterRack gameRack;
    private Hangman gameHangman;
    private int numIncorrect;
    private JLabel correct;
    private JLabel incorrect;
    private JLabel player;// player name
    private String name;
    private final String[] words = { "reddit", "facebook", "java", "assignment",
            "game", "helloWorld", "weather", "religion", "internet", "face"};
    private final String[] animals = {"squirrel", "dog", "monkey", "panda", "rat",
            "kangaroo", "horse", "elephant", "fox", "cow"};
    public String password;
    private StringBuilder passwordHidden;



// displays Hangman Game
    public HangmanGame(){
        WIDTH = 500;
        HEIGHT = 500;
        MAX_INCORRECT = 6;
        MAX_PLAYER_LENGTH = 10;
        MAX_PASSWORD_LENGTH = 10;


        HANGMAN_IMAGE_DIRECTORY = LETTER_IMAGE_DIRECTORY = "images/";
        HANGMAN_IMAGE_TYPE = LETTER_IMAGE_TYPE = ".png";
        HANGMAN_IMAGE_BASE_NAME = "hangman";

        setTitle("Hangman");
        setSize(WIDTH,HEIGHT);
        setResizable(true);

        initialize();
    }


    //refreshes game



    private void initialize() {
        numIncorrect = 0;
        correct = new JLabel("Word: ");
        incorrect = new JLabel("Incorrect: " + numIncorrect);
        name = "";
        player = new JLabel("PlayerName: " + name);
        password = "";
        passwordHidden = new StringBuilder();


        getPassword();
        addTextPanel();
        addLetterRack();
        addHangman();
        getPlayer();



        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2 - 200);
        setVisible(true);

    }

// displays the correct,player,and incorrect

    private void addTextPanel(){
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new GridLayout(1,2));
            textPanel.add(correct);
            textPanel.add(incorrect);
            textPanel.add(player);



            add(textPanel, BorderLayout.NORTH);

        }

        // adds LetterRack and attaches the lettertiles to tilelistenr
    private void addLetterRack(){

        gameRack = new LetterRack(password , LETTER_IMAGE_DIRECTORY, LETTER_IMAGE_TYPE);
        gameRack.attachListener(new ClickListener());
        add(gameRack, BorderLayout.SOUTH);
    }

    private void addHangman(){
        JPanel hangmanPanel = new JPanel();
        gameHangman = new Hangman(HANGMAN_IMAGE_BASE_NAME, HANGMAN_IMAGE_DIRECTORY,HANGMAN_IMAGE_TYPE);

        hangmanPanel.add(gameHangman);
        add(hangmanPanel, BorderLayout.CENTER);
    }

    private static <T> T getRandomElement(T[] arr){
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];

    }

    //selects game mode based off password
    private void getPassword(){
        String[] options = {"Select Game Mode: ", "YOU HAVE TO PICK ONE"};
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Enter Game Mode to Play: ");
        JTextField passwordText = new JTextField(MAX_PASSWORD_LENGTH);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordText);
        int confirm = -1;

        while(password.isEmpty()){
            confirm = JOptionPane.showOptionDialog(null, passwordPanel, "Enter Game Mode:",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                    options, options[0]
            );
            if(confirm == 0 ){
                password = passwordText.getText();
                String randStr;
                if(password.contains("animals")){
                    randStr = getRandomElement(animals);
                    password = randStr;
                }
                else if(password.contains("words")){
                    randStr = getRandomElement(words);
                    password = randStr;
                }
                else if(!password.contains("animals") && !password.contains("words") || password.length() > MAX_PASSWORD_LENGTH){
                    JOptionPane.showMessageDialog(null,
                            "Game Mode must be either animals or words", "Invalid Game Mode",JOptionPane.ERROR_MESSAGE);
                    password = "";
                }
            else if(confirm == 1 ){
                System.exit(0);
                }

                passwordHidden.append(password.replaceAll(".", "*"));
                correct.setText(correct.getText() + passwordHidden.toString());
            }


        };

    }

    //player name
    private void getPlayer(){
        String[] options = {"Add player name","Quit"};
        JPanel playerPanel = new JPanel();
        JLabel playerLabel = new JLabel("Enter Player Name: ");
        JTextField playerText = new JTextField(MAX_PLAYER_LENGTH);
        playerPanel.add(playerLabel);
        playerPanel.add(playerText);
        int id = -1;

        while(name.isEmpty()) {
            id = JOptionPane.showOptionDialog(null, playerPanel, "Enter Name: ",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, options,
                    options[0]);

            if (id == 0) {
                name = playerText.getText();

                if (!name.matches("[a-zA-z]+") || name.length() > MAX_PLAYER_LENGTH) {
                    JOptionPane.showMessageDialog(null,
                            "Player name must be less than 10 characters and " +
                                    "only contain letters A-Z", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                    name = "";
                }
            }
            else if (id == 1)
                System.exit(0);
        }
        player.setText("PlayerName: " + name);

    }


    private void newGameDialog(){
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "The password was: " + password +
                "/nWould You Like to Start a New Game?",
                "Play Again?", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION)
            initialize();
        else
            System.exit(0);
    }

    private class ClickListener implements MouseListener{

        @Override
        public void mousePressed(MouseEvent e) {
            Object source = e.getSource();
            if(source instanceof alphabet tilePressed){
                char c = ' ';
                int index = 0;
                boolean updated = false;

                c = tilePressed.guess();

                // reveals each character that is guessed correctly
                while((index = password.toLowerCase().indexOf(c, index)) != -1){
                    passwordHidden.setCharAt(index, password.charAt(index));
                    index++;
                    updated = true;
                }

                if(updated){
                    correct.setText("Word: " + passwordHidden.toString());

                    //removing the newGameDialog and making a completed words option and then if the completed words reaches a number it runs newGameDialog();

                    if(passwordHidden.toString().equals(password)){
                        gameRack.removeListener();
                        gameHangman.winImage();
                        newGameDialog();
                    }
                }
                else{
                    incorrect.setText("Incorrect: " + ++numIncorrect);

                    if(numIncorrect >= MAX_INCORRECT){
                        gameHangman.loseImage();
                        gameRack.removeListener();
                        newGameDialog();
                    }
                    else
                        gameHangman.nextImage(numIncorrect);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}





