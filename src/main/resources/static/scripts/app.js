"use strict";

let address = "http//localhost:8080";

let mailL;
let nameL;
let lastL;
let descL;
let passL;
let repeatL;
let registerL;
let loginL;

let t1L; // тесты
let t2L; // пользователи

// C - Call

function getUser(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/user`,
        data: { id: id },
        success: function(response) {
            console.log('User details:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting user:', error);
        }
    });
}

function getTest(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/test`,
        data: { id: id },
        success: function(response) {
            console.log('Test details:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting test:', error);
        }
    });
}

function nextQuestion(answer) {
    $.ajax({
        type: 'POST',
        url: `${address}/question`,
        data: { answer: answer },
        success: function(response) {
            console.log('Next question:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting next question:', error);
        }
    });
}

function register(email, password, name, lastName, description) {
    /*$.post(
        "/api/register",
        {
            email: email,
            password: password,
            name: name,
            lastName: lastName,
            description: description
        },
        function (data, status) {
            console.log("Data: " + data + "\nStatus: " + status);
        }
    );*/
    console.log("parameters:  email "+email+"  password "+password+"  name "+name+ "  lastName "+lastName+"  description "+description)
    $.ajax({
        url: "/api/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            email: email,
            password: password,
            name: name,
            lastName: lastName,
            description: description
        }),
        success: function(data, status) {
            console.log("Data: " + data + "\nStatus: " + status);
        },
        error: function(xhr, status, error) {
            console.error("Error: " + xhr.responseText);
        }
    });
}

function login(email, password) {
    $.ajax({
        type: 'POST',
        url: `${address}/login`,
        data: { email: email, password: password },
        success: function(response) {
            console.log('User logged in:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error logging in:', error);
        }
    });
}

function logout() {
    $.ajax({
        type: 'POST',
        url: `${address}/logout`,
        success: function(response) {
            console.log('User logged out:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error logging out:', error);
        }
    });
}

function getAvatar() {
    $.ajax({
        type: 'GET',
        url: `${address}/avatar`,
        success: function(response) {
            console.log('User avatar:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting avatar:', error);
        }
    });
}

function changeAvatar(avatar) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeAvatar`,
        data: { avatar: avatar },
        success: function(response) {
            console.log('Avatar changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing avatar:', error);
        }
    });
}

function changeDescription(description) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeDescription`,
        data: { description: description },
        success: function(response) {
            console.log('Description changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing description:', error);
        }
    });
}

function beginTesting(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/beginTesting`,
        data: { id: id },
        success: function(response) {
            console.log('Testing started:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error starting testing:', error);
        }
    });
}

function getResult(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/result`,
        data: { id: id },
        success: function(response) {
            console.log('Test result:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting result:', error);
        }
    });
}

function endUpTesting() {
    $.ajax({
        type: 'POST',
        url: `${address}/endUpTesting`,
        success: function(response) {
            console.log('Testing ended:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error ending testing:', error);
        }
    });
}

function getTests() {
    $.ajax({
        type: 'POST',
        url: `${address}/getTests`,
        success: function(response) {
            console.log('All tests:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting tests:', error);
        }
    });
}

function getTimeUsed(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/getTimeUsed`,
        data: { id: id },
        success: function(response) {
            console.log('Time used:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting time used:', error);
        }
    });
}

function createTest(name, description) {
    $.ajax({
        type: 'POST',
        url: `${address}/createTest`,
        data: { name: name, description: description },
        success: function(response) {
            console.log('Test created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating test:', error);
        }
    });
}

function changeTest(id, name, description, prev) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeTest`,
        data: { id: id, name: name, description: description, prev: prev },
        success: function(response) {
            console.log('Test changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing test:', error);
        }
    });
}

function deleteTest(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteTest`,
        data: { id: id },
        success: function(response) {
            console.log('Test deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting test:', error);
        }
    });
}

function getAllTests() {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllTests`,
        success: function(response) {
            console.log('All tests:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting tests:', error);
        }
    });
}

function createAnswer(qid, name) {
    $.ajax({
        type: 'POST',
        url: `${address}/createAnswer`,
        data: { qid: qid, name: name },
        success: function(response) {
            console.log('Answer created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating answer:', error);
        }
    });
}

function changeAnswer(qid, name, id) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeAnswer`,
        data: { qid: qid, name: name, id: id },
        success: function(response) {
            console.log('Answer changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing answer:', error);
        }
    });
}

function deleteAnswer(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteAnswer`,
        data: { id: id },
        success: function(response) {
            console.log('Answer deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting answer:', error);
        }
    });
}

function getAllAnswers(qid) {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllAnswers`,
        data: { qid: qid },
        success: function(response) {
            console.log('All answers:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting answers:', error);
        }
    });
}

function createQuestion(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/createQuestion`,
        data: { id: id },
        success: function(response) {
            console.log('Question created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating question:', error);
        }
    });
}

