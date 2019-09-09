
manifestGUISelectLarge
('dateOfAppointment',apiDataGlobal.dateOfAppointment);

manifestGUISelectLarge
('patient',apiDataGlobal.patient);
manifestGUISelectLarge
('doctor',apiDataGlobal.doctor);

manifestGUISelectLarge
('appointmentTypeEntity',apiDataGlobal.appointmentTypeEntity);
manifestGUISelectLarge
('appointmentStatusEntity',apiDataGlobal.appointmentStatusEntity);
var appi;
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