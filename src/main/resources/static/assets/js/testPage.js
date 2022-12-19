let studentTest = document.querySelector("#studentTest")
console.log(studentTest)
window.onload = function () {
    let url = window.location.href.split('/')
    let id = url[url.length - 1]
    var settings = {
        "url": "http://localhost:8080/tests/" + id + "?projection=testPreview",
        "method": "GET",
        "headers": {
            "Content-Type": "application/json"
        }
    };
    $.ajax(settings).done(function (response) {

        studentTest.querySelector('span').innerHTML = "| " + response.title
        let startButton = studentTest.querySelector('.btn-info')
        startButton.addEventListener('click', function () {
            var settings = {
                "url": "http://localhost:8080/questions/search/inTest?id=" + response.id + "&projection=withoutAnswer,",
                "method": "GET",
                "headers": {
                    "Content-Type": "application/json"
                }
            };
            $.ajax(settings).done(function (response) {
                console.log(response)
                let questions = response._embedded.questions
                studentTest.style.display='none'
                for (let i = 0; i < questions.length; i++) {
                    createQuestion(questions[i])
                }
                document.querySelector('#questionArea').innerHTML+="<div class=\"d-flex justify-content-around\" id=\"endTest\">\n" +
                    "        <button type=\"button\" class=\"btn btn-success \" data-bs-toggle=\"modal\"\n" +
                    "                                                data-bs-target=\"#endTest\">Закончить тест</button>\n" +
                    "    </div>"
            })

        })
    });
}

function createQuestion(question) {

    let questionEl = document.createElement('div')
    questionEl.classList.add("singleQuestion", "card-body")

    questionEl.innerHTML = "<h5 class=\"card-title mb-3\">" + question.title + "</h5>\n" +
        "                                <div class=\"row justify-content-around \">\n" +
        "\n" +
        "                                    <div id=\"question-" + question.id + "\" class=\"carousel slide col-6 mb-3\"\n" +
        "                                         data-bs-ride=\"carousel\">\n" +
        "                                        <div class=\"carousel-inner\">\n" +
        "                                        </div>\n" +
        "\n" +
        "                                        <button class=\"carousel-control-prev\" type=\"button\"\n" +
        "                                                data-bs-target=\"#question-" + question.id + "\" data-bs-slide=\"prev\">\n" +
        "                                            <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>\n" +
        "                                            <span class=\"visually-hidden\">Previous</span>\n" +
        "                                        </button>\n" +
        "                                        <button class=\"carousel-control-next\" type=\"button\"\n" +
        "                                                data-bs-target=\"#question-" + question.id + "\" data-bs-slide=\"next\">\n" +
        "                                            <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>\n" +
        "                                            <span class=\"visually-hidden\">Next</span>\n" +
        "                                        </button>\n" +
        "                                    </div>\n"

    let mediaArea = questionEl.querySelector('.carousel-inner')
    for (let i = 0; i < question.media.length; i++) {
        let image = document.createElement('div')
        image.classList.add("carousel-item")
        if (i === 0) {
            image.classList.add("active")
        }
        image.innerHTML = "                                                <img src=\"/image/" + question.media[i].id + "\" class=\"d-block w-100\" alt=\"...\">\n"
        mediaArea.appendChild(image)
    }
    let answers = question.answers
    for (let i = 0; i < answers.length; i++) {
        let answer = document.createElement('div')
        answer.classList.add("row", "mb-3", "align-items-center", "justify-content-start")
        answer.innerHTML = "                                        <div class=\" col-sm-auto\">\n" +
            "                                            <input class=\"form-check-input check\" type=\"radio\"\n" +
            "                                                   name=\"gridRadios\"\n" +
            "                                                   value=\"option1\">\n" +
            "                                        </div>\n" +
            "                                        <label class=\"col-sm-auto col-form-label\">" + answers[i] + "</label>\n"
        questionEl.appendChild(answer)

    }


    document.querySelector('#questionArea').appendChild(questionEl)

}

