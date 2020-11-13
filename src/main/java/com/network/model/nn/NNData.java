package com.network.model.nn;

import org.springframework.stereotype.Component;

@Component
public class NNData {

    Double[][] neurons;
    Double[][] biases;
    Double[][][] weights;

    private final int INPUTS = 5;
    private final int OUTPUTS = 38;

    public NNData() {
        updateData(new Integer[] {});
    }

    private void fillWeightsAndBiases() {
        weights = new Double[neurons.length - 1][][];
        biases = new Double[neurons.length - 1][];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Double[neurons[i].length][];
            biases[i] = new Double[neurons[i + 1].length];

            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = new Double[neurons[i + 1].length];

                for (int k = 0; k < neurons[i + 1].length; k++) {
                    weights[i][j][k] = Math.random() * 2 - 1;
                }
            }

            for (int j = 0; j < biases[i].length; j++) {
                biases[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    void fillWithValues(Double[] values) throws Exception {
        if (neurons[0].length != values.length) {
            throw new Exception("Inputs lengths are mismatched!!!");
        }

        neurons[0] = values;
    }

    public void updateData(Integer[] hiddenLayers) {
        neurons = new Double[hiddenLayers.length + 2][];
        neurons[0] = new Double[INPUTS];

        for (int i = 0; i < hiddenLayers.length; i++) {
            neurons[i + 1] = new Double[hiddenLayers[i]];
        }

        neurons[neurons.length - 1] = new Double[OUTPUTS];

        fillWeightsAndBiases();
    }

}
