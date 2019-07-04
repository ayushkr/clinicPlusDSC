
updateCurrentDate('dateCurrent');

function  routeFunctions() {
    console.log('sammy all ');
    this.debug = true;
    this.get('#/', function () {
        this.app.swap('Click form!');
    });
    this.get('#/file', file_i);
    this.get('#/cmd', cmd);
    this.get('#/dummy', dummy);
    this.post('#/cmd/post', cmd_post);
       this.post('#/postFile', postFile);


}
var module;
var id = 0;
var action;
var directUrl;
var pageNumber = 1;

// controller
(function ($) {
    console.log('sammy loaded ');
//var sammy_navbar = $.sammy('#navbar', routeFunctions);
    var sammy_main1 = $.sammy('#main1_inner', routeFunctions);
    var sammy_main2_inner = $.sammy('#main2_inner', routeFunctions);
    var sammy_main3_inner = $.sammy('#main3_inner', routeFunctions);





    $(function () {
        //sammy_navbar.run('#/');
        sammy_main1.run('#/');
        sammy_main2_inner.run('#/');
        sammy_main3_inner.run('#/');
    });
})(jQuery);


function postFile(context, data) {
    console.log("akr #/postFile=" + this);
}

function dummy(context) {
    console.log("dummy-----------");

}
var ayu;
function cmd(context) {
       console.log("cmd-----------");
    ayu=context;
    
    
 var params_=  Object.getOwnPropertyNames ( this.params);
   for(var i=0;i<params_.length;i++){
        console.log("param_ ----------"+params_[i]+":"+this.params[params_[i]]);
   }
     listAsPages( this.params['module'],
     'api/'+this.params['module']+'/pageable?pageNumber='+this.params['pageNumber']+'&sortColumn='+this.params['sortColumn']+'&sortOrder='+this.params['sortOrder'], 'main1');
 

    
    // this.partial(partial_path);
}


