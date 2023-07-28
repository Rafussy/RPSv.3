import java.util.*;

class NeuralNetwork {
    private InputLayer inputLayer;
    private HiddenLayer hiddenLayer;
    private OutputLayer outputLayer;
    private double learningRate = 0.1;
    private Random random;
    private int currentEpoch;
    private double[][] currentHiddenWeights;
    private double[] hiddenLayerOutput;
    private double[] initialOutput;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        inputLayer = new InputLayer(inputSize);
        hiddenLayer = new HiddenLayer(hiddenSize);
        outputLayer = new OutputLayer(outputSize);
        random = new Random();

        inputLayer.connectTo(hiddenLayer, random);
        hiddenLayer.connectTo(outputLayer, new Random());

        currentEpoch = 0;
        currentHiddenWeights = new double[hiddenSize][inputSize];
        hiddenLayerOutput = new double[hiddenSize];
        initialOutput = new double[outputSize];
    }

    public String predict(String userChoice) {
    int inputNeuronIndex = mapChoiceToNeuron(userChoice);
    inputLayer.setInputValue(inputNeuronIndex);
    hiddenLayer.calculateOutput();
    outputLayer.calculateOutput();
    double[] outputValues = outputLayer.getOutputValues();

    // Normalize output values to probabilities
    double sum = Arrays.stream(outputValues).sum();
    for (int i = 0; i < outputValues.length; i++) {
        outputValues[i] /= sum;
    }

    // Select computer's choice based on probabilities
    double rand = random.nextDouble();
    double cumulativeProb = 0.0;
    int computerChoiceIndex = -1;
    for (int i = 0; i < outputValues.length; i++) {
        cumulativeProb += outputValues[i];
        if (rand <= cumulativeProb) {
            computerChoiceIndex = i;
            break;
        }
    }

    return mapNeuronToChoice(computerChoiceIndex);
}


    public void train(String userChoice) {
        int inputNeuronIndex = mapChoiceToNeuron(userChoice);
        inputLayer.setInputValue(inputNeuronIndex);
        hiddenLayer.calculateOutput();
        outputLayer.calculateOutput();

        double[] targetOutput = new double[outputLayer.getOutputSize()];
        targetOutput[inputNeuronIndex] = 1;

        outputLayer.updateWeights(targetOutput, learningRate);
        hiddenLayer.updateWeights(outputLayer, learningRate);

        currentHiddenWeights = copyWeights(hiddenLayer.getWeights());
        hiddenLayerOutput = hiddenLayer.getOutputValues();
        if (currentEpoch == 0) {
            initialOutput = outputLayer.getOutputValues();
        }

        currentEpoch++;
    }

    private int mapChoiceToNeuron(String choice) {
        if ("Rock".equals(choice)) {
            return 0;
        } else if ("Paper".equals(choice)) {
            return 1;
        } else if ("Scissors".equals(choice)) {
            return 2;
        } else {
            throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }

    private String mapNeuronToChoice(int neuronIndex) {
        switch (neuronIndex) {
            case 0:
                return "Rock";
            case 1:
                return "Paper";
            case 2:
                return "Scissors";
            default:
                throw new IllegalArgumentException("Invalid neuron index: " + neuronIndex);
        }
    }

    private int getWinningNeuronIndex(double[] outputLayer) {
        int maxIndex = 0;
        for (int i = 1; i < outputLayer.length; i++) {
            if (outputLayer[i] > outputLayer[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private double[][] copyWeights(double[][] weights) {
        int rows = weights.length;
        int cols = weights[0].length;
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(weights[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    public int getCurrentEpoch() {
        return currentEpoch;
    }

    public double[][] getHiddenWeights() {
        return hiddenLayer.getWeights();
    }

    public double[][] getCurrentHiddenWeights() {
        return currentHiddenWeights;
    }

    public double[] getHiddenLayerOutput() {
        return hiddenLayerOutput;
    }

    public double[] getInitialOutput() {
        return initialOutput;
    }
    public double[] getOutputValues() {
        return outputLayer.getOutputValues();
    }
}

