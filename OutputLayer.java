import java.util.Random;
class OutputLayer {
    private double[] outputValues;
    private double[] outputErrors;
    private double[][] weights;

    public OutputLayer(int outputSize) {
        outputValues = new double[outputSize];
        outputErrors = new double[outputSize];
    }

    public void calculateOutput() {
        int outputSize = outputValues.length;
        for (int j = 0; j < outputSize; j++) {
            outputValues[j] = sigmoid(outputValues[j]);
        }
    }

    public void updateWeights(double[] targetOutput, double learningRate) {
        int outputSize = outputValues.length;
        for (int j = 0; j < outputSize; j++) {
            outputErrors[j] = targetOutput[j] - outputValues[j];
            double outputDerivative = sigmoidDerivative(outputValues[j]);
            for (int i = 0; i < outputSize; i++) {
                weights[j][i] += learningRate * outputErrors[j] * outputDerivative * outputValues[i];
            }
        }
    }

    public double[] getOutputValues() {
        return outputValues;
    }

    public int getOutputSize() {
        return outputValues.length;
    }

    public double[] getOutputErrors() {
        return outputErrors;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public void setInputLayer(HiddenLayer hiddenLayer) {
        int hiddenSize = hiddenLayer.getOutputValues().length;
        int outputSize = this.getOutputSize();
        weights = new double[outputSize][hiddenSize];
    }

    public void initializeWeights(Random random) {
        int outputSize = this.getOutputSize();
        int hiddenSize = weights[0].length;

        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                weights[i][j] = random.nextDouble() * 2 - 1;
            }
        }
    }

    public double[][] getWeights() {
        return weights;
    }
}
