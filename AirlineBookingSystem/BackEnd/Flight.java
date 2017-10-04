package BackEnd;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * This class holds all information for a given flight. It also contains
 * the list of available tickets that are able to be booked or unbooked.
 * 
 * @author Tevin Schmidt
 * @author James Bews
 * @since March 31, 2017
 *
 */
public class Flight implements Serializable{
		
	/**
	 * The serialVersionUID for Flight
	 */
	private static final long serialVersionUID = 4748L;
	
	/**
	 * This is the linked list containing all tickets for the flight
	 */
	private LinkedList<Ticket> tickets;
	
	/**
	 * The flight number of the Flight
	 */
	private int flightNumber;
	
	/**
	 * The total amount of seats on the Flight
	 */
	private int totalSeats;
	
	/**
	 * The amount of seats left available on the Flight
	 */
	private int seatsAvailable;
	
	/**
	 * The source of the Flight
	 */
	private String source;
	
	/**
	 * The destination of the Flight
	 */
	private String destination;
	
	/**
	 * The departure time of the Flight
	 */
	private String departureTime;
	
	/**
	 * The duration of the Flight
	 */
	private String duration;
	
	/**
	 * The price of the Flight
	 */
	private float price;
	
	/**
	 * The date of the Flight
	 */
	private String date;
	
	
	/**
	 * Constructs a default flight that is empty
	 */
	public Flight(){
		this.tickets = new LinkedList<Ticket>();
	}
	
	/**
	 * Constructs a flight with the given values in the string
	 * @param methods is the Strings that have the required information
	 */
	public Flight(String [] methods){
		if(methods.length == 9){
			this.flightNumber = Integer.parseInt(methods[0]);
			this.destination = methods[1];
			this.source = methods[2];
			this.departureTime = methods[3];
			this.duration = methods[4];
			this.totalSeats = Integer.parseInt(methods[5]);
			this.seatsAvailable = Integer.parseInt(methods[6]);
			this.price = Float.parseFloat(methods[7]);
			this.date = methods[8];
			
			createTickets(totalSeats);

		}
	}
	
	/**
	 * Sets the flight number
	 * @param flightNumber is the desired flight number
	 */
	public void setFlightNumber(int flightNumber){
		this.flightNumber = flightNumber;
	}
	
	/**
	 * Sets the total seats
	 * @param totalSeats is the desired total seats
	 */
	public void setTotalSeats(int totalSeats){
		this.totalSeats = totalSeats;
	}
	
	/**
	 * Sets the number of available seats
	 * @param seatsAvailable is the desired number of available seats
	 */
	public void setAvailable(int seatsAvailable){
		this.seatsAvailable = seatsAvailable;
	}
	
	/**
	 * Sets the source of the Flight
	 * @param source is the desired source
	 */
	public void setSource(String source){
		this.source = source;
	}
	
	/**
	 * Sets the destination of the Flight
	 * @param destination is the desired destination
	 */
	public void setDestination(String destination){
		this.destination = destination;
	}
	
	/**
	 * Sets the departure time of the Flight
	 * @param departureTime is the desired departure time
	 */
	public void setDepartureTime(String departureTime){
		this.departureTime = departureTime;
	}
	
	/**
	 * Sets the duration of the Flight
	 * @param duration is the desired duration
	 */
	public void setDuration(String duration){
		this.duration = duration;
	}
	
	/**
	 * Sets the price of the Flight
	 * @param price is the desired price
	 */
	public void setPrice(float price){
		this.price = price;
	}
	
	/**
	 * Sets the date of the Flight
	 * @param date is the desired date
	 */
	public void setDate(String date){
		this.date = date;
	}
	
	/**
	 * Sets the List of tickets to the desired linked list
	 * @param tickets is the desired LinkedList of Tickets
	 */
	public void setTickets(LinkedList<Ticket> tickets){
		this.tickets = tickets;
	}
	
	/**
	 * Gets the Flight's number
	 * @return the flight number
	 */
	public int getFlightNumber(){
		return this.flightNumber;
	}
	
	/**
	 * Gets the Flight's total seats
	 * @return the total seats
	 */
	public int getTotalSeats(){
		return this.totalSeats;
	}
	
	/**
	 * Gets the Flight's number of available seats
	 * @return the number of available seats
	 */
	public int getSeatsAvailable(){
		return this.seatsAvailable;
	}
	
	/**
	 * Gets the Flight's source
	 * @return the source
	 */
	public String getSource(){
		return this.source;
	}
	
	/**
	 * Gets the Flight's destination
	 * @return the destination
	 */
	public String getDestination(){
		return this.destination;
	}
	
	/**
	 * Gets the Flight's departure time
	 * @return the departure time
	 */
	public String getDepartureTime(){
		return this.departureTime;
	}
	
	/**
	 * Gets the Flight's duration
	 * @return the duration
	 */
	public String getDuration(){
		return this.duration;
	}
	
	/**
	 * Gets the Flight's price
	 * @return the price
	 */
	public float getPrice(){
		return this.price;
	}
	
	/**
	 * Gets the Flight's date
	 * @return the date
	 */
	public String getDate(){
		return this.date;
	}
	
	/**
	 * Gets the Flight's LinkedList of Tickets
	 * @return the list of Tickets
	 */
	public LinkedList<Ticket> getTickets(){
		return this.tickets;
	}
	
	/**
	 * This creates all tickets for the Flight
	 * @param numberOfSeats is the desired number of tickets to create
	 */
	public void createTickets(int numberOfSeats){
		tickets = new LinkedList<Ticket>();
		for(int i = 1; i <= numberOfSeats; i++){
			tickets.add(new Ticket(i, this));
		}
	}
	
	/**
	 * Prints Flight info to terminal, used for debugging
	 */
	public void seeFlight(){
		System.out.println(flightNumber + " " + source + " " + destination + " " + date + "SEE");
	}

}
