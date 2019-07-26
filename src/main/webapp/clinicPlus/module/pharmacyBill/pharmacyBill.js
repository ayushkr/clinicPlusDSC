
var pharmacyCashBill = new PharmacyCashBill();
function  PharmacyCashBill() {
    this.moduleName = 'pharmacyCashBill';
    this.id = 0;

    this.render_list = function (id, divName) {

        console.log('GT PharmacyCashBill.render_list() id=' + id);
//        window.location.href = "#/dummy?a=PharmacyCashBill/" + id;
        aylinker({
            urlOfTemplate: "/clinicPlus/module/pharmacyCashBill/menuTop.html?ran=" + Math.random(),
            selector: "main1_menu",
            data: {id: id}
        }
        );



        $.get("/clinicPlus/api/pharmacyBillRow/ByBillId/" + id, function (result) {
            console.log('PharmacyCashBill.render_list()  get("/clinicPlus/api/pharmacyBillRow/ByBillId/' + id);
            aylinker({
                urlOfTemplate: "/clinicPlus/module/pharmacyCashBill/list/template.html?ran=" + Math.random(),
//                selector: "main1_inner",
                selector: divName,

                data: {obj: result}
            }
            );
        });

        document.getElementById('cashBill_inbuilt').innerHTML = "kitti";
        document.getElementById('main1_paging').innerHTML = "";
    };


    this.onRowSelectByKey =
            function (id) {
               // alert(event.keyCode);
                if(event.keyCode===13){
                    this.onRowSelect(id);
                }
                
            };


    this.onRowSelect =
            function (id) {

                console.log('onRowSelect id=' + id);

//                var rows = document.getElementsByClassName('Row');
//                for (let row of rows) {
//                    row.style = "background:'#ecf6fd';color:'white';";
//                    // row.className+="pharmacyCashBill_selectedRow";
//                }
//                if (id !== 0) {
//                    document.getElementById('row_' + id).style =
//                            'background-color: var(--color_d4);color: var(--color_l4); ';
//                }

                document.getElementById('oneRowInEdit_' + id).style.display = 'table-row';

                $.get("/clinicPlus/api/pharmacyBillRow/" + id, function (result) {
                    // console.log('onRowSelect result=' + JSON.stringify(result));
                    aylinker({
                        urlOfTemplate: "/clinicPlus/module/pharmacyCashBill/oneRowfillFormTemplate.html?ran=" + Math.random(),
                        selector: 'oneRowInEdit_' + id,
//                       selector: 'row_' + id,
                        data: result
                    }
                    );
                });

                setTimeout(function () {
                    document.getElementById('qty').select();
                }, 100);

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
        console.log("postAkr() d= " + JSON.stringify(d));
        var apiUrl = '/clinicPlus/api/pharmacyBillRow';

        $.post(apiUrl, d)
                .fail(
                        function (data)
                        {
                            console.log("postToPharmacyBillRowAPI error ay " + data.responseJSON.message);
                            alert_1('ERROR ', data.responseJSON.message);
                        }
                )
                .done(
                        function (data)
                        {
                            console.log("func postToPharmacyBillRowAPI  .done(, create/delete pharmacyBillRow (local) data=" + JSON.stringify(data));

                            $.get("/clinicPlus/api/pharmacyBillRow/ByBillId/" + data.pharmacyBill.id, function (result) {
                                console.log("postToPharmacyBillRowAPI  get ");
                                aylinker({
                                    urlOfTemplate: "/clinicPlus/module/pharmacyCashBill/list/template.html?ran=" + Math.random(),
                                    selector: "main1_inner",
                                    data: {obj: result}
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
    this.calculateAmount =
            function (j) {
                var medicineStock;
                var medicineStock_id = document.getElementById('medicineStock').value;
                $.get("/clinicPlus/api/medicineStock/" + medicineStock_id, function (medicineStock) {
//                    console.log('calculateAmount' + JSON.stringify(medicineStock));
                     console.log('calculateAmount--->' +medicineStock.id);
                    
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
       if (event.keyCode === 13){
                        this.saveEdit();
       }
    }

            };



}
