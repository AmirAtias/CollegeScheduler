
public class Day implements FinalsAndMsg{
	int dayVal;
	Hour[] hours=new Hour[HOURS_SIZE];
	public Day(int dayVal) {
		this.dayVal=dayVal;	
				initHours();}
	private void initHours() {// init hours
		for(int i=0;i<hours.length;i++) {
			hours[i]=new Hour(i+MIN_HOUR);
		}
	}
	public Hour getHour(int index) {
		return hours[index-MIN_HOUR];
	}
public void  removeCourse(int index) {
	hours[index-MIN_HOUR].setCourseCode(DEFAULT_NEG_VAL);
	hours[index-MIN_HOUR].setCourseName(null);
	hours[index-MIN_HOUR].setRoom(null);
}
public void setCourse(int index,String courseName,int courseCode,String room) {
	hours[index-MIN_HOUR].setCourseCode(courseCode);
	hours[index-MIN_HOUR].setCourseName(courseName);
	hours[index-MIN_HOUR].setRoom(room);

}
}
