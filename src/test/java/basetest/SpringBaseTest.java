package basetest;

import cn.hejinyo.core.utils.Tools;
import cn.hejinyo.other.service.ITableIpService;
import cn.hejinyo.system.dao.SysResourceDao;
import cn.hejinyo.system.dao.SysRoleDao;
import cn.hejinyo.system.dao.SysUserDao;
import cn.hejinyo.system.model.dto.UserMenuDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/spring/spring-base.xml"}) //加载配置文件
public class SpringBaseTest {
    @Resource(name = "tableIpServiceImpl")
    ITableIpService service;
    @Resource
    SysUserDao sysUserDao;
    @Resource
    SysRoleDao sysRoleDao;
    @Resource
    SysResourceDao sysResourceDao;
    //------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例
    //这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
    //@Transactional
    //这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时//指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
    //@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
    //------------


    @Test   //标明是测试方法
    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void printTable() {
        System.out.println("====================" + service.getRowCount() + "====================");
    }

    @Test   //标明是测试方法
    public void test1() {
        String path = Tools.class.getClass().getResource("/").getPath();
        System.out.println("===========================" + path);
    }

    @Test
    public void testUser() {
        List<UserMenuDTO> userMenuDTOS = sysResourceDao.getUserMenuList(1);
        for (UserMenuDTO menu : userMenuDTOS) {
            System.out.println(menu.toString());
        }
    }

    @Cacheable(value = "cacheTest", key = "#param")
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }

    @Test
    public void getTimestampTest() throws InterruptedException {
        System.out.println("第一次调用：" + getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + getTimestamp("param"));
    }


}

