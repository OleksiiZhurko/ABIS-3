package com.network.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrainData {

    private Integer[] hiddenLayers;

    private Double learningRate;

    private Long epochs;

}
