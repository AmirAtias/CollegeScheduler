<<<<<<< HEAD:src/SlotDetails.java
public class SlotDetails {
	private String day;
=======
public class Slot {//slot class
	private int day;
>>>>>>> 1ccda9cfe741263291393b31093181342ddf5a35:src/Slot.java
	private int startHour;
	private int finishHour;
	private String Room;
	private String lecture;
 
    public Slot(String room,String lecture,int day,int startHour,int finishHour) {
    	setRoom(room);
    	setLecture(lecture);
    	setDay(day);
    	setStartHour(startHour);
    	setFinishHour(finishHour);
    }
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getFinishHour() {
		return finishHour;
	}
	public void setFinishHour(int finishHour) {
		this.finishHour = finishHour;
	}
	
	public String getLecture() {
		return lecture;
	}
	public void setLecture(String lecture) {
		this.lecture = lecture;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getRoom() {
		return Room;
	}
	public void setRoom(String room) {
		Room = room;
	}

}
