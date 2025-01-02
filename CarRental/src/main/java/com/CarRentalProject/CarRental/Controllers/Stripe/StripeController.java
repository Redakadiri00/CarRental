package com.CarRentalProject.CarRental.Controllers.Stripe;

import com.CarRentalProject.CarRental.Services.Stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paiements")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        try {
            double amount = Double.parseDouble(data.get("amount").toString());
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
