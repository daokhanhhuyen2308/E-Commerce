const URL = 'http://localhost:8082/';

$(document).ready(function (){
    getProduct();
});

function getProduct(){
    $.ajax({
        url: URL + 'products/all-products',
        type: 'GET',
        success: function (response){
            console.log('AJAX response:', response);  // Log the entire response

            // Access the 'content' array in the response, not 'data'
            let productDtos = response.content;

            if (!productDtos || productDtos.length === 0) {
                console.log('No products found or data is undefined');
                return;
            }

            let productContainer = $('#product'); // Use correct id selector with #
            productContainer.empty(); // Clear the container first

            for (let i = 0; i < productDtos.length; i++) {
                let product = productDtos[i];
                let productHTML = `
                    <div class="product">
                        <img src="${product.image}" alt="${product.name}">
                        <div class="color-options1">
                            <ul class="color-list1">
                                <li style="background-color: #f0bf82;"></li>
                                <li style="background-color: #e86868;"></li>
                                <li style="background-color: #e47bf0;"></li>
                            </ul>
                        </div>
                        <h3 id="product_name">${product.name}</h3>
                        <p id="product_price">$${product.price}</p>
                        <div class="rating">
                            ★★★★☆
                        </div>
                        <button>Add to Cart</button>
                    </div>`;

                productContainer.append(productHTML); // Append each product HTML
            }
        },
        error: function (error){
            console.error('Error fetching products:', error);
        }
    });
}
