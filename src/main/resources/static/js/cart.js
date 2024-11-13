const URL = "http://localhost:8082/carts";
let carts = [];
let totalQuantityHTML = 0;

$(document).ready(function () {
    getAllProductsInCart();
$('#addToCart').on('click', function() {
    console.log('Add to cart clicked!');
    const productId = $(this).data('id');
    const quantityInput = $('#quantityInput').val();
    addProductIntoCart(productId, quantityInput);
    });
});

// Hàm lấy thông tin giỏ hàng
function getAllProductsInCart(){
    $.ajax({
        url: `${URL}/all-items`,
        type: "GET",
        contentType: "application/json",
        success: function(response) {
            console.log("Cart fetched successfully:", response);
            // Lưu thông tin sản phẩm vào mảng carts
            carts = response.items;
            // Lưu tổng số lượng
            totalQuantityHTML = response.totalQuantity;
            displayCartItems(carts);
        },
        error: function(error) {
            console.error("Error fetching cart:", error);
        }
    });
}

function addProductIntoCart(productId, quantity){

    const existingItemIndex = carts.findIndex((item) => item.productId === productId);
    if (existingItemIndex > -1){
                carts[existingItemIndex].productQuantity += quantity;
                console.log(`Product ID ${productId} already exists. Increased quantity`);
    } else {
                carts.push({productId: productId, productQuantity: quantity});
                console.log(`Product ID ${productId} does not exist. Added new product`);
            }
    callAPIAddToCart(productId, quantity);

}

function callAPIAddToCart(productId, quantity){
    $.ajax({
        url: `${URL}/add`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ productId: productId, quantity: quantity }),
        success: function(response) {
            console.log("Product added to cart successfully:", response);
            $('#quantityCart').text(totalQuantityHTML);
            $('#totalQuantityInCart').text(totalQuantityHTML);
            displayCartItems(response);
        },
        error: function(error){
            console.error("Error adding product to cart:", error);
        }
    })
}

// Hàm hiện các sản phẩm trong giỏ hàng trên UI
function displayCartItems(carts){
    const listProductCart = $('#listProductCart tbody');
    listProductCart.empty();
    for (let i = 0; i < carts.length; i++) {
        let item = carts[i];
        item.productDescription = '';
        let imageUrl = item.productImage || '/images/product1.webp';
        const newCart = document.createElement("tr");
        newCart.innerHTML = `<td data-th="Product">
                <div class="row">
                    <div class="col-sm-2 hidden-xs"><img src="${imageUrl}" alt="${item.name}" class="img-responsive"/></div>
                    <div class="col-sm-10">
                        <h4 class="nomargin">${item.productName}</h4>
                        <p>${item.productDescription}</p>
                    </div>
                </div>
            </td>
            <td data-th="Price">${item.productPrice.toFixed(2).toLocaleString()}</td>
            <td data-th="Quantity">
                <label>
                    <input type="number" class="form-control text-center" value="${item.productQuantity}">
                </label>
            </td>
<!--            <td data-th="Subtotal" class="text-center">0</td>-->
            <td class="actions" data-th="">
                <button class="btn btn-info btn-sm" data-id="${item.itemId}"><i class="fa fa-refresh"></i></button>
                <button class="btn btn-danger btn-sm" data-id="${item.itemId}"><i class="fa fa-trash-o"></i></button>
            </td>`

        listProductCart.append(newCart);
    }
}

// function updateQuantityOfItemInCart(){
//
//
// }

