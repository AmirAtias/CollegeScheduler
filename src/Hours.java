

public interface Hours {
	public static int FromStringToInt(String hour) { // casting the hour from string to int
		switch (hour) {
		case "07:00":
			return 7;
		case "08:00":
			return 8;
		case "09:00":
			return 9;
		case "10:00":
			return 10;
		case "11:00":
			return 11;
		case "12:00":
			return 12;
		case "13:00":
			return 13;
		case "14:00":
			return 14;
		case "15:00":
			return 15;
		case "16:00":
			return 16;
		case "17:00":
			return 17;
		case "18:00":
			return 18;
		case "19:00":
			return 19;
		case "20:00":
			return 20;
		case "21:00":
			return 21;
		case "22:00":
			return 22;
		default:
			return 23;

		}
	}
}
