//http://localhost:8080/clinicPlus/module/pharmacyBill/1.js?_=1556297803504


manifestGUISelectLarge('medicineStock',apiDataGlobal.medicineStock);

function  selectionDone() {
    console.log('selectionDone , name=' + mn.module['select'].name);
    $('#' + mn.module['select'].name).val(mn.module['select'].id);
    $('#' + mn.module['select'].name + '_display').html(mn.module['select'].extra);


}

function calculateAmount(j) {
    var medicineStock;
    var medicineStock_id = document.getElementById('medicineStock').value;
    $.get("/clinicPlus/api/medicineStock/" + medicineStock_id, function (medicineStock) {
        console.log('calculateAmount' + medicineStock.id);
        var rate = medicineStock.sellingPrice;

        var qty = document.getElementById('qty').innerHTML;

        var amt = rate * qty;
        console.log('rate =' + rate + ' qty=' + qty + ' amt=' + amt);
        console.log('rate qty amt' + rate + '---' + qty + '------amt' + amt);
        document.getElementById('amount').value = amt;
        document.getElementById('amount_disp').innerHTML = amt;

    });

}