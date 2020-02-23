
function  jsonToFormData(item){

    var form_data = new FormData();

    for ( var key in item ) {
        form_data.append(key, item[key]);
    }


    //    var postData = JSON.stringify(item);
    //    var form_data = new FormData();
    //    form_data.append("postData",postData );

    return form_data;
}

function customFormPost(item,module){
    var formData = new FormData();

    formData.append("username", "Groucho");
    formData.append("accountnum", 123456); // number 123456 is immediately converted to a string "123456"
    for ( var key in item ) {

        console.log(key);
        formData.append(key, "Groucho");
    }


    //
    //    for ( var key in item ) {
    //        try{
    //            formData.append(key, item[key]);
    //        }catch(err){
    //            console.log(err);
    //
    //        }
    //    }


    // HTML file input, chosen by user
    //    formData.append("userfile", fileInputElement.files[0]);

    // JavaScript file-like object
    var content = '<a id="a"><b id="b">hey!</b></a>'; // the body of the new file...
    //    var blob = new Blob([content], { type: "text/xml"});

    //    formData.append("webmasterfile", blob);

    var request = new XMLHttpRequest();
    request.open("POST", 'http://localhost:8083/clinicPlus/api/' + module + '');
    request.send(formData);

}


