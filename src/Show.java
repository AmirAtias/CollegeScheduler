
import java.util.HashMap;

public class Show {//show class
private HashMap <Integer,Slot> allSlots;
public HashMap<Integer, Slot> getAllSlots() {
	return allSlots;
}
public void setAllSlots(HashMap<Integer, Slot> allSlots) {
	this.allSlots = allSlots;
}
public Show() {
	allSlots=new HashMap<>();
}



public void addSlotDetails(int slotId,Slot tempSlot){
	allSlots.put(slotId,tempSlot);
		
}
}
