package edu.ecnu.ykyl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.KnowledgePoint;
import edu.ecnu.ykyl.entity.Option;
import edu.ecnu.ykyl.entity.Question;
import edu.ecnu.ykyl.entity.QuestionType;
import edu.ecnu.ykyl.replyentity.ReplyOptionStyle;
import edu.ecnu.ykyl.replyentity.ReplyQuestionStyle;
import edu.ecnu.ykyl.model.KnowledgePointRepository;
import edu.ecnu.ykyl.model.LabelRepository;
import edu.ecnu.ykyl.model.OptionRepository;
import edu.ecnu.ykyl.model.QuestionRepository;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.service.mapper.QuestionMapper;
import edu.ecnu.ykyl.service.redis.service.IRedisService;
import edu.ecnu.ykyl.service.util.JoinService;
import edu.ecnu.ykyl.service.util.MyParser;

@Service(value = "questionService")
public class QuestionService extends MyParser {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private KnowledgePointRepository knowledgePointRepository;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;
	@Autowired
	// 实现sql语句的拼接
	private JoinService joinService;
	@Autowired
	// 实现数据库字段的封装
	private QuestionMapper questionMapper;

	@Autowired
	private IRedisService redisService;
	
	
	/**
	 * 将excel中的题目导入数据库
	 */
	public void importQuestions(){
		String absId = null;//题目绝对id
		String quesContent = null;//题干
		String OptA = null;
		String OptB = null;
		String OptC = null;
		String OptD = null;
		String stdAns = null;//标准答案
		String quesAnalysis = null;//题目分析
		String kpStr = null;//知识点name
		String kpid = null;//知识点id
		String relativeId = null;//题目在该知识点中的相对编号
		String temp = null;
		String[] tempArr = null;  
        HSSFWorkbook workbook = null;
        
		try {
			workbook = new HSSFWorkbook(new FileInputStream(new File("C:/Users/Carl_Hugo.DESKTOP-FJAE3QC/Desktop/题目导入/题目整理.xls")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        HSSFSheet sheet = null;
        int i = workbook.getSheetIndex("题目表final"); // sheet表名
        sheet = workbook.getSheetAt(i);
        //外层循环 一行一行的扫描
        //getLastRowNum 获取最后一行的行标
        for (int j = 1; j < sheet.getLastRowNum(); j++) {
        	tempArr = new String[11];
            HSSFRow row = sheet.getRow(j);
            if (row != null) {
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    if (row.getCell(k) != null) { // getCell 获取单元格数据
                        System.out.print(temp = row.getCell(k) + "\t");
                        switch(k){
	                        case 0:absId = temp;break;//题目的绝对id
	                        case 1:quesContent = temp;break;//题目内容
	                        case 2:OptA = temp;break;//A
	                        case 3:OptB = temp;break;//B
	                        case 4:OptC = temp;break;//C
	                        case 5:OptD = temp;break;//D
	                        case 6:temp = temp.substring(0,1);break;//答案
	                        case 7:quesAnalysis = temp;break;//题目分析
	                        case 8:kpStr = temp;break;//知识点name
	                        case 9:kpid = temp;break;//知识点id
	                        case 10:relativeId = temp;break;//题目在该知识点中的相对id编号
                        }
                        tempArr[k]=temp;
                    } else {
                        System.out.print("\t");
                    }
                }
                //将该数组中除了2.3.4.5这四个索引的值封装到t_question表中
                insertQuestion(tempArr);
                //将数组中2.3.4.5这四个索引的值（ABCD）封装，并传入当前question的绝对id
                insertQuestionOption(tempArr);
            }
            System.out.println("");
        }
		//将excel中的数据取出
		//题目绝对id，题干，题支(ABCD),标准答案，题目分析，知识点，题目知识点id，题目在该知识点中的相对编号
		//+t_question
		//题目绝对id，题干，标准答案，题目知识点id，题目标签(默认为1),题目分析，题目种类(选择题默认0)，题目在该知识点中的相对编号
		//t_option
		//题支绝对id，op_code(A,B,C,D),题支内容，所对应的题目id
		
		
	}
	
	/**
	 * 封装options
	 * @param tempArr
	 * index0表示该题目的id号，index 2，3，4，5表示ABCD
	 */
    private void insertQuestionOption(String[] tempArr) {
    	int absId = (int)Double.parseDouble(tempArr[0]);//题目绝对id
		String OptA = tempArr[2];
		String OptB = tempArr[3];
		String OptC = tempArr[4];
		String OptD = tempArr[5];
		optionRepository.insertOption("A", OptA, absId);
		optionRepository.insertOption("B", OptB, absId);
		optionRepository.insertOption("C", OptC, absId);
		optionRepository.insertOption("D", OptD, absId);
		System.out.println("======insert option success======");
	}


	/**
     * 封装question对象
     * @param tempArr
     */
	private void insertQuestion(String[] tempArr) {
		String quesContent = tempArr[1];//题干
		String stdAns = tempArr[6];//标准答案
		String quesAnalysis = tempArr[7];//题目分析
		int kpid = (int)Double.parseDouble(tempArr[9]);//知识点id
		int relativeId = (int)Double.parseDouble(tempArr[10]);//题目在该知识点中的相对编号
		int questionType = 0;//选择题的题目类型为0
		questionRepository.insertQuestion(relativeId, quesContent, quesAnalysis, questionType, stdAns, kpid);
		System.out.println("======insert question success======");
	}


	/**
	 * 题目的详细解析
	 * @param qid
	 * @return
	 */
	public Map<String, Object> findSpecificAnswer(int qid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Question q = questionRepository.findByQuestionId(qid);
		List<String> options = optionRepository.findOptionsByQuestionId(qid);
		String knowledgePoint = knowledgePointRepository
				.findKPNameByQuestionId(qid);
		String label = labelRepository.findLabelByQuestionId(qid);
		String question = q.getContent();
		String stdAnswer = q.getStdAnswer();
		String analyse = q.getQuAnalyse();
		// redis
		String specfic = "Specfic Question";

		map.put("knowledgePoint", knowledgePoint);
		map.put("id", qid);
		map.put("question", question);
		map.put("options", options);
		map.put("stdAnswer", stdAnswer);
		map.put("analyse", analyse);
		map.put("label", label);
		
		return map;
	}

	// PK模块，根据科目id随机出题
	public Map<String, Object> replyPKQuestions(Integer sid, Integer count) {
		// 随机测试 随机取出该科目下的30题存入redis
		List<ReplyQuestionStyle> list = new ArrayList<ReplyQuestionStyle>();
		// 根据科目号查找在该科目的所有题目(List<Object>)
		String sql = "SELECT t_question.id,t_question.question_content,t_question.question_type"
				+ " FROM t_subject,t_knowledge_point ,t_question "
				+ "WHERE t_subject.id=t_knowledge_point.kp_subject_id "
				+ "AND t_knowledge_point.id=t_question.qu_knowledge_point_id "
				+ "AND t_subject.id=" + sid.toString();
		list = (List<ReplyQuestionStyle>) joinService
				.query(sql, questionMapper);
		int length = list.size();
		ReplyQuestionStyle replyQuestionStyle = null;
		Integer[] a = new Integer[length];
		int random = 0;
		int temp = 0;
		Long questionId = 0L;
		ReplyOptionStyle replyOptionStyle = null;
		List<ReplyOptionStyle> optionList = null;
		List<Option> options = null;
		List<ReplyQuestionStyle> resultList = new ArrayList<ReplyQuestionStyle>();
		Map<String, Object> replyMap = new HashMap<String, Object>();
		String PKOrder = "";
		String[] orderArray = null;
		int index = 0;
		for (int i = 1; i <= length; i++) {
			a[i - 1] = i - 1;
		}
		// shuffle
		for (int i = length - 1; i >= 1; i--) {
			random = (int) (Math.random() * (length));
			temp = a[i];
			a[i] = a[random];
			a[random] = temp;
		}
		System.out.println(a.toString());
		// 取出Object中对应的questionId，并且根据for循环赋予相对Id中便于以后shuffle
		for (int i = 1; i <= count; i++) {
			optionList = new ArrayList<ReplyOptionStyle>();
			replyQuestionStyle = list.get(a[i]);
			questionId = replyQuestionStyle.getQuestionId();
			PKOrder += a[i].toString() + " ";
			replyQuestionStyle.setUIrelativeId(i+0L);
			// 查出该题的四个选项
			options = optionRepository.findOptions(replyQuestionStyle
					.getQuestionId());
			for (Option o : options) {
				replyOptionStyle = new ReplyOptionStyle();
				replyOptionStyle.setCode(o.getCode());
				replyOptionStyle.setContent(o.getContent());
				optionList.add(replyOptionStyle);
			}
			replyQuestionStyle.setOptions(optionList);

			// 将问题添加到问题列表
			resultList.add(replyQuestionStyle);

		}
		redisService.set("PKOrder", PKOrder);
		System.out.println(PKOrder);

		replyMap.put("content", resultList);
		replyMap.put("count", resultList.size());
		return replyMap;
	}

	/**
	 * 
	 * @param currentId
	 * @param firstFlag
	 * @param type
	 * @param uid
	 * @return
	 */
	public Map<String, Object> findForwardQuestions(
			int currentId,
			int firstFlag, 
			String type,
			Long uid) {
		// 从该用户的历史记录中找到该知识点下的相对ID号
		int latestKPRelativeId = 1;
		// 从该用户的历史记录中找到的最后的绝对ID号
		int latestQuestionId = 1;
		int kid = 0;
		int size = 0;
		//记录绝对id和相对id的差值
		long diffLen = 0; 
				
		if("normal".equals(type)){
			String hist = userQuestionHistRepository.findSelectedHist(uid);
			if(hist!=null){
				List<Map<String, Object>> histList = parserJsonHist(hist);
				
				//根据问题绝对id查找到该问题所属的知识点以及该问题在该知识点的相对id
				List<Question> questionList = questionRepository.findQuestionByQid(currentId+1);
				if(questionList==null){
					questionList = questionRepository.findQuestionByQid(currentId-9);
				}
				Question question = questionList.get(0);
				KnowledgePoint kp = question.getQuKnowledgePointId();
				kid = (int) kp.getId();
				Long KPrelativeId = question.getKPrelativeId();
				//记录绝对id和相对id的差值
				diffLen =  question.getId()-question.getKPrelativeId();
				
				//查找用户该知识点下的历史记录
				int[] a = new int[50];
				for (Map<String, Object> m : histList) {
					// 获取历史记录中用户每个知识点对应完成题目的数量
					a[(int) m.get("kpId") - 1]++;
				}
				
				if (histList.size()!=0) {
					//获取该知识点下题目的相对id号，用户从相对id号开始继续答题
					latestKPRelativeId = a[(int) (kid-1)]+1;
				} else {
					//设置该知识点下题目的相对id号为1，用户从该知识点的第一题开始作答
					latestKPRelativeId = 1;
				}
				
				//根据找到的该题在这个知识点下的相对id号，查找题目的绝对id号（因为redis中保存的是绝对id号）
				latestQuestionId = questionRepository.findQuestionIdByKPIdAndKPRelativeId(kid, latestKPRelativeId);
			}else{
				latestKPRelativeId = 1;
				//根据问题绝对id查找到该问题所属的知识点以及该问题在该知识点的相对id
				List<Question> questionList = questionRepository.findQuestionByQid(currentId+1);
				if(questionList==null){
					questionList = questionRepository.findQuestionByQid(currentId-9);
				}
				Question question = questionList.get(0);
				KnowledgePoint kp = question.getQuKnowledgePointId();
				kid = (int) kp.getId();
				//根据找到的该题在这个知识点下的相对id号，查找题目的绝对id号（因为redis中保存的是绝对id号）
				latestQuestionId = questionRepository.findQuestionIdByKPIdAndKPRelativeId(kid, latestKPRelativeId);
			}
			
		}
		// 从数据库中取出历史记录最后一题之后的所有题目latest question
		// 从数据库中取出对应题目的编号进行封装Map<String,Object>
		// 将所有map封装到list中
		// 将list存入redis中
		// 从前端获取当前题目主键
		// 从redis中取出当前题目后10题
		ReplyQuestionStyle replyQuestionStyle = null;
		ReplyOptionStyle replyOptionStyle = null;
		List<Object> content = new ArrayList<Object>();
		List<Question> questions = new ArrayList<Question>();
		List<Option> options = null;
		List<ReplyOptionStyle> replyOptionsList = null;
		List<ReplyQuestionStyle> replyList = new ArrayList<ReplyQuestionStyle>();
		Map<String, Object> replyMap = new HashMap<String, Object>();
		Long questionId = 0L;
		String questionContent = "";
		QuestionType questionType = null;
		Long count = 0L;
		Long totalCount = 0L;
		totalCount = questionRepository.findCountByKP(kid);
//		count = questionReposotory.count() - latestQuestionId;

		if ("normal".equals(type)) {
			// 将数据从redis中取出
			String I = "";
			int start = 0;
			// int start=Math.max(latestQuestionId, currentId);
			if (firstFlag == 0)
				start = currentId + 1;
			else
				start = latestQuestionId;
			
			
			for (Integer i = start; i <= start + 10 && i <= totalCount+diffLen; i++) {
				//i记录的是问题的绝对id
				replyOptionsList = new ArrayList<ReplyOptionStyle>();
				replyQuestionStyle = new ReplyQuestionStyle();
				I = i.toString();
				String replyQuestion = redisService.get(I);
				// System.out.println(replyQuestion);
				
				if(replyQuestion!=null){
					JSONObject jsonObject = JSONObject.fromObject(replyQuestion);
					String finalOptions = jsonObject.getString("options");
					JSONArray jsonOptionsArray = JSONArray.fromObject(finalOptions);
					int optionSize = jsonOptionsArray.size();
					for (int j = 0; j < optionSize; j++) {
						replyOptionStyle = new ReplyOptionStyle();
						JSONObject jObj = jsonOptionsArray.getJSONObject(j);
						replyOptionStyle.setCode(jObj.getString("code"));
						replyOptionStyle.setContent(jObj.getString("content"));
						replyOptionsList.add(replyOptionStyle);
					}
					replyQuestionStyle.setOptions(replyOptionsList);
					replyQuestionStyle.setQuestionContent(jsonObject.getString("questionContent"));
					replyQuestionStyle.setQuestionType(jsonObject.getInt("questionType"));
					replyQuestionStyle.setQuestionId(jsonObject.getLong("questionId"));
					//根据绝对qid获取相对id
					int UIrelativeId = questionRepository.findKPRelativeIdByQid(i);
					replyQuestionStyle.setUIrelativeId(UIrelativeId+0L);
					
					replyList.add(replyQuestionStyle);
				}else{
					//当缓存中不存在时，从数据库中获取题目
					List<Question> findQuestionByQid = questionRepository.findQuestionByQid(i);
					Question ques = findQuestionByQid.get(0);
					List<String> optionsByQuestionId = optionRepository.findOptionsByQuestionId(i);
					int optionsSize = optionsByQuestionId.size();
					for(int j=0;j<optionsSize;j++){
						replyOptionStyle = new ReplyOptionStyle();
						String option = optionsByQuestionId.get(j);
						String code = "";
						if(j==0)code="A";
						if(j==1)code="B";
						if(j==2)code="C";
						if(j==3)code="D";
						replyOptionStyle.setCode(code);
						replyOptionStyle.setContent(option);
						replyOptionsList.add(replyOptionStyle);
					}
					replyQuestionStyle.setOptions(replyOptionsList);
					replyQuestionStyle.setQuestionContent(ques.getContent());
					replyQuestionStyle.setQuestionType(ques.getQuestionType().ordinal());
					replyQuestionStyle.setQuestionId(ques.getId());
					//根据绝对qid获取相对id
					int UIrelativeId = questionRepository.findKPRelativeIdByQid(i);
					replyQuestionStyle.setUIrelativeId(UIrelativeId+0L);
					
					replyList.add(replyQuestionStyle);
				}
			}
			replyMap.put("content", replyList);
			replyMap.put("count", replyList.size());
			return replyMap;
		} else if ("random".equals(type)) {
			replyMap = new HashMap<String, Object>();
			List<ReplyQuestionStyle> resultList = new ArrayList<ReplyQuestionStyle>();
			// 根据当前id，从redis中取出10题发送
			String order = redisService.get("order");
			String[] orderArray = order.split(" ");
			List<ReplyOptionStyle> optionList = new ArrayList<ReplyOptionStyle>();
			for (Integer i = currentId; i <= currentId + 10 && i < 50; i++) {
				replyQuestionStyle = new ReplyQuestionStyle();
				optionList = new ArrayList<ReplyOptionStyle>();
				String index = orderArray[i].toString();
				String replyQuestion = redisService.get(index);
				// System.out.println(replyQuestion);
				JSONObject jsonObject = JSONObject.fromObject(replyQuestion);
				String finalOptions = jsonObject.getString("options");
				JSONArray jsonOptionsArray = JSONArray.fromObject(finalOptions);
				int optionSize = jsonOptionsArray.size();
				for (int j = 0; j < optionSize; j++) {
					replyOptionStyle = new ReplyOptionStyle();
					JSONObject jObj = jsonOptionsArray.getJSONObject(j);
					replyOptionStyle.setCode(jObj.getString("code"));
					replyOptionStyle.setContent(jObj.getString("content"));
					optionList.add(replyOptionStyle);
				}
				replyQuestionStyle.setOptions(optionList);
				replyQuestionStyle.setQuestionContent(jsonObject
						.getString("questionContent"));
				replyQuestionStyle.setQuestionType(jsonObject
						.getInt("questionType"));
				replyQuestionStyle.setQuestionId((long) i + 1);
				replyQuestionStyle.setUIrelativeId(i + 1L);
				resultList.add(replyQuestionStyle);
			}
			replyMap.put("content", resultList);
			replyMap.put("count", resultList.size());
			return replyMap;
		} else {
			replyMap = new HashMap<String, Object>();
			return replyMap;
		}

	}

	// test-page-1.json
	public Map<String, Object> findBackwardQuestions(int currentId, String type) {

		Map<String, Object> replyMap = new HashMap<String, Object>();
		if ("normal".equals(type)) {
			// 向前找十道题
			// 从该用户的历史记录中找到的最后的ID号
			Long userId = 1L;
			int latestQuestionId = 0;
			String hist = userQuestionHistRepository.findSelectedHist(userId);
			List<Map<String, Object>> jsonHist = parserJsonHist(hist);
			int size = jsonHist.size();
			Map<String, Object> lastRecord = jsonHist.get(size - 1);
			latestQuestionId = (int) lastRecord.get("qid") + 1;
			System.out.println("latestQuestionId============="
					+ latestQuestionId);

			int start = Math.max(currentId - 11, latestQuestionId);
			System.out.println(start);
			List<ReplyOptionStyle> replyOptionsList = null;
			ReplyOptionStyle replyOptionStyle = null;
			ReplyQuestionStyle replyQuestionStyle = null;
			List<ReplyQuestionStyle> replyList = new ArrayList<ReplyQuestionStyle>();
			replyMap = new HashMap<String, Object>();
			// 将数据从redis中取出
			String I = "";
			if (currentId > start) {
				for (Integer i = start; i < currentId; i++) {
					replyOptionsList = new ArrayList<ReplyOptionStyle>();
					replyQuestionStyle = new ReplyQuestionStyle();
					I = i.toString();
					String replyQuestion = redisService.get(I);
					// System.out.println(replyQuestion);
					JSONObject jsonObject = JSONObject
							.fromObject(replyQuestion);
					String finalOptions = jsonObject.getString("options");

					JSONArray jsonOptionsArray = JSONArray
							.fromObject(finalOptions);
					int optionSize = jsonOptionsArray.size();
					for (int j = 0; j < optionSize; j++) {
						replyOptionStyle = new ReplyOptionStyle();
						JSONObject jObj = jsonOptionsArray.getJSONObject(j);
						replyOptionStyle.setCode(jObj.getString("code"));
						replyOptionStyle.setContent(jObj.getString("content"));
						replyOptionsList.add(replyOptionStyle);
					}
					replyQuestionStyle.setOptions(replyOptionsList);
					replyQuestionStyle.setQuestionContent(jsonObject.getString("questionContent"));
					replyQuestionStyle.setQuestionType(jsonObject.getInt("questionType"));
					replyQuestionStyle.setQuestionId(jsonObject.getLong("questionId"));
					replyQuestionStyle.setUIrelativeId(i+0L);
					replyList.add(replyQuestionStyle);
				}
				replyMap.put("content", replyList);
				replyMap.put("count", replyList.size());
				return replyMap;
			} else {
				replyList = new ArrayList<ReplyQuestionStyle>();
				replyMap = new HashMap<String, Object>();
				replyMap.put("content", replyList);
				replyMap.put("count", replyList.size());
				return replyMap;
			}
		} else if ("random".equals(type)) {
			// 获取redis中的出题顺序
			String order = redisService.get("order");
			String[] orderArray = order.split(" ");
			ReplyQuestionStyle replyQuestionStyle = null;
			ArrayList<ReplyOptionStyle> optionList = null;
			ReplyOptionStyle replyOptionStyle = null;
			List<ReplyQuestionStyle> resultList = new ArrayList<ReplyQuestionStyle>();
			replyMap = new HashMap<String, Object>();
			// 查找当前题号前10题，封装
			int start = Math.max(0, currentId - 10);
			for (int i = start; i < currentId - 1; i++) {
				replyQuestionStyle = new ReplyQuestionStyle();
				optionList = new ArrayList<ReplyOptionStyle>();
				String replyQuestion = redisService.get(orderArray[i].toString());
				// System.out.println(replyQuestion);
				JSONObject jsonObject = JSONObject.fromObject(replyQuestion);
				String finalOptions = jsonObject.getString("options");
				JSONArray jsonOptionsArray = JSONArray.fromObject(finalOptions);
				int optionSize = jsonOptionsArray.size();
				for (int j = 0; j < optionSize; j++) {
					replyOptionStyle = new ReplyOptionStyle();
					JSONObject jObj = jsonOptionsArray.getJSONObject(j);
					replyOptionStyle.setCode(jObj.getString("code"));
					replyOptionStyle.setContent(jObj.getString("content"));
					optionList.add(replyOptionStyle);
				}
				replyQuestionStyle.setOptions(optionList);
				replyQuestionStyle.setQuestionContent(jsonObject
						.getString("questionContent"));
				replyQuestionStyle.setQuestionType(jsonObject
						.getInt("questionType"));
				replyQuestionStyle.setQuestionId((long) i + 1);
				replyQuestionStyle.setUIrelativeId(i + 1L);
				resultList.add(replyQuestionStyle);
			}
			// 发送
			replyMap.put("content", resultList);
			replyMap.put("count", resultList.size());
			return replyMap;
		} else {
			replyMap = new HashMap<String, Object>();
			return replyMap;
		}

	}

	/**
	 * 随机生成一定数量的题目
	 * @param count
	 * @return
	 */
	public Set<Question> findQuestionByRandom(int count) {
		Set<Question> set = new HashSet<Question>();
		// 总题数
		int totalCount = questionRepository.findCount();
		System.out.println("totalCount:" + totalCount);
		// 随机生成的题号
		int id;
		for (; set.size() < count;) {
			id = (int) (Math.random() * totalCount);
			Question question = questionRepository.findByQuestionId(id);
			set.add(question);
		}
		return set;
	}

	/**
	 * 根据题目的id号查找题目
	 * @param id
	 * @return
	 */
	public Question findByQuestionId(long id) {
		Question question = questionRepository.findByQuestionId(id);
		return question;
	}

	/**
	 * 查找所有题目
	 * @return
	 */
	public List<Question> findAllQuestion() {
		List<Question> allQuestion = questionRepository.findAll();
		return allQuestion;
	}

	public String findStdAnswerByAnswerId(Long id) {
		return questionRepository.findStdAnswerByAnswerId(id);
	}

	public int findKpIdByQId(int qid) {
		return questionRepository.findKPIdByQuestionId(qid);
	}

}
