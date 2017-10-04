package BackEnd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class deals with the transfer of data between the client, database, and the system.
 * It receives information and decides what action to take.
 * 
 * @author Tevin Schmidt
 * @author James Bews
 *
 */
public class Server{
	
	/**
	 * The port number for the sockets
	 */
	static final int PORTNUM = 3306;
	
	/**
	 * The driver to connect to the database with
	 */
	private Driver driver;
	
	/**
	 * The socket for the server
	 */
	private ServerSocket serverSocket;
	
	/**
	 * The socket of the current client being handled
	 */
	private Socket socket;
	
	/**
	 * The List of all the flights 
	 */
	private LinkedList<Flight> flights;
	


	/**
	 * Constructs a Server with able to handle the given number of threads
	 * @param numThreads is the desired number of threads 
	 */
	public Server(int numThreads){
		driver = new Driver();
		driver.initialize();
		flights = new LinkedList<Flight>();
		

		
	}
	
	/**
	 * Setup of the ThreadPool to be used to manage the clients
	 */
	public void run(){
		ThreadPool pool = new ThreadPool(6);
		
		try{
			serverSocket = new ServerSocket(PORTNUM);
		}
		catch(IOException e){
			System.err.println("There was an error creating the serverSocket.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating...");
			System.exit(1);
		}
		
		while(true){
			try{
				System.out.println("Waiting For client.");
				socket = serverSocket.accept();
			}
			catch(IOException e){
				System.err.println("Error connecting to socket.");
				System.err.println(e.getMessage());
				System.err.println("Program terminating...");
				System.exit(1);
			}
			
			Task newTask = new Task(this, socket);
			System.out.println("Client has connected.");
			pool.execute(newTask);	
		
		}
	}
	
	/**
	 * Gets the driver so it can be used to connect to the database
	 * @return the driver for the server
	 */
	public Driver getDriver(){
		return this.driver;
	}
	
	public synchronized int nextFlightNum(){
		synchronized (flights){
			updateFlightList();
			return (flights.size() + 1);
		}
	}
	
	/**
	 * Adds all flights from the given text file
	 * @param inputName is the name of the inputFile
	 */
	public synchronized void addFlightList(String inputName){
		
		try{
			FileReader reader = new FileReader(inputName + ".txt");
			BufferedReader read = new BufferedReader(reader);
			String current = read.readLine();
			
			while(true){
				if(current == null){
					break;
				}
				
				String[] values = current.split(";");
				Flight newFlight = new Flight(values);
				flights.add(newFlight);
				insertTickets(newFlight.getTickets(), newFlight.getFlightNumber());
				
				current = read.readLine();
				
			}
			
			insertFlights(flights);
			
			read.close();
			
		}
		catch(IOException e){
			System.err.println("Problem reading from file.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating.");
			System.exit(1);
		}
		
		
	}
	
	/**
	 * This inserts The given list of flights into the database
	 * @param flights is the LinkedList of flights to be added
	 */
	public synchronized void insertFlights(LinkedList<Flight> flights){
		for(int i = 0; i < flights.size(); i++){
			String [] toInsert = new String[8];
			toInsert[0] = flights.get(i).getDestination();
			toInsert[1] = flights.get(i).getSource();
			toInsert[2] = flights.get(i).getDepartureTime();
			toInsert[3] = flights.get(i).getDuration();
			toInsert[4] = Integer.toString(flights.get(i).getTotalSeats());
			toInsert[5] = Integer.toString(flights.get(i).getSeatsAvailable());
			toInsert[6] = Float.toString(flights.get(i).getPrice());
			toInsert[7] = flights.get(i).getDate();
			
			String sqlOut = "insert into flights"
					+ "(flightNumber, destination, source, departureTime, duration, totalSeats, seatsAvailable, price, date)"
					+ "values ('" + flights.get(i).getFlightNumber() + "', '"
					+ toInsert[0] + "', '" + toInsert[1] + "', '" + toInsert[2] + "', '" 
					+ toInsert[3] + "', '" + toInsert[4] + "', '" + toInsert[5] + "', '" 
					+ toInsert[6] + "', '" + toInsert[7] + "')";
			
			try{
				driver.getState().executeUpdate(sqlOut);
			}
			catch(SQLException e){
				System.err.println("There was a problem inserting to flights.");
				System.err.println(e.getMessage());
				System.err.println("Program terminating...");
				System.exit(1);
			}
		}
	}
	
