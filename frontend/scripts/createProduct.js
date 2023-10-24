document.addEventListener("DOMContentLoaded", function () {
  const formCreateProduct = document.getElementById("product-form");

  formCreateProduct.addEventListener("submit", function (event) {
    event.preventDefault();

    const productName = document.getElementById("productName").value;
    const productDescription = document.getElementById("productDescription").value;
    const productPrice = document.getElementById("productPrice").value;
    const productQuantity = document.getElementById(
      "productQuantity"
    ).value;
    const productType = document.getElementById("productType").value;
    const productActive = document.getElementById("productActive").checked;

    const fileInput = document.getElementById("productImg");
    console.log(fileInput);
    const file = fileInput.files[0];

    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    console.log(formData);

    $.ajax({
      url: "http://localhost:8080/files",
      type: "POST",
      contentType: false,
      processData: false, 
      headers: { Authorization: sessionStorage.getItem("token") },
      data: formData,
      success: function (response) {
        const imageName = response;
        console.log(imageName);
        const imageUrl = imageName;

        const product = {
          name: productName,
          description: productDescription,
          imageUrl: imageUrl,
          price: productPrice,
          quantity: productQuantity,
          type: productType,
          active: productActive,
        };

        console.log(product);

        $.ajax({
          url: "http://localhost:8080/products",
          type: "POST",
          contentType: "application/json",
          headers: { Authorization: sessionStorage.getItem("token") },
          data: JSON.stringify(product),
          success: function (response) {
            console.log("Daten erfolgreich gesendet:", response);
          },
          error: function (xhr, status, error) {
            console.error("Fehler beim Senden der Daten:", error);
          },
        });
      },
      error: function (xhr, status, error) {
        console.error("Fehler beim Senden des Productes:", error);
      },
    });
  });
});
