var resultDummy = {
    module: '',
    pageList: ''
};

var vue_status = new Vue({
    el: '#div_api_float_status',
    data: {
        ready: false,
        display: false,
        style: "",
        module: 'appointment',
        result: {
        },
        z: function (v) {
            this.style = "z-index:" + v;
        }
    }
});


var vue_pageable_actions = new Vue({
    el: '#div_pageable_actions',
    data: {
        display: false,
        button: {
            display: true,
            select: true,
            delete: true,
            edit: true,
            print: true


        },
        mode: 'entity',
        selectionMode: {
            display: true,
            for : null,
            back: null
        }


    },
    methods: {
        backTo: function () {
            
            console.log(JSON.stringify(this.selectionMode));
            var p=st.pop();
            vue_edit.result=p.for;
            console.log("popped ="+JSON.stringify(p));
        }

    }

});


var vue_pageable = new Vue({
    el: '#div_api_pageable',
    data: {
        ready: false,
        settings_column_visibility: false,
        result: resultDummy
    }
});


var vue_edit = new Vue({
    el: '#div_api_edit',
    data: {
        ready: false,
        result: null
    }
});

var vue_bill = new Vue({
    el: '#div_api_bill',
    data: {
        ready: false,
        result: {showBillMetaData: true}
    }
});

