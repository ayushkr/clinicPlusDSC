//GUI injectors

manifestGUISelectLarge('purchaseBill',apiDataGlobal.purchaseBill);
manifestGUISelectLarge('expiryDate',apiDataGlobal.expiryDate)






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

