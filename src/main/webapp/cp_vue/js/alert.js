// alerts


function alert_1(head, body, typ) {

    console.log('alert_akr type=' + typ);
    var element = document.getElementById("alert_akr");
    if (typ === 'success') {
        element.style = "display:block;background-color: #108208eb;";
    } else {
        element.style = "display:block;background-color: #ef5377eb;";
    }


    // element.style = "background-color:'red';";
    document.getElementById("alert_akr_head").innerHTML = head;
    document.getElementById("alert_akr_body").innerHTML = body;
    if (typ === 'success') {
        setTimeout(function () {
            hideDivAy('alert_akr');
        }, 500);
    }

}

function alert_11(head, body) {
    alert(head + "--" + body);
}

function hideDivAy(e) {
    var elem = document.getElementById(e);
    if (elem === undefined) {
        return 'undefined';
    } else {
        elem.style.display = "none";
        return 'hide ' + e;
    }
}