function cmd_post(context, data) {
    console.log("akr #/cmd/post=" + this);
//                    alert('a data' + data);
    var module_direct = this.params['module_direct'];
    var redirect = this.params['redirect'];
    if (module_direct !== undefined) {
        module = module_direct;
    }
    if (module === "doctor") {
        var d = {
            "fixedId": this.params['fixedId'],
            "userId": this.params['userId'],
            "dateOfJoining": this.params['dateOfJoining'],

            "id": this.params['id'],
            "name": this.params['name'],
            "fees": this.params['fees'],
            "feesList": this.params['feesList'],
            "department": this.params['department'],
            "address": this.params['address'],
            "description": this.params['description'],
            "remediesFor": this.params['remediesFor'],
            "contactPhone": this.params['contactPhone'],
            "feeForClinic": this.params['feeForClinic'],
            "doctorCode": this.params['doctorCode'],
            "timeStamp": this.params['timeStamp'],
            "visitDay": this.params['visitDay'],
            "visitTime": this.params['visitTime'],
            "email": this.params['email'],
            "displayId": this.params['displayId'],
            "profileImage": this.params['profileImage'],
            "newId": this.params['newId'],
            "remarks": this.params['remarks']
                    //  "": this.params[''],
        };
    } else

    if (module === "patient") {
        var d = {
            "bookId": this.params['bookId'],
            "fixedId": this.params['fixedId'],
            "userId": this.params['userId'],
            "id": this.params['id'],
            "name": this.params['name'],
            "pinCode": this.params['pinCode'],
            "age": this.params['age'],
            "sex": this.params['sex'],
            "dob": this.params['dob'],

            "notes": this.params['notes'],
            "contactEmergency": this.params['contactEmergency'],
            "dateOfRegistration": this.params['dateOfRegistration'],
            "address": this.params['address'],
            "description": this.params['description'],
            "contactPhone": this.params['contactPhone'],
            "email": this.params['email'],
            "displayId": this.params['displayId'],
            "profileImage": this.params['profileImage'],
            "remarks": this.params['remarks']
                    //  "": this.params[''],
        };
    } else

    if (module === "appointment") {
        var d = {

            "bookId": this.params['bookId'],
            "doctor": this.params['doctor'],
            "patient": this.params['patient'],
            "dateOfAppointment": this.params['dateOfAppointment'],
            "appointmentTypeEntity": this.params['appointmentTypeEntity'],
            "appointmentStatusEntity": this.params['appointmentStatusEntity'],
            "consultFee": this.params['consultFee'],
            "feeForClinic": this.params['feeForClinic'],

            "id": this.params['id']
        };
        console.log("post_akr appointment d: " + JSON.stringify(d));
    } else

    if (module === "medicineBrandName") {
        var d = {
            "id": this.params['id'],
            "brandName": this.params['brandName'],
            "company": this.params['company'],
            "genericName": this.params['genericName'],
            "usedFor": this.params['usedFor'],
            "type": this.params['type'],
            "groupid": 0,
            "description": this.params['description'],
            "other": this.params['other'],
            "userid": this.params['brandName']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else

    if (module === "vendor") {
        var d = {

            "name": this.params['name'],
            "description": this.params['description'],
            "address": this.params['address'],

            "place": this.params['place'],
            "pinCode": this.params['pinCode'],
            "contactPhone": this.params['contactPhone'],
"email": this.params['email'],
            "dlNo": this.params['dlNo'],
            "ssid": this.params['ssid'],
           
            "id": this.params['id']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else

    if (module === "medicineStock") {
        var d = {

            "medicineBrandName": (this.params['medicineBrandName']),
            "vendor": this.params['vendor'],
             "billNo": this.params['billNo'],
            "expiryDate": this.params['expiryDate'],
            "costPrice": this.params['costPrice'],
            "discount": this.params['discount'],
            "sellingPrice": this.params['sellingPrice'],
            "batch": this.params['batch'],
            "dateOfPurchase": this.params['dateOfPurchase'],

            "qtyPurchased": this.params['qtyPurchased'],
            "qtyRemaining": this.params['qtyRemaining'],
            "cgst": this.params['cgst'],
              "sgst": this.params['sgst'],
            "rate": this.params['rate'],
            "mrp": this.params['mrp'],
            "subCount": this.params['subCount'],

            "id": this.params['id']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else

    if (module === "pharmacyBillRow") {
        var d = {
            "pharmacyBill": this.params['pharmacyBill'],
            "appointment": this.params['appointment'],
            "medicineBrandName": this.params['medicineBrandName'],
            "medicineStock": this.params['medicineStock'],
            "qty": this.params['qty'],
            "amount": this.params['amount'],
            "bill": this.params['bill'],
            "remaining": this.params['remaining'],
            "id": this.params['id']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else

    if (module === "pharmacyBill") {
        var d = {

            "dateOfBill": this.params['dateOfBill'],
            "remarks": this.params['remarks'],
            "appointment": this.params['appointment'],
            "pharmacyBillRow": this.params['pharmacyBillRow'],

            "id": this.params['id']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else




    if (module === "bill") {
        var d = {
            "pharmacyBill": this.params['pharmacyBill'],
            "remarks": this.params['remarks'],
            "id": this.params['id']
        };
        console.log("akr d: " + JSON.stringify(d));
    } else



    if (module === "jote") {
        var d = {
            "rid": this.params['rid'],
            "body": this.params['body'],
            "id": this.params['id'],
            "title": this.params['title'],
            "category": this.params['category'],
            // "dateOfCreation": this.params['dateOfCreation'],
            "status": this.params['status']
                    //  "": this.params[''],
        };
    } else

    if (module === "fileStore") {
        var d = {

            "title": this.params['title'],
            "status": this.params['status'],
            "id": this.params['id']

        };
    } else



    if (module === "fileStorage") {
        var d = {
            "file": this.params['file']
        };
    }



    var addOrDeleteFlag = this.params['addOrDeleteFlag'];

    //alert_1("addOrDeleteFlag=" + addOrDeleteFlag);

    if (addOrDeleteFlag === 'add')
    {
        //  alert_1("add",d);
        d.id = 0;
        d.rid = 0;

    } else
    {
        //  alert_1("edit",d);
    }
    console.log("akr api_Posting : " + JSON.stringify(d));
    $.post('/clinicPlus/api/' + module + '', d)
            .fail(
                    function (data)
                    {
                        console.log("error ay " + JSON.stringify(data));
                        alert_1('ERROR ', JSON.stringify(data), 'error');
                    }
            )
            .done(
                    function (data)
                    {
                        alert_1('OK', JSON.stringify(data), 'success');
                        mn.module[module] = data;
                        console.log("post.done module=" + module + ", data=" + JSON.stringify(data));

                        var path = "#/cmd?module=" + module + "&action=/all/list" + pageNewAy(1);
                        console.log('redirect to ' + path);
                        // window.location.href = path;
//                        if (redirect === undefined) {
//                           // window.location.href = path;
//                        } else {
//                            window.location.href = redirect+pageNewAy(1);
//
//                        }

                    }
            )
            ;
}

function file_i(context) {

    var path = this.params['path'];
    id = this.params['id'];

    mn.module[module] = {"id": id};
    mn.module.current = module;

    var partial_path = '/clinicPlus/' + path + '?' + pageNewAy(1);
    document.getElementById('main1_menu').innerHTML = "";

    console.log("#/file , partial=" + partial_path);
    this.partial(partial_path);
}

