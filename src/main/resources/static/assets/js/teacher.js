let testMenu = document.querySelector('#showTest')
console.log(testMenu)
let testSelect = testMenu.querySelector('.form-select')
window.onload=function () {
    var settings = {
        "url": "http://localhost:8080/tests?projection=testPreview",
        "method": "GET",
        "timeout": 0,
    };
    $.ajax(settings).done(function (response) {
        let tests=response._embedded.tests
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

    if (selectedOption.value !== "0") {
        var settings = {
            "url": "http://localhost:8080/tests/"+selectedOption.value+"?projection=fullTest",
            "method": "GET",
            "timeout": 0,
        };
        $.ajax(settings).done(function (response) {
            console.log(response)
            let test=response._embedded
            showTest(test)

        });
    } else {
    }
})

function showTest(response)
{

}function showQuestionInTest(question)
{

}