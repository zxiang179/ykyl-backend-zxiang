package edu.ecnu.ykyl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.Option;
import edu.ecnu.ykyl.model.OptionRepository;

@Service
public class OptionService {

	@Autowired
	private OptionRepository repo;

	public List<String> findOptionsByQuestionId(int qid) {
		return repo.findOptionsByQuestionId(qid);
	}

	public List<Option> findOptions(Long qid) {
		return repo.findOptions(qid);
	}

}
