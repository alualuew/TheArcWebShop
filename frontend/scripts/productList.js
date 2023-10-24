
let products;


$.ajax({
    url: 'http://localhost:8080/products',
    type: 'GET',
    headers: {
        'Content-Type': 'application/json'
    },
    success: function(data) {
        console.log(data);
        products = data;
        console.log(products);
        displayProducts();
    },
    error: function(jqXHR, textStatus, errorThrown) {
        console.error('There was a problem with the fetch operation:', errorThrown);
    }
});

let currentBatch = 0;
const batchSize = 3;

$('#next-button').click(function () {
    currentBatch += batchSize;
    displayProducts();
});

$('#prev-button').click(function () {
    currentBatch -= batchSize;
    displayProducts();
});

function displayProducts() {
    const productContainer = $('#product-list');
    productContainer.empty();

    for (let i = currentBatch; i < currentBatch + batchSize && i < products.length; i++) {
        const productElem = `
            <div class="product-item col-md-4">
                <img src="./product-images/${products[i].imageUrl}" class="img-fluid" alt="${products[i].name}">
                <p>${products[i].name}</p>
            </div>`;
        
        productContainer.append(productElem);
    }

    // Control visibility of the navigation buttons
    $('#prev-button').prop('disabled', currentBatch === 0);
    $('#next-button').prop('disabled', currentBatch + batchSize >= products.length);
}