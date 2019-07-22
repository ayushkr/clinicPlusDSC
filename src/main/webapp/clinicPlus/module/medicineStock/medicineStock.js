

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