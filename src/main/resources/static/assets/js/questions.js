let modal = document.querySelector('.modal-body')
let inputs = modal.querySelectorAll('.form-control')
let checks = modal.querySelectorAll('.form-check-input')
let modalFooter = document.querySelector(".modal-footer")
let cancelButton = modalFooter.querySelector('.btn-secondary')
let sendButton = modalFooter.querySelector('.btn-primary')
sendButton.addEventListener('click', createQuestion)
console.log(user)

function createQuestion() {
    let answers = []

    for (let i = 0; i < inputs.length; i++) {
        if (i !== 1 && inputs[i].value === "") {
            inputs[i].classList.add('is-invalid')
            return
        }
        else {
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

