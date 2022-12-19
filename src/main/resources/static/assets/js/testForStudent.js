let testArea = document.querySelector("#studentTests")
window.onload = function () {

    var settings = {
        "url": "http://localhost:8080/student/test",
        "method": "GET",
        "headers": {
            "Content-Type": "application/json"
        }
    };
    $.ajax(settings).done(function (response) {
        console.log(response)
        for (let i = 0; i < response.length; i++) {
            createStudentTestBlock(response[i])
        }
    });
}

function createStudentTestBlock(data) {
    let div = document.createElement("div")
    div.classList.add("card-body")
    div.innerHTML = "" +
        "                                <div class=\"d-flex align-items-center mb-2 mt-2\">\n" +
        "                                    <div class=\"card-icon rounded-circle d-flex align-items-center justify-content-center\" style=\"" + (!data["available"] ? "color: #bcbebf;" : "") + "\">\n" +
        "                                    " + (data["available"] ? ("<a class=\"icon\" href=\"" + "/student/test/" + data["id"] + "\"> <i class=\"bi bi-card-checklist\"></i></a>\n")
            : ("<i class=\"bi bi-card-checklist\"></i> ")) +
        "                                    </div>\n" +
        "                                    <div class=\"ps-3\">\n" +
        "                                        <h6 style=\"" + (!data["available"] ? "color: #bcbebf;" : "") + "\" > " + data["title"] + "</h6>\n" +
        "                                    </div>\n" +
        "                                </div>"
    testArea.appendChild(div)

}
