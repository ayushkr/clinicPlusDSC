
updateCurrentDate('dateCurrent');

function  routeFunctions() {
    console.log('sammy all ');
    this.debug = true;
    this.get('#/', function () {
//        this.app.swap('Click form!');
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
    var sammy_main1 = $.sammy('#main1_inner', routeFunctions);
    var sammy_main2_inner = $.sammy('#main_2', routeFunctions);
    var sammy_main3_inner = $.sammy('#main_3', routeFunctions);
    var sammy_main4_inner = $.sammy('#main_4', routeFunctions);
    var sammy_main5_inner = $.sammy('#main_5', routeFunctions);
     var sammy_main6_inner = $.sammy('#main_6', routeFunctions);




    $(function () {
        //sammy_navbar.run('#/');
        sammy_main1.run('#/');
        sammy_main2_inner.run('main_2');
        sammy_main3_inner.run('main_3');
        sammy_main4_inner.run('main_4');
        sammy_main5_inner.run('main_5');
         sammy_main6_inner.run('main_6');
    });
})(jQuery);


function postFile(context, data) {
    console.log("akr #/postFile=" + this);
}

function dummy(context) {
    console.log("dummy(context)-----------");

}
var ayu;
function cmd(context) {
    console.log("cmd-----------");
    ayu = context;


    var params_ = Object.getOwnPropertyNames(this.params);
    for (var i = 0; i < params_.length; i++) {
        console.log("param_ ----------" + params_[i] + ":" + this.params[params_[i]]);
    }

    var elems = document.querySelectorAll('.modalLayer');
    for (var i = 0; i < elems.length; i++) {
        console.log("modalLayer ----------" + elems[i].id);
        hideDivAy(elems[i].id);
    }




    listAsPages(this.params['module'],
            'api/' + this.params['module'] +
            '/pageable?pageNumber=' + this.params['pageNumber'] +
            '&filterColumn=' + this.params['filterColumn'] +
            '&filter=' + this.params['filter'] +
            '&sortColumn=' + this.params['sortColumn'] +
            '&sortOrder=' + this.params['sortOrder']

            , 'main1');

}


function cmd_post(context, data) {
//    console.log("akr #/cmd/post=" + this);
//                    alert('a data' + data);
    var module_direct = this.params['module_direct'];
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
                    var path = "#/cmd?module=" + module + "&action=/all/list" + pageNewAy(1);
                    console.log('redirect to ' + path);
                    if (data.status === 'success') {
                        alert_1('Done :)', data.message, data.status);
                        window.history.back();
                    } else {
                        alert_1('Sorry :(', data.message, data.status);

                    }


//                    if (jqXHR.getResponseHeader('ok') === 'no') {
//                        alert_1(jqXHR.getResponseHeader('problem'),
//                                JSON.stringify(data), 'error');
//
//                    } else {
//                        alert_1('OK', jqXHR.getResponseHeader('ok'), 'success');
//                        mn.module[module] = data;
//                        console.log("post.done module=" + module + ", data=" + JSON.stringify(data));
//
//                        
//
//                    }




                }

        ,
        error: function (data) {

            alert_1('ERROR', JSON.stringify(data), 'error');

        }
    });

}

function file_i(context) {

    var path = this.params['path'];
    id = this.params['id'];

    mn.module[module] = {"id": id};
    mn.module.current = module;

    var partial_path = '/clinicPlus/' + path + '?' + pageNewAy(1);
    document.getElementById('main1_menu').innerHTML = "";

    console.log("#/file , partial=" + partial_path);
    this.partial(partial_path);
}

