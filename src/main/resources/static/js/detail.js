$(document).ready(function() {
    console.log("Ready");

    $('#products div').on('click', function () {
        console.log('Product clicked!');
        const productId = $(this).data('id');
        alert("The data-id of clicked item is: " + productId);
        $.ajax({
            url: `http://localhost:8082/products/get/${productId}`,
            type: "GET",
            contentType: "application/json",
            success: function(response) {
                response.slug = '';
                console.log("Product fetched successfully:", response);
                const slug = response.slug;
                window.location.href = `http://localhost:8082/products/${slug}`;
                displayProductDetails(response);
            },
            error: function(error) {
                console.error("Error fetching product details:", error);
            }
        });
    });


})

function displayProductDetails(product){
    product.imageUrl = undefined;
    $('#productName').text(product.name);
    $('#productImage').attr('src', product.imageUrl);
    $('#productPrice').text(product.price.toLocaleString());
    $('#productDescription').text(product.description);
    $('#addToCart').attr('data-id', product.id);
    $('#buyNow').attr('data-id', product.id);
}

