package edu.ecnu.ykyl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.Answer;
import edu.ecnu.ykyl.entity.Status;
import edu.ecnu.ykyl.model.AnswerRepository;
import edu.ecnu.ykyl.model.KnowledgePointRepository;
import edu.ecnu.ykyl.model.QuestionRepository;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.service.util.MyParser;

@Service
public class AnswerService extends MyParser {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private KnowledgePointRepository knowledgePointRepository;
	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;

	/**
	 * 返回PK一道题的批改结果
	 * 
	 * @param submitResult
	 *            格式：{'qid': 1, 'answer': 2}
	 * @return true/false
	 */
	/*
	 * public boolean PKAnswerJudgement(String submitResult){ Map<String,
	 * Object> PKAnswerMap = parsePKAnswer(submitResult); Long qid =
	 * Long.parseLong((String) PKAnswerMap.get("qid")); String
	 * answer=(String)PKAnswerMap.get("answer"); String myAnswer="";
	 * switch(answer){ case "1":myAnswer="A";break; case "2":myAnswer="B";break;
	 * case "3":myAnswer="C";break; case "4":myAnswer="D";break; default:break;
	 * }
	 * 
	 * String stdAnswer=answerRepository.findStdAnswer(qid);
	 * if(myAnswer.equals(stdAnswer)){ return true; } return false; }
	 */

	/**
	 * 返回PK一道题的批改结果
	 * 
	 * @param qid
	 * @param answer
	 * @return
	 */
	public boolean PKAnswerJudgement(Long qid, Long answer) {
		String myAnswer = "";
		switch (answer.toString()) {
		case "1":
			myAnswer = "A";
			break;
		case "2":
			myAnswer = "B";
			break;
		case "3":
			myAnswer = "C";
			break;
		case "4":
			myAnswer = "D";
			break;
		default:
			break;
		}

		String stdAnswer = answerRepository.findStdAnswer(qid);
		if (myAnswer.equals(stdAnswer)) {
			return true;
		}
		return false;
	}

	public void addAnswer(Long id, String answer, Long qid, Long uid) {
		answerRepository.addAnswer(id, answer, qid, uid);
	}

	public void save(Answer answer) {
		answerRepository.save(answer);
	}

