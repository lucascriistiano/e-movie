var daysWeek = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return '';
}

function getFormattedTime(dateString) {
    var date = new Date(dateString);
    var hours = date.getHours();
    if(hours < 10) {
        hours = '0' + hours;
    }

    var minutes = date.getMinutes();
    if(minutes < 10) {
        minutes = '0' + minutes;
    }

    return hours + ':' + minutes;
}

function getFormattedDate(dateString) {
    var date = new Date(dateString);
    var strDate = date.getDate() + '/' + (date.getMonth()+1) + '/' + date.getFullYear();
    return strDate;
}