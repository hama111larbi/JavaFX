package models;

public class Reponses {
    private int idrep;

    public Reponses(int id) {
        this.idrec=id;
    }

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }

    public void setContenu(String texte) {
        this.contenu = texte;
    }





    public int getIdrec() {
        return idrec;
    }
private String titre;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private String description;
    private int idrec;
    private String contenu;

    public int getIdrep() {
        return idrep;
    }

    public Reponses(int idrec,String texte) {

        this.contenu = texte;
        this.idrec = idrec;
       // this.idrep = idrep;


    }

    @Override
    public String toString() {
        return "Reponses{" +
                " titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", contenu='" + contenu + '\'' +
                '}';
    }

    public Reponses(int idrep, String contenu, String titre, String description, int idrec) {
        this.idrep = idrep;
        this.titre = titre;
        this.contenu = contenu;
        this.description = description;
        this.idrec = idrec;
    }

    public Reponses(){}

    public String getContenu() {
        return contenu;
    }


}
