let modal = document.querySelector('.modal-body')
let inputs = modal.querySelectorAll('.form-control')
let checks = modal.querySelectorAll('.form-check-input')
let modalFooter = document.querySelector(".modal-footer")
let cancelButton = modalFooter.querySelector('.btn-secondary')
let sendButton = modalFooter.querySelector('.btn-primary')
sendButton.addEventListener('click', createQuestion)
let showQuestionButton = document.querySelector('#showQuestion')
let questionsMedia = document.querySelector('.carousel-inner')
showQuestionButton.addEventListener('click', showAllQuestions)
let questionArea = document.querySelector('#questions')

function createQuestion() {
    let answers = []

    for (let i = 0; i < inputs.length; i++) {
        if (i !== 1 && inputs[i].value === "") {
            inputs[i].classList.add('is-invalid')
            return
        } else {
            inputs[i].classList.add('is-valid')
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
    // var settings = {
    //     "url": "http://localhost:8080/question/create",
    //     "method": "POST",
    //     "timeout": 0,
    //     "data": formData,
    // };
    //
    // $.ajax(settings).done(function (response) {
    //     cancelButton.click()
    //     console.log(response);
    // });
    fetch("http://localhost:8080/question/create", {
        method: "POST",
        body: formData,
    }).then((response) => {
        let data = response.json().then(async function (value) {
            console.log(value)
            return value
        })
    })
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
        let questionSelect = questionArea.querySelector('.form-select')
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
                    questionsMedia.innerHTML = ''
                    let anwersList = questionArea.querySelector('.list-group')
                    anwersList.innerHTML = ''
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
                        anwersList.appendChild(answer)
                    }
                });
            }
        })
    });
}

//<div class="carousel-item active">
//                                                 <img src="assets/img/slides-2.jpg" class="d-block w-100" alt="...">
//                                             </div>
