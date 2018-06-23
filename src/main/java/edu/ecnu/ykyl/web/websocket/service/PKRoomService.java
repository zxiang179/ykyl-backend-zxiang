package edu.ecnu.ykyl.web.websocket.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.web.websocket.model.PKRoomRepository;

@Service(value = "pkRoomService")
public class PKRoomService {

	@Autowired
	private PKRoomRepository pkRoomRepository;
	
	/**
	 * 根据pkRoomId删除数据库的pkResult字段
	 * @param pkRoomId
	 */
	public void removePKRoomByPKRoomId(Long pkRoomId){
		pkRoomRepository.removePKRoomByPKRoomId(pkRoomId);
	}

	/**
	 * 根据pkRoomId查找pkResult
	 * 
	 * @param pkRoomId
	 * @return
	 */
	public String getPKResultByPKRoomId(Long pkRoomId) {
		String pkResult = pkRoomRepository.getPKResultByPKRoomId(pkRoomId);
		return pkResult;
	}

	/**
	 * 根据pkRoomId更新数据库的pkResult字段
	 * 
	 * @param pkResult
	 * @param pkRoomId
	 */
	public void updatePKResultByPKRoomId(String pkResult, Long pkRoomId) {
		pkRoomRepository.updatePKResultByPKRoomId(pkResult, pkRoomId);
	}

	/**
	 * 添加一条pkRoom的记录
	 * 
	 * @param questions
	 * @param id
	 */
	public void insertPKRoom(String questions, Long id) {
		pkRoomRepository.addPKRoom(questions, id);
	}

	/**
	 * 根据pkRoomId查找随机生成的题目
	 * 
	 * @param id
	 * @return
	 */
	public String findQuestionsByPKRoomId(Long id) {
		String questionsStr = pkRoomRepository.findQuestionsByPKRoomId(id);
		return questionsStr;
	}

	/**
	 * 查找当前pkRoom的最大编号
	 * 
	 * @return
	 */
	public Long findMaxPKRoomId() {
		return pkRoomRepository.findMaxPKRoomId();
	}

}
