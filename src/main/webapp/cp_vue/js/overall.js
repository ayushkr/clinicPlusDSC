//overall.js
var st=[];
var apiData;
var folderModule = 'cp_vue';
var apiRoot = "clinicPlus";
var gPageablePageNumber = 1;
var gModule = "";
var enquiry = {
    pageNumber: 0,
    filterColumn: null,
    filter: null,
    sortColumn: null,
    sortOrder: null
};
var MODE = {
    EDIT: 0,
    SELECT: 1
};
var modeCurrent = MODE.SELECT;
var doctor = "doctor", patient = "patient", appointment = "appointment", medicineStock = "medicineStock",
        purchaseBill = "purchaseBill", pharmacyBill = "pharmacyBill";
var apiDataMap = new Map();
apiDataMap.set("doctor", 1);
apiDataMap.set("patient", 1);
apiDataMap.set("appointment", 1);
apiDataMap.set("medicineStock", 1);
apiDataMap.set("purchaseBill", 1);
apiDataMap.set("pharmacyBill", 1);


var api = {
    post: function (d, moduleArg, successFn) {
        console.log("module=" + moduleArg);
        //     enctype: 'multipart/form-data',    JSON.stringify(d)     contentType: false,
        //      contentType: "application/json; charset=utf-8",
        //   var module=d.module
        var data;
        $.ajax({
            type: "POST",
            async: true,
            contentType: "application/json; charset=utf-8",
            url: 'http://localhost:8083/clinicPlus/api/' + moduleArg + '/json',
            beforeSend: null,
            data: JSON.stringify(d),
            processData: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
//            alert(JSON.stringify(data));
                alert_1('fnAjaxPostOne', JSON.stringify(data), 'success');
                successFn(moduleArg, gPageablePageNumber, data);
            }
            ,
            error: function (data) {
                alert_1('fnAjaxPostOne', JSON.stringify(data), 'error');
            }
        });
        return data;
    }
    ,

    fnAjaxGet: function (path) {
        $.ajax({
            type: "GET",
            url: path,
            beforeSend: null,
            success: fnAjaxGetOnSuccess,
            error: function (data) {
                alert_1("fnAjaxGet", data, "error");
            }
        });
    }
    ,

    fnAjaxGetOnSuccess: function (data) {
        apiData = data;
        fnLog(data);
    }
    ,
    pageable2: function (moduleArg, pageNumberArg) {
        gPageablePageNumber = pageNumberArg;
        var o = {module: moduleArg,
            pageNumber: pageNumberArg
        };
        api.pageable(o);
    },
    pageable: function (obj) {
        vue_pageable_actions.button.display = false;
        showLoading();
        if (obj.pageNumber === undefined) {
            obj.pageNumber = gPageablePageNumber;
        }
        if (obj.pageNumber === null) {
            obj.pageNumber = gPageablePageNumber;
        }


        console.log('api.pageable(obj) obj=' + JSON.stringify(obj));
        vue_bill.result = null;
        gModule = obj.module;
        gPageablePageNumber = obj.pageNumber;
        vue_pageable.ready = true;
        vue_edit.ready = false;
        $.ajax({
            type: "GET",
            async: true,
            url: "http://localhost:8083/clinicPlus/api/" + obj.module + "/pageable?pageNumber=" + (obj.pageNumber) + "&filterColumn=undefined&filter=undefined&sortColumn=undefined&sortOrder=undefined&template=undefined" + pageNewAy(1),
            beforeSend: null,
            success: function (data) {
                vue_pageable.result = data;
                apiData = data;
                apiDataMap.set(obj.module, data);
                hideLoading();
            },
            error: function (data) {
//            
                alert_1("fnApiPageable", JSON.stringify(data), "error");
                hideLoading();
            }
        });
    }
    ,

    edit: function (moduleNameArg, id) {
        vue_pageable_actions.button.display = false;
        vue_bill.result = null;
        vue_pageable.ready = false;
        vue_edit.ready = true;
        console.log(' in api_edit ');
        $.ajax({
            type: "GET",
            url: "http://localhost:8083/clinicPlus/api/" + moduleNameArg + "/" + id,
            beforeSend: null,
            success: function (data1) {

                return data1;
            }
        }).then(data2 =>
        {
//        console.log('in api_edit , get, data2=' + JSON.stringify(data2));
            apiData = data2;
            vue_edit.result = data2;
            vue_edit.result.showBillMetaData = true;
        });
        if (moduleNameArg === "pharmacyBill") {
            $.ajax({
                type: "GET",
                async: false,
                url: "http://localhost:8083/clinicPlus/api/pharmacyBillRow/ByBillId/" + id,
                beforeSend: null,
                success: function (data) {
                    vue_bill.result = data;
                    apiData2 = data;
                    console.log('vue_bill data =');
                    //           console.log( JSON.stringify(data1));
                    return data;
                }
            }).then(data2 =>
            {
// console.log(' data2=' + JSON.stringify(data2));
            });
        }

    }


};

