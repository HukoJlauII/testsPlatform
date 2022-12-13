var currentLocation = document.location.protocol + "//" + document.location.host;
var searchLine = document.querySelector(".dataTable-input");
var dayValue = document.querySelector("#day");
var weekValue = document.querySelector("#week");

document.querySelector("#searchTimeTable").addEventListener('click', searchTimeTableForGroup)

function searchTimeTableForGroup() {
    createAjaxQuery("/getTimeTable", {searchLine: searchLine.value, day: Number(dayValue.value), week: Number(weekValue.value)}, successFindGroup)
}

// Создание запросов
function createAjaxQuery(url, data, toFunction) {
    console.log(currentLocation + url);
    jQuery.ajax({
        type: 'POST',
        url: currentLocation + url,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: toFunction
    });
}

function successFindGroup(data) {
    console.log(data)
}