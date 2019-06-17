//medicineStock/1.js

$.getScript("/clinicPlus/module/datalist/medicineBrandName/1.js");
//
//DatalistHelper.go("medicineBrandName");

//function medicineBrandName_datalist_changed() {
//
//    var brandName = $('#brandName').val() + "";
//    console.log("medicineBrandName_datalist_changed() v=" + brandName);
//    var id = brandName.split('_')[0];
//    console.log("id=" + id);
//    $('#brandNameId').val(id);
//}

function calculateRate(j){
 
   
 
 
 var  subCount= document.getElementById('subCount').value;
  var  mrp= document.getElementById('mrp').value;
   var  discount= document.getElementById('discount').value;
   var sp=(mrp*(1-discount/100));
   console.log('sp'+sp);
    var res=sp/subCount;
     console.log('res'+res);
     document.getElementById('rate_disp').innerHTML=res;
     document.getElementById('rate').value=res;;
    
}