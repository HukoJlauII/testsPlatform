let createQuestionModal = document.querySelector('#largeModal')
let modal = createQuestionModal.querySelector('.modal-body')
let inputs = modal.querySelectorAll('.form-control')
let checks = modal.querySelectorAll('.form-check-input')
let modalFooter = createQuestionModal.querySelector(".modal-footer")
let cancelButton = modalFooter.querySelector('.btn-secondary')
let sendButton = modalFooter.querySelector('.btn-primary')
sendButton.addEventListener('click', createQuestion)
let showQuestionButton = document.querySelector('#showQuestion')
let questionsMedia = document.querySelector('.carousel-inner')
showQuestionButton.addEventListener('click', showAllQuestions)
let questionArea = document.querySelector('#questions')
let changeQuestionArea = document.querySelector('#changeQuestion')
let editQuestionButton = changeQuestionArea.querySelector('.btn-secondary')
let deleteQuestionButton = changeQuestionArea.querySelector('.btn-danger')

function createQuestion() {
    let answers = []

    for (let i = 0; i < inputs.length; i++) {
        if (i !== 1 && inputs[i].value === "") {
            inputs[i].classList.add('is-invalid')
            inputs[i].classList.remove('is-valid')
            return
        } else {
            inputs[i].classList.add('is-valid')
            inputs[i].classList.remove('is-invalid')
        }
        if (i > 1) {
            answers.push(inputs[i].value)
        }
    }
    let rightAnswer
    for (let i = 0; i < checks.length; i++) {
        if (checks[i].checked) {
            rightAnswer = i
        }
    }
    console.log(rightAnswer)
    let title = inputs[0].value
    let files = inputs[1].files
    let question = {
        title: title,
        answers: answers,
        rightAnswer: answers[rightAnswer],
    }
    let formData = new FormData();
    for (let i = 0; i < files.length; i++) {
        formData.append("file", files[i]);
    }
    formData.append("question", JSON.stringify(question))

    console.log(question)
    fetch("http://localhost:8080/question/create", {
        method: "POST",
        body: formData,
    }).then((response) => {
        let data = response.json().then(async function (value) {
            console.log(value)
            return value
        })
    })
    for (let i = 0; i < inputs.length; i++) {
        if (i === 1) {
            inputs[i].value = null
        } else {
            inputs[i].value = ''
        }
        inputs[i].classList.remove('is-invalid', 'is-valid')
    }
    checks[0].checked = true
    cancelButton.click()
}

function showAllQuestions() {
    var settings = {
        "url": "http://localhost:8080/question/all",
        "method": "GET",
        "timeout": 0,
    };

    $.ajax(settings).done(function (response) {
        questionArea.style.display = 'block'
        console.log(response);
        editQuestionButton.style.display = 'none'
        deleteQuestionButton.style.display = 'none'
        let questionSelect = questionArea.querySelector('.form-select')
        questionSelect.innerHTML = "<option value=\"0\" selected>Выберите вопрос</option>"
        console.log(questionSelect)
        for (let i = 0; i < response.length; i++) {
            let questionOption = document.createElement('option')
            questionOption.innerHTML = response[i].title
            questionOption.value = response[i].id
            questionSelect.appendChild(questionOption)
        }
        questionSelect.addEventListener('change', function () {
            let selectedOption = questionSelect.options[questionSelect.selectedIndex]

            if (selectedOption.value !== "0") {
                var settings = {
                    "url": "http://localhost:8080/questions/" + selectedOption.value,
                    "method": "GET",
                    "timeout": 0,
                };
                $.ajax(settings).done(function (response) {
                    showQuestionPreview(response)
                });
                editQuestionButton.style.display = 'block'
                deleteQuestionButton.style.display = 'block'
            } else {
                editQuestionButton.style.display = 'none'
                deleteQuestionButton.style.display = 'none'
            }
        })
    });
}

function showEditForm(response) {
    let editQuestionArea = document.querySelector('#editQuestion')
    let submitEditButton = editQuestionArea.querySelector('.btn-primary')
    editQuestionArea.querySelector('#formFile1').value = null
    let oldButton = submitEditButton
    let parentButton = submitEditButton.parentNode
    submitEditButton = submitEditButton.cloneNode()
    submitEditButton.innerHTML = oldButton.innerHTML
    parentButton.removeChild(oldButton)
    parentButton.appendChild(submitEditButton)
    let body = editQuestionArea.querySelector('.modal-body')
    let title = body.querySelector('textarea')
    title.value = response.title
    let answers = body.querySelectorAll('.text')
    let checks = body.querySelectorAll('.check')
    document.querySelector('#editPhotos').innerHTML = ''
    for (let i = 0; i < response.media.length; i++) {
        let media = document.createElement('div')
        media.classList.add("col-xxl-3", "col-md-4")
        media.innerHTML = "                                                <div class=\"card info-card sales-card\">\n" +
            "\n" +
            "                                                    <div class=\"filter\">\n" +
            "                                                        <a class=\"icon\" href=\"#\"><i class=\"bi bi-trash\"></i></a>\n" +
            "                                                    </div>\n" +
            "\n" +
            "                                                    <div class=\"card-body\">\n" +
            "                                                        <h5 class=\"card-title\">" + response.media[i].originalFileName.substring(0, 8) + "...</span></h5>\n" +
            "                                                        <div class=\"d-flex align-items-center justify-content-around\">\n" +
            "                                                            <div class=\"card-icon  d-flex align-items-center justify-content-center\">\n" +
            "                                                                <img src=\"/image/" + response.media[i].id + "\" alt=\"Profile\" class=\"w-100 h-100\" style='max-height: 400px'>\n" +
            "                                                            </div>\n" +
            "                                                        </div>\n" +
            "                                                    </div>\n" +
            "\n" +
            "                                                </div>\n"
        media.querySelector('.bi-trash').addEventListener('click', function () {
            var settings = {
                "url": "http://localhost:8080/media/" + response.media[i].id,
                "method": "DELETE",
                "timeout": 0,
            };
            $.ajax(settings).done(function (response) {
                media.parentNode.removeChild(media)
            })
        })
        document.querySelector('#editPhotos').appendChild(media)
    }
    for (let i = 0; i < answers.length; i++) {
        answers[i].value = response.answers[i]
        if (response.answers[i] === response.rightAnswer) {
            checks[i].checked = true
        }
    }
    submitEditButton.addEventListener('click', function () {
        editQuestion(response)
    })

}

