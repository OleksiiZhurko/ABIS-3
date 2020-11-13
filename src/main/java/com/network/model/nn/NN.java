package com.network.model.nn;

public abstract class NN {
    protected NNData nnData;
    protected final Sigmoid sigmoid;

    public NN(NNData nnData, Sigmoid sigmoid) {
        this.nnData = nnData;
        this.sigmoid = sigmoid;
    }

    public void setNnData(NNData nnData) {
        this.nnData = nnData;
    }

    protected void predictResult(Double[] values) throws Exception {
        nnData.fillWithValues(values);

        for (int i = 1; i < nnData.neurons.length; i++) {
            forwardPropagation(i);
        }
    }

    private void forwardPropagation(int level) {
        double result;
        int prevLayer = level - 1;

        for (int i = 0; i < nnData.neurons[level].length; i++) {
            result = 0;

            for (int j = 0; j < nnData.neurons[prevLayer].length; j++) {
                result += nnData.neurons[prevLayer][j] * nnData.weights[prevLayer][j][i];
            }

            nnData.neurons[level][i] = sigmoid.produceSigmoid(result + nnData.biases[prevLayer][i]);
        }
    }
}
