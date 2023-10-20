document.addEventListener('DOMContentLoaded', function() {
    const registrationForm = document.getElementById('registrationForm');
    const passwordInput = document.getElementById('password');
  
    registrationForm.addEventListener('submit', function(event) {
      event.preventDefault();
  
      const username = document.getElementById("username").value;
      const email = document.getElementById("email").value;
      const password = passwordInput.value;
  
      const user = {
        username: username,
        email: email,
        password: password,
        active: true,
        admin: false
      };
  
      $.ajax({
        url: 'http://localhost:8080/users/createUser',
        type: 'POST',
        contentType: 'application/json',
        headers: { "Authorization": sessionStorage.getItem("token") },
        data: JSON.stringify(user),
        success: function(response) {
          console.log('Daten erfolgreich gesendet:', response);
  
          // Login
  
          $.post({
            url: "http://localhost:8080/login",
            contentType: "application/json",
            headers: { "Authorization": sessionStorage.getItem("token") },
            data: JSON.stringify(user),
            success: function(data) {
              let timestamp = new Date().toISOString(); // Aktuellen Zeitpunkt erfassen
              sessionStorage.setItem("token", data);
              sessionStorage.setItem("loginTimestamp", timestamp); // Timestamp speichern
              location.href = "index.html";
              console.log("Eingeloggt");
            },
            error: console.error
          });
          alert("Sie haben sich erfolgreich Registriert!")
        },
        error: function(xhr, status, error) {
          console.error('Fehler beim Senden der Daten:', error);
          
        }
      });
    });
    });
 
  