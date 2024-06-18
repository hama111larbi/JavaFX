package models;

public class Club {
    private int idclub;
    private int iduser;
    private String nameclub;
    private String email;
    private int numtlf;

    public Club(int idclub, int iduser, String nameclub, String email, int numtlf) {
        this.idclub = idclub;
        this.iduser = iduser;
        this.nameclub = nameclub;
        this.email = email;
        this.numtlf = numtlf;
    }

    public Club(int iduser, String nameclub, String email, int numtlf) {
        this.iduser = iduser;
        this.nameclub = nameclub;
        this.email = email;
        this.numtlf = numtlf;
    }

    public int getIdclub() {
        return idclub;
    }

    public void setIdclub(int idclub) {
        this.idclub = idclub;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNameclub() {
        return nameclub;
    }

    public void setNameclub(String nameclub) {
        this.nameclub = nameclub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumtlf() {
        return numtlf;
    }

    public void setNumtlf(int numtlf) {
        this.numtlf = numtlf;
    }

    @Override
    public String toString() {
        return
                "\tiduser=" + iduser +
                "\tnameclub='" + nameclub + '\'' +
                "\temail='" + email + '\'' +
                "\tnumtlf=" + numtlf +"\n";

    }
}
