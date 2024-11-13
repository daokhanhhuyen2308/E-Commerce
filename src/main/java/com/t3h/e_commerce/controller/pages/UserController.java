package com.t3h.e_commerce.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guest")
public class UserController {

    @GetMapping(value="/login")
    public String loginPage() {
        return "guest/login";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "guest/homepage";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "guest/checkout";
    }

    @GetMapping("/payment")
    public String paymentPage() {
        return "guest/payment";
    }
    @GetMapping("/viewDetail")
    public String viewDetailPage() {
        return "guest/detail";
    }
    @GetMapping("/cart")
    public String viewCart(){
        return "guest/cart";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(){
        return "guest/shoppingCart";
    }


}
