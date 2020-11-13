package com.network.model.nn;

public class MatrixOperations {

    private MatrixOperations () {}

    public static Double[] addValueToVector(Double[] v, Double num) {
        Double[] vector = new Double[v.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v[one] + num;
        }

        return vector;
    }

    public static Double[] subtractValueFromVector(Double[] v, Double num) {
        Double[] vector = new Double[v.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v[one] - num;
        }

        return vector;
    }

    public static Double[] scaleVector(Double[] v, Double scale) {
        Double[] vector = new Double[v.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v[one] * scale;
        }

        return vector;
    }

    public static Double[] addVectors(Double[] v1, Double[] v2) throws Exception {
        if (v1.length != v2.length) {
            throw new Exception("Lengths are mismatched!!!");
        }

        Double[] vector = new Double[v1.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v1[one] + v2[one];
        }

        return vector;
    }

    public static Double[] subtractVectors(Double[] v1, Double[] v2) throws Exception {
        if (v1.length != v2.length) {
            throw new Exception("Lengths are mismatched!!!");
        }

        Double[] vector = new Double[v1.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v1[one] - v2[one];
        }

        return vector;
    }

    public static Double[] multiplyByValueVectors(Double[] v1, Double[] v2) throws Exception {
        if (v1.length != v2.length) {
            throw new Exception("Lengths are mismatched!!!");
        }

        Double[] vector = new Double[v1.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v1[one] * v2[one];
        }

        return vector;
    }

    public static Double[] divideByValueVectors(Double[] v1, Double[] v2) throws Exception {
        if (v1.length != v2.length) {
            throw new Exception("Lengths are mismatched!!!");
        }

        Double[] vector = new Double[v1.length];

        for (int one = 0; one < vector.length; one++) {
            vector[one] = v1[one] / v2[one];
        }

        return vector;
    }

    public static Double[][] multiplyVectors(Double[] v1, Double[] v2) {
        Double[][] matrix = new Double[v1.length][v2.length];

        for (int one = 0; one < matrix.length; one++) {
            for (int two = 0; two < matrix[0].length; two++) {
                matrix[one][two] = v1[one] * v2[two];
            }
        }

        return matrix;
    }

    public static Double[][] addMatrices(Double[][] m1, Double[][] m2) throws Exception {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            throw new Exception("Dimensions are mismatched!!!");
        }

        Double[][] matrix = new Double[m1.length][m1[0].length];

        for (int one = 0; one < matrix.length; one++) {
            for (int two = 0; two < matrix[0].length; two++) {
                matrix[one][two] = m1[one][two] + m2[one][two];
            }
        }

        return matrix;
    }

    public static Double[][] subtractMatrices(Double[][] m1, Double[][] m2) throws Exception {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            throw new Exception("Dimensions are mismatched!!!");
        }

        Double[][] matrix = new Double[m1.length][m1[0].length];

        for (int one = 0; one < matrix.length; one++) {
            for (int two = 0; two < matrix[0].length; two++) {
                matrix[one][two] = m1[one][two] - m2[one][two];
            }
        }

        return matrix;
    }

}