	public synchronized void insertFlightsFF(LinkedList<Flight> flightsList){
		synchronized (flights){
			updateFlightList();
			int nextFN = nextFlightNum();
			System.out.println(nextFN);
			for(int i = 0; i < flightsList.size(); i++){
				flightsList.get(i).setFlightNumber(nextFN);
				flightsList.get(i).createTickets(flightsList.get(i).getTotalSeats());
				nextFN++;
			}
			
		
		
			for(int i = 0; i < flightsList.size(); i++){
				String [] toInsert = new String[8];
				toInsert[0] = flightsList.get(i).getDestination();
				toInsert[1] = flightsList.get(i).getSource();
				toInsert[2] = flightsList.get(i).getDepartureTime();
				toInsert[3] = flightsList.get(i).getDuration();
				toInsert[4] = Integer.toString(flightsList.get(i).getTotalSeats());
				toInsert[5] = Integer.toString(flightsList.get(i).getSeatsAvailable());
				toInsert[6] = Float.toString(flightsList.get(i).getPrice());
				toInsert[7] = flightsList.get(i).getDate();
			
				String sqlOut = "insert into flights"
						+ "(flightNumber, destination, source, departureTime, duration, totalSeats, seatsAvailable, price, date)"
						+ "values ('" + flightsList.get(i).getFlightNumber() + "', '"
						+ toInsert[0] + "', '" + toInsert[1] + "', '" + toInsert[2] + "', '" 
						+ toInsert[3] + "', '" + toInsert[4] + "', '" + toInsert[5] + "', '" 
						+ toInsert[6] + "', '" + toInsert[7] + "')";
			
				try{
					driver.getState().executeUpdate(sqlOut);
				}
				catch(SQLException e){
					System.err.println("There was a problem inserting to flights.");
					System.err.println(e.getMessage());
					System.err.println("Program terminating...");
					System.exit(1);
				}
				
				insertTickets(flightsList.get(i).getTickets(), flightsList.get(i).getFlightNumber());
			}
		}
	}
	
	/**
	 * Inserts the given LinkedList of tickets into the database
	 * @param tickets is the Linked list of tickets
	 * @param flightNumber is the flight number to be associated with them
	 */
	public synchronized void insertTickets(LinkedList<Ticket> tickets, int flightNumber){
		for(int i = 0, j = 1; i < tickets.size(); i++, j++){
			String [] toInsert = new String[8];
			toInsert[0] = tickets.get(i).getLastName();
			toInsert[1] = tickets.get(i).getFirstName();
			toInsert[2] = null;
			toInsert[3] = tickets.get(i).getDestination();
			toInsert[4] = tickets.get(i).getSource();
			toInsert[5] = tickets.get(i).getDepartureTime();
			toInsert[6] = tickets.get(i).getDuration();
			toInsert[7] = tickets.get(i).getDate();
			
			String sqlOut = "insert into tickets"
					+ "(flightNumber, seatNumber, lastName, firstName, dateOfBirth, destination, source, departureTime, duration, date, available)"
					+ "values ('" + flightNumber + "', '" + j + "', '" + toInsert[0] + "', '" + toInsert[1] + "', '" 
					+ toInsert[2] + "', '" + toInsert[3] + "', '" + toInsert[4] + "', '"
					+ toInsert[5] + "', '" + toInsert[6] + "', '" + toInsert[7] + "', '" + 1 + "')";
		
			try{
				driver.getState().executeUpdate(sqlOut);
			}
			catch(SQLException e){
				System.err.println("There was a problem inserting to tickets.");
				System.err.println(e.getMessage());
				System.err.println("Program terminating...");
				System.exit(1);
			}
		}
	}
	
