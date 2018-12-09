
public interface FinalsAndMsg { // this interface contain all the finals and Strings msg
	public final int MIN_VAL_OF_ASCII_DIGIT = 48;// ASCII code of 0
	public final int MAX_VAL_OF_ASCII_DIGIT = 57;// ASCII code of 9
	public final int MIN_VAL_OF_ASCII_CHAR = 65;// ASCII code of A
	public final int MAX_VAL_OF_ASCII_CHAR = 122;// ASCII code of z
	public final int VAL_DASH_ASCII = 45;// ASCII code of -
	public final int VAL_OF_SPACE_ASCII = 32;// ASCII code of space
	public final int VAL_OF_AMPERSAND_ASCII = 38;// ASCII code of &
	public final int VAL_OF_APOSTROPHE_ASCII = 39;// ASCII code of ' 
	public final int VAL_OF_QUOTE_ASCII = 34;// ASCII code of " 
	public final double MIN_VAL_OF_CREDITS = 0.5;
	public final int MAX_VAL_OF_CREDITS = 10;
	public final int DEFAULT_VAL = 0;
	public final int MAX_NUM_OF_SLOTS = 5;
	public String NOT_IN_CREDITS_RANGE_ERROR = "the credits range is 0.5-10";
	public String NOT_ONLY_DIGITS_ERROR = "the input should contain only digits!";
	public String COURSE_EXIST_ERROR = "this course is already on the list!";
	public String COURSE_NAME_NOT_VALID_ERROR = "this course name is not valid!";
	public String MISSING_INPUT_ERROR = "missing input!";
	public String ROOM_NOT_VALID_ERROR = "this Room name is not valid!";
	public String LECTURE_NOT_VALID_ERROR = "this lecture name is not valid!";
    public String START_AND_FINISH_EQUAL="the start hour and finish hour are equals!";
    public String START_BIGGER_THEN_FINISH="start hour bigger then finish hour";
    public String ROOM_OCCUPIED_ERROR="this room is already occupied";
    public String LECTURE_TEACHING_ERROR="this lecture is already teaching in another class";
    public String SHOW_NULL_EXCEPTION="for create a new show you should add at least 1 slot";
    public String INPUT_NOT_VALID="Input not valid";
    public String SUCCESSFUL="Successful";
    public String SUCCESS_ADD_SLOT="slot added successfully";
    public String SHOW_NULL="Show null exception";
}
