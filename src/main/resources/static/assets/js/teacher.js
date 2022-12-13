let testMenu = document.querySelector('#showTest')
console.log(testMenu)
let testSelect = testMenu.querySelector('.form-select')
window.onload = function () {
    var settings = {
        "url": "http://localhost:8080/tests?projection=testPreview",
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
            "url": "http://localhost:8080/tests/" + selectedOption.value + "?projection=fullTest",
            "method": "GET",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            console.log(response)
            let test = response
            showTest(test)
        });
    } else {
    }
})

function showTest(response) {
    document.querySelector('#questionArea').innerHTML = ''
    console.log(response.questions)
    let questions = response.questions
    for (let i = 0; i < questions.length; i++) {
        showQuestionInTest(questions[i])
    }
}

function showQuestionInTest(question) {
    let questionEl = document.createElement('div')
    questionEl.classList.add("singleQuestion")
    if (question.media.length!==0) {
        questionEl.innerHTML = "<h5 class=\"card-title question-title\">" + question.title + "</h5>" +
            "<div id=\"carouselExampleControls\" class=\"carousel slide pointer-event\" data-bs-ride=\"carousel\">\n" +
            "                                        <div class=\"carousel-inner\">\n" +
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
            "\n" +
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
    }
    document.querySelector('#questionArea').appendChild(questionEl)
}