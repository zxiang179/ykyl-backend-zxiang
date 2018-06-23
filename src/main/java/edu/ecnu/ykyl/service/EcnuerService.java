package edu.ecnu.ykyl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.Unit;
import edu.ecnu.ykyl.model.EcnuerRepository;

@Service
public class EcnuerService {

	@Autowired
	private EcnuerRepository ecnuerRepository;

	public Unit findUnitByName(String name) {
		return ecnuerRepository.findUnitByName(name);
	}

}
