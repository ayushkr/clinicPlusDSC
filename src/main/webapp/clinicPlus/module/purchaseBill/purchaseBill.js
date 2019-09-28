

manifestGUISelectLarge('vendor', apiDataGlobal.vendor);
manifestGUISelectLarge('dateOfBill', apiDataGlobal.dateOfBill);


function vendorSelected() {

}

function saveAndReload(id){
    
    save('purchaseBill');
    go(id, 'purchaseBill', id);
}


function saveAndNew(id){
    
    save('purchaseBill');
    go(id, 'purchaseBill', id);
}

