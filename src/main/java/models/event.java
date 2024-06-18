package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class event {
    private int id;
    private String lieu;
    private String name;
    private LocalDate date;
    private LocalTime heure;
    private int idClub;
    private int idUser;

    public event(String nameValue, String lieuValue) {
        this.name = nameValue;
        this.lieu = lieuValue;
    }

    public event() {
        // Initialiser les attributs à des valeurs par défaut si nécessaire
    }

    public event(String nameValue, String lieuValue, LocalDate dateValue, LocalTime timeValue, int idClub, int idUser) {
        this.name = nameValue;
        this.lieu = lieuValue;
        this.date = dateValue;
        this.heure = timeValue;
        this.idClub = idClub;
        this.idUser = idUser;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return
                "\tlieu='" + lieu +
                "\tname='" + name +
                "\tdate=" + date +
                "\theure=" + heure +
                "\tidClub=" + 1 +
                "\tidUser=" + 1 +"\n";

    }
}