	// test-answer.json
	public Map<String, Object> replyAnswer(String submitResult,Long uid,String type) {
		Map<String, Object> replyMap = new HashMap<String, Object>();
		//normal保存历史记录 random不保存历史记录
		if("normal".equals(type)){
			//得到答案
			Map<Integer, Integer> answerMap = parserJsonAnswer(submitResult);
			//将答案存入用户的历史记录
			// 从session中获取userId，根据userId获取历史记录
			String selectedHist = userQuestionHistRepository
					.findSelectedHist(uid);
			List<Map<String, Object>> histList = null;
			if (selectedHist != null) {
				// 不为空则将之前的hist转为list取出
				histList = parserJsonHist(selectedHist);
			} else {
				// 为空则新建list并为该用户创建hist
				histList = new ArrayList<Map<String, Object>>();
				userQuestionHistRepository.addQuestionHist("", uid);
			}
			String[] code = new String[] { "undo", "A", "B", "C", "D" };
			// 问题id
			int qid = 0;
			// 问题相对id
			int uirelativeID = 0;
			// 知识点id
			long kpId = 0;
			// 考生作答的答案
			int answer = 0;
			int countCorrect = 0;
			int countWrong = 0;
			int countUndo = 0;
			// 转换后的考生作答的答案
			String acceptableAnswer = "";
			// 该题标准答案
			String stdAnswer = "";

			// 获取user的答案map，取出key值并根据key值查找标准答案，取出value值，根据value值判断正误
			// 题目数量
			int size = answerMap.size();
			Map<String, Object> replySubMap = null;
			List<Map<String, Object>> addHistList = new ArrayList<Map<String, Object>>();
			Map<String, Object> addHistMap = null;

			List<Object> contentList = new ArrayList<Object>();
			for (Map.Entry<Integer, Integer> m : answerMap.entrySet()) {
				replySubMap = new HashMap<String, Object>();
				addHistMap = new HashMap<String, Object>();
				qid = m.getKey();
				uirelativeID = questionRepository.findKPRelativeIdByQid(qid);
				kpId = questionRepository.findKPIdByQuestionId(qid);
				answer = m.getValue();
				acceptableAnswer = code[answer];

				addHistMap.put("qid", qid);
				addHistMap.put("answer", acceptableAnswer);
				addHistMap.put("kpId", kpId);

				stdAnswer = questionRepository.findStdAnswerByQuestionId(qid);
				if (acceptableAnswer.equals(stdAnswer)) {
					replySubMap.put("qid", uirelativeID);
					replySubMap.put("status", Status.correct.ordinal());
					addHistMap.put("status", Status.correct.ordinal());

					++countCorrect;
				} else if (acceptableAnswer.equals("undo")) {
					replySubMap.put("qid", uirelativeID);
					replySubMap.put("status", Status.undo.ordinal());
					addHistMap.put("status", Status.undo.ordinal());
					++countUndo;
				} else {
					replySubMap.put("qid", uirelativeID);
					replySubMap.put("status", Status.wrong.ordinal());
					addHistMap.put("status", Status.wrong.ordinal());
					++countWrong;
				}
				addHistList.add(addHistMap);
				contentList.add(replySubMap);
			}
			// 将新旧历史记录叠加
			histList.addAll(addHistList);
			// list->json->string->存入数据库
			JSONArray jsonArray = JSONArray.fromObject(histList);
			String newHist = jsonArray.toString();
			userQuestionHistRepository.updateHistByUserId(newHist, uid);

			String kpName = knowledgePointRepository.findKPNameByQuestionId(qid);
			replyMap.put("kpName", kpName);
			replyMap.put("size", size);
			replyMap.put("difficulty", "较低");
			replyMap.put("countCorrect", countCorrect);
			replyMap.put("countWrong", countWrong);
			replyMap.put("countUndo", countUndo);
			replyMap.put("content", contentList);
		}else if("random".equals(type)){
			//得到答案
			Map<Integer, Integer> answerMap = parserJsonAnswer(submitResult);
			String[] code = new String[] { "undo", "A", "B", "C", "D" };
			// 问题id
			int qid = 0;
			// 知识点id
			long kpId = 0;
			// 考生作答的答案
			int answer = 0;
			int countCorrect = 0;
			int countWrong = 0;
			int countUndo = 0;
			// 转换后的考生作答的答案
			String acceptableAnswer = "";
			// 该题标准答案
			String stdAnswer = "";
			// 获取user的答案map，取出key值并根据key值查找标准答案，取出value值，根据value值判断正误
			// 题目数量
			int size = answerMap.size();
			Map<String, Object> replySubMap = null;
			List<Object> contentList = new ArrayList<Object>();
			for (Map.Entry<Integer, Integer> m : answerMap.entrySet()) {
				replySubMap = new HashMap<String, Object>();
				qid = m.getKey();
				kpId = questionRepository.findKPIdByQuestionId(qid);
				answer = m.getValue();
				acceptableAnswer = code[answer];
				stdAnswer = questionRepository.findStdAnswerByQuestionId(qid);
				if (acceptableAnswer.equals(stdAnswer)) {
					replySubMap.put("qid", qid);
					replySubMap.put("status", Status.correct.ordinal());

					++countCorrect;
				} else if (acceptableAnswer.equals("undo")) {
					replySubMap.put("qid", qid);
					replySubMap.put("status", Status.undo.ordinal());
					++countUndo;
				} else {
					replySubMap.put("qid", qid);
					replySubMap.put("status", Status.wrong.ordinal());
					++countWrong;
				}
				contentList.add(replySubMap);
			}
			String kpName = knowledgePointRepository.findKPNameByQuestionId(qid);
			replyMap.put("kpName", kpName);
			replyMap.put("size", size);
			replyMap.put("difficulty", "较低");
			replyMap.put("countCorrect", countCorrect);
			replyMap.put("countWrong", countWrong);
			replyMap.put("countUndo", countUndo);
			replyMap.put("content", contentList);
		}
		return replyMap;
	}

	/**
	 * 根据问题的id号查找该问题的标准答案
	 * 
	 * @param qid
	 * @return
	 */
	public String findStdAnswer(Long qid) {
		String stdAnswer = answerRepository.findStdAnswer(qid);
		return stdAnswer;
	}

}
