package edu.ecnu.ykyl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Option;
import edu.ecnu.ykyl.service.OptionService;

@RestController
public class OptionController {

	@Autowired
	private OptionService optionService;

	@RequestMapping("/findOptionsContent")
	public List<String> findOptionsByQuestionId(int qid) {
		return optionService.findOptionsByQuestionId(qid);
	}

	@RequestMapping("/findOptions")
	public List<Option> findOptions(long qid) {
		return optionService.findOptions(qid);
	}

	@RequestMapping("/findOptions2")
	public Map<String, Object> findOptions2(long qid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Option> options = optionService.findOptions(qid);
		map.put("OP", options);
		return map;
	}

}
