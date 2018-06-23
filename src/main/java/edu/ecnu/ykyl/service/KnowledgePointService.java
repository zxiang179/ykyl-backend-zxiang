package edu.ecnu.ykyl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.KnowledgePoint;
import edu.ecnu.ykyl.entity.Option;
import edu.ecnu.ykyl.entity.Question;
import edu.ecnu.ykyl.entity.QuestionType;
import edu.ecnu.ykyl.model.KnowledgePointRepository;
import edu.ecnu.ykyl.model.OptionRepository;
import edu.ecnu.ykyl.model.QuestionRepository;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.replyentity.ReplyOptionStyle;
import edu.ecnu.ykyl.replyentity.ReplyQuestionStyle;
import edu.ecnu.ykyl.service.mapper.QuestionMapper;
import edu.ecnu.ykyl.service.redis.service.IRedisService;
import edu.ecnu.ykyl.service.util.JoinService;
import edu.ecnu.ykyl.service.util.MyParser;

@Service
public class KnowledgePointService extends MyParser {

	@Autowired
	private KnowledgePointRepository knowledgePointRepository;
	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private OptionRepository optionRepository;

	@Autowired
	// 实现sql语句的拼接
	private JoinService joinService;

	@Autowired
	// 实现数据库字段的封装
	private QuestionMapper questionMapper;

	// 连接redis
	@Autowired
	private IRedisService redisService;


	public List<String> findKPNameBySubjectId(int id) {
		return knowledgePointRepository.findKPNameBySubjectId(id);
	}

	public List<KnowledgePoint> findAllKnowledgePoint() {
		return knowledgePointRepository.findAll();
	}

	public int findOneKPCountByKPId(int kpid) {
		return knowledgePointRepository.findOneKPCountByKPId(kpid);
	}

	/**
	 * 根据用户传来的知识点，将该知识点的历史记录清空
	 * @param kpId
	 * @param uid
	 * @return
	 */
	public Map<String, Object> resetUserHist(int kpId,long uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		//从该用户的历史记录中找到的最后的ID号
		int latestQuestionId = 0;
		String hist = userQuestionHistRepository.findSelectedHist(uid);
		if (!("").equals(hist)) {
			hist = removeJsonHistByKnowledgePointId(hist, kpId);
			userQuestionHistRepository.updateHistByUserId(hist, uid);
			hist = userQuestionHistRepository.findSelectedHist(uid);
		} else {
			latestQuestionId = 1;
		}

		int count = knowledgePointRepository.findOneKPCountByKPId(kpId);
		String name = knowledgePointRepository.findSubjectNameById(kpId);
		map.put("id", kpId);
		map.put("latestId", 1);
		map.put("count", count);
		map.put("name", name);
		return map;
	}

