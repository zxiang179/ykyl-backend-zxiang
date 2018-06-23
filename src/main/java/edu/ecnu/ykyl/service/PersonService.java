package edu.ecnu.ykyl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.Person;
import edu.ecnu.ykyl.model.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public List<Person> findByAddress(String addressName) {
		List<Person> list = personRepository.findByAddress(addressName);
		return list;
	}

	public Person findPersonById(Long id) {
		Person person = (Person) personRepository.findOne(id);
		if (person != null) {
			return person;
		} else {
			System.out.println("用户不存在");
			return null;
		}
	}

}
