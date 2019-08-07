//overall.js

//addEventListener("contextmenu",function(event){
//    event.preventDefault();
////    alert('aaa');
//    var ctxMenu = document.getElementById("ctxMenu");
//    ctxMenu.style.display = "block";
////    ctxMenu.style.left = (event.pageX - 10)+"px";
////    ctxMenu.style.top = (event.pageY - 10)+"px";
//},false);
function img_preview_upload(idOfImage) {
    // <input type='file' onchange=img_preview_upload
    var imgObj = document.getElementById(idOfImage);
    imgObj.src = window.URL.createObjectURL(event.target.files[0]);
    imgObj.parent[0].load();
    URI.revokeObjURI(imgObj.src);
}

function  navGo() {
    var selectedItem = event.target;
    console.log('navGo ,selectedItem=' + selectedItem.id);
}

function checkServer() {
    console.log('checking server ');
    $.ajax('/clinicPlus/status',
            {

                timeout: 500, // timeout milliseconds
                type: 'GET',
                async: false,
                success: function (data) {
                    $('#navbar').css('backgroundColor', 'var(--color_l3)');
//                    hideDivAy('alert_akr');
                    var v = document.getElementById('dateCurrent');
                    if (v.style.visibility === 'visible') {
                        v.style.visibility = "hidden";
                    } else {
                        v.style.visibility = "visible";
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#navbar').css('backgroundColor', 'red');
//                    alert_1('Network problem',
//                            'There is no network connection to server '
//                            ,
//                            'failure');

                }
            }
    );

    setTimeout(checkServer, 2000);
}

setTimeout(checkServer, 2000);

function filter1() {
    var elem = event.target;
    console.log('onenter');
    mn.temp[elem.id] = elem.value;
    if (event.keyCode === 13) {
        console.log('data-href=' + elem.getAttribute('data-href'));
        window.location.href = elem.getAttribute('data-href') + elem.value;
    }
}



var localClipBoardVariable = "";
function copyToClipBoard(input) {
    /* Get the text field */
    var copyText = document.getElementById(input);
    /* Select the text field */
    copyText.select();
    /* Copy the text inside the text field */
    document.execCommand("copy");
    /* Alert the copied text */
    alert("Copied the text: " + copyText.value);
}
var dummy;
function copyToClipBoardValue(val) {
    localClipBoardVariable = val;
    dummy = document.createElement("textarea");
//    dummy.style.visibility='hidden';
    document.body.appendChild(dummy);
    dummy.setAttribute("id", "dummy_id");
    document.getElementById("dummy_id").value = val;
    dummy.select();
    document.execCommand("copy");
//    alert("Copied the text: " +dummy.value);
    document.body.removeChild(dummy);
}


function pasteFromClipBoardTo(id) {
    document.getElementById(id).value = localClipBoardVariable;
}


function  patientCard_show(id) {
    aylinker({
        urlOfTemplate: "/clinicPlus/module/appointment/patientCard/patientCardMenuTemplate.html?ran=" + Math.random(),
        selector: "main1_menu",
        data: {}
    });
    document.getElementById('main1_paging').innerHTML = "";
    var dataGot;
    var path = '/clinicPlus/api/appointment/' + id;
    $.ajax(path,
            {
                dataType: 'json', // type of response data
                timeout: 500, // timeout milliseconds
                type: 'GET',
                async: false,
                success:
                        function (data) {
                            aylinker({
                                urlOfTemplate: "/clinicPlus/module/appointment/patientCard/patientCardTemplate.html?ran=" + Math.random(),
                                selector: "main1_inner",
                                data: data
                            }
                            );
                            console.log('ajax success path=' + path);
                        }



            });
    document.getElementById("header1").innerHTML =
            document.getElementById("header_common").innerHTML;
    document.getElementById("header2").innerHTML =
            document.getElementById("header_common").innerHTML;
    document.getElementById("a5_2nd").innerHTML =
            document.getElementById("a5_1st").innerHTML;
    console.log('after ajax patient card');
}

function getTodayAsExpiryDate() {
    var date = new Date();
    var reqDateStr = date.getFullYear() + "-" + twoDigitise(date.getMonth() + 1);
    return reqDateStr;
}

function getToday() {
    var date = new Date();
    var weekday = new Array(7);
    weekday[0] = "Sunday";
    weekday[1] = "Monday";
    weekday[2] = "Tuesday";
    weekday[3] = "Wednesday";
    weekday[4] = "Thursday";
    weekday[5] = "Friday";
    weekday[6] = "Saturday";
    var day = weekday[date.getDay()];
    var reqDateStr = date.getFullYear() + "-" + twoDigitise(date.getMonth() + 1) + '-' + twoDigitise(date.getDate());
    return {'full': reqDateStr, 'day': day, dd: date.getDate(), mm: date.getMonth() + 1, yyyy: date.getFullYear()};
}

function updateCurrentDate(divName) {
    var reqDateStr = getToday();
    console.log(' updateCurrentDate(divName), divName=' + divName + ', reqDate=' + reqDateStr.full);
    document.getElementById(divName).innerHTML = reqDateStr.day + "<br>" + reqDateStr.full;
    return reqDateStr;
}


function twoDigitise(num) {
    var res = num;
    if (num < 10)
        res = '0' + num;
    return  res;
}




function populateCreate2(module, id, divName) {
//    var data_;

    window.location.href = '#/dummy?a=populateCreate2/' + module + '/' + divName + '/' + id;
    var paging_data = {
        'moduleName': module,
        'edit': false,
        'divName': divName,
        'id': id
    };
    if (id === 0) {
//        data_ = mn.module[module + "_new"];
        $.get("/clinicPlus/api/" + module + "/" + id, function (data_) {
            console.log('new data_' + JSON.stringify(data_));
//        data_.'id': 0, 'fixedId': 0, 'newId': 0, 'bookId': 0};
            if (module === 'doctor') {
                data_.dateOfJoining = getToday().full;
            }
            if (module === 'patient') {
                data_.dateOfRegistration = getToday().full;
            }

            if (module === 'appointment') {
                data_.dateOfAppointment = getToday().full;
            }

            if (module === 'pharmacyBill') {
                data_.dateOfBill = getToday().full;
            }

            if (module === 'medicineBrandName') {
                data_.groupid = 0;
            }

            if (module === 'medicineStock') {
                data_.cgst = 0;
                data_.sgst = 0;
                data_.discount = 0;
                data_.dateOfPurchase = getToday().full;
//              
                if (mn.medicineStock.vendor !== undefined) {

                    data_.vendor = {};

                    data_.vendor.id = mn.medicineStock.vendor.id;
                    data_.vendor.name = mn.medicineStock.vendor.name;
                }

                if (mn.medicineStock.billNo !== undefined) {
                    data_.billNo = mn.medicineStock.billNo;
                }

                if (mn.medicineStock.dateOfPurchase !== undefined) {
                    data_.dateOfPurchase = mn.medicineStock.dateOfPurchase;
                }


            }
            if (module === 'user') {
                data_.dateOfRegistration = getToday().full;
            }






            console.log("populateCreate2 , module=" + module + "data-json= " + JSON.stringify(data_));
            aylinker({
                urlOfTemplate: "/clinicPlus/module/" + module + "/fillForm/template.html?ran=" + Math.random(),
                selector: divName + "_inner",
                data: data_
            }
            );
        });
    } else {
        $.get("/clinicPlus/api/" + module + "/" + id, function (result) {
            result.renderInDiv = divName;
            //var result={entity, 'a':'1'}
            console.log("populateCreate2 , module=" + module + ', data-api=' + JSON.stringify(result));
            aylinker({
                urlOfTemplate: "/clinicPlus/module/" + module + "/fillForm/template.html?ay=0" + pageNewAy(1),
                selector: divName + "_inner",
                data: result
            }
            );
        });
        paging_data.edit = true;
    }


    if (divName === 'main1') {
        aylinker({
            urlOfTemplate: "/clinicPlus/module/entity/crud/update/template_menuTop_update.html?ay=0" + pageNewAy(1),
            selector: divName + '_menu',
            data: paging_data
        });
    } else {
        var str = "<i class='fa fa-close entity_select_closeButton' \n\
onclick=save('" + module + "');hideMainLevel(); ></i>";
        document.getElementById(divName + "_menu").innerHTML = str;


    }



    document.getElementById(divName + "_paging").innerHTML = '';
    document.getElementById(divName).style.display = 'block';
    $.getScript("/clinicPlus/module/" + module + "/" + module + ".js");
}

function listAsPages(module, path, divName) {
    console.log('listAsPages module=' + module + ' divName=' + divName);
    console.log('listAsPages path=' + path);
    $.ajax({
        dataType: "json",
        url: path,
        success:
                function (result) {

                    if (module !== undefined) {
                        var paging_data = {
                            'moduleName': module,
                            'totalPages': result.pageList.totalPages,
                            'pageable': {'pageNumber': result.pageList.pageable.pageNumber},
                            'sortOrder': result.sortOrder,
                            'sortColumn': result.sortColumn
                        };
                        aylinker({
                            // urlOfTemplate: "/clinicPlus/module/" +mn.module.current+ "/all/list/template1.html?ran=" + Math.random(),
                            urlOfTemplate: "/clinicPlus/module/" + module + "/list/template.html?ay=0" + pageNewAy(1),
                            selector: divName + "_inner",
                            data: {obj: result}
                        }
                        );
                        aylinker({
                            urlOfTemplate: "/clinicPlus/module/entity/all/list/templatePaging.html?ay=0" + pageNewAy(1),
                            selector: divName + "_paging",
                            data: {obj: paging_data}
                        }
                        );
                        aylinker({
                            urlOfTemplate: "/clinicPlus/module/entity/all/list/template_menuTop.html?ay=0" + pageNewAy(1),
                            selector: divName + "_menu",
                            data: {obj: paging_data}
                        }
                        );
                    }
                },
        error: function () {
            alert_1('Network problem', 'There is no network connection to server ', 'failure');
        }
    });
    $.getScript("/clinicPlus/module/" + module + "/" + module + ".js");
}


//////////

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

function popupGeneral(level_, moduleName, divName, url) {
    level = level_;
    var div = document.getElementById(divName);
    console.log('modulename=' + moduleName + ',  divname=' + divName + '  div=' + div + '  url=' + url);
    div.style = "display:block";
    mn.module['select'].name = moduleName;
    $("#" + divName + "_inner").load(url + "?" + pageNewAy(1));
}


function popup_selection_obj(obj) {

    console.log('obj=' + JSON.stringify(obj));
    mn.module['select'].obj = obj;
//    console.log("entity_select=" + mn.module['select'].obj.entity_select);
//    console.log("input=" + mn.module['select'].obj.input);

    if (obj.afterClick === undefined) {

    } else {

        afterClick = obj.afterClick;
    }

    if (obj.entity_select === 'dateForOrganisation') {

        document.getElementById("modalDate").style = "display:block";
        loadTemplate_entity_select_into(obj, 'modalDate');
    } else


    {

        var divNode = newModal();
        loadTemplate_entity_select_into(obj, divNode.id);
        document.getElementById(divNode.id + '_menu').innerHTML = '';
    }







}

function newModal() {

    mainLayerNumberNow += 1;

//    divNode = document.createElement('div');
//    divNode.id = "main_" + mainLayerNumberNow;
//    
    divNode = document.getElementById("main_" + mainLayerNumberNow);

//    divNode.class = 'main_';
    divNode.style = "z-index:" + mainLayerNumberNow * 100 +
            ";background-color:white;position:fixed;width: 90%;height: 100%;\n\
overflow: scroll;";
    document.body.appendChild(divNode);
    divNode_menu = document.createElement('div');
    divNode_menu.id = "main_" + mainLayerNumberNow + '_menu';
    divNode.appendChild(divNode_menu);
    divNode_inner = document.createElement('div');
    divNode_inner.id = "main_" + mainLayerNumberNow + '_inner';
    divNode.appendChild(divNode_inner);
    divNode_paging = document.createElement('div');
    divNode_paging.id = "main_" + mainLayerNumberNow + '_paging';
    divNode.appendChild(divNode_paging);
//              obj.div='main2';
    console.log('new div added as=' + divNode.id + " body=" + divNode);
    divNode.style.display = "block";
    return divNode;
}

var mainLayerNumberNow = 1;
let divNode = undefined;
let divNode_menu = undefined;
let divNode_inner = undefined;
function hideMainLevel() {
    var name = 'main_' + mainLayerNumberNow;
    console.log("name=" + name);
    var elem = document.getElementById(name)
//    elem.innerHTML = "";
    if (mainLayerNumberNow != 1) {
        elem.style.display = "none";
        mainLayerNumberNow -= 1;
    }
    // document.body.removeChild(elem);

}



var level = "";
var afterClick = '';
function  selectionDone(obj) {
    console.log('selectionDone , name=' + mn.module['select'].name + ' obj=' + JSON.stringify(obj));
    console.log('selectionDone ,obj.extra' + JSON.stringify(obj.extra));
    $('#' + obj.who).val(obj.id);
    $('#' + obj.who + '_display').html(obj.extra);

    if (obj.who === 'doctor') {
        $('#' + obj.who + '_display').html(obj.extra.name);
        $('#feesList').html((obj.extra.feesList));
        $('#consultFee').val((obj.extra.feesList).split(',')[0]);
        $('#feeForClinic').val(obj.extra.feeForClinic);
    }


    if (afterClick === '') {

    } else {
        document.getElementById(afterClick).click();
    }
}

function modal(obj) {
    console.log('modal url=' + obj.url);
    document.getElementById('main3').style = 'display:block';
    $.get(obj.url, function (result) {
        aylinker({
            urlOfTemplate: obj.url,
            selector: 'main3_inner',
            data: {obj: result}
        }
        );
    });
}
function showDivAy(e) {
    document.getElementById(e).style = "display:block";
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


function refresh_entitySelectList(module, filterWord) {
    var divname = 'main_' + mainLayerNumberNow;
    var obj = {'entity_select': module};
    loadTemplate_entity_select_into(obj, divname);
    filter(filterWord);
}
function filter(attr) {
    var givenWord = "";
    if (event.target.value === undefined) {
        givenWord = attr;
    } else {
        givenWord = (event.target.value).toLowerCase();
    }

    console.log('-----filterBy attribute=' + attr + "   word =" + givenWord);
    var dom = document.getElementsByTagName('d');
    for (var i = 0; i < dom.length; i++) {
        var id = dom[i].getAttribute('id');
        var name = (dom[i].getAttribute(attr) + "").toLowerCase();
        console.log('id=' + id + ' name=' + name);
        if (name.includes(givenWord)) {
            document.getElementById('select_' + id).style = 'display:table-row';
        } else {
            document.getElementById('select_' + id).style = 'display:none';
        }

    }
}



function selectThis(obj) {

    mn.module['select'].id = obj.id;
    mn.module['select'].name = obj.who;
    mn.module['select'].extra = obj.extra;
    console.log(mn.module['select']);
    selectionDone(obj);
    hideMainLevel();
}




function check_1() {
    var Sname = select_1.getAttribute('Sname');
    console.log('Sname=' + Sname);
}

function loadTemplate_entity_select_into(obj, divname) {
//var entity = mn.module['select'].obj.entity_select;
    var module = obj.entity_select;
    var urlOfTemplate = "/clinicPlus/module/entity_select/" + module + "/" + module + ".html?" + pageNewAy(1);
    console.log('loadTemplate_entity_select_into(obj, divname)' +
            '  \n urlOfTemplate=' + urlOfTemplate
            + '\n divname=' + divname + '_inner');
    document.getElementById(divname + "_menu").innerHTML = "Loading.... Please wait";
    document.getElementById(divname + "_inner").innerHTML = "";
    document.getElementById(divname + "_paging").innerHTML = "";
    if (obj.apiUrl === undefined) {
        obj.apiUrl = module;
    }
    $.get("/clinicPlus/api/" + obj.apiUrl, function (result) {
        aylinker({
            urlOfTemplate: urlOfTemplate,
            selector: divname + '_inner',
            data: {
                obj: result,
                'moduleName': module
            }
        }
        );
        aylinker({
            urlOfTemplate: '/clinicPlus/module/entity_select/template_menu.html',
            selector: divname + "_menu",
            data: {
                'moduleName': module,
                'a': 2
            }
        });
        document.getElementById(divname + "_paging").innerHTML = "";
        //   $.getScript("/clinicPlus/module/entity_select/" + entity + "/" + entity + ".js?" + pageNewAy(1));

    });
//    menu







}



function openCity() {
}

function moveToMark(a, markName) {
    window.location.href = '#' + markName;
}

function openCity(evt, cityName) {

    var i, x, tablinks;
    x = document.getElementsByClassName("city");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < x.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" tabSelected", "");
    }

    document.getElementById(cityName).style.display = "block";
    if (evt === 0) {

    } else {
        evt.currentTarget.className += " tabSelected";
    }
}



