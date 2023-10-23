if (sessionStorage.getItem("token") !== null) {
  setTimeout(() => {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", waitForDropdownAndExecute);
    } else {
      waitForDropdownAndExecute();
    }
  }, 500);

  async function waitForDropdownAndExecute() {
    await whenDropdownOpens();
    mainFunction();
  }

  function whenDropdownOpens() {
    return new Promise((resolve) => {
      function checkDropdown() {
        if (isDropdownOpen()) {
          resolve();
        } else {
          requestAnimationFrame(checkDropdown);
        }
      }
      checkDropdown();
    });
  }

  function isDropdownOpen() {
    let dropdownLink = document.getElementById("navbarDropdown");
    return dropdownLink.getAttribute("aria-expanded") === "true"; // Return as a boolean
  }

  function mainFunction() {
    console.log("Iam here");


    function decodeJwt(token) {
      try {
        const base64Url = token.split(".")[1];
        const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/"); // Use global replacement
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
      console.log(isDropdownOpen());
      if (isDropdownOpen()) {
        const dropdownMenu = document.querySelector(".dropdown-menu");

        const productLink = document.createElement("a");
        productLink.href = "./productAdmin.html";
        productLink.className = "btn btn-green";
        productLink.textContent = "Admin";

        dropdownMenu.appendChild(document.createElement("br"));
        dropdownMenu.appendChild(productLink);
      }
    }
  }
}
