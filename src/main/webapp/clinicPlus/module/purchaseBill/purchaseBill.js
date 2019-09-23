
manifestGUISelectLarge('vendor', apiDataGlobal.vendor);


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