	/**
	 * Inserts a single flight into the database
	 * @param toInsert is the string containing flight info to insert into the database
	 */
	public synchronized void insertFlight(String[] toInsert){
		int flightNumber = nextFlightNum();
		Flight newFlight = new Flight();
		newFlight.setFlightNumber(flightNumber);
		newFlight.setDestination(toInsert[1]);
		newFlight.setSource(toInsert[2]);
		newFlight.setDepartureTime(toInsert[3]);
		newFlight.setDuration(toInsert[4]);
		newFlight.setTotalSeats(Integer.parseInt(toInsert[5]));
		newFlight.setAvailable(Integer.parseInt(toInsert[6]));
		newFlight.setPrice(Float.parseFloat(toInsert[7]));
		newFlight.setDate(toInsert[8]);
		
		newFlight.createTickets(Integer.parseInt(toInsert[5]));
		
		LinkedList<Flight> toAdd = new LinkedList<Flight>();
		toAdd.add(newFlight);
		insertFlights(toAdd);
		insertTickets(newFlight.getTickets(), flightNumber);
	}
	
	/**
	 * Deletes a single flight from the database
	 * @param toDelete is the flightNumber of the flight to delete
	 */
	public synchronized void deleteFlight(String toDelete){
		String sqlOut = "delete from flights where flightNumber='" + Integer.parseInt(toDelete) + "'";
		
		try{
			driver.getState().executeUpdate(sqlOut);
		}
		catch(SQLException e){
			System.err.println("There was a problem deleting from flights.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating...");
			System.exit(1);
		}
	}
	
	/**
	 * Deletes tickets associated with a flight 
	 * @param toDelete is the flight number of the tickets to delete
	 */
	public synchronized void deleteFlightTickets(String toDelete){
		String sqlOut = "delete from tickets where flightNumber='" + Integer.parseInt(toDelete) + "'";
		
		try{
			driver.getState().executeUpdate(sqlOut);
		}
		catch(SQLException e){
			System.err.println("There was a problem deleting from tickets.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating...");
			System.exit(1);
		}
	}
	
	/**
	 * This updates the LinkedList of flights from the data base
	 */
	public synchronized void updateFlightList(){
		synchronized (flights){	
			flights = driver.returnFlights();
		}
	}
	
	/**
	 * Searches for flights based on the given parameters
	 * @param source is the source of the flight
	 * @param destination is the destination of the flight
	 * @param date is the date of the flight
	 * @return is the LinkedList containing all relevent flights
	 */
	public synchronized LinkedList<Flight> search(String source, String destination, String date){
		LinkedList<Flight> rv = new LinkedList<Flight>();
		synchronized (flights) {
			updateFlightList();
			System.out.println(flights.size());
			for(int i = 0; i < flights.size(); i++){
				Flight tempFlight = flights.get(i);
				if(tempFlight.getSource().contentEquals(source) 
						&& tempFlight.getDestination().contentEquals(destination) 
						&& tempFlight.getDate().contentEquals(date)){
					
					rv.add(tempFlight);
					
				}
			}
		}
		
		return rv;
	}
	
	/**
	 * Books a ticket based of off the given information
	 * @param flightNumber is the flight number of the flight
	 * @param firstName is the first name of the passenger
	 * @param lastName is the last name of the passenger
	 * @param dateOfBirth is the date of birth of the passenger
	 * @return is the ticket to send to the client
	 */
	public synchronized Ticket bookTicket(int flightNumber, String firstName, String lastName, String dateOfBirth){
		synchronized(flights) {
			updateFlightList();
			LinkedList<Ticket> tempTickets = new LinkedList<Ticket>();
			for(int i = 0; i < flights.size(); i++){
				Flight tempFlight = flights.get(i);
				if(tempFlight.getFlightNumber() == flightNumber){
					tempTickets = tempFlight.getTickets();
					break;
				}
			}
			
			for(int i = 0, j = 1; i < tempTickets.size(); i++, j++){
				Ticket tempTicket = tempTickets.get(i);
				if(tempTicket.getAvailable()){
					updateTicketInDB(j,flightNumber,firstName,lastName,dateOfBirth);
					return tempTicket;
				}
			}
			
		}
		return null;
	}
	
