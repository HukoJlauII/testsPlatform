let modal = document.querySelector('.modal-body')
let inputs = modal.querySelectorAll('.form-control')
let checks = modal.querySelectorAll('.form-check-input')
let sendButton = document.querySelector('.btn-primary')
sendButton.addEventListener('click', createQuestion)
console.log(user)

function createQuestion() {
    let answers = []

    for (let i = 0; i < inputs.length; i++) {
        if (i !== 1 && inputs[i].value === "") {
            return
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
    let images = []
    for (let i = 0; i < files.length; i++) {
        let image = {
            originalFileName: files[i].name,
            size: files[i].size,
            mediaType: files[i].type,
            bytes: fileToByte(files[i])
        }
        images.push(image)
    }
    let question = {
        title: title,
        answers: answers,
        rightAnswer: answers[rightAnswer],
        media: images
    }

    console.log(question)
    var settings = {
        "url": "http://localhost:8080/question/create",
        "method": "POST",
        "timeout": 0,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify(question),
    };

    $.ajax(settings).done(function (response) {
        cancelButton.click()
        console.log(response);
    });
}

function fileToByte(myFile) {
    var reader = new FileReader();
    let fileByteArray=[];

    reader.onloadend = function (evt) {
        if (evt.target.readyState == FileReader.DONE) {
            var arrayBuffer = evt.target.result,
            array = new Uint8Array(arrayBuffer)

            for (var i = 0; i < array.length; i++) {
                fileByteArray.push(array[i]);
            }
        }
    }
    reader.readAsArrayBuffer(myFile);
    let newFileByteArray=new Uint8Array(fileByteArray.buffer)
    console.log(newFileByteArray)
    return fileByteArray;
}
