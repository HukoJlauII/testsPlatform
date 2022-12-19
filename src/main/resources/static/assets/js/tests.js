let testModal = document.querySelector('#createTest')
let testBody = testModal.querySelector('.modal-body')
let showCreateTestModalButton = document.querySelector('#createTestArea')
showCreateTestModalButton.addEventListener('click', showTestInfo)
let createTestButton = testModal.querySelector('.btn-primary')
let closeModalButton = testModal.querySelector('.btn-secondary')
createTestButton.addEventListener('click', createTest)


function showTestInfo() {
    getAllQuestionsPreview()
    getAllTestsPreview()
}

function createTest() {
    let title = testBody.querySelector('textarea')
    if (title.value === "") {
        title.classList.add('is-invalid')
        title.classList.remove('is-valid')
        return
    } else {
        title.classList.add('is-valid')
        title.classList.remove('is-invalid')
    }
    let checkBoxArea = testBody.querySelector('.checksArea')
    let checks = checkBoxArea.querySelectorAll('input')
    let selectArea = testBody.querySelector('.form-select')
    let questions = []
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].checked) {
            questions.push(
                "http://localhost:8080/questions/" + checks[i].value
            )
        }
    }
    let selectedTest = selectArea.options[selectArea.selectedIndex]
    console.log(selectedTest.value)

    let previousTest = selectedTest === '0' ? null : "http://localhost:8080/tests/" + selectedTest.value

    let test = {
        title: title.value,
        questions: questions,
        previousTest: previousTest
    }

    console.log(test)
    var settings = {
        "url": "http://localhost:8080/tests",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify(test),
    };
    $.ajax(settings).done(function (response) {
        closeModalButton.click()
    });
}

function checkCorrectTest() {
    let activeCount = 0
    let checkBoxArea = testBody.querySelector('.checksArea')
    let questions = checkBoxArea.querySelectorAll('input')
    for (let i = 0; i < questions.length; i++) {
        if (questions[i].checked) {
            activeCount++
        }
    }
    if (activeCount >= 5) {
        createTestButton.style.display = "block"
    } else {
        createTestButton.style.display = "none"
    }
}

function getAllQuestionsPreview() {
    var settings = {
        "url": "http://localhost:8080/questions?projection=questionPreview?size=100",
        "method": "GET",
        "timeout": 0,
    };
    $.ajax(settings).done(function (response) {
        let checkBoxArea = testBody.querySelector('.checksArea')
        checkBoxArea.innerHTML = ''
        let questions = response._embedded.questions
        for (let i = 0; i < questions.length; i++) {
            let question = document.createElement('div')
            question.classList.add("form-check")
            question.innerHTML = "                                                                    <label class=\"form-check-label\">\n" +
                "                                                                        " + questions[i].title +
                "                                                                    </label>" +
                "                                                                    <input class=\"form-check-input\" type=\"checkbox\" value='" + questions[i].id + "' id=\"gridCheck" + questions[i].id + "\">\n"

            question.querySelector('input').addEventListener('click', checkCorrectTest)
            checkBoxArea.appendChild(question)
        }
        checkCorrectTest()
    });
}

function getAllTestsPreview() {
    var settings = {
        "url": "http://localhost:8080/tests?projection=testPreview&size=100",
        "method": "GET",
        "timeout": 0,
    };
    $.ajax(settings).done(function (response) {
        let selectArea = testBody.querySelector('.form-select')
        selectArea.innerHTML = "<option value=\"0\" selected>Без теста</option>"
        let tests = response._embedded.tests
        for (let i = 0; i < tests.length; i++) {
            let test = document.createElement('option')
            test.value = tests[i].id
            test.innerHTML = tests[i].title
            selectArea.appendChild(test)
        }
    });
}

// "<div class=\"form-check\">\n" +
// "                                                                    <input class=\"form-check-input\" type=\"checkbox\" id=\"gridCheck1\">\n" +
// "                                                                    <label class=\"form-check-label\" for=\"gridCheck1\">\n" +
// "                                                                        Example checkbox\n" +
// "                                                                    </label>\n" +
// "                                                                </div>"