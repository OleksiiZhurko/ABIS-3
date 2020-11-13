package com.network.model.nn;

import org.springframework.stereotype.Component;

@Component
public class Sigmoid {

    public double produceSigmoid(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    public double produceDifferentiateSigmoid(double value) {
        return value * (1 - value);
    }

}
