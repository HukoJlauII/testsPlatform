let testMenu = document.querySelector('#showTest')
console.log(testMenu)
let testSelect = testMenu.querySelector('.form-select')
window.onload = function () {
    var settings = {
        "url": "http://localhost:8080/tests?projection=testPreview&size=100",
        "method": "GET",
        "timeout": 0,
    };
    $.ajax(settings).done(function (response) {
        let tests = response._embedded.tests
        console.log(tests)
        for (let i = 0; i < tests.length; i++) {
            let testOption = document.createElement('option')
            testOption.innerHTML = tests[i].title
            testOption.value = tests[i].id
            testSelect.appendChild(testOption)
        }
    });
}
testSelect.addEventListener('change', function () {
    let selectedOption = testSelect.options[testSelect.selectedIndex]
    console.log(selectedOption)
    if (selectedOption.value !== "0") {
        var settings = {
            "url": "http://localhost:8080/tests/" + selectedOption.value + "?projection=fullTest&size=100",
            "method": "GET",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            showTest(response)
        });
    } else {
    }
})

function showTest(response) {
    document.querySelector('#questionArea').innerHTML = ''
    console.log(response.questions)
    let questions = response.questions
    for (let i = 0; i < questions.length; i++) {
        console.log(questions[i])
        showQuestionInTest(questions[i])
    }
    document.querySelector('#questionArea').innerHTML += "<div class=\"d-flex justify-content-around\" id=\"deleteTest\">\n" +
        "        <button type=\"button\" class=\"btn btn-danger \" data-bs-toggle=\"modal\"\n" +
        "                                                data-bs-target=\"#deleteConfirm\">Удалить тест</button>\n" +
        "    </div>"

    let deleteButton = document.querySelector('#deleteConfirm').querySelector('.btn-danger')
    let newNode = deleteButton.cloneNode()
    newNode.innerHTML = 'Удалить тест'
    deleteButton.parentNode.appendChild(newNode)
    deleteButton.parentNode.removeChild(deleteButton)
    newNode.addEventListener('click', function () {
        var settings = {
            "url": "http://localhost:8080/tests/" + response.id,
            "method": "DELETE",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            document.querySelector('#questionArea').innerHTML = "<div class=\"card-body\">\n" +
                "                                <h5 class=\"card-title mb-3\">Пример заголовка вопроса</h5>\n" +
                "                                <div class=\"row justify-content-around singleQuestion\">\n" +
                "\n" +
                "                                    <div id=\"carouselExampleControls\" class=\"carousel slide col-6 mb-3\"\n" +
                "                                         data-bs-ride=\"carousel\">\n" +
                "                                        <div class=\"carousel-inner\">\n" +
                "                                            <div class=\"carousel-item active\">\n" +
                "                                                <img src=\"assets/img/slides-2.jpg\" class=\"d-block w-100 m-0\"\n" +
                "                                                     alt=\"...\">\n" +
                "                                            </div>\n" +
                "                                            <div class=\"carousel-item\">\n" +
                "                                                <img src=\"assets/img/slides-3.jpg\" class=\"d-block w-100 m-0\"\n" +
                "                                                     alt=\"...\">\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "\n" +
                "                                        <button class=\"carousel-control-prev\" type=\"button\"\n" +
                "                                                data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"prev\">\n" +
                "                                            <span class=\"carousel-control-prev-icon\" aria-hidden=\"true\"></span>\n" +
                "                                            <span class=\"visually-hidden\">Previous</span>\n" +
                "                                        </button>\n" +
                "                                        <button class=\"carousel-control-next\" type=\"button\"\n" +
                "                                                data-bs-target=\"#carouselExampleControls\" data-bs-slide=\"next\">\n" +
                "                                            <span class=\"carousel-control-next-icon\" aria-hidden=\"true\"></span>\n" +
                "                                            <span class=\"visually-hidden\">Next</span>\n" +
                "                                        </button>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"row mb-3 align-items-center justify-content-start\">\n" +
                "                                        <div class=\" col-sm-auto\">\n" +
                "                                            <input class=\"form-check-input check\" type=\"radio\"\n" +
                "                                                   name=\"gridRadios\"\n" +
                "                                                   value=\"option1\">\n" +
                "                                        </div>\n" +
                "                                        <label class=\"col-sm-auto col-form-label\">Вариант 1</label>\n" +
                "\n" +
                "                                    </div>\n" +
                "                                    <div class=\"row mb-3 align-items-center justify-content-start\">\n" +
                "                                        <div class=\" col-sm-auto\">\n" +
                "                                            <input class=\"form-check-input check\" type=\"radio\"\n" +
                "                                                   name=\"gridRadios\"\n" +
                "                                                   value=\"option1\">\n" +
                "                                        </div>\n" +
                "                                        <label class=\"col-sm-auto col-form-label\">Вариант 2</label>\n" +
                "\n" +
                "                                    </div>\n" +
                "                                    <div class=\"row mb-3 align-items-center justify-content-start\">\n" +
                "                                        <div class=\" col-sm-auto\">\n" +
                "                                            <input class=\"form-check-input check\" type=\"radio\"\n" +
                "                                                   name=\"gridRadios\"\n" +
                "                                                   value=\"option1\">\n" +
                "                                        </div>\n" +
                "                                        <label class=\"col-sm-auto col-form-label\">Вариант 3</label>\n" +
                "\n" +
                "                                    </div>\n" +
                "                                    <div class=\"row mb-3 align-items-center justify-content-start\">\n" +
                "                                        <div class=\" col-sm-auto\">\n" +
                "                                            <input class=\"form-check-input check\" type=\"radio\"\n" +
                "                                                   name=\"gridRadios\"\n" +
                "                                                   value=\"option1\">\n" +
                "                                        </div>\n" +
                "                                        <label class=\"col-sm-auto col-form-label\">Вариант 4</label>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>"
            testSelect.options[testSelect.selectedIndex].parentNode.removeChild(testSelect.options[testSelect.selectedIndex])
            testSelect.options[0].selected = 'selected'
            document.querySelector('#deleteConfirm').querySelector('.btn-secondary').click()

        });
    })
}

function showQuestionInTest(question) {
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

