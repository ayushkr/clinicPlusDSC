//overall.js

//addEventListener("contextmenu",function(event){
//    event.preventDefault();
////    alert('aaa');
//    var ctxMenu = document.getElementById("ctxMenu");
//    ctxMenu.style.display = "block";
////    ctxMenu.style.left = (event.pageX - 10)+"px";
////    ctxMenu.style.top = (event.pageY - 10)+"px";
//},false);


function medicineStockSummary(){
    
    
    
}



var entitySelectedLast;

function back() {
    if (mainLayerNumberNow === 1) {
        window.history.back();
    } else {
        hideMainLevel();
    }
}


console.log('loaded overall.js');
var authorizationToken;

function beforeSend_authorize(request) {
    if (authorizationToken !== undefined) {
        request.setRequestHeader("Authorization", 'Bearer ' + authorizationToken);
    }
}


function authorize() {
    var myJSObject = {
        'username': $('#username').val(),
        'password': $('#password').val()
    };

    $.ajax({
        url: '/authenticate',
        type: 'POST',
        data: JSON.stringify(myJSObject),
        contentType: 'application/json',
        success: function (data) {
            console.log('succ of sendJson=' + JSON.stringify(data));
            result_ = data;
            authorizationToken = data.token;
            $('#prompt').html('<h2 style="color:green;">' + 'Ok' + '</h2>');
            $('#loginForm').html('Login Success <a href="home.html#/cmd?module=appointment&action=/all/list&pageNumber=1&div=main_1" > a</a>');
            //                window.location.href = '/clinicPlus/index.html';
            //window.location.href="#/cmd?module=appointment&action=/all/list&pageNumber=1&div=main_1";

        },
        error: function (xhr, status, error) {
            result_ = xhr;
            console.log('error of sendJson=' + JSON.stringify(xhr));
            console.log('error of sendJson=' + JSON.stringify(error));
            $('#prompt').html('<h2 style="color:red;">' + xhr.responseJSON.message + '</h2>');

        }
    });
}

function manifestGUISelectLarge(nameFull, dataV) {
    try {
        var nparts = nameFull.split('_');

        var name = nparts[0];
        var nameNumber = "";
        if (nparts[1]) {
            nameNumber = "_" + nparts[1];
        }
        console.log('manifestGUISelectLarge=' + nameFull + " ");
        var jso = {'id': 10};
        var elem = document.getElementById('selectLarge_' + name + nameNumber);

        console.log('dataV=' + dataV);

        if (elem === null) {
            console.log('elem is null');
        } else {
            console.log('elem is not null');
            elem.innerHTML += 'modified';
            if (dataV === undefined) {
                elem.innerHTML = "data undefined";
            } else {
                var jsonStr = '{"' + name + '":' + JSON.stringify(dataV) +
                        ',"id":' + '"' + nameNumber + '"' +
                        '}';
                jso = JSON.parse(jsonStr);


            }

            console.log('jso=' + JSON.stringify(jso));

            aylinker({
                urlOfTemplate: '/clinicPlus/component/selectLarge/' + name + '.html' + pageNewAy(1),
                selector: 'selectLarge_' + name + nameNumber,
                data: jso

            });


        }

    } catch (e) {

        console.log('error=' + e);
    }
}

function img_preview_upload(idOfImage) {
    // <input type='file' onchange=img_preview_upload
    var imgObj = document.getElementById(idOfImage);
    imgObj.src = window.URL.createObjectURL(event.target.files[0]);
    imgObj.parent[0].load();
    URI.revokeObjURI(imgObj.src);
}


function smallMenuToggle(id) {
    var m = document.getElementById(id);
    if (m.style.display === 'block') {
        m.style.display = 'none';
    } else {
        m.style.display = 'block';
    }

}

function navGo() {
    var selectedItem = event.target;
    console.log('navGo ,selectedItem=' + selectedItem.id);
}

 navigator.connection.online=akrNetworkChanged;
