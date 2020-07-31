package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository personRepository;
		
	public PersonVO create(PersonVO personVO) {
		
		var entity = DozerConverter.parseObject(personVO, Person.class); 
		
		var vo = DozerConverter.parseObject(personRepository.save(entity), PersonVO.class); 
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		var entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerConverter.parseObject(personRepository.save(entity), PersonVO.class); 
		
		return vo;
	}
	
	public void delete(Long id) {
		
		Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
	
		personRepository.delete(entity);
	}
	
	public PersonVO findById(Long id) {
		
		var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	public List<PersonVO> findAll(){
		
		return DozerConverter.parseListObjects(personRepository.findAll(), PersonVO.class);
	}	
}