function fnLog(data) {
    console.log(data);
}
;


;

function showLoading() {
    vue_status.display = true;
}
function hideLoading() {
    vue_status.display = false;
}



//
//function load_appointment(obj) {
//    vue_appointment.display = true;
//    console.log('load_appointment..');
//    $.ajax({
//        type: "GET",
//        url: "http://localhost:8083/clinicPlus/api/appointment/pageable?pageNumber="
//                +
//                (obj.pageNumber)
//                + "&filterColumn=undefined&filter=undefined&sortColumn=undefined&sortOrder=undefined&template=undefined"
//                + pageNewAy(1),
//        beforeSend: null,
//        success: function (data) {
//            console.log('data=' + JSON.stringify(data));
//            apiData = data;
//            vue_appointment.result = data;
//        }
//    });
//}






function rowEdited(moduleNameArg, id) {
    fnApiEdit(moduleNameArg, id);
}




function fnApiSave() {
    vue_edit.ready = false;
    vue_pageable.ready = true;
    api.post(apiData, gModule, api.pageable2);
//    vue_pageable_actions.button.edit = false;
//    vue_pageable_actions.button.delete = false;
}

function fnApiSaveCancel() {
    vue_edit.ready = false;
    vue_pageable.ready = true;
    vue_pageable_actions.button.edit = false;
    vue_pageable_actions.button.delete = false;
}

var fnSettingsColumnVisibility = function () {
//    alert('settings_column_visibility');
    vue_pageable.settings_column_visibility = !vue_pageable.settings_column_visibility;
    ajax_post_akr(apiData.titles, 'titles', alert('saved titles'))
}

function selectPatient() {
//    alert("selectPatient");
    vue_float1.display = true;
    var data = fnAjaxPostOne({pageNumber: 1}, 'patient/pageable', selectPatient_part2);
}
function selectPatient_part2(data) {
    vue_float1.result = data;
}

var prev = null, next = null;
function nextStep() {
}

function alert_1(header, body, type) {
    console.log(header + "\n\n " + body + "\n\n" + type);
    if (type === "error") {
        alert("ERROR\n" + header + "\n\n " + body + "\n\n" + type);
    } else {
        console.log(header + "\n\n " + body + "\n\n" + type);
    }
}

var p;
var provision = null;
var returnState = null;
function fetch(obj) {
    console.log("fetch(obj) --> " + JSON.stringify(obj));
//{"get":"appointment","for":{"module":"pharmacyBill","id":2}}
    vue_pageable_actions.selectionMode.get = obj.get;
    vue_pageable_actions.selectionMode.for = obj.for;
    st.push(obj);
    if (obj.get === "appointment") {
        provision =
                function (load) {
                    console.log("provision for appointment called  --> \n" + JSON.stringify(load));
                    vue_pageable_actions.selectionMode.get = null;
                };
    }else{
        provision =
                function (load) {
                    console.log("provision for general called  --> \n" + JSON.stringify(load));
                    vue_pageable_actions.selectionMode.get = null;
                };
        
    }


    obj.module = obj.get;
    api.pageable(obj);
}

var entity = {
    selected: null,
    select: function (obj) {
        selected = obj;
        vue_pageable_actions.display = true;
        console.log("select obj=" + JSON.stringify(obj));

        try {
            vue_pageable.result.pageList.content[obj.key].selected = !vue_pageable.result.pageList.content[obj.key].selected;
        } catch (e) {
            console.log("" + e);
        }

        if (vue_pageable_actions.selectionMode.get === null) {
            console.log("vue_pageable_actions.selectionMode.get  === null  ordinary");

        } else {
            console.log("vue_pageable_actions.selectionMode.get not null so select for provision");

            provision(obj);
        }
//  alert(JSON.stringify(obj));
    },
    edit: function () {
        api.edit(selected.module, selected.data.id);
    },
    selectFor: function (obj) {
        alert("selectFor " + JSON.stringify(obj));
    }

};




