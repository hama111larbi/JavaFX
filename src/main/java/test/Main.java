package test;

public class Main {
    public static void main(String[] args) {

    /*    projetpi db1 = projetpi.getInstance();
        projetpi db2 = projetpi.getInstance();

        // Define date and time formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Corrected time formatter

        // Parse date and time strings
        LocalDate date1 = LocalDate.parse("01/03/2002", dateFormatter);
        LocalTime time1 = LocalTime.parse("00:00:11", timeFormatter); // Corrected time string

        LocalDate date2 = LocalDate.parse("01/03/2002", dateFormatter);
        LocalTime time2 = LocalTime.parse("12:00:11", timeFormatter); // Corrected time string

        LocalDate date3 = LocalDate.parse("01/03/2002", dateFormatter);
        LocalTime time3 = LocalTime.parse("12:00:11", timeFormatter); // Corrected time string

        LocalDate date4 = LocalDate.parse("01/03/2002", dateFormatter);
        LocalTime time4 = LocalTime.parse("12:00:11", timeFormatter); // Corrected time string

        // Create event instances using the parsed date and time
        event e1 = new event("hhh","hhh",date3,time3);
        event e2 = new event();
        event e3 = new event();
        event e4 = new event();

        eventservice services = new eventservice();

        try {
            services.add(e1);
//            services.add(e3);
//            services.add(e4);*/
//            /*services.delete(2);*/
//            services.update(e1);

//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}}