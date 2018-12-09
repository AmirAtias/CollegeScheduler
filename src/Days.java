
public interface Days {
	public enum AllDays{
		Sunday(1), Monday(2), Tuesday(3), Wednesday(4), Thursday(5), Friday(6), Saturday(7);
		 int value;

		AllDays(int value) {
			this.value = value;
		}

		public int getIntValue() {
			return value;
		}
	};
}
