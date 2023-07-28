import java.util.Random;
class InputLayer {
    private double[] inputValues;

    public InputLayer(int inputSize) {
        inputValues = new double[inputSize];
    }

    public void setInputValue(int index) {
        for (int i = 0; i < inputValues.length; i++) {
            inputValues[i] = (i == index) ? 1 : 0;
        }
    }

    public double[] getInputValues() {
        return inputValues;
    }

    public void connectTo(HiddenLayer hiddenLayer, Random random) {
        hiddenLayer.setInputLayer(this);
        hiddenLayer.initializeWeights(random);
    }
}

