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
    medicineStock: {},
    module: ["a", "b"],
    'temp': ["a", "b"]

};


//  {{createStars averageRating max=5/}}
//{{sort array reverse=true/}}

$.views.tags({
    createStars:
            function (rating) {
                var ratingArray = [], defaultMax = 5;
                var max = this.props.max || defaultMax;
                for (var i = 1; i <= max; i++) {
                    ratingArray.push(i <= rating ?
                            "rating fullStar" : "rating emptyStar");
                }
                var htmlString = "";
                if (this.tmpl) {
// Use the content or the template passed in with the template
                    htmlString = this.renderContent(ratingArray);
                } else {
// Use the compiled named template.
                    htmlString = $.render.compiledRatingTmpl(ratingArray);
                }
                return htmlString;
            },

    sort: function (array) {
        var ret = "";
        if (this.props.reverse) {
            for (var i = array.length; i; i--) {
                ret += this.tmpl.render(array[i - 1]);
            }
        } else {
            ret += this.tmpl.render(array);
        }
        return ret;
    }


});



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
            ddmmyyyyDashed: ddmmyyyyDashed,
            ddyyyyDashed: ddyyyyDashed,

            utilities: {
                maxCount: 23,
                subtractMax: function (val) {
                    return val - this.maxCount;
                }
                ,
                errorMessages: {
                    msg1: "not available"
                }
                ,
                alert2: function (val) {
                    alert('hai2');
                }

            },
            sortByColumnUI: sortByColumnUI

        }
);

function sortByColumnUI(name, obj) {
    var r = ayRenderNoSelector({
        urlOfTemplate: '/clinicPlus/component/sortByColumnUI.html' + pageNewAy(1),
        data: {name: name,
            obj: obj
           
        }
    });
//    console.log('r=' + r);
    return r;
}


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
                            "<img width=150 style='padding:10px' src='" + dataGot[a].path + "'></a>";

                } else {
                    result += "<a target='_blank' href='" + dataGot[a].path + "'>"
                            +
                            "<img  style='padding:10px' src='./images/text-x-generic.png'></a>";

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



function ddmmyyyyDashed(yyyymmddValue) {
    if (yyyymmddValue !== null) {
        var parts = yyyymmddValue.split("-");
        var ddmmyyyyValue = parts[2] + "-" + parts[1] + "-" + parts[0];
        return ddmmyyyyValue;
    } else {
        return 'undefined';
    }

}

function ddyyyyDashed(yyyymmddValue) {
    if (yyyymmddValue !== null) {
        var parts = yyyymmddValue.split("-");
        var ddmmyyyyValue = parts[1] + "-" + parts[0];
        return ddmmyyyyValue;
    } else {
        return 'undefined';
    }


}


mn.module['select'] = {

    "id": 0,
    "name": "no",
    'extra': {},
    "s": 0
};




