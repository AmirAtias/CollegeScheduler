import java.util.ArrayList;

public class Model { 
private ArrayList<Course> allCourses=new ArrayList<>();//all courses

public ArrayList<Course> getAllCourses() {
	return allCourses;
}

public void setAllCourses(ArrayList<Course> allCourses) {
	this.allCourses = allCourses;
}
public void addCourse(Course course) {
	allCourses.add(course);
}
public void RemoveCourse(Course course){
	if(allCourses.remove(course))
		System.out.println("the course is not part of the courses list anymore");
	else
		System.out.println("can't remove the course from list");
}
}
