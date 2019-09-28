//GUI injectors

manifestGUISelectLarge('purchaseBill', apiDataGlobal.purchaseBill);
manifestGUISelectLarge('expiryDate', apiDataGlobal.expiryDate);

manifestGUISelectLarge('medicineBrandName', apiDataGlobal.medicineBrandName);

function amountTotal_keyUp() {

    var qtyPurchased = document.getElementById('qtyPurchased');
    var amountTotal = document.getElementById('amountTotal');
    var costPrice = document.getElementById('costPrice');

    costPrice.value = amountTotal.value / qtyPurchased.value;

}

function costPrice_keyUp() {

    var qtyPurchased = document.getElementById('qtyPurchased');
    var amountTotal = document.getElementById('amountTotal');
    var costPrice = document.getElementById('costPrice');
    if (costPrice.value == 's' || costPrice.value == 'S') {

        costPrice.value = mrp.value;
        var sellingPrice = document.getElementById('sellingPrice');
        sellingPrice.value = mrp.value;
    } else {

    }
    amountTotal.value = costPrice.value * qtyPurchased.value;

}

function sellingPrice_keyUp() {
    var sellingPrice = document.getElementById('sellingPrice');
    var mrp = document.getElementById('mrp');
    if (sellingPrice.value == 's' || sellingPrice.value == 'S') {

        sellingPrice.value = mrp.value;
    }
}

function amountTotal_keyUp() {

    var qtyPurchased = document.getElementById('qtyPurchased');
    var amountTotal = document.getElementById('amountTotal');
    var costPrice = document.getElementById('costPrice');
    costPrice.value = amountTotal.value / qtyPurchased.value;
}

function amountTotalWOcess_keyUp() {

    var amountTotalWOcess = document.getElementById('amountTotalWOcess');
    var amountTotal = document.getElementById('amountTotal');
   amountTotal.value=amountTotalWOcess.value;
    cess_keyUp();
}

function cess_keyUp() {

//      var mrp = document.getElementById('mrp');
//      var costPrice = document.getElementById('costPrice');
//    var sellingPrice = document.getElementById('sellingPrice');
//    
    var cess = document.getElementById('cess');
    var amountTotalWOcess = document.getElementById('amountTotalWOcess');
    var amountTotal = document.getElementById('amountTotal');

    amountTotal.value =1*
            ((Number(amountTotalWOcess.value))* (1+ (Number(cess.value)/ 100)));
amountTotal_keyUp();
}


function calculateRate(j) {
    var subCount = document.getElementById('subCount').value;
    var mrp = document.getElementById('mrp').value;
    var discount = document.getElementById('discount').value;
    var sp = (mrp * (1 - discount / 100));
    console.log('sp' + sp);
    var res = sp / subCount;
    console.log('res' + res);
    document.getElementById('rate_disp').innerHTML = res;
    document.getElementById('rate').value = res;
}


function vendorSelected() {
    mn.medicineStock.vendor = {};
    mn.medicineStock.vendor.id = document.getElementById('vendor').value;
    mn.medicineStock.vendor.name = document.getElementById('vendor_display').innerHTML;
    mn.medicineStock.billNo = document.getElementById('billNo').value;
}

function billNoChanged() {
    mn.medicineStock.billNo = document.getElementById('billNo').value;
}

function dateOfPurchase_selected() {
    mn.medicineStock.dateOfPurchase =
            document.getElementById('dateOfPurchase').value;
    console.log('dateOfPurchase_selected() , dateOfPurchase=' + mn.medicineStock.dateOfPurchase);


}

