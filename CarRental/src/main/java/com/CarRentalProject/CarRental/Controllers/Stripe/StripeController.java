package com.CarRentalProject.CarRental.Controllers.Stripe;

import com.CarRentalProject.CarRental.Services.Stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/paiements")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    private static final Logger logger = LoggerFactory.getLogger(StripeController.class);

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        logger.info("Données reçues pour la création de l'intention de paiement : {}", data);
        try {
            double amount = Double.parseDouble(data.get("amount").toString());
            logger.info("Montant pour l'intention de paiement : {}", amount);
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);
            logger.info("ClientSecret généré : {}", paymentIntent.getClientSecret());

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
