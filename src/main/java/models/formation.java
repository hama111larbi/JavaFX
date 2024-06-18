package models;

import java.util.Date;

public class formation {
    private static int 	ID_de_Formation;

private String nomCategorie,Nom_de_Formation,Description,Durée,Niveau,Coût;
private Date Date_Deb,Date_Fin;

    public formation(String nomCategorie, String nom_de_Formation, String description, String durée, String niveau, Date date_Deb, Date date_Fin, String coût) {
        this.ID_de_Formation = ID_de_Formation;
        this.nomCategorie = nomCategorie;
        this.Nom_de_Formation = nom_de_Formation;
        this.Description = description;
        this.Durée = durée;
        this.Niveau = niveau;
        this.Date_Deb = date_Deb;
        this.Date_Fin = date_Fin;
        this.Coût = coût;


    }

    public formation() {

    }

    public static int getID_de_Formation() {
        return ID_de_Formation;
    }

    public void setID_de_Formation(int ID_de_Formation) {
        this.ID_de_Formation = ID_de_Formation;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getNom_de_Formation() {
        return Nom_de_Formation;
    }

    public void setNom_de_Formation(String nom_de_Formation) {
        Nom_de_Formation = nom_de_Formation;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDurée() {
        return Durée;
    }

    public void setDurée(String durée) {
        Durée = durée;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public String getCoût() {
        return Coût;
    }

    public void setCoût(String coût) {
        Coût = coût;
    }

    public Date getDate_Deb() {
        return Date_Deb;
    }

    public void setDate_Deb(Date date_Deb) {
        Date_Deb = date_Deb;
    }

    public Date getDate_Fin() {
        return Date_Fin;
    }

    public void setDate_Fin(Date date_Fin) {
        Date_Fin = date_Fin;
    }

    @Override
    public String toString() {
        return "formation{" +
                "ID_de_Formation=" + ID_de_Formation +
                ", nomCategorie='" + nomCategorie + '\'' +
                ", Nom_de_Formation='" + Nom_de_Formation + '\'' +
                ", Description='" + Description + '\'' +
                ", Durée='" + Durée + '\'' +
                ", Niveau='" + Niveau + '\'' +
                ", Date_Deb=" + Date_Deb +
                ", Date_Fin=" + Date_Fin +
                ", Coût='" + Coût + '\'' +

                '}';
    }
}
