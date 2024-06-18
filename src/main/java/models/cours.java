package models;

import java.util.Arrays;

public class cours {

    private  int 	idCour;
    private String 	Nom_Cours;
    private byte[] Description_Cours;
    private int IDfor;
    private byte[] pdf;

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public cours(int idCour, String nom_Cours, byte[] description_Cours, int IDfor) {
        this.idCour = idCour;
        Nom_Cours = nom_Cours;
        Description_Cours = description_Cours;
        this.IDfor = IDfor;
    }

    public cours() {

    }

    public cours(String nomCours, byte[] descriptionBytes, int idDeFormation) {
    }

    public int getIdCour() {
        return idCour;
    }

    public void setIdCour(int idCour) {
        this.idCour = idCour;
    }

    public String getNom_Cours() {
        return Nom_Cours;
    }

    public void setNom_Cours(String nom_Cours) {
        Nom_Cours = nom_Cours;
    }

    public byte[] getDescription_Cours() {
        return Description_Cours;
    }

    public void setDescription_Cours(byte[] description_Cours) {
        Description_Cours = description_Cours;
    }

    public int getIDfor() {
        return IDfor;
    }

    public void setIDfor(int IDfor) {
        this.IDfor = IDfor;
    }

    @Override
    public String toString() {
        return "cours{" +
                "idCour=" + idCour +
                ", Nom_Cours='" + Nom_Cours + '\'' +
                ", Description_Cours=" + Arrays.toString(Description_Cours) +
                ", IDfor=" + IDfor +
                '}';
    }


}