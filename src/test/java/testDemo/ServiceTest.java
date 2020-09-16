package testDemo;

import com.pd.Application;
import com.pd.system.domain.UserDO;
import com.pd.system.service.MenuService;
import com.pd.system.service.impl.MenuServiceImpl;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application.yml"})
@SpringBootTest(classes = Application.class)
public class ServiceTest {

	@Autowired
	MenuServiceImpl menuService;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
    public void should_be_able_to_find_by_using_and_or() {

		System.out.println(menuService.listPerms(1l));;
        
	}

	/**
	 * 查询用户名
	 */
	@Test
	public void findByUserName() {
		String str= jdbcTemplate.queryForObject("select username from sys_user where user_id = 3",String.class);
		System.out.println(str);
	}

	/**
	 * 查询用户信息
	 */
	@Test
	public void findByUser() {
		String sql = "select * from sys_user where user_id = 123";
		List<UserDO> userList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<UserDO>(UserDO.class));
		System.out.println(userList.get(0).toString());
	}

	/**
	 * 插入一条数据
	 */
	@Test
	public void insertUser() {
		String sql = "insert into sys_user values ('3', ?, ?, 'bfd9394475754fbe55866eba97738c36', '7', 'test@bootdo.com', null, '1', null, null, null, null, null, null, null, null, null, null, null);";
		jdbcTemplate.update(sql, "test", "测试用户");
	}

	/**
	 * 多条件查询（or语句会使索引失效）UNION ALL和UNION比UNION ALL不去重
	 * 查询id为1或者id为2的用户
	 */
	@Test
	public void findAllQuery() {
		String sql = "select * from sys_user where user_id = 1 UNION ALL select * from sys_user where user_id = 2;";
		List<UserDO> userDOList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<UserDO>(UserDO.class));
		System.out.println(userDOList.size());
	}

    /**
     * 统计count,null值不参与统计
     */
    @Test
    public void countField() {
        String sql ="select count(province) from sys_user";
        int num = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(num);
    }

	/**
	 * jdbcTemplate的query方法执行查询语句，execute执行DDL定义语句，用来建表或者修改结构，update方法执行DML操作语言，用来增删改
	 */
}
