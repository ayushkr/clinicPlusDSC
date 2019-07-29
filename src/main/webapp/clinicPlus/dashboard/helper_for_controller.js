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
    module: ["a", "b"],
    'temp':["a","b"]

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
            expiryDate_mmyyyy: function expiryFormat1(yyyy_mm_dd) {
                var dparts = yyyy_mm_dd.split("-");
                console.log(dparts);
                return dparts[1] + "-" + dparts[0];
            }
            ,

            listFiles: listFiles
            ,

            jsonise: jsonise
            ,

            ayLoad: ayLoad
            ,
            ddmmyyyyDashed:ddmmyyyyDashed,
          ddyyyDashed:ddyyyyDashed,

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

function ddmmyyyyDashed(yyyymmddValue){
    var parts=yyyymmddValue.split("-");
    var ddmmyyyyValue=parts[2]+"-"+parts[1]+"-"+parts[0];
    return ddmmyyyyValue;
    
}

function ddyyyyDashed(yyyymmddValue){
    var parts=yyyymmddValue.split("-");
    var ddmmyyyyValue=parts[1]+"-"+parts[0];
    return ddmmyyyyValue;
    
}


mn.module['select'] = {

    "id": 0,
    "name": "no",
    'extra': {},
    "s": 0
};




