const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");

loginForm.addEventListener("submit", function (event) {

    event.preventDefault();

    message.textContent = "Admin authentication will be connected to the backend.";

});