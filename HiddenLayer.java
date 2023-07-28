import java.util.Random;
class HiddenLayer {
    private double[][] weights;
    private double[] outputValues;
    private InputLayer inputLayer;

    public HiddenLayer(int hiddenSize) {
        outputValues = new double[hiddenSize];
    }

    public void setInputLayer(InputLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public void initializeWeights(Random random) {
        int hiddenSize = outputValues.length;
        int inputSize = inputLayer.getInputValues().length;
        weights = new double[hiddenSize][inputSize];
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = random.nextDouble() * 2 - 1;
            }
        }
    }

    public void calculateOutput() {
        int hiddenSize = outputValues.length;
        int outputSize = weights[0].length;
        double[] inputValues = inputLayer.getInputValues();

        for (int j = 0; j < hiddenSize; j++) {
            double sum = 0;
            for (int i = 0; i < outputSize; i++) {
                sum += inputValues[i] * weights[j][i];
            }
            outputValues[j] = sigmoid(sum);
        }
    }

    public double[] getOutputValues() {
        return outputValues;
    }

    public void updateWeights(OutputLayer outputLayer, double learningRate) {
        int hiddenSize = outputValues.length;
        int outputSize = outputLayer.getOutputSize();

        double[] outputErrors = outputLayer.getOutputErrors();
        double[][] outputWeights = outputLayer.getWeights();

        for (int j = 0; j < hiddenSize; j++) {
            double hiddenError = 0;
            for (int i = 0; i < outputSize; i++) {
                hiddenError += outputErrors[i] * outputWeights[i][j];
            }
            double hiddenDerivative = sigmoidDerivative(outputValues[j]);
            for (int i = 0; i < outputSize; i++) {
                weights[j][i] += learningRate * hiddenError * hiddenDerivative * inputLayer.getInputValues()[i];
            }
        }
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public void connectTo(OutputLayer outputLayer, Random random) {
        outputLayer.setInputLayer(this);
        outputLayer.initializeWeights(random);
    }

    public double[][] getWeights() {
        return weights;
    }
}

