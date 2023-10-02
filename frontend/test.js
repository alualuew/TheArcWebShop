function login(event) {

event.preventDefault();

var mail = document.getElementById('mail').value;
var password = document.getElementById('password').value;

if(mail === 'admin' && password === 'password') {

    alert('Login successful!');

} else  {
    alert('Invalid mail or password! Please try again');
}
}