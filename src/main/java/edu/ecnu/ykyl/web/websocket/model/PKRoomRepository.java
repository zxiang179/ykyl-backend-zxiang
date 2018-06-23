package edu.ecnu.ykyl.web.websocket.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.web.websocket.entity.PKRoom;

@Repository
@Transactional
public interface PKRoomRepository extends JpaRepository<PKRoom, Long> {

	/**
	 * 根据pkRoomId查找pkResult
	 * 
	 * @param pkRoomId
	 * @return
	 */
	@Query(value = "SELECT pk_result FROM t_pkroom WHERE pk_room_id=?1", nativeQuery = true)
	public String getPKResultByPKRoomId(Long pkRoomId);

	/**
	 * 根据pkRoomId更新数据库的pkResult字段
	 * 
	 * @param pkResult
	 * @param pkRoomId
	 */
	@Modifying
	@Query(value = "UPDATE t_pkroom SET pk_result=?1 WHERE pk_room_id=?2", nativeQuery = true)
	public void updatePKResultByPKRoomId(String pkResult, Long pkRoomId);

	/**
	 * 根据pkRoomId删除数据库的pkResult字段
	 * @param pkRoomId
	 */
	@Modifying
	@Query(value="DELETE FROM t_pkroom WHERE pk_room_id=?1",nativeQuery=true)
	public void removePKRoomByPKRoomId(Long pkRoomId);
	
	/**
	 * 添加一条pkRoom的记录
	 * 
	 * @param questions
	 * @param id
	 */
	@Modifying
	@Query(value = "insert into t_pkroom(questions,pk_room_id) values(?1,?2)", nativeQuery = true)
	public void addPKRoom(String questions, Long id);

	/**
	 * 根据pkRoomId查找随机生成的题目
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT questions FROM t_pkroom WHERE pk_room_id=?1", nativeQuery = true)
	public String findQuestionsByPKRoomId(Long id);

	/**
	 * 查找当前pkRoom的最大编号
	 * 
	 * @return
	 */
	@Query(value = "SELECT MAX(pk_room_id) FROM t_pkroom", nativeQuery = true)
	public Long findMaxPKRoomId();

}
