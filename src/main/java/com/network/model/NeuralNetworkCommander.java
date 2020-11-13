package com.network.model;

import com.network.dto.PredictData;
import com.network.dto.TrainData;
import com.network.model.nn.NNData;
import com.network.model.nn.NNPredict;
import com.network.model.nn.NNTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class NeuralNetworkCommander {

    private final NNData nnData;

    private final NNPredict nnPredict;

    private final NNTrain nnTrain;

    @Autowired
    public NeuralNetworkCommander(NNData nnData, NNPredict nnPredict, NNTrain nnTrain) {
        this.nnData = nnData;
        this.nnPredict = nnPredict;
        this.nnTrain = nnTrain;
    }

    public Map<String, String> predict(PredictData predictData) throws Exception {
        int index;
        Double[] res;
        StringBuilder text = new StringBuilder();
        StringBuilder nums = new StringBuilder();

        for (Double[] aChar : predictData.getChars()) {
            res = nnPredict.predict(aChar);

            index = findMaxPosition(res);

            System.out.println(Arrays.toString(res));

            text.append(Data.ALPHABET[index]);
            nums.append((int) (res[index] * 100));
            nums.append(' ');
        }

        System.out.println(text.toString());
        System.out.println(nums.toString());

        return Map.of(
                "text", text.toString(),
                "nums", nums.toString()
        );
    }

    public Long train(TrainData trainData) throws Exception {
        nnData.updateData(trainData.getHiddenLayers());
        nnTrain.setLearningRate(trainData.getLearningRate());

        long time = System.currentTimeMillis();

        nnTrain.fit(Data.INPUTS, Data.OUTPUTS, trainData.getEpochs());

        return System.currentTimeMillis() - time;
    }

    private int findMaxPosition(Double[] array) {
        int index = 0;
        double max = array[0];

        for (int one = 1; one < array.length; one++) {
            if (max < array[one]) {
                max = array[one];
                index = one;
            }
        }

        return index;
    }


}
