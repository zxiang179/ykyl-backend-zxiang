package edu.ecnu.ykyl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.UserQuestionHist;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.service.util.MyParser;

@Service
public class UserQuestionHistService extends MyParser {

	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;

	// public void resetUserQuestionHistById(Long uid,int kpId){
	// String jsonHist="";
	// String resultHist="";
	// jsonHist=userQuestionHistRepository.findSelectedHist(uid);
	// resultHist=removeJsonHistByKnowledgePointId(jsonHist,kpId);
	// userQuestionHistRepository.updateHistByUserId(resultHist, uid);
	//
	// }

	// public String findSelectedHist(Long qhid);
	public List<Map<String, Object>> findSelectedHist(Long qhid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String str = userQuestionHistRepository.findSelectedHist(qhid);
		if (!"".equals(str)) {
			list = parserJsonHist(str);
			return list;
		} else {
			return null;
		}

	}

	public void addHistByUserId(String hist, Long uid) {
		// 历史记录表中没有该用户的记录需要insert Hist
		userQuestionHistRepository.addQuestionHist(hist, uid);
	}

	public void addMoreHistByUserId(String hist, Long uid) {
		// 根据用户id查找该用户
		UserQuestionHist userQuestionHist = userQuestionHistRepository
				.findUserQuestionHistById(uid);
		if (userQuestionHist == null) {
			userQuestionHistRepository.addQuestionHist(hist, uid);
		} else {
			// 查找该为用户的过往历史记录
			String str = userQuestionHistRepository.findSelectedHist(uid);
			List<Map<String, Object>> oldList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			oldList = parserJsonHist(str);
			newList = parserJsonHist(hist);
			oldList.addAll(newList);
			JSONArray jsonArray = JSONArray.fromObject(oldList);
			String newHist = jsonArray.toString();
			// 更新用户的过往历史
			userQuestionHistRepository.updateHistByUserId(newHist, uid);

		}

	}

	public UserQuestionHist findUserQuestionHistById(Long uid) {
		return userQuestionHistRepository.findUserQuestionHistById(uid);
	}

	public void updateHistByUserId(String hist, Long uid) {
		// 根据用户id查找该用户
		UserQuestionHist userQuestionHist = userQuestionHistRepository
				.findUserQuestionHistById(uid);
		if (userQuestionHist == null) {
			userQuestionHistRepository.addQuestionHist(hist, uid);
		} else {
			// 查找该为用户的过往历史记录
			List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
			newList = parserJsonHist(hist);
			JSONArray jsonArray = JSONArray.fromObject(newList);
			String newHist = jsonArray.toString();
			// 更新用户的过往历史
			userQuestionHistRepository.updateHistByUserId(newHist, uid);

		}
	}

}