function selectAndUpload_old(uploadName_akr, ToUrl) {
    fileSelected(uploadName_akr);
    startUploading(ToUrl);
}

function post_akr(formId, ToUrl) {

    vfd = new FormData(document.getElementById(formId))
//    // create XMLHttpRequest object, adding few event listeners, and POSTing our data
//    var oXHR = new XMLHttpRequest();
//    oXHR.open('POST', ToUrl);
//    oXHR.send(vFD);
    $.ajax({
        async: true,
        type: "POST",
        url: ToUrl,
        data: vfd,
        success: function (data) {
            console.log("sccess " + JSON.stringify(data));
        },
        contentType: false,
        cache: false,
        processData: false

    });
}



var vfd;
function submitFormAKR(formId, ToUrl) {

    vfd = new FormData(document.getElementById(formId))
//    // create XMLHttpRequest object, adding few event listeners, and POSTing our data
//    var oXHR = new XMLHttpRequest();
//    oXHR.open('POST', ToUrl);
//    oXHR.send(vFD);
    $.ajax({
        async: false,
        type: "POST",
        url: ToUrl,
        data: vfd,
        success: function (data) {
            console.log("sccess " + JSON.stringify(data));
            var img = document.getElementById(formId + '_img');
            console.log(img);
            if (img !== null) {
                document.getElementById(formId + '_img').src = data.fileDownloadUri + '?a=3' + pageNewAy(1);
            }

        },
        contentType: false,
        cache: false,
        processData: false


    });
}

