
public class Day implements FinalsAndMsg{
	int dayVal;
	Hour[] hours=new Hour[17];
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
public void setCourse(int index,int courseCode) {
	hours[index-MIN_HOUR].setCourseCode(courseCode);
}
}
