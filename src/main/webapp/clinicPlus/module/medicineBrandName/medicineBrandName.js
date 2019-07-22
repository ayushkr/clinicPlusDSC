 
console.log('medicineBrandName.js');
setTimeout(init,100);

setTimeout(function(){
    var medicineBrandNameInp=document.getElementById('medicineBrandName');
    medicineBrandNameInp.focus();
     if(medicineBrandName!==undefined){
    medicineBrandNameInp.value=medicineBrandName;
     }
},200);

var medicineBrandName;



function medicineBrandNameKeyUp(e,destination) {
   
    // alert('pressed'+e.keyCode);
    if (e.keyCode === 13) {
       document.getElementById('button_medicineBrandName_go').click();
      
       medicineBrandName= document.getElementById('medicineBrandName').value;
        
    }
  
};

function init(){
document.getElementById('medicineBrandName').onkeyup=medicineBrandNameKeyUp;
}