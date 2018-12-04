import java.util.ArrayList;

public class Show {//show class
private int id;
private ArrayList <SlotDetails> allSlots;
public Show(int id) {
	setId(id);
	allSlots=new ArrayList<>();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public ArrayList <SlotDetails> getShow() {
	return allSlots;
}
public void setShow(ArrayList <SlotDetails> show) {
	this.allSlots = new ArrayList<>(show);
	
}
public void addSlotDetails(SlotDetails tempSlot){
	allSlots.add(tempSlot);
		
}
}
