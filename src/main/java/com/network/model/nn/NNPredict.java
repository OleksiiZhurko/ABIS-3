package com.network.model.nn;

import org.springframework.stereotype.Component;

@Component
public class NNPredict extends NN {

    public NNPredict(NNData nnData, Sigmoid sigmoid) {
        super(nnData, sigmoid);
    }

    public Double[] predict(Double[] values) throws Exception {
        predictResult(values);

        return nnData.neurons[nnData.neurons.length - 1];
    }
}
