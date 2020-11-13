package com.network.model.nn;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NNTrain extends NN {

    private double learningRate = 0.01;

    public NNTrain(NNData nnData, Sigmoid sigmoid) {
        super(nnData, sigmoid);
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void fit(List<Double[]> dataset, List<Double[]> expected, long epochs) throws Exception {
        int index;
        List<Double[]> dataToTrain;
        List<Double[]> expectedData;

        for (int one = 0; one < epochs; one++) {
            dataToTrain = new ArrayList<>(List.copyOf(dataset));
            expectedData = new ArrayList<>(List.copyOf(expected));

            while (dataToTrain.size() > 0) {
                index = (int) Math.round(Math.random() * (dataToTrain.size() - 1));

                predictResult(dataToTrain.get(index));

                train(MatrixOperations.subtractVectors(expectedData.get(index), nnData.neurons[nnData.neurons.length - 1]));

                dataToTrain.remove(index);
                expectedData.remove(index);
            }

//            if (one % 200 == 0) {
//                index = (int) Math.round(Math.random() * (dataset.size() - 1));
//                predictResult(dataset.get(index));
//                System.out.println(costFunction(nnData.neurons[nnData.neurons.length - 1], expected.get(index)));
//            }
        }
    }

    private void train(Double[] error) throws Exception {
        nnData.neurons[nnData.neurons.length - 1] = MatrixOperations.scaleVector(
                MatrixOperations.multiplyByValueVectors(
                        differentiateVector(nnData.neurons[nnData.neurons.length - 1]),
                        error
                ),
                learningRate
        ); // produce gradient
        nnData.weights[nnData.weights.length - 1] = MatrixOperations.addMatrices(
                nnData.weights[nnData.weights.length - 1],
                MatrixOperations.multiplyVectors(
                        nnData.neurons[nnData.neurons.length - 2],
                        nnData.neurons[nnData.neurons.length - 1]
                ) // produce deltas
        );
        nnData.biases[nnData.biases.length - 1] = MatrixOperations.addVectors(
                nnData.neurons[nnData.neurons.length - 1],
                nnData.biases[nnData.biases.length - 1]
        );

        for (int two = nnData.weights.length - 2; two >= 0; two--) {
            error = backPropagation(error, two);
        }
    }

    private Double[] backPropagation(Double[] errors, int level) throws Exception {
        Double[] error = findError(nnData.weights[level + 1], errors); // find errors

        nnData.neurons[level + 1] = MatrixOperations.scaleVector(
                MatrixOperations.multiplyByValueVectors(
                        differentiateVector(nnData.neurons[level + 1]),
                        error
                ),
                learningRate
        ); // produce gradient
        nnData.weights[level] = MatrixOperations.addMatrices(
                nnData.weights[level],
                MatrixOperations.multiplyVectors(
                        nnData.neurons[level],
                        nnData.neurons[level + 1]
                ) // produce deltas
        );
        nnData.biases[level] = MatrixOperations.addVectors(
                nnData.biases[level],
                nnData.neurons[level + 1]
        );

        return error;
    }

    private Double[] differentiateVector(Double[] v) {
        Double[] result = new Double[v.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = sigmoid.produceDifferentiateSigmoid(v[i]);
        }

        return result;
    }

    private Double[] findError(Double[][] m, Double[] v) {
        Double[] result = new Double[m.length];
        int index = 0;
        double sum;

        for (Double[] mV : m) {
            sum = 0;

            for (int j = 0; j < m[0].length; j++) {
                sum += mV[j] * v[j];
            }

            result[index++] = sum;
        }

        return result;
    }

    private double costFunction(Double[] result, Double[] target) {
        double sum = 0;

        for (int one = 0; one < result.length; one++) {
            sum += Math.abs(target[one] - result[one]);
        }

        return sum / result.length;
    }
}