//////////////////////////////////


function go(id, module) {
//        var path = '#/cmdc?module=' + module + '&action=/crud/update&id=' + id + pageNewAy(1);
//        console.log("go() .......... path=" + path);
//        window.location.href = path;

    populateCreate2(module, id, 'main_1');
}



//function goto_update() {
//    var path = '#/cmdc?module=entity&action=/crud/update&id=' + mn.module[mn.module.current].id + pageNewAy(1);
//    console.log("goto_create path=" + path);
//    window.location.href = path;
//}

function goto_list(moduleName) {
//    save(moduleName);
    var path = '#/cmd?module=' + moduleName + '&action=/all/list' + pageNewAy(1);
    console.log("goto_list path=" + path);
    window.location.href = path;
}


function goto_delete(module, id) {
    console.log('goto_delete , module=' + module + '  id=' + id + '   ' + option);
    var option = 'y';
//    var option = prompt("Enter y or Y to confirm ", "");
    console.log(option);
    if (option === 'y' || option === 'Y') {

        $.get("/clinicPlus/api/" + module + "/delete/id/" + id,
                function (result) {
                    console.log(" goto_delete api result=" + JSON.stringify(result));
//                   
                    if (result.status === 'success') {
                        alert_1("Done :)", result.message, result.status);
                        window.history.back();
                    } else {
                        alert_1("Sorry :( <br>I cannot", result.message, result.status);
                    }
                }
        );
    }
}

