
document.getElementById("main_1_paging").innerHTML = 'ok';
aylinker({
    // urlOfTemplate: "/clinicPlus/module/" +mn.module.current+ "/all/list/template1.html?ran=" + Math.random(),
    urlOfTemplate: "/clinicPlus/module/reports/patientsByDoctor/list/listTemplate1.html?ran=" + Math.random(),
    selector: "main_1_inner",
    data: {obj: ''}
}
);