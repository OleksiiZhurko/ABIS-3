// Tabs
$('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
    localStorage.setItem('activeTab', $(e.target).attr('href'));
});

let activeTab = localStorage.getItem('activeTab');

if (activeTab) {
    $('#Tabs a[href="' + activeTab + '"]').tab('show');
}


// Input group
$("#btn-point").click(function () {
    addChar('●');
});

$("#btn-line").click(function () {
    addChar('—');
});

$("#btn-space").click(function () {
    addSpace(' ');
});

$("#btn-tab").click(function () {
    addSpace('\t');
});

$("#btn-del").click(function () {
    $("#Textarea1").val(getTextFromMainTextarea().slice(0, -1));
});

$("#btn-clear").click(function () {
    $("#Textarea1").val("");
});
$("#btn-clc").click(function () {
    $("#Textarea2").val("");
    $("#Textarea3").val("");
});

function getLastChar() {
    let str = document.getElementById("Textarea1").value;
    return str.charAt(str.length - 1);
}

function addSpace(char) {
    if (getTextFromMainTextarea().length > 0 && getLastChar() !== ' ' && getLastChar() !== '\t') {
        document.getElementById("Textarea1").value += char;
    }
}

function addChar(char) {
    let str = document.getElementById("Textarea1").value;

    let indexSpace = str.lastIndexOf(' ');
    let indexTab = str.lastIndexOf('\t');

    let maxIndex = indexSpace > indexTab ? indexSpace : indexTab;

    if (str.length - maxIndex < 6) {
        document.getElementById("Textarea1").value += char;
    }
}

function getTextFromMainTextarea() {
    return document.getElementById("Textarea1").value;
}

//Const
const DAY = 24 * 60 * 60 * 1000;
const HOUR = 60 * 60 * 1000;
const MINUTE = 60 * 1000;
const SECOND = 1000;

// AJAX
$("#btn-submit").click(function () {
    let str = getTextFromMainTextarea().trim().replaceAll("\t", " \t ");

    if (str.length > 0) {

        let arr = [];
        let symbols = str.split(' ');

        for (let one = 0; one < symbols.length; one++) {
            arr.push(produceArrFromString(symbols[one]));
        }

        $.ajax({
            type: "POST",
            contentType : "application/json",
            url: "predict",
            data : JSON.stringify(
                {
                    chars : arr,
                }
            ),
            dataType: 'json'
        }).done(function(data) {
            console.log(data);
            console.log(1);
            $("#Textarea2").val(data.text);
            $("#Textarea3").val(data.nums);
        });
    }
});

$("#btn-train").click(function () {
    if (document.getElementById("epochs").value.length > 0
        && document.getElementById("learningRate").value.length > 0) {
        $("#elapsedTime").text("0d 0h 0m 0s");

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "train",
            data : JSON.stringify(
                {
                    hiddenLayers : document.getElementById("layers").value.split(' ').map(x => parseInt(x)),
                    learningRate : parseFloat(document.getElementById("learningRate").value),
                    epochs : parseInt(document.getElementById("epochs").value)
                }
            ),
            dataType : 'json',
        }).done(function(data) {
            let d = Math.floor(data / DAY);
            let h = Math.floor((data - d * DAY) / HOUR);
            let m = Math.floor((data - d * DAY - h * HOUR) / MINUTE);
            let s = Math.floor((data - d * DAY - h * HOUR - m * MINUTE) / SECOND);
            $("#elapsedTime").text(d + "d " + h + "h " + m + "m " + s + "s");
        });
    }
});

function produceArrFromString(str) {
    let arr = [];

    for (let one = 0; one < str.length; one++) {
        arr.push(
            str[one] === '●' ? 0.5 :
                str[one] === '—' ? 1.0 : 0.0
        );
    }

    for (let one = str.length; one < 5; one++) {
        arr.push(0.0);
    }

    return arr;
}