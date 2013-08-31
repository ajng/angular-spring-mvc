package com.splendidcode.angular.sample.util;

import com.splendidcode.angular.sample.startup.SpringTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@TransactionConfiguration
public abstract class RepositoryTest {
}
