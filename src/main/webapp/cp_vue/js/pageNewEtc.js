 function pageNewAy(status) {

        if (status === 1) {
            return "?refreshId=" + Math.random();
        } else {
            return "?refreshId=0";
        }

    };

    function aylinker(o){
        console.log(o);

    }