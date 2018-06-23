package edu.ecnu.ykyl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.KnowledgePoint;
import edu.ecnu.ykyl.entity.Subject;
import edu.ecnu.ykyl.model.KnowledgePointRepository;
import edu.ecnu.ykyl.model.OptionRepository;
import edu.ecnu.ykyl.model.SubjectRepository;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.service.mapper.QuestionMapper;
import edu.ecnu.ykyl.service.util.JoinService;
import edu.ecnu.ykyl.service.util.MyParser;

@Service
public class SubjectService extends MyParser {

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private KnowledgePointRepository knowledgePointRepository;
	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private JoinService joinService;
	@Autowired
	private QuestionMapper questionMapper;

	public List<Object> findQuestionsBySubjectId(Integer sid) {
		List<Object> questions = subjectRepository
				.findQuestionsBySubjectId(sid);
		return questions;

	}

	// subject-content-math.json
	public Map<String, Object> findSubjectInfo(long sid,long uid) {
		List<Object> list = new ArrayList<Object>();
		HashMap<String, Object> subMap = null;
		Map<String, Object> map = new HashMap<String, Object>();

		// 修改为查找对应subject的kp
		List<KnowledgePoint> kpList = knowledgePointRepository
				.findKnowledgePointBySubjectId(sid);
		int[] a = new int[50];
		int temp = 0;
    	String hist = userQuestionHistRepository.findSelectedHist(uid);
    	
    	if(hist!=null){
    		List<Map<String, Object>> histList = parserJsonHist(hist);
    		System.out.println("=================");
    		System.out.println(histList);
    		System.out.println("=================");
    		for (Map<String, Object> m : histList) {
    			// 保存历史记录中每个知识点对应题目的数量
    			a[(int) m.get("kpId") - 1]++;
    		}
    		Subject subject = subjectRepository.findSubjectById(sid);
    		String sbShortcut = subject.getShortcut();
    		String sbName = subject.getSbName();
    		String sbTitle = subject.getTitle();
    		String kpShortCut = "";
    		String kpName = "";
    		int kpCount = 0;
    		// 完成的题目数量
    		int finished = 0;
    		int unfinished = 0;
    		int kpId = 0;
    		for (KnowledgePoint kp : kpList) {
    			subMap = new HashMap<String, Object>();
    			kpShortCut = kp.getKpShortCut();
    			kpName = kp.getKpName();
    			kpId =  (int) kp.getId();
    			kpCount = knowledgePointRepository.findOneKPCountByKPId(kpId);
    			finished = a[(int) (kpId - 1)];
    			unfinished = kpCount - finished;
    			subMap.put("finished", finished);
    			subMap.put("unfinished", unfinished);
    			subMap.put("kpShortCut", kpShortCut);
    			subMap.put("kpName", kpName);
    			subMap.put("kpCount", kpCount);
    			subMap.put("kpId", kpId);
    			list.add(subMap);
    		}
    		map.put("sbShortcut", sbShortcut);
    		map.put("sbName", sbName);
    		map.put("sbTitle", sbTitle);
    		map.put("content", list);
    	}else{
    		//当历史记录都为空时
    		Subject subject = subjectRepository.findSubjectById(sid);
    		String sbShortcut = subject.getShortcut();
    		String sbName = subject.getSbName();
    		String sbTitle = subject.getTitle();
    		String kpShortCut = "";
    		String kpName = "";
    		int kpCount = 0;
    		// 完成的题目数量
    		int finished = 0;
    		int unfinished = 0;
    		int kpId = 0;
    		for (KnowledgePoint kp : kpList) {
    			subMap = new HashMap<String, Object>();
    			kpShortCut = kp.getKpShortCut();
    			kpName = kp.getKpName();
    			kpId =  (int) kp.getId();
    			kpCount = knowledgePointRepository.findOneKPCountByKPId(kpId);
    			finished = 0;
    			unfinished = kpCount - finished;
    			subMap.put("finished", finished);
    			subMap.put("unfinished", unfinished);
    			subMap.put("kpShortCut", kpShortCut);
    			subMap.put("kpName", kpName);
    			subMap.put("kpCount", kpCount);
    			subMap.put("kpId", kpId);
    			list.add(subMap);
    		}
    		map.put("sbShortcut", sbShortcut);
    		map.put("sbName", sbName);
    		map.put("sbTitle", sbTitle);
    		map.put("content", list);
    	}
		return map;

	}

	// subject-list.json
	public List<Subject> findAllSubjects() {
		return subjectRepository.findAll();
	}

}
