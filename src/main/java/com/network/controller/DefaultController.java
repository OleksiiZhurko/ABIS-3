package com.network.controller;

import com.network.dto.PredictData;
import com.network.dto.TrainData;
import com.network.model.NeuralNetworkCommander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private final NeuralNetworkCommander nnCommander;

    @Autowired
    public DefaultController(NeuralNetworkCommander nnCommander) {
        this.nnCommander = nnCommander;
    }

    @GetMapping(value = "")
    public String produceMainPage(Model model) {
        model.addAttribute("time", "0d 0h 0m 0s");

        return "index";
    }

    @PostMapping(value = "/predict")
    public @ResponseBody Map<String, String>
    produceMainPage(@RequestBody final PredictData chars) throws Exception {
        return nnCommander.predict(chars);
    }

    @PostMapping(value = "/train")
    public @ResponseBody Long
    produceMainPage(@RequestBody final TrainData trainData) throws Exception {
        return nnCommander.train(trainData);
    }

}
