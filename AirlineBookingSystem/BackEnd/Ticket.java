package BackEnd;

import java.io.Serializable;

/**
 * This class holds all ticket information. This includes the ticket holder
 * information, the Date of the flight along with destination, departure time,
 * duration, and seat number. Is a serializable object, so it can be sent to
 * the clients
 * 
 * @author Tevin Schmidt
 *
 */
public class Ticket implements Serializable {


		private static final long serialVersionUID = 464748L;
		
		private String lastName;
		private String firstName;
		private String dateOfBirth;
		private String destination;
		private String source;
		private String departureTime;
		private String duration;
		private int seatNumber;
		private int flightNumber;
		private String date;
		private boolean available;
		
		public Ticket(){
			
		}
		
		/**
		 * Constructs a ticket with the given seat number assigned to it. Initializes all
		 * other members with data from flight.
		 * @param seatNumber is the seat number to be assigned to the ticket
		 * @param theFlight is the flight to initializes members with
		 */
		public Ticket(int seatNumber, Flight theFlight){
			this.lastName = null;
			this.firstName = null;
			this.dateOfBirth = null;
			this.destination = theFlight.getDestination();
			this.source = theFlight.getSource();
			this.departureTime = theFlight.getDepartureTime();
			this.duration = theFlight.getDuration();
			this.seatNumber = seatNumber;
			this.date = theFlight.getDate();
			this.available = true;
			this.flightNumber = theFlight.getFlightNumber();
		}
		
		public void setLastName(String lastName){
			if(lastName == null){
				this.lastName = null;
			}
			else{
				this.lastName = lastName;
			}
		}
		
		public void setFirstName(String firstName){
			if(firstName == null){
				this.firstName = null;
			}
			else{
				this.firstName = firstName;
			}
		}
		
		public void setDateOfBirth(String date){
			if(date == null){
				this.dateOfBirth = null;
			}
			else{
				this.dateOfBirth = date;
			}
		}
		
		public void setDestination(String destination){
			this.destination = destination;
		}
		
		public void setSource(String source){
			this.source = source;
		}
		
		public void setDepatureTime(String departureTime){
			this.departureTime = departureTime;
		}
		
		public void setDuration(String duration){
			this.duration = duration;
		}
		
		public void setSeatNumber(int seatNumber){
			this.seatNumber = seatNumber;
		}
		
		public void setFlightNumber(int flightNumber){
			this.flightNumber = flightNumber;
		}
		public void setDate(String date){
			this.date = date;
		}
		
		public void setAvalable(boolean state){
			this.available = state;
		}
		
		public String getLastName(){
			return this.lastName;
		}
		
		public String getFirstName(){
			return this.firstName;
		}
		
		public String getDateOfBirth(){
			return this.dateOfBirth;
		}
		
		public String getDestination(){
			return this.destination;
		}
		
		public String getSource(){
			return this.source;
		}
		
		public String getDepartureTime(){
			return this.departureTime;
		}
		
		public String getDuration(){
			return this.duration;
		}
		
		public int getSeatNumber(){
			return this.seatNumber;
		}
		
		public int getFlightNumber() {
			return this.flightNumber;
		}
		public String getDate(){
			return this.date;
		}
		
		public boolean getAvailable(){
			return this.available;
		}
		

		
		
		
		
}