function changeQuestion(id, name, testId, inOrder, perInstance) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeQuestion`,
        data: { id: id, name: name, testId: testId, inOrder: inOrder, perInstance: perInstance },
        success: function(response) {
            console.log('Question changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing question:', error);
        }
    });
}

function deleteQuestion(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteQuestion`,
        data: { id: id },
        success: function(response) {
            console.log('Question deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting question:', error);
        }
    });
}

function getAllQuestions(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllQuestions`,
        data: { id: id },
        success: function(response) {
            console.log('All questions:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting questions:', error);
        }
    });
}

function createReward(id, reward, parameterId) {
    $.ajax({
        type: 'POST',
        url: `${address}/createReward`,
        data: { id: id, reward: reward, parameterId: parameterId },
        success: function(response) {
            console.log('Reward created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating reward:', error);
        }
    });
}

function changeReward(id, reward, parameterId, answerId) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeReward`,
        data: { id: id, reward: reward, parameterId: parameterId, answerId: answerId },
        success: function(response) {
            console.log('Reward changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing reward:', error);
        }
    });
}

function deleteReward(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteReward`,
        data: { id: id },
        success: function(response) {
            console.log('Reward deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting reward:', error);
        }
    });
}

function getAllRewards(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllRewards`,
        data: { id: id },
        success: function(response) {
            console.log('All rewards:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting rewards:', error);
        }
    });
}

function createParameter(name, required, prevRequired, testId) {
    $.ajax({
        type: 'POST',
        url: `${address}/createParameter`,
        data: { name: name, required: required, prevRequired: prevRequired, testId: testId },
        success: function(response) {
            console.log('Parameter created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating parameter:', error);
        }
    });
}

function changeParameter(id, name, required, prevRequired, testId) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeParameter`,
        data: { id: id, name: name, required: required, prevRequired: prevRequired, testId: testId },
        success: function(response) {
            console.log('Parameter changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing parameter:', error);
        }
    });
}

function deleteParameter(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteParameter`,
        data: { id: id },
        success: function(response) {
            console.log('Parameter deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting parameter:', error);
        }
    });
}

function getAllParameters(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllParameters`,
        data: { id: id },
        success: function(response) {
            console.log('All parameters:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting parameters:', error);
        }
    });
}

function createCategory(name) {
    $.ajax({
        type: 'POST',
        url: `${address}/createCategory`,
        data: { name: name },
        success: function(response) {
            console.log('Category created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating category:', error);
        }
    });
}

function changeCategory(id, name) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeCategory`,
        data: { id: id, name: name },
        success: function(response) {
            console.log('Category changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing category:', error);
        }
    });
}

function deleteCategory(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteCategory`,
        data: { id: id },
        success: function(response) {
            console.log('Category deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting category:', error);
        }
    });
}

function getAllCategories() {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllCategories`,
        success: function(response) {
            console.log('All categories:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting categories:', error);
        }
    });
}

function createBook(name, file, categoryId, preview) {
    $.ajax({
        type: 'POST',
        url: `${address}/createBook`,
        data: { name: name, file: file, categoryId: categoryId, preview: preview },
        success: function(response) {
            console.log('Book created:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error creating book:', error);
        }
    });
}

function changeBook(id, name, file, categoryId, preview) {
    $.ajax({
        type: 'POST',
        url: `${address}/changeBook`,
        data: { id: id, name: name, file: file, categoryId: categoryId, preview: preview },
        success: function(response) {
            console.log('Book changed:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error changing book:', error);
        }
    });
}

function deleteBook(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/deleteBook`,
        data: { id: id },
        success: function(response) {
            console.log('Book deleted:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error deleting book:', error);
        }
    });
}

function getAllBooks(id) {
    $.ajax({
        type: 'POST',
        url: `${address}/getAllBooks`,
        data: { id: id },
        success: function(response) {
            console.log('All books:', response);
        },
        error: function(xhr, status, error) {
            console.error('Error getting books:', error);
        }
    });
}












function main() {
    mailL = $("#mail");
    console.log(mailL);
    nameL = $("#name");
    lastL = $("#last");
    descL = $("#desc");
    passL = $("#pass");
    repeatL = $("#repeat");
    registerL = $("#register");
    loginL = $("#login");
    t1L = $("#t1");
    t2L = $("#t2");



    /*if (passL === repeatL) {
        message.textContent = "Пароли совпадают!";
        message.style.color = "green";
    } else {
        message.textContent = "Пароли не совпадают.";
        message.style.color = "red";
    }

    // Проверяем, если поле повторного ввода пустое
    if (repeatL === "") {
        message.textContent = ""; // Очищаем сообщение, если поле пустое
        return;
    }*/

    // Добавляем обработчики событий для обоих полей
    passL.addEventListener('input', checkPasswords);
    repeatL.addEventListener('input', checkPasswords);
}

window.onload=main;