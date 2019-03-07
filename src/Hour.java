
public class Hour implements FinalsAndMsg{
private int hour;
private int courseCode;
private String courseName;
private String room;
public Hour(int hour)  {
	this.hour=hour;
	courseCode=DEFAULT_NEG_VAL;
	courseName=null;
	room=null;
}
public int getHour() {
	return hour;
}
public void setHour(int hour) {
	this.hour = hour;
}
public int getCourseCode() {
	return courseCode;
}
public void setCourseCode(int courseCode) {
	this.courseCode = courseCode;
}
public String getCourseName() {
	return courseName;
}
public void setCourseName(String courseName) {
	this.courseName = courseName;
}
public String getRoom() {
	return room;
}
public void setRoom(String room) {
	this.room = room;
}
@Override
public String toString() {
	return courseName+" :  " +courseCode+"  "	+ "  - room:  "+room;
}

}
