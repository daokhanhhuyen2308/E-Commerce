const URL = 'http://localhost:8082/';

const page = 0;
const size = 10;

$(document).ready(function (){
    getProduct();
});

function getAllProductFromApiToView(pageSize, pageIndex){

}

function getProduct(){
    $.ajax({
        url: URL + "products/all-products",
        type: "GET",
        success: function (response){
            console.log('AJAX response:', response);  // Log the entire response

            // Access the 'content' array in the response, not 'data'
            let productDTOs = response.content;
            console.log("ProductDTOs:", productDTOs);

            if (!productDTOs || productDTOs.length === 0) {
                console.log('No products found or data is undefined');
                return;
            }

            let productContainer = $('#products'); // Use correct id selector with #
            productContainer.empty(); // Clear the container first

            for (let i = 0; i < productDTOs.length; i++) {
                let product = productDTOs[i];
                let imageUrl = product.imageUrl !== null ? product.imageUrl : '/image/product1.webp';
                let productHTML = `
                    <div class="product" data-id="${product.id}">
                        <img src="${imageUrl}" alt="${product.name}">
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
         
                    </div>`;

                productContainer.append(productHTML); // Append each product HTML
            }
        },
        error: function (error){
            console.error('Error fetching products:', error);
        }
    });
}


//<button>Add to Cart</button>