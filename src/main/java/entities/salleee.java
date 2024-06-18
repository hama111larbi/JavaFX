package entities;

public class  salleee {
    private int id;  // Changer le nom de l'attribut id en idsalle

    private String matiere;



    // Constructeurs, getters et setters

    public salleee() {
    }





    // Constructeur avec idsalle, iduser et matiere
    public salleee(int id,  String matiere) {
        this.id = id;
        this.matiere = matiere;


    }

    // Constructeur sans idsalle (utilisé lors de l'insertion en base de données)
    public salleee( String matiere) {

        this.matiere = matiere;


    }

    // Getters et setters

    // Getter and Setter for password




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    // Reste du code inchangé


    @Override
    public String toString() {
        return "salleee{" +
                "id=" + id +
                ", matiere='" + matiere + '\'' +
                '}';
    }
}