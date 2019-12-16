//medicineStock/1.js

$.getScript("/clinicPlus/module/datalist/medicineBrandName/1.js");

DatalistHelper.go("medicineBrandName");

function medicineBrandName_datalist_changed() {

    var brandName = $('#brandName').val() + "";
    console.log("medicineBrandName_datalist_changed() v=" + brandName);
    var id = brandName.split('_')[0];
    console.log("id=" + id);
    $('#brandNameId').val(id);
}

function popup_selection(name) {
    
    document.getElementById("main2").style = "display:block";
    mn.module['select'].name = name;
    console.log(name+"_selected "+mn.module['select'].name);
    $("#main2").load("/clinicPlus/module/entity_select/view.html?" + pageNewAy(1));
}


function  selectionDone(){
    console.log('selectionDone , name='+mn.module['select'].name);
    $('#'+mn.module['select'].name ).val(mn.module['select'].id);
    $('#'+mn.module['select'].name +'_display').html(mn.module['select'].extra);
   
    
}