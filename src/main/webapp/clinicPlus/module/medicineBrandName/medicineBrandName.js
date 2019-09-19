
console.log('medicineBrandName.js');


setTimeout(function () {


    var inputsFilters = document.querySelectorAll('[data-href]');
    if (inputsFilters[0] !== undefined) {
        inputsFilters[0].focus();
    }
    for (var i = 0; i < inputsFilters.length; ++i) {
        console.log('inp=' + inputsFilters[i].name);
        if (mn.temp[inputsFilters[i].name] !== undefined) {
            inputsFilters[i].value = mn.temp[inputsFilters[i].name];
        }
    }



}, 200);



