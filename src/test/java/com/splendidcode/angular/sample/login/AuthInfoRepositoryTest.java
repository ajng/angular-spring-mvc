package com.splendidcode.angular.sample.login;

import com.splendidcode.angular.sample.util.RepositoryTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static com.splendidcode.angular.sample.util.builders.Builders.newAuthInfo;
import static org.fest.assertions.api.Assertions.assertThat;

@Transactional
public class AuthInfoRepositoryTest extends RepositoryTest {


   @Inject
   private NamedParameterJdbcTemplate jdbcTemplate;
   private AuthInfoRepository repository;

   @Before
   public void setup() {
      repository = new AuthInfoRepository(jdbcTemplate);
   }

   @Test
   public void test_auth_info_round_trip() {
      AuthInfo authInfo = newAuthInfo().withUsername("jdoe").withHash("00000000").withSalt("11111111").withIterations(1).build();
      repository.add(authInfo);
      AuthInfo fromDatabase = repository.getAuthInfo("jdoe");
      assertThat(fromDatabase).isEqualsToByComparingFields(authInfo);
   }
   
   @Test
   public void updating_auth_info_works() {
      AuthInfo authInfo = newAuthInfo().withUsername("jdoe").withHash("00000000").withSalt("11111111").withIterations(1).build();
      AuthInfo updatedAuthInfo = newAuthInfo().withSameUsernameAs(authInfo).withHash("22222222").withSalt("33333333").withIterations(2).build();
      repository.add(authInfo);
      repository.update(updatedAuthInfo);
      AuthInfo fromDatabase = repository.getAuthInfo("jdoe");
      assertThat(fromDatabase).isEqualsToByComparingFields(updatedAuthInfo);
   }
   
   @Test
   public void get_auth_info_returns_null_if_no_matching_auth_info_found(){
      AuthInfo authInfo = newAuthInfo().withUsername("jdoe").withHash("00000000").withSalt("11111111").withIterations(1).build();
      repository.add(authInfo);
      AuthInfo fromDatabase = repository.getAuthInfo("someoneElse");
      assertThat(fromDatabase).isNull();
      
   }
}
