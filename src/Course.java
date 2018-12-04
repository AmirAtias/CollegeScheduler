
import java.util.TreeMap;

public class Course {// course ...co
private double credits;
private int courseCode;
private int sizeOfShow;
private String courseName;
private TreeMap <Integer,Show>allShows=new TreeMap<>();
public Course(double credits,int CourseCode,int sizeOfShow,String courseName) {
	setCourseName(courseName);
	setCourseCode(CourseCode);
	setCredits(credits);
	setSizeOfShow(sizeOfShow);
}
public TreeMap <Integer,Show> getShows() {
	return allShows;
}
public void setShows(TreeMap <Integer,Show> shows) {
	this.allShows = shows;
}
public double getCredits() {
	return credits;
}
public void setCredits(double credits) {
	this.credits = credits;
}
public void addShow(int ShowId,SlotDetails tempSlot){
if(!allShows.containsKey(ShowId)){ //create new show
	Show tempShow=new Show(ShowId);
    allShows.put(ShowId,tempShow);
}
	
allShows.get(ShowId).addSlotDetails(tempSlot);
	
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
public int getSizeOfShow() {
	return sizeOfShow;
}
public void setSizeOfShow(int sizeOfShow) {
	this.sizeOfShow = sizeOfShow;
}
}