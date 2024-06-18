package Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Payment {
    @FXML
    private Label accountInfoLabel;

    @FXML
    private void retrieveAccountInfo() {
        try {
            // Your account retrieval logic here
            Account account = Account.retrieve();
            accountInfoLabel.setText("Account ID: " + account.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51P6dihBtpAjMlV8bUFIDKoPF9JdQFNc4qo6lM44lOrGMi0Iv9SdfAHu3B1J2tPB8wjshqhxiYKiIVCSRzcWTKshp00PEbveIOB";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

}


//