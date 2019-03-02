
import java.util.HashMap;

public class Course { //course class
private double credits;
private int courseCode;
private String courseName;
private HashMap <Integer,Show>allShows;


public Course(double credits,int CourseCode,String courseName) {
	setCourseName(courseName);
	setCourseCode(CourseCode);
	setCredits(credits);
	allShows=new HashMap<>();
}

public double getCredits() {
	return credits;
}
public void setCredits(double credits) {
	this.credits = credits;
}
public void addShow(int showId,Show showForAdd){
allShows.put(showId,showForAdd);
}
public HashMap<Integer, Show> getAllShows() {
	return allShows;
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
@Override
public String toString() {
	return courseCode+ " : " +courseName;
}
}
