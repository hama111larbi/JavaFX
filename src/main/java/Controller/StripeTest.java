package Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripeTest {
    public static void main(String[] args) {
        Stripe.apiKey = "sk_test_51P6dihBtpAjMlV8bUFIDKoPF9JdQFNc4qo6lM44lOrGMi0Iv9SdfAHu3B1J2tPB8wjshqhxiYKiIVCSRzcWTKshp00PEbveIOB";

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(20000L) // Amount in cents (e.g., $200.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println("PaymentIntent created: " + intent.toJson());

            if ("succeeded".equals(intent.getStatus())) {
                System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            } else {
                System.out.println("Payment failed. PaymentIntent status: " + intent.getStatus());
            }
        } catch (StripeException e) {
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}
