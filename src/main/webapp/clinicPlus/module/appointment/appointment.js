
manifestGUISelectLarge
('dateOfAppointment',apiDataGlobal.dateOfAppointment);
manifestGUISelectLarge
('doctor',apiDataGlobal.doctor);

manifestGUISelectLarge
('patient',apiDataGlobal.patient);


manifestGUISelectLarge
('appointmentTypeEntity',apiDataGlobal.appointmentTypeEntity);
manifestGUISelectLarge
('appointmentStatusEntity',apiDataGlobal.appointmentStatusEntity);
var appi;


function  doctor_afterClick_fn(){
    doctorFeesDecisionMaker();
    
}

function doctorFeesDecisionMaker() {
    var consultLevel = $('#consultLevel').val();
    console.log('consultLevel=' + consultLevel);
    var feesList=$('#feesList').html();
    var list = feesList.split(',');
    console.log('list size=' + list.length);
    if (consultLevel > (list.length - 1)) {
        var consultFee = list[(list.length) - 1];
    } else {
        var consultFee = list[consultLevel];
    }
    $('#consultFee').val(consultFee);

}


function appointmentTypeEntity_afterClick_fn(){
    
    
//    appi=$.view(this);
//    var vvv=$.observable($.view(this).data);
            
//            vvv.setProperty("feeForClinic",2);
    
//    1 . New
//2 . No Consultation Fee
//3 . free
//4 . follow up
//5 . booking
//6 . no registration fee
//7 . ReNew
   var v= $('#appointmentTypeEntity').val();
    console.log('v=='+v);
    var feeList=apiDataGlobal.doctor.feesList.split(',');
    var feeForClinic=apiDataGlobal.doctor.feeForClinic;
    switch (v){
        case '1':{
                
               $('#consultFee').val(feeList[0]); 
               $('#feeForClinic').val(feeForClinic); 
               $.observable($.view(this).data).setProperty("consultFee",2);
               
               
                break;
        };
        case '2':{
                $('#consultFee').val(0); 
               $('#feeForClinic').val(feeForClinic); 
               break;
        }
        case '3':{
                 $('#consultFee').val(0); 
               $('#feeForClinic').val(0); 
                break;
        }
         case '4':{
                 $('#consultFee').val(0); 
               $('#feeForClinic').val(0); 
                break;
        }
          case '5':{
                 $('#consultFee').val(0); 
               $('#feeForClinic').val(0); 
                break;
        }
          case '6':{
                 $('#consultFee').val(feeList[0]); 
               $('#feeForClinic').val(0); 
                break;
        }
        case '7':{
               $('#consultFee').val(feeList[0]); 
               $('#feeForClinic').val(feeForClinic); 
                break;
        };
        
    }
}