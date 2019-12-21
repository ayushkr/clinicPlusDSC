manifestGUISelectLarge('doctor',{id:0,name:'null'});



var dateFrom_ultimate="2019-07-20";
function dateGroup(type) {
    var dateFromObj = document.getElementById('dateFrom');
    var dateToObj = document.getElementById('dateTo');
    var calcButton = document.getElementById('reportPatientsByDoctor_Calculate');
    var today = getToday();

    if (type === 'db_yesterday') {
        if (today.dd === 2)
        {
            today.dd = 31;
            today.mm -= 1;
        } else {
            today.dd -= 2;

        }
        dateFromObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(today.dd);
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(today.dd);

        calcButton.click();
        return 0;
    }


    if (type === 'yesterday') {
        if (today.dd === 1)
        {
            today.dd = 31;
            today.mm -= 1;
        } else {
            today.dd -= 1;

        }
        dateFromObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(today.dd);
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(today.dd);

        calcButton.click();
        return 0;
    }

    if (type === 'today') {
        dateFromObj.value = getToday().full;
        dateToObj.value = getToday().full;
        calcButton.click();
        return 0;
    }
    if (type === 'thisMonth') {

        dateFromObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(1);
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(31);
        calcButton.click();
        return 0;
    }
    if (type === 'lastMonth') {

        dateFromObj.value = today.yyyy + '-' + twoDigitise(today.mm - 1) + '-' + twoDigitise(1);
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm - 1) + '-' + twoDigitise(31);
        calcButton.click();
        return 0;
    }
    if (type === 'tillNow') {

        dateFromObj.value = dateFrom_ultimate;
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm) + '-' + twoDigitise(31);
        calcButton.click();
        return 0;
    }
    if (type === 'tillLastMonth') {

        dateFromObj.value = dateFrom_ultimate;
        dateToObj.value = today.yyyy + '-' + twoDigitise(today.mm - 1) + '-' + twoDigitise(31);
        calcButton.click();
        return 0;
    }

}

function dateDecide(id) {
    if ($('#' + id).val === 0) {
        return getToday().full;
    } else {
        var v = (document.getElementById(id).value) + '';
        console.log('v=' + v);
        return v;
    }
}
var result_;
function popul___() {
    var id = document.getElementById('doctor').value;
    document.getElementById('reportPart').innerHTML = "Please Wait";
    $.ajax({
        type: 'GET',
        url: "/clinicPlus/api/appointment/doctor/" +
                id + '?a=1' +
                '&dateFrom=' + document.getElementById('dateFrom').value +
                '&dateTo=' + document.getElementById('dateTo').value
        ,
        beforeSend: beforeSend_authorize,
        success: function (result) {
            result_ = result;
            aylinker({
                // urlOfTemplate: "/clinicPlus/module/" +mn.module.current+ "/all/list/template1.html?ran=" + Math.random(),
                urlOfTemplate: "/clinicPlus/module/report_incomeFromDoctor/list/listTemplate2.html?ran=" + Math.random(),
                selector: "reportPart",
                data: {obj: result,
                    'doctor': $('#doctor_display').html()
                }
            });
        }
    }
    );

}