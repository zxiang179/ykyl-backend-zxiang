package edu.ecnu.ykyl.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

	@Autowired
	private JdbcTemplate jt;

	public Object query(String sql, RowMapper mapper) {
		return jt.query(sql, mapper);
	}

}