function akrNetworkChanged(){
//    alert('Network changed '+navigator.connection.type);
    console.log(navigator.connection.type);
    if(navigator.onLine){
  alert('online');
 } else {
  alert('offline');
        alert_11('offline')
  
 }
}

function checkServer() {
    //    console.log('checking server ');
    $.ajax('/clinicPlus/status',
            {

                timeout: 500, // timeout milliseconds
                type: 'GET',
                async: false,
                success: function (data) {
                    updateCurrentDate('dateCurrent');
                    $('#navbar').css('backgroundColor', 'var(--color_l3)');
                    //                    hideDivAy('alert_akr');
                    var v = document.getElementById('dateCurrent');

                    //                    console.log('status  ' + v.getAttribute('data-1'));
                    if (v.getAttribute('data-1') === "1") {
                        v.setAttribute('data-1', 0);
                        $('#dateCurrent').css('font-weight', 'bolder');

                    } else {
                        v.setAttribute('data-1', 1);
                        $('#dateCurrent').css('font-weight', 'lighter');
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

//setTimeout(checkServer, 2000);
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


function patientCard_show(id) {


    aylinker({
        urlOfTemplate: "/clinicPlus/module/appointment/patientCard/patientCardMenuTemplate.html?ran=" + Math.random(),
        selector: "main_1_menu",
        data: {}
    });
    document.getElementById('main_1_paging').innerHTML = "";
    var dataGot;
    var path = '/clinicPlus/api/appointment/' + id;
    $.ajax({
        url: path,
        dataType: 'json', // type of response data
        timeout: 500, // timeout milliseconds
        type: 'GET',
        async: false,
        success:
                function (data) {
                    aylinker({
                        urlOfTemplate: "/clinicPlus/module/appointment/patientCard/patientCardTemplate.html?ran=" + Math.random(),
                        selector: "main_1_inner",
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
    //    console.log(' updateCurrentDate(divName), divName=' + divName + ', reqDate=' + reqDateStr.full);
    document.getElementById(divName).innerHTML = reqDateStr.day + "<br>" + reqDateStr.full;
    return reqDateStr;
}



function twoDigitise(num) {
    var res = num;
    if (num < 10)
        res = '0' + num;
    return res;
}



var apiDataGlobal = {};

function populateCreate2(module, id, divName, paramsExtraStr) {
    console.log('populateCreate2(), id=' + id + ', paramsExtraStr=' + JSON.stringify(paramsExtraStr));

    var paging_data = {
        'moduleName': module,
        'edit': false,
        'divName': divName,
        'id': id
    };

    var path = "/clinicPlus/api/" + module + "/" + id;
    console.log('api path=' + path);

    $.ajax({
        type: "GET",
        url: path,
        beforeSend: beforeSend_authorize,
        success: function (apiData) {
            //            paging_data.id=apiData.id;
            apiDataGlobal = apiData;
            apiDataGlobal.renderInDiv = divName;
            console.log('populateCreate2()->ajax-> apiData= ' + JSON.stringify(apiData));
            //           

            apiDataGlobal.paging_data = paging_data;
            return apiDataGlobal;
        }
    }

    ).then(d => {
        var module = paging_data.moduleName;
        //        console.log('populateCreate2(), api returning with paging_data='
        //                +JSON.stringify(paging_data));
        //        console.log(' d=' + JSON.stringify(d));
        //        console.log('d=' + JSON.stringify(d));
        if (paging_data.id === "-1") {

            console.log('id= -1 data= ' + JSON.stringify(d));

            if (module === 'doctor') {
                apiDataGlobal.dateOfJoining = getToday().full;
            }
            if (module === 'patient') {
                apiDataGlobal.dateOfRegistration = getToday().full;
            }

            if (module === 'appointment') {
                apiDataGlobal.dateOfAppointment = getToday().full;
            }

            if (module === 'pharmacyBill') {
                apiDataGlobal.dateOfBill = getToday().full;
                apiDataGlobal.bookId = 0;
            }

            if (module === 'medicineBrandName') {
                apiDataGlobal.groupid = 0;
            }

            if (module === 'medicineStock') {

                apiDataGlobal.cgst = 0;
                apiDataGlobal.sgst = 0;
                apiDataGlobal.discount = 0;
                apiDataGlobal.dateOfPurchase = getToday().full;
                apiDataGlobal.purchaseBill = {id: paramsExtraStr - 0};
                //            

            }
            if (module === 'pharmacyBillRow') {

                console.log('paramsExtraStr from inner=' + JSON.stringify(paramsExtraStr));
                apiDataGlobal.pharmacyBill = {id: paramsExtraStr - 0};
            }

            if (module === 'purchaseBill') {

                console.log('paramsExtraStr from inner=' + JSON.stringify(paramsExtraStr));
                apiDataGlobal.pharmacyBill = {id: paramsExtraStr - 0};
            }


            if (module === 'user') {
                apiDataGlobal.dateOfRegistration = getToday().full;
            }
            console.log('altered apiDataGlobal =' +
                    JSON.stringify(apiDataGlobal));
        } else {
            paging_data.edit = true;
        }






        // populting menu
        if (mainLayerNumberNow !== 0) {
            aylinker({
                urlOfTemplate: "/clinicPlus/module/entity/form/template_menuTop_update.html" + pageNewAy(1),
                selector: divName + '_menu',
                data: paging_data
            });
        } else {
            document.getElementById(divName + "_menu").innerHTML += 'issue';

        }

        // populting inner
        aylinker({
            urlOfTemplate: "/clinicPlus/module/" + module + "/fillForm/template.html?ran=" + Math.random(),
            selector: divName + "_inner",
            data: apiDataGlobal
        }, callbackAfterPopulateCreate2, [divName, module]);



    });

}
//callbackAfterPopulateCreate2
function callbackAfterPopulateCreate2(params) {
    var divName = params[0];
    var module = params[1];
    document.getElementById(divName + "_paging").innerHTML = '';
    document.getElementById(divName).style.display = 'block';
    $.getScript("/clinicPlus/module/" + module + "/" + module + ".js" + pageNewAy(1));


}

var result_ = {};
function listAsPages(module, path, divName, template) {
    console.log('listAsPages module=' + module + ' divName=' + divName);
    console.log('listAsPages path=' + path);
    $.ajax({
        async: true,
        type: "GET",
        dataType: "json",
        url: path,
        beforeSend: function (request) {
            if (authorizationToken !== undefined) {
                request.setRequestHeader("Authorization", 'Bearer ' + authorizationToken);
            }
        },
        success:
                function (result) {
                    document.getElementById(divName + "_menu").innerHTML = "--";
                    document.getElementById(divName + "_inner").innerHTML = "--";
                    document.getElementById(divName + "_paging").innerHTML = "--";

                    result.template = template;
                    apiDataGlobal = result;
                    if (module !== undefined) {

                        if (result.pageList !== undefined) {
                            var paging_data = {
                                'template': template,
                                'moduleName': module,
                                'totalPages': result.pageList.totalPages,
                                'pageable': {'pageNumber': result.pageList.pageable.pageNumber},
                                'filter': result.filter,
                                'filterColumn': result.filterColumn,
                                'sortOrder': result.sortOrder,
                                'sortColumn': result.sortColumn
                            };
                        } else {
                            var paging_data = {
                                'template': template,
                                'moduleName': module,
                                pageable: null
                            };
                        }

                        if (paging_data.pageable !== null) {
                            aylinker({
                                urlOfTemplate: "/clinicPlus/module/entity/list/templatePaging.html" + pageNewAy(1),
                                selector: divName + "_paging",
                                data: {obj: paging_data}
                            }
                            );

                        }
                        aylinker({
                            urlOfTemplate: "/clinicPlus/module/entity/list/template_menuTop.html" + pageNewAy(1),
                            selector: divName + "_menu",
                            data: {obj: paging_data}
                        });



                        var template_page = 'template';

                        if (template === undefined || template === "") {
                            template_page = "template";
                        } else {
                            template_page = template;
                        }


                        aylinker({
                            // urlOfTemplate: "/clinicPlus/module/" +mn.module.current+ "/all/list/template1.html?ran=" + Math.random(),
                            urlOfTemplate: "/clinicPlus/module/" + module + "/list/" + template_page + ".html" + pageNewAy(1),
                            selector: divName + "_inner",
                            data: {obj: result, template: template}
                        });

                        $.getScript("/clinicPlus/module/" + module + "/" + module + ".js" + pageNewAy(1));
                        loading(0, '', '');
                    }
                },
        error: function (jqXHR, textStatus, errorThrown) {
            result_ = jqXHR;
            alert_1('API ' + module + "  " + errorThrown, JSON.stringify(jqXHR), 'failure');
        }
    });



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

function loading(status, head, body) {
    var element = document.getElementById("alert_akr");

    if (status == 1) {
        element.style = "display:block;background-color: #108208eb;";
        document.getElementById("alert_akr_head").innerHTML = head;
        document.getElementById("alert_akr_body").innerHTML = body;
    } else {
        console.log('loading 0 off');
        element.style = "display:none;";
        document.getElementById("alert_akr_head").innerHTML = "";
        document.getElementById("alert_akr_body").innerHTML = "";
    }
    // setTimeout(function () {
    //     hideDivAy('alert_akr');
    // }, 2000);

}



function popupGeneral(level_, moduleName, divName, url) {
    level = level_;
    var div = document.getElementById(divName);
    console.log('modulename=' + moduleName + ',  divname=' + divName + '  div=' + div + '  url=' + url);
    div.style = "display:block";
    mn.module['select'].name = moduleName;
    $("#" + divName + "_inner").load(url + pageNewAy(1));
}


function popup_selection_obj(obj) {
    parent.document.getElementById('iframeNav').style.visibility = 'hidden';
    //    parent.document.iframeNav.style.display = 'none';
    console.log('popup_selection_obj=' + JSON.stringify(obj));
    mn.module['select'].obj = obj;
    //    console.log("entity_select=" + mn.module['select'].obj.entity_select);
    //    console.log("input=" + mn.module['select'].obj.input);

    if (obj.afterClick !== undefined) {
        afterClick = obj.afterClick;
    }

    if (obj.entity_select === 'dateForOrganisation') {
        document.getElementById("modalDate").style = "display:block";
        entitySelect(obj, 'modalDate');
    } else
    if (obj.entity_select === 'expiryDate') {
        document.getElementById("modalDate").style = "display:block";
        entitySelect(obj, 'modalDate');
    } else {
        var divNode = newModal();
        entitySelect(obj, divNode.id);

    }
}

function newModal() {

    mainLayerNumberNow += 1;
    //    divNode = document.createElement('div');
    //    divNode.id = "main_" + mainLayerNumberNow;
    //    
    divNode = document.getElementById("main_" + mainLayerNumberNow);
    //    divNode.class = 'main_';
    divNode.style = "z-index:" + mainLayerNumberNow * 100 + ";";



    divNode_menu0 = document.createElement('div');
    divNode_menu0.innerHTML = "<span class='hideMainLevelButton' onclick=back() >Close</span>";
    divNode_menu0.id = "main_" + mainLayerNumberNow + '_menu0';


    if (true) {
        var closeNode = document.getElementById('hideMainLevelButton_div');
        divNode_menu0.innerHTML = closeNode.innerHTML;
    }

    divNode_menu = document.createElement('div');
    divNode_menu.id = "main_" + mainLayerNumberNow + '_menu';

    divNode_inner = document.createElement('div');
    divNode_inner.id = "main_" + mainLayerNumberNow + '_inner';

    divNode_paging = document.createElement('div');
    divNode_paging.id = "main_" + mainLayerNumberNow + '_paging';

    //              obj.div='main2';
    console.log('data-populated=' + divNode.getAttribute('data-populated'));
    if (divNode.getAttribute('data-populated') === null) {
        document.body.appendChild(divNode);
        divNode.appendChild(divNode_menu0);
        divNode.appendChild(divNode_menu);
        divNode.appendChild(divNode_inner);
        divNode.appendChild(divNode_paging);
        divNode.setAttribute('data-populated', true);
    }



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
    console.log("hideMainLevel()=" + name);
    var elem = document.getElementById(name)
    //    elem.innerHTML = "";
    if (mainLayerNumberNow !== 1) {
        elem.style.display = "none";
        mainLayerNumberNow -= 1;
    } else {

    }
    // document.body.removeChild(elem);
    if (mainLayerNumberNow === 1) {
        //        parent.document.iframeNav.style.display = 'unset';
        parent.document.getElementById('iframeNav').style.visibility = 'visible';
    } else {

    }
}

function hideDateModal() {
    document.getElementById('modalDate').style.display = 'none';
    //    parent.document.iframeNav.style.display = 'unset';
    parent.document.iframeNav.style.visibility = 'visible';
    //    hideMainLevel();
}

function a(b) {
    b();
}

var level = "";
var afterClick = '';

var openedCup;
function selectionDone(obj) {
    console.log('selectionDone , mn.module[select].name=' + mn.module['select'].name);
    console.log('selectionDone ,obj ..=' + JSON.stringify(obj));
    //

    var manifest_elem = document.getElementById('selectLarge_' + obj.who);


    if (manifest_elem !== null) {
        manifestGUISelectLarge(obj.who, obj.extra);
    } else {
        console.log('manifest_elem ' + 'selectLarge_' + obj.who + ' not exist');
        $('#' + obj.who).val(obj.id);
        $('#' + obj.who + '_display').html(obj.extra);
    }

    if (obj.who === 'doctor') {
        //        $('#' + obj.who + '_display').html(obj.extra.name);
        $('#feeForClinic').val(obj.extra.feeForClinic);
        $('#feesList').html(obj.extra.feesList + " ");


    }




    var aclickNodeId = obj.who + '_afterClick';
    var aclick_node = document.getElementById(aclickNodeId);
    if (aclick_node !== null) {
        aclick_node.setAttribute('data:full', JSON.stringify(obj));
    }

    if (aclick_node !== null) {
        console.log('aclick_node clicked =' + aclickNodeId);
        aclick_node.click();
    } else {
        console.log('aclick_node   not exist for :' + aclickNodeId);
    }




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
    console.log('refresh_entitySelectList(module=' + module
            + ', filterWord=' + filterWord);
    var divname = 'main_' + mainLayerNumberNow;
    var obj = {'entity_select': module};
    entitySelect(obj, divname);
    //    filter(filterWord);
}


function filter(attr, moduleName) {
    var givenWord = "";
    if (event.target.value === undefined) {
        givenWord = attr;
    } else {
        givenWord = (event.target.value).toLowerCase();
    }
    console.log('filter(a,b)  attr=' + attr + 'givenWord=[' + givenWord + '] ');

    //    var dom = document.getElementsByTagName('d_' + moduleName);
    var dom = document.getElementsByClassName('data_' + moduleName);
    for (var i = 0; i < dom.length; i++) {
        var id = dom[i].getAttribute('id');
        var attrDB = (dom[i].getAttribute(attr) + "".trim()).toLowerCase();
        var idOfUI = 'select_' + moduleName + '_' + id;
        console.log('var idOfUI=\'' + idOfUI + '\'');
        document.getElementById(idOfUI).style = 'display:none';
        console.log('idOfUi=' + idOfUI + ',  id=' + id + ' attrDB=' + attrDB);

        console.log('comparing (givenWord)' + givenWord + ' with (attrDB)' + attrDB);

        if (attrDB.includes(givenWord)) {
//        if (attrDB.startsWith(givenWord)) {
            console.log('---matched----');
            document.getElementById(idOfUI).style = 'display:table-row';
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

function entitySelect(obj, divname) {

    console.log('entity_select_into(obj=' + JSON.stringify(obj) + ',\n divname=' + divname + ')');
    //window.location.href="/clinicPlus/home.html#/dummy?function=a&divName="+divname;
    //    window.location.href = "home.html#/dummy" + pageNewAy(1) +
    //            "function=a&divName=" + divname;
    var module = obj.entity_select;
    entitySelectedLast = module;
    var urlOfTemplate = "/clinicPlus/module/entity_select/"
            + module + "/" + module + ".html" + pageNewAy(1);
    console.log('entitySelect(obj, divname)' +
            '  \n urlOfTemplate=' + urlOfTemplate
            + '\n divname=' + divname + '_inner');
    document.getElementById(divname + "_menu").innerHTML = "Loading.... Please wait";
    document.getElementById(divname + "_inner").innerHTML = "";
    document.getElementById(divname + "_paging").innerHTML = "";
    if (obj.apiUrl === undefined) {
        obj.apiUrl = module;
    }

    if (true) {
        $.ajax({
            type: "GET",
            url: "/clinicPlus/api/" + obj.apiUrl,
            beforeSend: beforeSend_authorize,
            success: function (result) {
                apiDataGlobal = result;
                if (module === 'dateExpiry') {
                    document.getElementById(divname + "_menu").innerHTML = "";
                } else {
                    aylinker({
                        urlOfTemplate: '/clinicPlus/module/entity_select/template_menu.html' + pageNewAy(1),
                        selector: divname + "_menu",
                        data: {
                            'moduleName': module,
                            'a': 2
                        }
                    });

                }


                aylinker({
                    urlOfTemplate: urlOfTemplate,
                    selector: divname + '_inner',
                    data: {
                        obj: result,
                        'moduleName': module
                    }
                });

//                document.getElementById(divname + "_paging").innerHTML = "";
                var esfb = document.getElementById("entitySelectFocusButton");
                if (esfb !== null) {
                    esfb.click();
                }

                $.getScript('/clinicPlus/module/entity_select/' + module + '/' + module + '.js' + pageNewAy(1));


            }
        });
    }



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
                document.getElementById(formId + '_img').src = data.fileDownloadUri + pageNewAy(1);
            }

        },
        contentType: false,
        cache: false,
        processData: false


    });
}

//////////////////////////////////


function go(id, module, paramsExtra) {
    console.log('go( id=' + id);
    window.location.href = 'home.html#/dummy?function=populateCreate2'
            + '&module=' + module
            + '&id=' + id
            + '&divName=' + 'main_1'
            + '&paramsExtra=' + paramsExtra
            ;
    //    populateCreate2(module, id, 'main_1');
}



//function goto_update() {
//    var path = '#/cmdc?module=entity&action=/crud/update&id=' + mn.module[mn.module.current].id + pageNewAy(1);
//    console.log("goto_create path=" + path);
//    window.location.href = path;
//}

function goto_list(moduleName) {
    //    save(moduleName);
    var path = 'home.html#/cmd?module=' + moduleName + '&action=/all/list' + pageNewAy(1);
    console.log("goto_list path=" + path);
    window.location.href = path;
}


function goto_delete(module, id) {
    console.log('goto_delete , module=' + module + '  id=' + id + '   ' + option);
    var option = 'y';
    //    var option = prompt("Enter y or Y to confirm ", "");
    console.log(option);
    if (option === 'y' || option === 'Y') {
        $.ajax({
            type: "GET",
            url: "/clinicPlus/api/" + module + "/delete/id/" + id,
            success: function (result) {
                console.log(" goto_delete api result=" + JSON.stringify(result));
                //                   
                if (result.status === 'success') {
                    alert_1("Done :)", result.message, result.status);
                    if (mainLayerNumberNow === 1) {


                        window.history.back();

                    } else {
                        hideMainLevel();
                        refresh_entitySelectList(module, 'name');
                    }



                } else {
                    alert_1("Sorry :( <br>I cannot", result.message, result.status);
                }
            }
        }
        );
    }
}

function save(module, id) {
    console.log('save , module=' + module);
    $('#form_' + module).submit();
    return id;
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


function pharmacyBill() {
    this.name = 'a';
}


function PrintUtils() {
    this.printPage = function () {
        alert('printPage not done');
    };
    this.printNewWindow = function () {

        var toPrint = document.getElementById('main_1_inner');
        var popupWin = window.open('', '_blank', 'width=850,height=850,location=no,left=200px');
        popupWin.document.open();
        popupWin.document.write('<html><title>::Preview::</title><link rel="stylesheet" type="text/css" href="print.css" /></head><body onload="window.print()">');
        popupWin.document.write(toPrint.innerHTML);
        popupWin.document.write('</html>');
        // popupWin.document.close();
    };
    this.printDiv_navOff = function (divName, type) {
        alert('1');
        var navbarDiv, printableAreaDiv, menuDiv, pagerDiv, dateDiv;
        //        navbarDiv = document.getElementById('navbar');
        //        navbarDiv.style.visibility = 'hidden';
        if (type === 'main') {
            printableAreaDiv = document.getElementById(divName + "_inner");
            menuDiv = document.getElementById(divName + "_menu");
            menuDiv.style = "display:none";
            pagerDiv = document.getElementById(divName + "_paging");
            pagerDiv.style = "display:none";
            //            dateDiv = document.getElementById('dateCurrent');
            //            dateDiv.style = "display:none";
        } else {
            divName = 'main_1';
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

        window.iframe1.style.height = "auto";
        window.iframe1.style.height = "-webkit-fill-available";
        window.iframeNav.style.height = "-webkit-fill-available"

        document.title = originalTitle;
        //        navbarDiv.style.display = 'block';
        printableAreaDiv.style = 'margin-left:0mm';
        if (type === 'main') {
            menuDiv.style.display = 'block';
            pagerDiv.style.display = 'block';
            dateDiv.style.display = 'block';
        }
    };


    this.printOPcard = function () {

        var divName = 'main_1';
        var navbarDiv, printableAreaDiv, menuDiv, pagerDiv, dateDiv;
        navbarDiv = document.getElementById('navbar');
        navbarDiv.style.visibility = 'hidden';
        printableAreaDiv = document.getElementById(divName + "_inner");
        menuDiv = document.getElementById(divName + "_menu");
        menuDiv.style.visibility = 'hidden';
        pagerDiv = document.getElementById(divName + "_paging");
        pagerDiv.style.visibility = 'hidden';
        //        dateDiv = document.getElementById('dateCurrent');
        //        dateDiv.style.visibility = 'hidden';
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
        //        dateDiv.style.visibility = 'visible';
    };
    this.hideBorder = function () {
        document.getElementsByClassName('a4').style = "border:none";
        document.getElementsByClassName('a5').style = "border:none";
        document.getElementsByClassName('a6').style = "border:none";
    };
}
var printUtils = new PrintUtils();

function printDiv_navOff(divName) {
//    alert('2');
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

//    // hide sublevel divs
//    var mains = "";
//    for (var i = 1; i < mainLayerNumberNow; i++) {
//        mains[i] = document.getElementById("main_" + i).innerHTML;
//        document.getElementById("main_" + i).innerHTML ='none';
//    }

    window.print();
    for (i = 0; i < elems.length; i++) {
        elems[i].style.display = elems[i].style.displayPrev;
    }

//    for (i = 1; i < mainLayerNumberNow; i++) {
//
//        document.getElementById("main_" + i).innerHTML = mains[i];
//    }

    document.title = originalTitle;
    navbarDiv.style.visibility = 'visible';
    //    printableAreaDiv.style = 'margin-left:0mm';

}
