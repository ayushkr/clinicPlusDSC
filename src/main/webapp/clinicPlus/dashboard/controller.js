
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
    var sammy_main2_inner = $.sammy('#main2_inner', routeFunctions);
    var sammy_main3_inner = $.sammy('#main3_inner', routeFunctions);





    $(function () {
        //sammy_navbar.run('#/');
        sammy_main1.run('#/');
        sammy_main2_inner.run('main2');
        sammy_main3_inner.run('main3');
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
    listAsPages(this.params['module'],
            'api/' + this.params['module'] +
            '/pageable?pageNumber=' + this.params['pageNumber'] +
            '&filterColumn=' + this.params['filterColumn'] +
            '&filter=' + this.params['filter'] +
            '&sortColumn=' + this.params['sortColumn'] +
            '&sortOrder=' + this.params['sortOrder']

            , 'main1');



    // this.partial(partial_path);
}


function cmd_post(context, data) {
    console.log("akr #/cmd/post=" + this);
//                    alert('a data' + data);
    var module_direct = this.params['module_direct'];
    var redirect = this.params['redirect'];
    if (module_direct !== undefined) {
        module = module_direct;
    }
    
     var d = new FormData(document.getElementById('form_'+module));

    
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


                    if (jqXHR.getResponseHeader('ok') === 'no') {
                        alert_1(jqXHR.getResponseHeader('problem'), JSON.stringify(data), 'error');

                    } else {
                        alert_1('OK', jqXHR.getResponseHeader('ok'), 'success');
                        mn.module[module] = data;
                        console.log("post.done module=" + module + ", data=" + JSON.stringify(data));

                        var path = "#/cmd?module=" + module + "&action=/all/list" + pageNewAy(1);
                        console.log('redirect to ' + path);


                    }

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

