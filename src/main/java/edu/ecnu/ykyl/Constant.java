package edu.ecnu.ykyl;

public class Constant {

	public static final boolean DEBUG = true;
	
	public static final String API = "/api";
	
	public static final String API_SELFINFO = API + "/selfInfo";

	public static final String API_METADATA = API + "/metadata";

	public static final String API_USER = API + "/user";

	public static final String API_QUESTION = API + "/question";

	public static final String API_EXAM = API + "/exam";

	public static final String API_YD = API + "/yd";

	public static final String API_AUTH = API + "/auth";

	// -------------------

	public static final String PAGE_PAGE = "0";

	public static final String PAGE_SIZE = "15";

	public static final int PAGE_PAGE_INT = Integer.parseInt(PAGE_PAGE);

	public static final int PAGE_SIZE_INT = Integer.parseInt(PAGE_SIZE);

	// -------------------

	/**
	 * The number of exams to be produced in an exam paper.
	 */
	public static final String EXAM_COUNT = "30";

	public static final String ERROR_CODE_FILE_INVALID = "1001";
	public static final String ERROR_CODE_DATA_PARSE = "1002";
	public static final String ERROR_CODE_DATA_STORE = "1003";
	public static final String ERROR_CODE_DB_CONN_TIMEOUT = "1004";
	public static final String ERROR_DES_FILE_INVALID = "文件无效";
	public static final String ERROR_DES_DATA_PARSE = "数据解析错误";
	public static final String ERROR_DES_DATA_STORE = "数据存储失败";
	public static final String ERROR_DES_DB_CONN_TIMEOUT = "数据库连接超时";

}