function editQuestion(question) {
    let editQuestionArea = document.querySelector('#editQuestion')
    let body = editQuestionArea.querySelector('.modal-body')
    let title = body.querySelector('textarea')
    let answersIn = body.querySelectorAll('.text')
    let checks = body.querySelectorAll('.check')
    let rightAnswer
    let answers = []

    for (let i = 0; i < answersIn.length; i++) {
        if (answersIn[i].value === "") {
            answersIn[i].classList.remove('is-valid')
            answersIn[i].classList.add('is-invalid')
            return
        } else {
            answersIn[i].classList.add('is-valid')
            answersIn[i].classList.remove('is-invalid')
        }
        console.log(answersIn[i].value)
        answers.push(answersIn[i].value)
        if (checks[i].checked) {
            rightAnswer = i
        }
    }
    let newQuestion = {
        id: question.id,
        title: title.value,
        answers: answers,
        rightAnswer: answers[rightAnswer],
    }
    let files = document.querySelector('#formFile1').files
    let formData = new FormData();
    for (let i = 0; i < files.length; i++) {
        formData.append("file", files[i]);
    }
    formData.append("question", JSON.stringify(newQuestion))

    console.log(newQuestion)
    console.log(files)

    fetch("http://localhost:8080/question/change", {
        method: "PUT",
        body: formData,
    }).then((response) => {
        let data = response.json().then(async function (value) {
            showQuestionPreview(value)
            return value
        })
        console.log(data)
    })
    editQuestionArea.querySelector('.btn-secondary').click()
}

function deleteQuestion(response) {
    var settings = {
        "url": "http://localhost:8080/questions/" + response.id,
        "method": "DELETE",
        "timeout": 0,
    };
    $.ajax(settings).done(function (response) {
        let questionSelect = questionArea.querySelector('.form-select')
        questionSelect.options[questionSelect.selectedIndex].parentNode.removeChild(questionSelect.options[questionSelect.selectedIndex])
        questionSelect.options[0].selected = 'selected'
        questionsMedia.innerHTML = ''
        let answersList = questionArea.querySelector('.list-group')
        answersList.innerHTML = ''
        let title = questionArea.querySelector('.question-title')
        title.innerHTML = ''
        editQuestionButton.style.display = 'none'
        deleteQuestionButton.style.display = 'none'
    })
}

function showQuestionPreview(response) {
    removeListenersFromButtons()
    questionsMedia.innerHTML = ''
    let answersList = questionArea.querySelector('.list-group')
    answersList.innerHTML = ''
    console.log(response)
    let title = questionArea.querySelector('.question-title')
    title.innerHTML = response.title
    let media = response.media
    for (let i = 0; i < media.length; i++) {
        let image = document.createElement('div')
        image.classList.add("carousel-item")
        if (i === 0) {
            image.classList.add("active")
        }
        image.innerHTML = "<img src=\"/image/" + media[i].id + "\" class=\"d-block w-100\" alt=\"...\">"
        questionsMedia.appendChild(image)
    }
    let answers = response.answers
    for (let i = 0; i < answers.length; i++) {
        let answer = document.createElement('li')
        answer.classList.add("list-group-item")
        if (answers[i] === response.rightAnswer) {
            answer.innerHTML = "<i class=\"bi bi-check me-1 text-success\"></i> " + answers[i]
        } else {
            answer.innerHTML = "<i class=\"bi bi-x-circle me-1 text-danger\"></i> " + answers[i]
        }
        answersList.appendChild(answer)
    }
    editQuestionButton.addEventListener('click', function () {
        showEditForm(response)
    })
    deleteQuestionButton.addEventListener('click', function () {
        console.log('del')
        deleteQuestion(response)
    })
}

function removeListenersFromButtons() {
    let oldEQB = editQuestionButton
    let oldDQB = deleteQuestionButton
    let buttonParent = editQuestionButton.parentNode
    buttonParent.innerHTML = ''
    editQuestionButton = editQuestionButton.cloneNode()
    deleteQuestionButton = deleteQuestionButton.cloneNode()
    editQuestionButton.innerHTML = oldEQB.innerHTML
    deleteQuestionButton.innerHTML = oldDQB.innerHTML
    buttonParent.appendChild(editQuestionButton)
    buttonParent.appendChild(deleteQuestionButton)
}