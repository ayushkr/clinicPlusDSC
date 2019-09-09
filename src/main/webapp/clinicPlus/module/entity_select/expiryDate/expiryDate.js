// entity_select/expiryDate/expiryDate.js

var fieldName = "";
var dateStrDashed;
var dateJSON = {year: 2020, month: 1, day: 31};
var guiControls = "";


function updateCallerUIWithDate() {
    dateStrDashed = getDateDashed(dateJSON);
//    $("#" + mn.module['select'].obj.input).val(dateStrDashed);
//    $("#" + mn.module['select'].obj.input + "_display").html(dateStrDashed);
//    
    document.getElementById("modalDate").style = "display:none";
    console.log("expiryDate, updateCallerUIWithDate()" );
    var obj = {
        who: 'expiryDate',
        id: dateStrDashed,
        extra: dateStrDashed
    };
    hideMainLevel();
    selectionDone(obj);
}

function inputChangedByUser() {

    var month_val = $("#combo_month").val();
    var year_val = $("#year").val();

    dateJSON.year = year_val;
    dateJSON.month = month_val;
    console.log("dateExpiry,inputChangedByUser(), dateJSON =" + getDateDashed(dateJSON));
  ;
}

function getTodayJSON() {
    var dateNow = new Date();
    dateJSON = 
            {year: dateNow.getFullYear(), month: dateNow.getMonth() + 1,
        day: dateNow.getDate()};
    console.log("getTodayJSON() dateJSON= " + JSON.stringify(dateJSON));
    $("#combo_month").val(dateJSON.month);
    $("#combo_year").val(dateJSON.year);
    console.log("getTodayJSON() dateJSON= " + JSON.stringify(dateJSON));
    displayCalendar(dateJSON);
    return dateJSON;
}
function getGivenDate() {
    var s = "";

    var strDate = mn.module['select'].obj.value + '';
    console.log(" getGivenDate(), input=" + strDate);
    if (strDate !== '') {
        var parts = strDate.split('-');
        dateJSON.year = Number(parts[0]);
        dateJSON.month = Number(parts[1]);
        dateJSON.day = Number(parts[2]);
    } else {
        dateJSON = getTodayJSON();
    }
    $("#combo_month").val(dateJSON.month);
    $("#combo_year").val(dateJSON.year);
    displayCalendar(dateJSON);
    console.log(' got ' + strDate + ' is converted to json ' + JSON.stringify(dateJSON));
    return dateJSON;
}
function getDateDashed(dateJSON) {

    console.log("getDateDashed() dateJSON= " + JSON.stringify(dateJSON));
    var day_str = "0";
    dateStrDashed = "" + dateJSON.year + "-" +
            twoDigitise(dateJSON.month) + "-" +
            twoDigitise(dateJSON.day);
    console.log("getDateDashed() dateStr= " + day_str);
    return dateStrDashed;
}

function getDateDashed_ddmmyyyy(dateJSON) {

    console.log("getDateDashed() dateJSON= " + JSON.stringify(dateJSON));
    var day_str = "0";
    dateStrDashed = "" + dateJSON.year + "-" +
            twoDigitise(dateJSON.month) + "-" +
            twoDigitise(dateJSON.day);
    console.log("getDateDashed() dateStr= " + day_str);
    return dateStrDashed;
}


document.getElementById('entity_select_closeButton_modalDate').style.display = 'none';