
//updateCurrentDate('dateCurrent');

function  routeFunctions(c) {
    console.log('routeFunctions() ' + c);
    this.debug = true;
    this.get('#/', function () {
//        this.app.swap('Click form!');
        console.log('#/  =');
    });
    this.get('#/file', file_i);
    this.get('#/cmd', cmd);
    this.get('#/dummy', dummy);
    this.post('#/cmd/post', cmd_post);
    this.post('#/postFile', postFile);


}
var module;
var id = 0;
var action;
var directUrl;
var pageNumber = 1;

// controller
(function ($) {
    console.log('sammy loaded ');
//var sammy_navbar = $.sammy('#navbar', routeFunctions);
    var sammy_main1_inner = $.sammy('#main_1', routeFunctions);
    var sammy_main2_inner = $.sammy('#main_2', routeFunctions);
    var sammy_main3_inner = $.sammy('#main_3', routeFunctions);
    var sammy_main4_inner = $.sammy('#main_4', routeFunctions);
    var sammy_main5_inner = $.sammy('#main_5', routeFunctions);
    var sammy_main6_inner = $.sammy('#main_6', routeFunctions);
//          var sammy_mainCover = $.sammy('#mainCover', routeFunctions);




    $(function () {
        //sammy_navbar.run('#/');
        sammy_main1_inner.run('#/');
//        sammy_main1_inner.run('main_1');
        sammy_main2_inner.run('main_2');
        sammy_main3_inner.run('main_3');
        sammy_main4_inner.run('main_4');
        sammy_main5_inner.run('main_5');
        sammy_main6_inner.run('main_6');
//         sammy_mainCover.run('mainCover');
    });
})(jQuery);


function postFile(context, data) {
    console.log("akr #/postFile=" + this);
}

function dummy(context) {
    console.log("dummy---context.app.element_selector=" + context.app.element_selector);
    console.log('divName=' + this.params['divName']);

    ayu = context;
    if (this.params['divName'] === undefined) {
        this.params['divName'] = 'main_1';
    }

    if (('#' + this.params['divName']) === context.app.element_selector) {
        result_ = context;
        console.log("dummy(context)-----context.app.element_selector="
                + context.app.element_selector);
        if (context.params['function'] !== undefined) {
            if (context.params['function'] === "populateCreate2") {
                populateCreate2(context.params['module'],
                        context.params['id'],
                        context.params['divName'],
                        context.params['paramsExtra']

                        );
            } else
            if (context.params['function'] === "patientCard_show") {
                patientCard_show(context.params['id']);
            } else
            if (context.params['function'] === "fileDirect") {
                var pathUpdated = '/clinicPlus/' + context.params['path'] + pageNewAy(1);
                document.getElementById('main_1_menu').innerHTML = "fileDirect";
                document.getElementById('main_1_inner').innerHTML = "fileDirect";
                document.getElementById('main_1_paging').innerHTML = "fileDirect";

                console.log("fileDirect() , pathUpdated=" + pathUpdated);
                console.log(" $.getScript(pathUpdated)=" + pathUpdated);
                $.getScript(pathUpdated);
//                    $.getScript('/clinicPlus/module/a.js'+ pageNewAy(1));
            } else {
                console.log('Not impl fn()=' + context.params['function']);
            }
        }
    }




}
var ayu;
function cmd(context) {
    console.log("cmd---context.app.element_selector=" + context.app.element_selector);
    console.log('div=' + this.params['div']);
    ayu = context;
//    
//    loading('Loading','Please Wait....');

    var params_ = Object.getOwnPropertyNames(this.params);

    if (this.params['div'] === undefined) {
        this.params['div'] = 'main_1';
    }

    if (('#' + this.params['div']) === context.app.element_selector) {

        for (var i = 0; i < params_.length; i++) {
            console.log("cmd,param_ ----------"
                    + params_[i] + ":" + this.params[params_[i]]);
        }

        var elems = document.querySelectorAll('.modalLayer');
        for (var i = 0; i < elems.length; i++) {
            console.log("cmd,hiding modalLayer ---" + elems[i].id);
            hideDivAy(elems[i].id);
        }




        listAsPages(this.params['module'],
                'api/' + this.params['module'] +
                '/pageable?pageNumber=' + this.params['pageNumber']
                +
                '&filterColumn=' + this.params['filterColumn'] +
                '&filter=' + this.params['filter'] +
                '&sortColumn=' + this.params['sortColumn'] +
                '&sortOrder=' + this.params['sortOrder']

                , 'main_1');

    }

}


function cmd_post(context, data) {
//    console.log("akr #/cmd/post=" + this);
//                    alert('a data' + data);
    var module_direct = this.params['module_direct'];
    var div = this.params['div'];

    if (div === undefined) {
        div = 'main_1';
    }
    var redirect = this.params['redirect'];
    if (module_direct !== undefined) {
        module = module_direct;
    }
    var d = new FormData(document.getElementById('form_' + module));
    console.log("akr api_Posting : " + JSON.stringify(d));

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: '/clinicPlus/api/' + module + '',
        beforeSend: beforeSend_authorize,
        data: d,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success:
                function (data, textStatus, jqXHR)
                {
//                    console.log(" jqXHR=" + JSON.stringify(jqXHR));
                    console.log("cmd post success, module=" +
                            module + ", data=" + JSON.stringify(data));
                    var path = "#/cmd" + pageNewAy(1) + "&module=" + module + "&action=/all/list&div=" + div;
                    console.log('ok redirect to ' + path);
                    if (data.status === 'success') {
                        alert_1('Done :)', data.message, data.status);
                        if (mainLayerNumberNow === 1) {
                            window.history.back();
                        }
//                        window.location.href = path;
                    } else {
                        alert_1('Sorry :(', data.message, data.status);
//                        window.history.back();
                    }
//                    if (jqXHR.getResponseHeader('ok') === 'no') {
//                        alert_1(jqXHR.getResponseHeader('problem'),
//                                JSON.stringify(data), 'error');
//
//                    } else {
//                        alert_1('OK', jqXHR.getResponseHeader('ok'), 'success');
//                        mn.module[module] = data;
//                        console.log("post.done module=" + module + ", data=" + JSON.stringify(data));
//                    }
                }

        ,
        error: function (data) {

            alert_1('ERROR', JSON.stringify(data), 'error');

        }
    });

}

function file_i(context) {

    if (this.params['div'] === undefined) {
        this.params['div'] = 'main_1';
    }

    var path = this.params['path'];
    id = this.params['id'];

    if (('#' + this.params['div']) === context.app.element_selector) {



        var elems = document.querySelectorAll('.modalLayer');
        for (var i = 0; i < elems.length; i++) {
            console.log("file_i,hiding modalLayer ---" + elems[i].id);
            hideDivAy(elems[i].id);
        }

        var pathUpdated = '/clinicPlus/' + path + pageNewAy(1);
        document.getElementById('main_1_menu').innerHTML = "";
        document.getElementById('main_1_inner').innerHTML = "";
        document.getElementById('main_1_paging').innerHTML = "";

        console.log("#/file , pathUpdated=" + pathUpdated);
        $.getScript(pathUpdated);

    } else {
        console.log("#/file not matches div");

    }
}

