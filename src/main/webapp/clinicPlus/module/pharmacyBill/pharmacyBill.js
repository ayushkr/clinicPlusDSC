


manifestGUISelectLarge('appointment',apiDataGlobal.appointment);
manifestGUISelectLarge('dateOfBill',apiDataGlobal.dateOfBill);





function  PharmacyCashBill() {
    this.moduleName = 'pharmacyCashBill';
    this.id = 0;
    
     

    this.render_list = function (id, divName) {
        $.get("/clinicPlus/api/pharmacyBillRow/ByBillId/" + id, 
        function (result) {
            console.log('  get("/clinicPlus/api/pharmacyBillRow/ByBillId/' + id);
            aylinker({
                urlOfTemplate:
                        "/clinicPlus/module/pharmacyBill/billBodyTemplate.html" + pageNewAy(1),
//                selector: "main_1_inner",
                selector: divName,
                data: result
            });
        });
        document.getElementById('main_1_paging').innerHTML = "";
    };


    this.onRowSelectByKey =
            function (id) {
                // alert(event.keyCode);
                if (event.keyCode === 13) {
                    this.onRowSelect(id);
                }

            };



    this.fillParamsFromGUI =
            function () {
                console.log('setValues');
                var name = [
                    'pharmacyBill',
                    "medicineStock",
                    "qty",
                    "amount",
                    "id"
                ];

                for (i = 0; i < name.length; i++) {
                    console.log('name [i]---------' + name[i]);
                    this.params[name[i]] = document.getElementById(name[i]).value;
                    console.log(name[i] + '---->' + this.params[name[i]]);
                }
            };


    this.deleteRow = function () {
        this.params = ["a"];
        console.log("deleteRow() ");
        this.fillParamsFromGUI();
        var d = {
            "qty": -1,
            "pharmacyBill": this.params['pharmacyBill'],
            "id": this.params['id']
        };
        console.log("deleteRow() d= " + JSON.stringify(d));
        this.postToPharmacyBillRowAPI(d);
    };

    this.saveNew = function (id) {
        console.log('saveNew id_pharmacyBill = ' + id);
        var d = {
            "qty": 0,
            "pharmacyBill": id,
            "id": 0
        };
        this.postToPharmacyBillRowAPI(d);
    };
    this.saveEdit = function () {
        this.params = ["a", "b"];
        this.fillParamsFromGUI();
        var d = {
            "action": "save",
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
        console.log("pharmacyCashBill.saveEdit() d= " + JSON.stringify(d));
        this.postToPharmacyBillRowAPI(d);
    };

    this.postToPharmacyBillRowAPI = function (d) {
        console.log("post  postToPharmacyBillRowAPI() d= " + JSON.stringify(d));
        var apiUrl = '/clinicPlus/api/pharmacyBillRow';

        $.post(apiUrl, d)
                .fail(
                        function (data)
                        {
                            console.log("postToPharmacyBillRowAPI() fail ,data.responseJSON.message="
                                    + data.responseJSON.message);
                            alert_1('ERROR ', data.responseJSON.message);
                        }
                )
                .done(
                        function (data)
                        {
                            console.log("postToPharmacyBillRowAPI()  .done(), data=" +
                                    JSON.stringify(data));

                            $.get("/clinicPlus/api/pharmacyBillRow/ByBillId/" +
                                    d.pharmacyBill,
                                    function (result) {
                                        console.log("postToPharmacyBillRowAPI  get ");
                                        aylinker({
                                            urlOfTemplate: "/clinicPlus/module/pharmacyCashBill/list/template.html?ran=" + Math.random(),
                                            selector: "main_1_inner",
                                            data: result
                                        }
                                        );
                                    });
                        }
                );


//        document.getElementById("pharmacyBillRowByBillId_fillForm").innerHTML = "";
    };

    this.postToPharmacyBill = function (d) {
        console.log("postAkr() d= " + JSON.stringify(d));
        $.post('/clinicPlus/api/pharmacyBill', d)
                .fail(
                        function (data)
                        {
                            console.log("error ay " + data.responseJSON.message);
                            alert_1('ERROR ', data.responseJSON.message);
                        }
                )

                .done(
                        function (data)
                        {
                            console.log("created pharmacyBillRow (local)  with data=" + JSON.stringify(data));
                            //alert_1('Success ', "OK");
                        }
                );
    };
    this.calculateAmount = function (j) {
        var medicineStock;
        var medicineStock_id = document.getElementById('medicineStock').value;
        $.get("/clinicPlus/api/medicineStock/" + medicineStock_id, function (medicineStock) {
//                    console.log('calculateAmount' + JSON.stringify(medicineStock));
            console.log('calculateAmount--->' + medicineStock.id);

            var rate = medicineStock.sellingPrice;

            var qty = document.getElementById('qty').value;

            var amt = (rate * qty);
            amt = amt.toFixed(2);
            console.log('rate =' + rate + ' qty=' + qty + ' amt=' + amt);
            console.log('rate qty amt' + rate + '---' + qty + '------amt' + amt);
            document.getElementById('amount').value = amt;
            document.getElementById('amount_disp').innerHTML = amt;

        });

        {
            if (event.keyCode === 13) {
                this.saveEdit();
            }
        }

    };



}
var pharmacyCashBill = new PharmacyCashBill();

pharmacyCashBill.render_list(apiDataGlobal.id,'main_1_innert');