var currentLocation = document.location.protocol + "//" + document.location.host;
var searchLine = document.querySelector(".dataTable-input");
var dayValue = document.querySelector("#day");
var weekValue = document.querySelector("#week");
const div = document.querySelector("#table");

document.querySelector("#searchTimeTable").addEventListener('click', searchTimeTableForGroup)

function searchTimeTableForGroup() {
    if (searchLine.value.match("^[А-Яа-я]{4}-[0-9]{2}-[0-9]{2}")) {
        let newSearchLineValueGroup = searchLine.value.split('-')[0].toUpperCase() + '-' + searchLine.value.split('-')[1] + "-" + searchLine.value.split('-')[2];
        createAjaxQuery("/getTimeTable", {
            searchLine: newSearchLineValueGroup,
            day: Number(dayValue.value),
            week: Number(weekValue.value)
        }, successFindGroup)
    } else {
        alert("Введите по шаблону: XXXX-XX-XX")
    }


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

function successFindGroup(data, xhr, statusCode) {
    console.log(data)
    console.log(xhr)
    console.log(statusCode)
    if (statusCode.status===200) {
        const arr = ['9:00 - 10:30', '10:40 - 12:10', '12:40 - 14:10', '14:20 - 15:50', '16:20 - 17:50', '18:00 - 19:30', '19:40 - 21:00']

        div.innerHTML = '';
        for (let i = 0; i < data.length; i++) {
            const table = document.createElement("tr");
            let code = "";
            i++;
            code += "<tr>\n";
            code += "<th scope=\"row\"><a href=\"#\">" + i + "</a></th>\n";
            i--;
            code += "<td>" + arr[i] + "</td>\n";
            code += "<td><a href=\"#\" class=\"text-primary\">" + data[i].title + "</a></td>\n";
            code += "<td>" + data[i].type + "</td>\n";
            code += "<td>" + data[i].audit + "</td>\n";
            code += "<td><span class=\"badge bg-success\">" + data[i].teacher + "</span></td>\n";
            code += "</tr>\n";
            console.log(code)
            table.innerHTML = code;
            div.appendChild(table);
        }
    }
    else
    {
        alert("Такой группы не существует")
    }
}