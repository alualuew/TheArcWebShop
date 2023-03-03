document.getElementById("createUser-form").addEventListener("submit", function(event){
    event.preventDefault();

    const formData = new FormData();

    formData.append("mail", document.querySelector("#mail").value);
    formData.append("vname", document.querySelector("#vname").value);
    formData.append("nname", document.querySelector("#nname").value);
    formData.append("plz", document.querySelector("#plz").value);
    formData.append("ort", document.querySelector("#ort").value);
    formData.append("strasse", document.querySelector("#strasse").value);
    formData.append("hausnummer", document.querySelector("#hausnummer").value);



    $.ajax({
        url: 'http://localhost:8080/api/users',
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify ({

            email: document.querySelector("#mail").value,
            firstname: document.querySelector("#vname").value,
            lastname: document.querySelector("#nname").value,
            postal_code: document.querySelector("#plz").value,
            city: document.querySelector("#ort").value,
            street: document.querySelector("#strasse").value,
            streetnumber: document.querySelector("#hausnummer").value,


        }), 
        success: function (response){console.log("Registration successful")},
        error: function (error){console.log(error)}
    });
});