	/**
	 * 根据用户的历史记录，将对应知识点的题目存放到redis中
	 * @param kid：知识点id号
	 * @param type：normal表示练习模式 random表示随机测试模式
	 * @param uid：用户id
	 * @return
	 */
	public Map<String, Object> findOneKnowledgePoint(int kid, String type,Long uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("normal".equals(type)) {
			// 取出该知识点的题目，并导入redis
			
			// 从该用户的历史记录中找到该知识点下的相对ID号
			int latestKPRelativeId = 1;
			// 从该用户的历史记录中找到的最后的绝对ID号
			int latestQuestionId = 1;
			int size = 0;
			//从db中获取该用户的历史记录
			String hist = userQuestionHistRepository.findSelectedHist(uid);
			
			if(hist!=null){
				//将该用户的历史记录保存为list数据结构
				List<Map<String, Object>> histList = parserJsonHist(hist);
				//查找用户该知识点下的历史记录
				int[] a = new int[50];
				for (Map<String, Object> m : histList) {
					// 获取历史记录中用户每个知识点对应完成题目的数量
					a[(int) m.get("kpId") - 1]++;
				}

				if (histList.size()!=0) {
					//获取该知识点下题目的相对id号，用户从相对id号开始继续答题
					latestKPRelativeId = a[(int)(kid-1)]+1;
				} else {
					//设置该知识点下题目的相对id号为1，用户从该知识点的第一题开始作答
					latestKPRelativeId = 1;
				}
				
				//根据找到的该题在这个知识点下的相对id号，查找题目的绝对id号（因为redis中保存的是绝对id号）
				latestQuestionId = questionRepository.findQuestionIdByKPIdAndKPRelativeId(kid, latestKPRelativeId);

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
				Long UIrelativeId = 0L;
				//剩余题数
				Long count = 0L;
				//总题数
				Long totalCount = 0L;
				totalCount = questionRepository.findCountByKP(kid);
				count = totalCount - latestKPRelativeId;

				//将数据库中该知识点的题目取出，从历史记录开始
				questions = questionRepository.findQuestionsByKidFromLatestId(kid,latestQuestionId);
				for (Question q : questions) {
					// 将question重新封装
					replyQuestionStyle = new ReplyQuestionStyle();
					replyOptionsList = new ArrayList<ReplyOptionStyle>();
					questionType = q.getQuestionType();
					questionId = q.getId();
					questionContent = q.getContent();
					UIrelativeId = q.getKPrelativeId();

					// 查出该题的四个选项
					options = optionRepository.findOptions((long) questionId);
					for (Option o : options) {
						replyOptionStyle = new ReplyOptionStyle();
						replyOptionStyle.setCode(o.getCode());
						replyOptionStyle.setContent(o.getContent());
						replyOptionsList.add(replyOptionStyle);
					}
					JSONArray jsonArray = JSONArray.fromObject(replyOptionsList);
					String sOptions = jsonArray.toString();
					System.out.println("sOptions=" + sOptions);

					replyQuestionStyle.setQuestionId((Long) questionId);
					replyQuestionStyle.setQuestionContent(questionContent);
					replyQuestionStyle.setQuestionType(questionType.ordinal());
					replyQuestionStyle.setOptions(replyOptionsList);
					replyQuestionStyle.setUIrelativeId(UIrelativeId);
					
					// redis实现存储
					String ID = questionId.toString();
					JSONObject json = JSONObject.fromObject(replyQuestionStyle);// 将java对象转换为json对象
					String sreplyQuestionStyle = json.toString();
					redisService.set(ID, sreplyQuestionStyle);
					System.out.println("=============" + redisService.get(ID));
				}
				count = (long) knowledgePointRepository.findOneKPCountByKPId(kid);
				String name = knowledgePointRepository.findSubjectNameById(kid);
				map.put("id", kid);
				map.put("latestId", latestQuestionId);
				map.put("count", count);
				map.put("name", name);
			}else{
				latestKPRelativeId = 1;
				//根据找到的该题在这个知识点下的相对id号，查找题目的绝对id号（因为redis中保存的是绝对id号）
				latestQuestionId = questionRepository.findQuestionIdByKPIdAndKPRelativeId(kid, latestKPRelativeId);
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
				Long UIrelativeId = 0L;
				//剩余题数
				Long count = 0L;
				//总题数
				Long totalCount = 0L;
				totalCount = questionRepository.findCountByKP(kid);
				count = totalCount - latestKPRelativeId;

				//将数据库中该知识点的题目取出，从历史记录开始
				questions = questionRepository.findQuestionsByKidFromLatestId(kid,latestQuestionId);
				for (Question q : questions) {
					// 将question重新封装
					replyQuestionStyle = new ReplyQuestionStyle();
					replyOptionsList = new ArrayList<ReplyOptionStyle>();
					questionType = q.getQuestionType();
					questionId = q.getId();
					questionContent = q.getContent();
					UIrelativeId = q.getKPrelativeId();

					// 查出该题的四个选项
					options = optionRepository.findOptions((long) questionId);
					for (Option o : options) {
						replyOptionStyle = new ReplyOptionStyle();
						replyOptionStyle.setCode(o.getCode());
						replyOptionStyle.setContent(o.getContent());
						replyOptionsList.add(replyOptionStyle);
					}
					JSONArray jsonArray = JSONArray.fromObject(replyOptionsList);
					String sOptions = jsonArray.toString();
					System.out.println("sOptions=" + sOptions);

					replyQuestionStyle.setQuestionId((Long) questionId);
					replyQuestionStyle.setQuestionContent(questionContent);
					replyQuestionStyle.setQuestionType(questionType.ordinal());
					replyQuestionStyle.setOptions(replyOptionsList);
					replyQuestionStyle.setUIrelativeId(UIrelativeId);
					
					// redis实现存储
					String ID = questionId.toString();
					JSONObject json = JSONObject.fromObject(replyQuestionStyle);// 将java对象转换为json对象
					String sreplyQuestionStyle = json.toString();
					redisService.set(ID, sreplyQuestionStyle);
					System.out.println("=============" + redisService.get(ID));
				}
				count = (long) knowledgePointRepository.findOneKPCountByKPId(kid);
				String name = knowledgePointRepository.findSubjectNameById(kid);
				map.put("id", kid);
				map.put("latestId", latestQuestionId);
				map.put("count", count);
				map.put("name", name);
			}
			
		} else if ("random".equals(type)) {
			// 随机测试 随机取出该科目下的50题存入redis
			List<ReplyQuestionStyle> list = new ArrayList<ReplyQuestionStyle>();
			// 根据科目号查找在该科目的所有题目(List<Object>)
			String sql = "SELECT t_question.id,t_question.question_content,t_question.question_type"
					+ " FROM t_subject,t_knowledge_point ,t_question "
					+ "WHERE t_subject.id=t_knowledge_point.kp_subject_id "
					+ "AND t_knowledge_point.id=t_question.qu_knowledge_point_id "
					+ "AND t_subject.id=" + String.valueOf(kid);
			list = (List<ReplyQuestionStyle>) joinService.query(sql,
					questionMapper);
			int length = list.size();
			ReplyQuestionStyle replyQuestionStyle = null;
			Integer[] a = new Integer[length];
			int random = 0;
			int temp = 0;
			Long questionId = 0L;
			ReplyOptionStyle replyOptionStyle = null;
			List<ReplyOptionStyle> optionList = null;
			List<Option> options = null;
			List<ReplyQuestionStyle> resultList = null;
			String order = "";
			String[] orderArray = null;
			int index = 0;
			for (int i = 1; i <= length; i++) {
				a[i - 1] = i;
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
			for (int i = 1; i <= 50; i++) {
				optionList = new ArrayList<ReplyOptionStyle>();
				replyQuestionStyle = list.get(a[i] - 1);
				questionId = replyQuestionStyle.getQuestionId();
				order += a[i].toString() + " ";
//				replyQuestionStyle.setRelativeId(i);
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
				// resultList.add(replyQuestionStyle);
				// 判断redis中是否有该题，如果没有则存入redis中
				// redis实现存储
				String ID = questionId.toString();
				if (redisService.get(ID) == null) {
					// if(redisTemplate.opsForValue().get(ID)==null){
					// redis实现存储,如果在redis中没有该题，则存入该题目
					JSONObject json = JSONObject.fromObject(replyQuestionStyle);// 将java对象转换为json对象
					String sreplyQuestionStyle = json.toString();
					redisService.set(ID, sreplyQuestionStyle);
					// redisTemplate.opsForValue().set(ID, sreplyQuestionStyle);
				}
			}
			redisService.set("order", order);
			// redisTemplate.opsForValue().set("order", order);
			System.out.println(order);

			map.put("id", 1);
			map.put("latestId", 1);
			map.put("count", 50);
			map.put("name", "随机测试");
		}

		return map;
	}

}
