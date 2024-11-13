const sign_in_btn = document.getElementById("sign-in-btn");
const sign_up_btn = document.getElementById("sign-up-btn");
const container = document.querySelector(".container");

if (sign_up_btn && sign_up_btn && container){

    sign_up_btn.addEventListener('click', () => {
        container.classList.add("sign-up-mode");
    });

    sign_in_btn.addEventListener('click', () => {
        container.classList.remove("sign-up-mode");
    });
}
else console.error("One or more elements not found");


$(document).ready(function(){
    $("#sign-in").submit(function (event) {
        event.preventDefault();
        const loginUsername = $('#loginUsername').val();
        const loginPassword = $('#loginPassword').val();

        $.ajax({
            url: "http://localhost:8082/api/auth/authenticate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({username: loginUsername, password: loginPassword}),
            success: function (response) {
                console.log("Response:", response);
                // alert("Login successful");
                window.location.href = "homepage"
            },
            error: function (error) {
                alert("Đăng nhập thất bại. Vui lòng kiểm tra thông tin đăng nhập.");
                console.log(error);
            }
        })

    });

    $("#sign-up").submit(function (event) {
        event.preventDefault();
        const signUpUsername = $('#signUpUsername').val();
        const signUpEmail = $('#signUpEmail').val();
        const signUpPassword = $('#signUpPassword').val();
        const firstName = $('#firstName').val();
        const lastName = $('#lastName').val();
        const phone = $('#phone').val();
        const address = $('#address').val();

        $.ajax({
            url: "http://localhost:8082/users/registration",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({username: signUpUsername, email: signUpEmail, password: signUpPassword,
                firstName: firstName, lastName: lastName, phone: phone, address: address}),
            success: function () {
                alert("Đăng ký thành công!");
                window.location.href = "login";
            },
            error: function (error) {
                console.log(error);
            }
        })

    })
})