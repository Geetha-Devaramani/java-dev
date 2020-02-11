package com.ring.util;

import com.ring.exceptions.RingErrorCode;
import com.ring.exceptions.RingException;
import com.ring.exceptions.RingResponseCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DaoUtil {

  @Autowired
  JdbcTemplate jdbctemplate;

  public int validateIdFromTable(int id, String columnName, String tableName)
      throws RingException {
    try {
      final String userIdQuery = "SELECT " + columnName + " FROM " + tableName
          + " WHERE " + columnName + " =?";
      return jdbctemplate.queryForObject(userIdQuery, new Object[]{id},
          new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum)
                throws SQLException {
              return rs.getInt(columnName);
            }
          });
    } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
      throw new RingException(RingErrorCode.RA_2007, RingResponseCode.OK);
    }
  }

  public static String getDate() {
    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd hh:mm:ss a z");
    return formatter.format(ZonedDateTime.now());
  }
}
