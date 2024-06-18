package entity;

import java.time.LocalDateTime;

public class Payment {
    int id;
    float montant;
    String moyendepaiement;
    String email;
    LocalDateTime date;

    public Payment(int id, float montant, String moyendepaiement, String email, LocalDateTime date) {
        this.id = id;
        this.montant = montant;
        this.moyendepaiement = moyendepaiement;
        this.email = email;
        this.date = date;
    }

    public Payment(float montant, String moyendepaiement, String email, LocalDateTime date) {
        this.montant = montant;
        this.moyendepaiement = moyendepaiement;
        this.email = email;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getMoyendepaiement() {
        return moyendepaiement;
    }

    public void setMoyendepaiement(String moyendepaiement) {
        this.moyendepaiement = moyendepaiement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", montant=" + montant +
                ", moyendepaiement='" + moyendepaiement + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }

    public Payment(int i, String text) {
        this.montant=i;
        this.moyendepaiement=text;
    }

    public Payment() {

    }

    public Payment(String mod, int mo) {
        this.moyendepaiement=mod;
        this.montant=mo;
    }
}

