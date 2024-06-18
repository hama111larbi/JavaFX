package entity;

public class Remise {
    int id;
    float montant;
    int pourcentage;
    float montantaprespourcentage;

    public Remise() {
    }

    public Remise(int id, float montant, int pourcentage, float montantaprespourcentage) {
        this.id = id;
        this.montant = montant;
        this.pourcentage = pourcentage;
        this.montantaprespourcentage = montantaprespourcentage;
    }

    public Remise(float montant, int pourcentage, float montantaprespourcentage) {
        this.montant = montant;
        this.pourcentage = pourcentage;
        this.montantaprespourcentage = montantaprespourcentage;
    }

    public Remise(int i, int j) {
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

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public String toString() {
        return "Remise{" +
                "id=" + id +
                ", montant=" + montant +
                ", pourcentage=" + pourcentage +
                ", montantaprespourcentage=" + montantaprespourcentage +
                '}';
    }

    public float getMontantaprespourcentage() {
        return montantaprespourcentage;
    }

    public void setMontantaprespourcentage(float montantaprespourcentage) {
        this.montantaprespourcentage = montantaprespourcentage;
    }
}