function save(module, id) {
    console.log('save , module=' + module);
    $('#form_' + module).submit();
    // goto_list(module) ;
}


function date_ay() {
    var nameOfSource = event.target.name;
    var nameOfTarget = (nameOfSource.split("__"))[0];
    var originalDate = event.target.value;
    var dateParts = originalDate.split("-");
    finalDate = dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0];
    t = (document.getElementsByName(nameOfTarget))[0];
    t.value = finalDate;
    console.log(t);
}


function  pharmacyBill() {
    this.name = 'a';
}

var printUtils = new PrintUtils();
function  PrintUtils() {
    this.printPage = function () {
        alert('printPage not done');
    };
    this.printNewWindow = function () {

        var toPrint = document.getElementById('main1_inner');
        var popupWin = window.open('', '_blank', 'width=850,height=850,location=no,left=200px');
        popupWin.document.open();
        popupWin.document.write('<html><title>::Preview::</title><link rel="stylesheet" type="text/css" href="print.css" /></head><body onload="window.print()">');
        popupWin.document.write(toPrint.innerHTML);
        popupWin.document.write('</html>');
        // popupWin.document.close();
    };
    this.printDiv_navOff = function (divName, type) {

        var navbarDiv, printableAreaDiv, menuDiv, pagerDiv, dateDiv;
        navbarDiv = document.getElementById('navbar');
        navbarDiv.style.visibility = 'hidden';
        if (type === 'main') {
            printableAreaDiv = document.getElementById(divName + "_inner");
            menuDiv = document.getElementById(divName + "_menu");
            menuDiv.style = "display:none";
            pagerDiv = document.getElementById(divName + "_paging");
            pagerDiv.style = "display:none";
            dateDiv = document.getElementById('dateCurrent');
            dateDiv.style = "display:none";
        } else {
            divName = 'main1';
            printableAreaDiv = document.getElementById(divName);
        }
        title = printableAreaDiv.getAttribute("title");
        console.log(printableAreaDiv);
        // printableAreaDiv.style = 'margin-left:-30mm';

        var originalTitle = document.title;
        document.title = title;
        var elems = $("[data-printable='false']");
        for (var el in elems) {
            console.log("print false=" + el);
        }

        window.print();
        document.title = originalTitle;
        navbarDiv.style.display = 'block';
        printableAreaDiv.style = 'margin-left:0mm';
        if (type === 'main') {
            menuDiv.style.display = 'block';
            pagerDiv.style.display = 'block';
            dateDiv.style.display = 'block';
        }
    };
    this.printOPcard = function () {

        var divName = 'main1';
        var navbarDiv, printableAreaDiv, menuDiv, pagerDiv, dateDiv;
        navbarDiv = document.getElementById('navbar');
        navbarDiv.style.visibility = 'hidden';
        printableAreaDiv = document.getElementById(divName + "_inner");
        menuDiv = document.getElementById(divName + "_menu");
        menuDiv.style.visibility = 'hidden';
        pagerDiv = document.getElementById(divName + "_paging");
        pagerDiv.style.visibility = 'hidden';
        dateDiv = document.getElementById('dateCurrent');
        dateDiv.style.visibility = 'hidden';
        this.hideBorder();
        title = printableAreaDiv.getAttribute("title");
        console.log(printableAreaDiv);
        printableAreaDiv.style.left = '0px';
        printableAreaDiv.style.top = '0px';
        var originalTitle = document.title;
        document.title = title;
//        alert();
        var elems = document.querySelectorAll("[data-akr-printable='false']");
        for (i = 0; i < elems.length; i++) {
            elems[i].style.displayPrev = elems[i].style.display;
            elems[i].style.display = 'none';
        }

        window.print();
        for (i = 0; i < elems.length; i++) {
            elems[i].style.display = elems[i].style.displayPrev;
        }
        document.title = originalTitle;
        document.title = originalTitle;
        navbarDiv.style.visibility = 'visible';
        printableAreaDiv.style.left = '0px';
        menuDiv.style.visibility = 'visible';
        pagerDiv.style.visibility = 'visible';
        dateDiv.style.visibility = 'visible';
    };
    this.hideBorder = function () {
        document.getElementsByClassName('a4').style = "border:none";
        document.getElementsByClassName('a5').style = "border:none";
        document.getElementsByClassName('a6').style = "border:none";
    };
}

function printDiv_navOff(divName) {
    var navbarDiv = document.getElementById('navbar');
    navbarDiv.style.visibility = 'hidden';
    var printableAreaDiv = document.getElementById("printableArea");
    //title = pri.getAttribute("pageTitle");
    title = printableAreaDiv.getAttribute("title");
    console.log(printableAreaDiv);
    var originalTitle = document.title;
    document.title = title;
    var elems = document.querySelectorAll("[data-akr-printable='false']");
    for (i = 0; i < elems.length; i++) {
        elems[i].style.displayPrev = elems[i].style.display;
        elems[i].style.display = 'none';
    }

    window.print();
    for (i = 0; i < elems.length; i++) {
        elems[i].style.display = elems[i].style.displayPrev;
    }
    document.title = originalTitle;
    navbarDiv.style.visibility = 'visible';
//    printableAreaDiv.style = 'margin-left:0mm';

}



