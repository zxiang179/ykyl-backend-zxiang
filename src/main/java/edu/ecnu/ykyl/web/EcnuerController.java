package edu.ecnu.ykyl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Unit;
import edu.ecnu.ykyl.service.EcnuerService;

@RestController
@RequestMapping("/ecnuer")
public class EcnuerController {

	@Autowired
	private EcnuerService ecnuerService;

	@RequestMapping("/findUnitByName")
	public Unit findUnitByName(String name) {
		Unit unit = ecnuerService.findUnitByName(name);
		return unit;
	}

}
