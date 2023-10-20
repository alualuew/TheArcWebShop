function login() {
  console.log("login here");

  // Get the username and password from input fields
  const username = $("#usernameInput").val();
  const password = $("#passwordInput").val();

  // Create a JSON object with the username and password
  const loginData = {
    username: username,
    password: password
  };

  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(loginData)
  })
    .then(response => {
      if (response.ok) {
        return response.text(); 
      } else {
        throw new Error("Network response was not ok.");
      }
    })
    .then(data => {
      const timestamp = new Date().toISOString();
      sessionStorage.setItem("token", data);
      sessionStorage.setItem("loginTimestamp", timestamp);
      location.href = "index.html";
      console.log("Eingeloggt");
    })
    .catch(error => {
      console.error(error);
      alert("Falscher Benutzername oder Passwort!");
    });
}
  
    document.getElementById("loginButton").addEventListener("click", login);
      $('#usernameInput').focus();
  
  
  
  
  function checkTokenValidity() {
    var loginTimestamp = sessionStorage.getItem("loginTimestamp");
    if (loginTimestamp) {
      var currentTime = new Date().getTime();
      var loginTime = new Date(loginTimestamp).getTime();
      var validityDuration = 3600 * 1000; // Gültigkeitsdauer des Tokens in Millisekunden (hier 1 Stunde)
  
      if (currentTime - loginTime >= validityDuration) {
        // Token ist abgelaufen
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("loginTimestamp");
        console.log("Token ist abgelaufen");
  
        location.href = "index.html";
        alert("Ihre Sitzung ist abgelaufen. Sie wurden abgemeldet.");
      }
    }
  }
  
  
  
  function checkAdminStatus() {
    const token = sessionStorage.getItem('token');
  
    if (token) {
      // Token aufteilen und den Payload extrahieren
      const tokenParts = token.split('.');
      const payloadBase64 = tokenParts[1];
      const payload = JSON.parse(atob(payloadBase64));
  
      // Überprüfen, ob der Benutzer ein Administrator ist
      const isAdmin = payload.admin;
  
      return isAdmin;
    } else {
      return false;
    }
  }
  
  $('#navbarModal').on('shown.bs.modal', function () {
    $('#usernameInput').focus();
  });
  
  document.addEventListener('keydown', function(event) {
    if (event.key === "Enter") {
      const usernameInput = document.getElementById("usernameInput");
      const passwordInput = document.getElementById("passwordInput");
      const activeElement = document.activeElement;
  
      if ((activeElement === usernameInput || activeElement === passwordInput) && activeElement.tagName === "INPUT") {
        event.preventDefault();
        document.getElementById("loginButton").click();
      }
    }
  });
  
  
  