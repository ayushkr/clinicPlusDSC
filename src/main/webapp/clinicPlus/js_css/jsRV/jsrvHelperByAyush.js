// item{name:"url" ,selector:"commonArea" }
console.log("jsrvHelperByAyush");
var ayrender = function (item) {
    //  console.log("ayrender item= " + JSON.stringify(item));
    $.ajax(item.name,
            {
                dataType: 'json', // type of response data
                timeout: 500, // timeout milliseconds
                type: 'GET',
                async: false,
                success: function (data, status) {
                    // console.log("jsrender "+JSON.stringify(data));
                    rend(data);
                }
            }
    );



    var rend = function (tmplData) {
//        console.log("got tmplData" + JSON.stringify(tmplData));
        // console.log("name=" + item.name);
//        console.log("SELECTOR " + item.selector);
//        console.log("data" + JSON.stringify(item.data));
        $.templates({tmpl: tmplData});
        
       var res= $.render.tmpl(item.data);
       console.log("rendered res=" + res);
        $(item.selector).html(res);
    };
};

var link_ay = function (templateBody, item) {
//    console.log("link_ay,templateBody---->  \n" + templateBody);
//    console.log("link_ay, item.data ---> \n " + JSON.stringify(item.data));
//    console.log("link_ay , item.selector --->  " + item.selector);

    var templateObj = $.templates(templateBody);
//    console.log("templateObj=",  JSON.stringify(templateObj));
    templateObj.link("#" + item.selector, item.data);
//    console.log('link_ay finish');
    try {
        afterLinking();
    } catch (err) {
//        console.log('afterLinking nt available here');
    }

};

var templateBodyG = "";

var aylinker = function (item) {
    //   console.log("aylinker item= " + JSON.stringify(item));

    $.ajax(item.urlOfTemplate,
            {

                type: 'GET',
                async: false,
                success:
                        function (templateBody, status) {
                            //console.log("item.urlOfTemplate ---> \n" + item.urlOfTemplate);
                            templateBodyG = templateBody;
                            link_ay(templateBodyG, item);
                        }
            }
    );

};


var ayRenderNoSelector = function (item) {
var reso;
   $.ajax(item.urlOfTemplate,
            {

                type: 'GET',
                async: false,
                success:
                        function (templateBody, status) {
                         //   console.log("item.urlOfTemplate ---> \n" + item.urlOfTemplate);
                         //  console.log("templateBody ---> \n" + templateBody);
                            $.templates({tmpl: templateBody});
                           var result= $.render.tmpl(item.data);
                         //    console.log("result="+result);
                             reso=result;
                        }
            }
    );
    
    return reso;

};
