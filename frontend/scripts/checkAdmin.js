if (sessionStorage.getItem("token") !== null) {
  setTimeout(function () {
    if (document.readyState === "loading") {
      $(document).ready(mainFunction);
    } else {
      mainFunction();
    }
  }, 500);

  function addAdminDropdown() {
    var referenceDropdown = document.querySelector(
      ".navbar-nav.mr-auto .nav-item.dropdown"
    );

    var dropdown = document.createElement("li");
    dropdown.className = "nav-item dropdown";

    var dropdownHtml = `
          <a class="nav-link dropdown-toggle btn-green" href="#" id="adminDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Admin
          </a>
          <div class="dropdown-menu" aria-labelledby="adminDropdown">
              <a href="./productAdmin.html" class="btn btn-green">Produkt erstellen</a>
              <br>
              <a href="./userList.html" class="btn btn-green">Benutzerverwaltung</a>
              <br>
              <a href="#" class="btn btn-green">Produke bearbeiten</a>
          </div>
      `;

    dropdown.innerHTML = dropdownHtml;

    referenceDropdown.parentNode.insertBefore(
      dropdown,
      referenceDropdown.nextSibling
    );
  }

  function mainFunction() {
    function decodeJwt(token) {
      try {
        const base64Url = token.split(".")[1];
        const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
        return JSON.parse(atob(base64));
      } catch (error) {
        console.error("Failed to decode JWT:", error);
        return null;
      }
    }

    const token = sessionStorage.getItem("token");
    const decodedToken = decodeJwt(token);
    const isAdmin = decodedToken && decodedToken.admin;

    if (isAdmin) {
      addAdminDropdown();
    }
  }
}
