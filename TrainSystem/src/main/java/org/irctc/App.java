package org.irctc;
import org.irctc.Services.BookingServices;
import org.irctc.db.TicketDAO;
import org.irctc.db.TrainDAO;
import org.irctc.db.UserDAO;
import org.irctc.model.Ticket;
import org.irctc.model.Train;
import org.irctc.model.User;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("*====WELCOME TO THE IRCTC TICKET BOOKING SYSTEM====*");
        while(true){
            System.out.println("\n1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1-> {
                    System.out.println("ENTER YOUR NAME:-");
                    String name = scanner.nextLine();
                    System.out.println("ENTER YOUR E-mail");
                    String mail = scanner.nextLine();
                    System.out.println("ENTER YOUR PASSWORD:-");
                    String pass = scanner.nextLine();

                    User user = new User(name, mail, pass);
                    if (userDAO.registerUser(user)) {
                        System.out.println("✅ USER REGISTERED SUCCESSFULLY:-");
                    } else {
                        System.out.println("❌ SIGN-UP FAILED:-");
                    }
                }
                case 2->{
                    System.out.println("ENTER YOUR E-mail");
                    String email = scanner.nextLine();
                    System.out.println("ENTER YOUR PASSWORD--");
                    String pass = scanner.nextLine();

                    String role =userDAO.loginUser(email, pass);
                    if(role!=null){
                        System.out.println("LOGIN SUCCESSFULLY--" + role);

                        if(role.equals("admin")){
                            while(true) {
                                System.out.println("\n ----* ADMIN MENU *----");
                                System.out.println("1. ADD TRAIN");
                                System.out.println("2. VIEW ALL TRAIN");
                                System.out.println("3. LOGOUT");
                                int adminChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (adminChoice) {
                                    case 1 -> {
                                        System.out.println("ENTER TRAIN NAME :-");
                                        String name = scanner.nextLine();
                                        System.out.println("ENTER SOURCE:-");
                                        String source = scanner.nextLine();
                                        System.out.println("ENTER DESTINATION :-");
                                        String desti = scanner.nextLine();
                                        System.out.println("ENTER THE TOTAL SEATS :--");
                                        int seats = scanner.nextInt();

                                        TrainDAO trainDAO = new TrainDAO();
                                        Train train = new Train(name, source, desti, seats, seats);

                                        if (trainDAO.addTrain(train)) {
                                            System.out.println("✅ TRAIN ADDED SUCCESSFULLY :---");
                                        } else {
                                            System.out.println("❌ FAILED TO ADD THE TRAIN :---");
                                        }
                                    }
                                    case 2 -> {
                                        System.out.println(" ALL TRAIN LISTED BELOW:--");
                                        TrainDAO trainDAO = new TrainDAO();
                                        var trains = trainDAO.getAllTrain();
                                        if (trains.isEmpty()) {
                                            System.out.println("⚠ NO AVAILABLE TRAINS");
                                        } else {
                                            System.out.println("\n--- List Of Trains ---");
                                            for (Train i : trains) {
                                                System.out.println(
                                                        "ID: " + i.getTrain_id() +
                                                                " | Name: " + i.getTrainName() +
                                                                " | From: " + i.getTrainSource() +
                                                                " | To: " + i.getDestination() +
                                                                " | Seats: " + i.getAvailableSeat() + "/" + i.getTotalSeat()
                                                );
                                            }
                                        }
                                    }
                                    case 3 -> {
                                        System.out.print("LOGOUT....");
                                        int i = 5;
                                        while (i != 0) {
                                            System.out.print(".");
                                            Thread.sleep(450);
                                            i--;
                                        }
                                        break;
                                    }
                                    default -> System.out.println("❌ Invalid choice!");

                                }   if (adminChoice == 3) {
                                    break;
                                }
                            }
                        }

                        else {
                            while (true) {
                                System.out.println("\n--- User Menu ---");
                                System.out.println("1. Search Train");
                                System.out.println("2. Book Ticket");
                                System.out.println("3. View Reservation");
                                System.out.println("4. Cancel Ticket");
                                System.out.println("5. Logout");

                                System.out.println();
                                System.out.println("ENTER THE CHOICE BY NUMBER:---");
                                int userChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (userChoice) {
                                    case 1 -> {
                                        System.out.println("ENTER THE TRAIN SOURCE");
                                        String source = scanner.nextLine();
                                        System.out.println("ENTER THE DESTINATION:--");
                                        String desti = scanner.nextLine();

                                        TrainDAO trainDAO = new TrainDAO();
                                        var trains = trainDAO.SearchTrains(source,desti);

                                        if(trains.isEmpty()){
                                            System.out.println("⚠️ NO TRAINS FOUND FOR THIS ROUTE");
                                        }else{
                                            System.out.println("\n --- AVAILABLE TRAINS --- ");
                                            for(Train t : trains){
                                                System.out.println("ID: "+ t.getTrain_id()+
                                                        " | Name: "+t.getTrainName()+
                                                        " | From: "+t.getTrainSource()+
                                                        " | To: "+t.getDestination()+
                                                        " | Seats: "+t.getAvailableSeat()+"/"+t.getTotalSeat());
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        String userEmail = email;
                                        System.out.println("ENTER TRAIN ID TO BOOK TICKET...");
                                        int bookId = scanner.nextInt();
                                        System.out.println("ENTER NUMBER OF SEATS TO BOOK...");
                                        int seats = scanner.nextInt();
                                        scanner.nextLine();

                                        Ticket ticket = new Ticket(userEmail, bookId, seats);
                                        TicketDAO ticketDAO = new TicketDAO();

                                        if(ticketDAO.bookTicket(ticket)){
                                            System.out.println("✅ TICKET BOOKED SUCCESSFULLY...");
                                            System.out.println("DEBUG: Trying to book " + seats + " seats on Train ID " + bookId);
                                        }else{
                                            System.out.println("❌ BOOKING FAILED ! NOT ENOUGH SEATS..");
                                        }


                                    }
                                    case 3 -> {
                                        TicketDAO ticketDAO = new TicketDAO();
                                        var tickets = ticketDAO.getTicketsByEmail(email);

                                        if (tickets.isEmpty()) {
                                            System.out.println("⚠ No tickets found!");
                                        } else {
                                            System.out.println("--- Your Tickets ---");
                                            for (Ticket t : tickets) {
                                                System.out.println("Ticket ID: " + t.getTicketId() +
                                                        " | Train ID: " + t.getTrainId() +
                                                        " | Seats: " + t.getSeatBooked());
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        System.out.println("👉 Cancel Ticket feature here...");
                                        System.out.println("ENTER YOUR TICKET ID TO CANCEL:-");
                                        int ticketid = scanner.nextInt();
                                        scanner.nextLine();

                                        TicketDAO ticketDAO = new TicketDAO();
                                        if(ticketDAO.cancelTicket(ticketid)){
                                            System.out.println("✅ TICKET CANCEL SUCCESSFULLY:--");
                                        }else{
                                            System.out.println("❌ CANCELLATION FAILED ! TICKET NOT FOUND..");
                                        }
                                    }
                                    case 5 -> {
                                        System.out.print("Logging out...");
                                        int i=5;
                                        while(i!=0){
                                            System.out.print(".");
                                            Thread.sleep(450);
                                            i--;
                                        }
                                        break;
                                    }
                                    default -> System.out.println("❌ Invalid choice!");
                                }

                                if (userChoice == 5) break;
                            }
                        }
                    } else {
                        System.out.println("❌ LOGIN FAILED: Invalid email or password.");
                    }

                }
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                }

            }

        }
        }
    }
