import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class RPS extends JFrame implements ActionListener {

    // Panels
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel infoPanel;
    private JPanel neuralInfoPanel;

    // Labels
    private JLabel computerChoiceLabel;
    private JLabel resultLabel;
    private JLabel epochLabel;
    private JLabel userScoreLabel;
    private JLabel computerScoreLabel;

    // Buttons
    private JButton rockButton;
    private JButton paperButton;
    private JButton scissorsButton;

    // Neural network instance
    private NeuralNetwork neuralNetwork;

    // Variables to keep track of user and computer scores
    private int userScore;
    private int computerScore;

    // Labels for displaying neural network information
    private JLabel epochInfoLabel;
    private JLabel weightsInfoLabel;
    private JLabel currentWeightsInfoLabel;
    private JLabel currentWeightsOfNodesLabel;
    private JLabel initialOutputLabel;
    private JLabel currentOutputValueLabel;

    public RPS() {
        setTitle("Rock-Paper-Scissors Game");
        setSize(800, 600); // Increased size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize panels
        mainPanel = new JPanel();
        buttonPanel = new JPanel();
        infoPanel = new JPanel();
        neuralInfoPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.setLayout(new FlowLayout());
        infoPanel.setLayout(new FlowLayout());
        neuralInfoPanel.setLayout(new GridLayout(6, 1));

        // Initialize labels
        computerChoiceLabel = new JLabel("Computer's Choice: ");
        resultLabel = new JLabel("Result: ");
        epochLabel = new JLabel("Epoch: 0");
        userScoreLabel = new JLabel("Your Score: " + userScore);
        computerScoreLabel = new JLabel("Computer Score: " + computerScore);

        // Initialize buttons
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorsButton = new JButton("Scissors");

        // Registering action listeners for buttons
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        // Add buttons to button panel
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        // Initialize the neural network with input, hidden, and output layers
        int inputSize = 3; // Three possible choices: Rock, Paper, Scissors
        int hiddenSize = 5; // Number of neurons in the hidden layer
        int outputSize = 3; // Three possible choices: Rock, Paper, Scissors

        neuralNetwork = new NeuralNetwork(inputSize, hiddenSize, outputSize);

        // Initialize labels for neural network information
        epochInfoLabel = new JLabel("Number of Epoch: 0");
        weightsInfoLabel = new JLabel("Value of Weights: ");
        currentWeightsInfoLabel = new JLabel("Current Value of Weights: ");
        currentWeightsOfNodesLabel = new JLabel("Current Weights of Nodes: ");
        initialOutputLabel = new JLabel("Initial Output: ");
        currentOutputValueLabel = new JLabel("Current Output Value: ");

        // Add neural network information labels to the neural info panel
        neuralInfoPanel.add(epochInfoLabel);
        neuralInfoPanel.add(weightsInfoLabel);
        neuralInfoPanel.add(currentWeightsInfoLabel);
        neuralInfoPanel.add(currentWeightsOfNodesLabel);
        neuralInfoPanel.add(initialOutputLabel);
        neuralInfoPanel.add(currentOutputValueLabel);

        // Add labels to info panel
        infoPanel.add(userScoreLabel);
        infoPanel.add(computerScoreLabel);
        infoPanel.add(computerChoiceLabel);
        infoPanel.add(resultLabel);
        infoPanel.add(epochLabel);

        // Add panels to the main panel
        mainPanel.add(infoPanel);
        mainPanel.add(neuralInfoPanel);
        mainPanel.add(buttonPanel);

        // Add main panel to the frame
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handling button click event (user's choice)
        String userChoice = e.getActionCommand();
        String[] choices = {"Rock", "Paper", "Scissors"};

        // Predict the computer's choice based on the neural network
        String computerChoice = neuralNetwork.predict(userChoice);

        // Train the neural network with the user's choice
        neuralNetwork.train(userChoice);

        // Determine the winner of the game
        String result = determineWinner(userChoice, computerChoice);

        // Update the GUI labels
        computerChoiceLabel.setText("Computer's Choice: " + computerChoice);
        resultLabel.setText("Result: " + result);
        epochLabel.setText("Epoch: " + neuralNetwork.getCurrentEpoch());

        // Update scores and score labels
        if (result.equals("You win!")) {
            userScore++;
        } else if (result.equals("Computer wins!")) {
            computerScore++;
        }

        userScoreLabel.setText("Your Score: " + userScore);
        computerScoreLabel.setText("Computer Score: " + computerScore);

        // Update neural network information labels
        epochInfoLabel.setText("Number of Epoch: " + neuralNetwork.getCurrentEpoch());
        weightsInfoLabel.setText("Value of Weights: " + Arrays.deepToString(neuralNetwork.getHiddenWeights()));
        currentWeightsInfoLabel.setText("Current Value of Weights: " + Arrays.deepToString(neuralNetwork.getCurrentHiddenWeights()));
        currentWeightsOfNodesLabel.setText("Current Weights of Nodes: " + Arrays.toString(neuralNetwork.getHiddenLayerOutput()));
        initialOutputLabel.setText("Initial Output: " + Arrays.toString(neuralNetwork.getInitialOutput()));
        currentOutputValueLabel.setText("Current Output Value: " + Arrays.toString(neuralNetwork.getOutputValues()));
    }

   private String determineWinner(String userChoice, String computerChoice) {
    if (userChoice.equals(computerChoice)) {
        return "Draw!";
    } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
               (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
               (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
        return "You win!";
    } else {
        return "Computer wins!";
    }
}

    
    // Main method to start the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RPS game = new RPS();
            game.setVisible(true);
        });
    }
}
