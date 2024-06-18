package entities;

public class equipement {
    int id;
    int salle_id;
    String nom;
    String brand;
    String category;
    String qr_code;

    public equipement(int id, int salle_id, String nom, String brand, String category, String qr_code) {
        this.id = id;
        this.salle_id = salle_id;
        this.nom = nom;
        this.brand = brand;
        this.category = category;
        this.qr_code = qr_code;
    }

    public equipement(int salle_id, String nom, String brand, String category, String qr_code) {
        this.salle_id = salle_id;
        this.nom = nom;
        this.brand = brand;
        this.category = category;
        this.qr_code = qr_code;
    }

    public equipement() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalle_id() {
        return salle_id;
    }

    public void setSalle_id(int salle_id) {
        this.salle_id = salle_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "equipement{" +
                "id=" + id +
                ", salle_id=" + salle_id +
                ", nom='" + nom + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}