	public synchronized LinkedList<Ticket> allBookedTickets(){
		synchronized (flights){
			updateFlightList();
			LinkedList<Ticket> rv = new LinkedList<Ticket>();
			LinkedList<Ticket> localTickets = new LinkedList<Ticket>();
			for(int i = 0; i < flights.size(); i++){
				localTickets = flights.get(i).getTickets();
				for(int j = 0; j < localTickets.size(); j++){
					if(!localTickets.get(j).getAvailable()){
						rv.add(localTickets.get(j));
					}
				}
			}
			return rv;
		}
	}
	
	
	/**
	 * Changes the status of the ticket to not available, and puts the passenger
	 * information into the ticket
	 * @param seatNum is the seat number of the ticket
	 * @param flightNumber is the flight number of the ticket
	 * @param firstName is the first name of the passenger
	 * @param lastName is the last name of the passenger
	 * @param dateOfBirth is the date of birth of the passenger
	 */
	public synchronized void updateTicketInDB(int seatNum, int flightNumber, String firstName, String lastName, String dateOfBirth){
		Flight tempFlight = searchFlights(flightNumber);
		int seatsLeft = tempFlight.getSeatsAvailable();
		seatsLeft--;
		
		String sqlOut1 = "UPDATE tickets SET firstName='" + firstName + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut2 = "UPDATE tickets SET lastName='" + lastName + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut3 = "UPDATE tickets SET dateOfBirth='" + dateOfBirth + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut4 = "UPDATE tickets SET available='" + 0 + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut5 = "UPDATE flights SET seatsAvailable='" + seatsLeft + "' WHERE (flightNumber='" + flightNumber  +"')";
		
		try{
			driver.getState().executeUpdate(sqlOut1);
			driver.getState().executeUpdate(sqlOut2);
			driver.getState().executeUpdate(sqlOut3);
			driver.getState().executeUpdate(sqlOut4);
			driver.getState().executeUpdate(sqlOut5);
		}
		catch(SQLException e){
			System.err.println("There was a problem updating ticket.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating...");
			System.exit(1);
		}
	}
	
	/**
	 * Deletes a valid ticket and updates its status to available
	 * @param flightNumber is the flight number of the ticket
	 * @param seatNum is the seat number of the ticket
	 */
	public synchronized void deleteTicket(int flightNumber, int seatNum){	
		Flight tempFlight = searchFlights(flightNumber);
		int seatsLeft = tempFlight.getSeatsAvailable();
		seatsLeft++;
		
		String sqlOut1 = "UPDATE tickets SET firstName='" + null + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut2 = "UPDATE tickets SET lastName='" + null + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut3 = "UPDATE tickets SET dateOfBirth='" + null + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut4 = "UPDATE tickets SET available='" + 1 + "' WHERE (flightNumber='" + flightNumber + "' AND seatNumber='" + seatNum +"')";
		String sqlOut5 = "UPDATE flights SET seatsAvailable='" + seatsLeft + "' WHERE (flightNumber='" + flightNumber  +"')";
		
		try{
			driver.getState().executeUpdate(sqlOut1);
			driver.getState().executeUpdate(sqlOut2);
			driver.getState().executeUpdate(sqlOut3);
			driver.getState().executeUpdate(sqlOut4);
			driver.getState().executeUpdate(sqlOut5);
		}
		catch(SQLException e){
			System.err.println("There was a problem updating ticket.");
			System.err.println(e.getMessage());
			System.err.println("Program terminating...");
			System.exit(1);
		}
	}
	

	/**
	 * Searches for a particular flight in the LinkedList
	 * @param flightNumber is the number of the flight to find
	 * @return is the Flight with the given number 
	 */
	private synchronized Flight searchFlights(int flightNumber){
		synchronized (flights){
			for(int i = 0; i < flights.size(); i++){
				Flight tempFlight = flights.get(i);
				if(tempFlight.getFlightNumber() == flightNumber){
					return tempFlight;
				}
			}
			
			return null;
			
		}
	}
	
	/**
	 * Sets up the server on startup
	 * @param args is the command line arguments
	 */
	public static void main(String []args){
		Server theServer = new Server(10);
		if(args.length == 1){
			theServer.addFlightList(args[0]);
		}
		theServer.run();
	}
}
