//   /dashboard/helper_for_controller


var mn = {
    a: 3,
    b: 4,
    jserror: '',
    appointment: {
        patient: {id: "", name: ""},
        doctor: {id: "", name: ""},
        date: ""
    },
    doctor: {},
    patient: {},
    medicineBrandName: {},
    module: ["a", "b"]

};



$.views.helpers(
        {
            pageNewAy: function (status) {
                if (status === 1) {
                    return "&refreshId=" + Math.random();
                } else {
                    return "?refreshId=0";
                }

            },
            expiryFormat1: function expiryFormat1(yyyy_mm_dd) {
                var dparts = yyyy_mm_dd.split("-");
                console.log(dparts);
                return dparts[0] + "-" + dparts[1];
            }
            ,

            listFiles: listFiles
            ,

            jsonise: jsonise
            ,

            ayLoad: ayLoad
            ,

            utilities: {
                maxCount: 23,
                subtractMax: function (val) {
                    return val - this.maxCount;
                },
                errorMessages: {
                    msg1: "not available"
                },
                alert2: function (val) {
                    alert('hai2');
                }

            }

        });
function ayLoad(url) {
    $.get(url, function (result) {
        console.log('ayLoad url=' + url);
    });

}
function listFiles(patientId, category, div) {
    var dataGot;

    var result = "";
    $.ajax({
        async: false,
        url: '/clinicPlus/api/fileStore/folder/patient.' + patientId + "." + category,
        type: 'GET',
        success: function (fullJson) {
            console.log("listFiles success ");
            dataGot = fullJson;

            for (var a  in dataGot) {
                console.log('a.path=' + dataGot[a].path);
                if (dataGot[a].fileType === 'jpeg') {
                    result += "<a target='_blank' href='" + dataGot[a].path + "'>" 
                           +
                            "<img width='150px' style='padding:10px' src='"+dataGot[a].path +"'></a>";

                } else {
                    result += "<a target='_blank' href='" + dataGot[a].path + "'>"
                          +
                            "<img width='150px' style='padding:10px' src='./images/pdf.png'></a>";

                }
            }


            var divElement = document.getElementById(div);
            if (divElement !== null) {
                divElement.innerHTML = result;
            }

        }


    });






    return dataGot;
}

function jsonise(o) {
    return JSON.stringify(o);
}

function pageNewAy(status) {
    if (status === 1) {
        return "&refreshId=" + Math.random();
    } else {
        return "?refreshId=0";
    }

}







mn.module['select'] = {

    "id": 0,
    "name": "no",
    'extra': {},
    "s": 0
};
//mn.module['jote_new'] = {
//
//    "id": 0,
//    "title": "",
//    "category": "",
//    "body": "",
//    "status": "",
//    "dateOfCreation": ""
//};
//
//mn.module['fileStore_new'] = {
//    "title": '--',
//    "id": 0
//};
////mn.module['doctor_new'] = {
////   
////    "dateOfJoining":getToday().full,
////     "id": 0
////    
////            //  "": this.params[''],
////
////};
//
//
//
//mn.module['patient_new'] = {
//    "fixedId": 0,
//    "id": 0,
//    "name": '',
//    "age": '',
//    "bookId": 0,
//   'dateOfRegistration': getToday().full,
//    "s": 0
//};
//mn.module['appointment_new'] = {
//    "bookId": 0,
//    "fixedId": 0,
//    "id": 0,
//    "doctor": {id: 0},
//    "patient": {id: 0},
//    "dateOfAppointment": getToday().full,
//    "creationTime": null,
//    "updationTime": null
//};
//mn.module['medicineBrandName_new'] = {
//    "brandName": "",
//    "company": "",
//    "genericName": "",
//    "usedFor": "",
//    "type": "",
//    "groupid": 0,
//    "description": "",
//    "other": "",
//    "id": 0
//};
//
//mn.module['medicineStock_new'] =
//        {
//            "id": 0,
//            "medicineBrandName": {
//                "id": 0
//             
//            },
//            "vendor": {
//                "id": 0
//            },
//            "expiryDate": "12/2000",
//            "gst": 0,
//            "costPrice": null,
//            "costPricePerSubCount": null,
//            "sellingPrice": null,
//            "rate": 0,
//            "rateAvailable": true,
//            "mrp": 0,
//            "batch": "",
//            "discount": 0,
//            "dateOfPurchase":  getToday().full,
//            "qtyPurchased": 0,
//            "qtyRemaining": 0,
//            "subCount": 0
//        };
//
//
//
//
//mn.module['vendor_new'] = {
//    "name": "",
//    "address": "",
//    "contactPhone": "",
//    "place": "",
//    "description": "",
//
//    "id": 0
//};
//
//mn.module['fileContent_new'] = {
//    
//
//    "id": 0
//};
//
//
//
//mn.module['pharmacyBillRow_new'] =
//        {
//            "id": 0,
//            "appointment": {
//                "rid": 0,
//                "id": 1,
//                "doctor": {
//                  
//                    "id": 1
//                    
//                },
//                "patient": {
//                    
//                    "id": 1
//                    
//                },
//                "dateOfAppointment": "10-04-2019",
//                "creationTime": null,
//                "updationTime": null
//            },
//            "medicineStock": {
//                "id": 1262,
//                "medicineBrandName": {
//                    "id": 429
//                   
//                },
//                "vendor": {
//                    "id": 1266
//                    
//                },
//                "expiryDate": "12/2009",
//                "gst": 9,
//                "costPrice": null,
//                "costPricePerSubCount": null,
//                "sellingPrice": null,
//                "rate": 10.75,
//                "mrp": 150,
//                "batch": "we223",
//                "discount": 14,
//                "dateOfPurchase": "12/12/2009",
//                "qtyPurchased": 100,
//                "qtyRemaining": 5,
//                "subCount": 12
//            },
//            "bill": null,
//            "qty": 2,
//            "discount": null,
//            "amount": 21.5
//        };
//
//mn.module['pharmacyBill_new'] =
//        {
//            "dateOfBill": getToday().full,
//            "remarks": "---",
//            "id": 0
//        };
//
//
//
//
//mn.module['bill_new'] = {
//    "remarks": 'not set',
//    "id": 0
//};






