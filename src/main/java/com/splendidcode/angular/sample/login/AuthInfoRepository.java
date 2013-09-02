package com.splendidcode.angular.sample.login;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthInfoRepository {

   private final NamedParameterJdbcTemplate jdbcTemplate;

   @Inject
   public AuthInfoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public AuthInfo getAuthInfo(final String username) {

      final Map<String, ?> params = new HashMap<String, Object>() {{
         put("username", username);
      }};

      List<AuthInfo> objects = jdbcTemplate.query("SELECT username, password, salt, iterations FROM auth_info WHERE username = :username",
            new MapSqlParameterSource(params),
            getAuthInfoRowMapper());
      if(objects.isEmpty()) {
         return null;
      }
      else {
        return objects.get(0);
      }
   }

   private RowMapper<AuthInfo> getAuthInfoRowMapper() {
      return new RowMapper<AuthInfo>() {
         @Override
         public AuthInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            String username = rs.getString("username");
            String password = rs.getString("password");
            String salt = rs.getString("salt");
            int iterations = rs.getInt("iterations");
            return new AuthInfo(username, password, salt, iterations);
         }
      };
   }

   public void update(AuthInfo authInfo) {
      jdbcTemplate.update("UPDATE auth_info SET password = :hash, salt=:salt, iterations = :iterations WHERE username = :username",
            new BeanPropertySqlParameterSource(authInfo));
   }

   public void add(AuthInfo authInfo) {
      jdbcTemplate.update("INSERT INTO auth_info (username, password, salt, iterations) VALUES (:username, :hash, :salt, :iterations)",
            new BeanPropertySqlParameterSource(authInfo));
   }

}
