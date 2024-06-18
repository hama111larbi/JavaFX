package Services;
import org.mindrot.jbcrypt.BCrypt;




import javax.sql.DataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;
import models.User;
import utils.MyDB;

import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserService implements IServices<User> {

    private Connection connection;
    private Statement ste;
    private BCrypt BCryptOld;


    public UserService(){
        connection = MyDB.getInstance().getConnection();
    }


    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO user(nom , prenom , mail , adresse , mdp,role,status,image,reset_code) VALUES( '" + user.getNom() + "' , '" + user.getPrenom() + "' , '" + user.getEmail() + "' , '" + user.getAdresse() + "' , '" + user.getMdp() + "','" + user.getrole() +  "','" + user.getStatus() +  "','" + user.getImage() +  "','" + user.getReset_code() + "')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET nom = ?, prenom = ?,  mail = ? , adresse = ? , mdp = ? ,role=?,status=?,image=?,reset_code=? WHERE iduser = ?";
        PreparedStatement us = connection.prepareStatement(req);
        us.setString(1, user.getNom());
        us.setString(2, user.getPrenom());
        us.setString(3, user.getAdresse());
        us.setString(4, user.getEmail());
        us.setString(5, user.getMdp());
        us.setString(6, user.getrole());
        us.setString(7, user.getStatus());
        us.setString(8, user.getImage());
        us.setString(9, user.getReset_code());
        us.setInt(10, user.getId());
        us.executeUpdate();
    }

    @Override
    public void supprimer(User user) {

    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req = "DELETE FROM user WHERE iduser = ? ";
        PreparedStatement us = connection.prepareStatement(req);
        us.setInt(1, id);
        us.executeUpdate();
    }


    @Override
    public ObservableList<User> recupperer() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        String req = "SELECT * FROM user";
        PreparedStatement us = connection.prepareStatement(req);
        ResultSet rs = us.executeQuery(req);


        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("iduser"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setAdresse(rs.getString("mail"));
            user.setEmail(rs.getString("adresse"));
            user.setMdp(rs.getString("mdp"));
            user.setrole(rs.getString("role"));
            user.setStatus(rs.getString("status"));
            user.setImage(rs.getString("image"));
            user.setReset_code(rs.getString("reset_code"));

            users.add(user);

        }
        return users;


    }

    public boolean authenticateUser(String email, String pass) {
        String query = "SELECT * FROM user WHERE email = ? AND mdp = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, pass);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String role(int id) {
        try {

            PreparedStatement stmt1 = connection.prepareStatement("SELECT role FROM user where id=?");
            stmt1.setInt(1, id);

            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "not found";
    }

    public List<User> displayAll() {
        String requete="select * from utilisateur";
        List<User> list=new ArrayList<>();

        try {
            ste=connection.createStatement();
            ResultSet et=ste.executeQuery(requete);
            while (et.next()){
                list.add(new User(et.getInt("id"),et.getString("nom"),et.getString("prenom"),et.getString("adresse"),et.getString("email"),et.getString("mdp"),et.getString("role")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public User getUserByEmail(String email) throws SQLException {

        String req = "SELECT * FROM user WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(req);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            User p = new User(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("adresse"),
                    rs.getString("mdp"),rs.getString("role"));
            return p;
        } else {
            return null; // no person found with this id
        }
    }
    public int getIdByEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = -1; // Default value if user is not found or there's an error

        try {
            conn = MyDB.getInstance().getConnection();
            String query = "SELECT id FROM users WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
        } finally {
            // Close resources
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return userId;
    }
    public int idg(String email) {
        try {

            PreparedStatement stmt1 = connection.prepareStatement("SELECT id FROM user where email=?");
            stmt1.setString(1, email);

            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public String emailg(int id) {
        try {

            PreparedStatement stmt1 = connection.prepareStatement("SELECT email FROM user where id=?");
            stmt1.setInt(1, id);

            ResultSet rs = stmt1.executeQuery();

            while (rs.next()) {
                return rs.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void envoyerEmailRecuperation(String email, String codeRecuperation) {
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du chiffrement TLS
        String password = "qjkw tpnc sryq dzku";
        String username = "manideliro@gmail.com";


        // Création d'une session avec l'authentification
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        PasswordAuthentication passwordAuthentication = new PasswordAuthentication(username, password);
                        return passwordAuthentication;
                    }
                });

        try {
            // Création d'un message MIME
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manideliro@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Adresse e-mail de destination
            message.setSubject("Récupération de mot de passe"); // Sujet de l'e-mail
            message.setText("Votre code de récupération de mot de passe est : " + codeRecuperation); // Corps de l'e-mail avec le code de récupération

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de récupération envoyé avec succès à " + email);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCodeRecuperation() {
        return codeRecuperation;
    }
    private static String codeRecuperation; // Variable pour stocker le code de récupération

    public String generateRandomCode() {
        // Générer un code aléatoire de 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return codeRecuperation = String.valueOf(code);
    }
    public boolean emailExiste(String email) throws SQLException {
        // Requête SQL pour vérifier si l'email existe déjà dans la base de données
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Utilisation de la connexion existante
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            // Vérifier si des résultats sont retournés
            if (resultSet.next()) {
                // Si des résultats sont retournés, l'email existe déjà
                return true;
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        // Aucun résultat trouvé, donc l'email n'existe pas encore
        return false;
    }
    public User getByEmail(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            user.setAdresse(resultSet.getString("adresse"));
            user.setEmail(resultSet.getString("email"));
            user.setMdp(resultSet.getString("mdp"));
        }

        return user;
    }
    public User authentifier(String email, String mdp) throws SQLException {
        // Retrieve the user from the database based on the provided email
        User user = getByEmail(email);

        if (user != null) {
            // Retrieve the hashed password stored in the database
            String hashedPassword = user.getMdp();

            // Verify the provided password using the hashed password from the database
            // Passwords match, return the authenticated user
            // Passwords do not match, return null
            if (checkPasswordUsingOldBCrypt(mdp, hashedPassword)) return null;
            else {
                return user;
            }
        } else {
            // User with the provided email not found, return null
            return null;
        }
    }

    private boolean checkPasswordUsingOldBCrypt(String plainPassword, String hashedPassword) {
        // Check password using an older version of BCrypt
        // This method should use the same version of BCrypt that was used to hash passwords
        return BCryptOld.checkpw(plainPassword, hashedPassword);
    }

    public void modifierMotDePasse(String email, String nouveauMdp) throws SQLException {
        // Construire la requête SQL pour mettre à jour le mot de passe de l'utilisateur avec l'e-mail donné
        String query = "UPDATE user SET mdp = ? WHERE email = ?";
        // Préparer la requête SQL
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Remplacer les paramètres de la requête par les valeurs correspondantes
            statement.setString(1, nouveauMdp);
            statement.setString(2, email);
            // Exécuter la requête SQL
            statement.executeUpdate();
        }
    }
    public User authentifier2(String email, String mdp) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtention d'une connexion à la base de données
            connection = MyDB.getInstance().getConnection();

            // Requête SQL pour rechercher l'utilisateur par email et mot de passe
            String query = "SELECT * FROM user WHERE email = ? AND mdp = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, mdp);

            // Exécution de la requête SQL et récupération du résultat
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Récupérer les informations de l'utilisateur depuis la base de données
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                String role = resultSet.getString("role");

                // Créer un nouvel utilisateur avec les informations récupérées
                return new User(id, nom, prenom, adresse, email, mdp, role);
            }
        } finally {
            // Fermeture des ressources pour libérer les connexions et éviter les fuites de ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        // Retourner null si aucun utilisateur correspondant n'a été trouvé
        return null;
    }

}