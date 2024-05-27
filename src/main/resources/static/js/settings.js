

function sendFormLR(key, checked) {
    const data = {
        'key': key,
        'value': checked
    }
    axios.post('/admin/settings/form', data).then( response =>
        console.log('success:' + response.data))
}

function sendDocumentsAcceptance() {
    let startDate = document.getElementById("date-input-start1").value;
    let startTime = document.getElementById("example-time-start1").value;
    let endDate = document.getElementById("date-input-end1").value;
    let endTime = document.getElementById("example-time-end1").value;


    // Формирование объекта с данными
    let data = {
        "key": "documentsAcceptance",
        "startDateTime": startDate + " " + startTime,
        "endDateTime": endDate + " " + endTime
    };
    console.log(data)
    axios.post('/admin/settings/dates', data).then( response =>
        console.log('success:' + response.data))

}

function sendAdmissionExam() {
    let startDate = document.getElementById("date-input-start2").value;
    let startTime = document.getElementById("example-time-start2").value;
    let endDate = document.getElementById("date-input-end2").value;
    let endTime = document.getElementById("example-time-end2").value;


    // Формирование объекта с данными
    let data = {
        "key": "admissionExam",
        "startDateTime": startDate + " " + startTime,
        "endDateTime": endDate + " " + endTime
    };
    console.log(data)
    axios.post('/admin/settings/dates', data).then( response =>
        console.log('success:' + response.data))

}

function sendExaminationVerification () {
    let startDate = document.getElementById("date-input-start3").value;
    let startTime = document.getElementById("example-time-start3").value;
    let endDate = document.getElementById("date-input-end3").value;
    let endTime = document.getElementById("example-time-end3").value;


    // Формирование объекта с данными
    let data = {
        "key": "examinationVerification",
        "startDateTime": startDate + " " + startTime,
        "endDateTime": endDate + " " + endTime
    };
    console.log(data)
    axios.post('/admin/settings/dates', data).then( response =>
        console.log('success:' + response.data))

}


// access((authorizationContext, object) -> new AuthorizationDecision(
//     !settingsService.getSettings().isFormLogin() && authorizationContext.get().getAuthorities().stream().anyMatch(authority ->
//         authority.getAuthority().equals("ROLE_STUDENT") ||
//         authority.getAuthority().equals("ROLE_RECTOR") ||
//         authority.getAuthority().equals("ROLE_TEACHER")))
// )
