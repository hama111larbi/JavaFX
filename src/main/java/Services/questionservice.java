package Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Questions;
import models.quiz;
import utils.MyDB;

import java.sql.*;

public class questionservice implements IService<Questions> {
    private Connection connection;

    public questionservice() {
        connection = MyDB.getInstance().getConnection();
    }

    public void add1(Questions questions) throws SQLException {
       // int idQuiz = add0(questions.getQuiz());
        quizservice qu=new quizservice() {
        };
       /// quiz q=new quiz();
        String sql = "INSERT INTO questions (op1,op2,op3,answer,question,idquiz) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        quiz q=new quiz();
        ps.setString(1, questions.getOp1());
        ps.setString(2, questions.getOp2());
        ps.setString(3, questions.getOp3());
        ps.setString(4, questions.getAnswer());
        ps.setString(5, questions.getQuestion());
        ps.setInt(6, questions.getIdquiz());
        ps.executeUpdate();

    }




    @Override
    public void update(Questions questions, int id) throws SQLException {
        String sql = "UPDATE questions SET op1=? ,op2=? , op3=? ,answer=?,question=?,idquiz=? WHERE idquest=?";
        // user u= new user();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, questions.getOp1());
        preparedStatement.setString(2, questions.getOp2());
        preparedStatement.setString(3,questions.getOp3());
        preparedStatement.setString(4,questions.getAnswer());
        preparedStatement.setString(5,questions.getQuestion());
        preparedStatement.setInt(6,questions.getIdquiz());
        preparedStatement.setInt(7,id);
        preparedStatement.executeUpdate();
        System.out.println("questionsmodifiée");
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from questions where idquest =?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public ObservableList<Questions> getAll() throws SQLException {
        ObservableList<Questions>list = FXCollections.observableArrayList();
        String sql ="SELECT questions.idquest,questions.op1,questions.op2,questions.op3,questions.answer,questions.question,quiz.nom,quiz.idquiz from questions INNER JOIN quiz on questions.idquiz=quiz.idquiz ";
        Statement stat = connection.prepareStatement(sql);
        ResultSet rs= stat.executeQuery(sql);
        while (rs.next())
        {
           Questions q=new Questions();

            q.setIdquest(rs.getInt("idquest"));
            q.setOp1(rs.getString("op1"));
            q.setOp2(rs.getString("op2"));
            q.setOp3(rs.getString("op3"));
            q.setAnswer(rs.getString("answer"));
            q.setQuestion(rs.getString("question"));
            q.setNom(rs.getString("nom"));
            q.setIdquiz(rs.getInt("idquiz"));

            //q.setUser.g("1");//q.setDescription(rs.getString("description"));
            list.add(q);
        }
        return list;
    }

    @Override
    public ObservableList<Questions> get() throws SQLException {
        return null;
    }

    public ObservableList<Questions> get(int idquiz) throws SQLException {
        ObservableList<Questions> list = FXCollections.observableArrayList();
        String sql = "SELECT idquest, op1, op2, op3, question,answer FROM questions WHERE idquiz=?";
        PreparedStatement stat = connection.prepareStatement(sql);
        stat.setInt(1, idquiz);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            Questions q = new Questions();
            q.setIdquest(rs.getInt("idquest"));
            q.setOp1(rs.getString("op1"));
            q.setOp2(rs.getString("op2"));
            q.setOp3(rs.getString("op3"));
            q.setQuestion(rs.getString("question"));
            q.setAnswer(rs.getString("answer"));
            q.setIdquiz(idquiz);
            list.add(q);
        }
        return list;
    }
    @Override
    public ObservableList getById(int id) throws SQLException {
        return null;
    }
    public ObservableList<Questions> search(String s) throws SQLException {
        ObservableList<Questions>list = FXCollections.observableArrayList();
        String sql ="SELECT questions.idquest,questions.op1,questions.op2,questions.op3,questions.answer,questions.question,quiz.nom,quiz.nbrquest,quiz.idquiz from questions INNER JOIN quiz on questions.idquiz=quiz.idquiz WHERE questions.question like ? ";
       PreparedStatement stat = connection.prepareStatement(sql);
        stat.setString(1,"%" +s + "%");
        ResultSet rs= stat.executeQuery(sql);
        while (rs.next())
        {
            Questions q=new Questions();

            q.setIdquest(rs.getInt("idquest"));
            q.setOp1(rs.getString("op1"));
            q.setOp2(rs.getString("op2"));
            q.setOp3(rs.getString("op3"));
            ///  q.setAnswer(rs.getString("answer"));
            q.setQuestion(rs.getString("question"));
            ///  q.setNom(rs.getString("nom"));
            ///  q.setNbrquest(rs.getInt("nbrquest"));
            q.setIdquiz(rs.getInt("idquiz"));

            //q.setUser.g("1");//q.setDescription(rs.getString("description"));
            list.add(q);
        }
        return list;
    }
}
