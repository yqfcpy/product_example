package com.yqfsoft.servicecenter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.yqfsoft.servicecenter.user.entity.Client;
import com.yqfsoft.servicecenter.user.entity.vo.ClientVo;
import com.yqfsoft.servicecenter.user.mapper.ClientMapper;
import com.yqfsoft.util.GlobalVariable;
import org.junit.Test;
import com.yqfsoft.security.security.DefaultPasswordEncoder;
import com.yqfsoft.servicecenter.product.mapper.ProductMapper;
import com.yqfsoft.servicecenter.user.entity.User;
import com.yqfsoft.servicecenter.user.service.UserService;
import com.yqfsoft.util.FileNameUtil;
import com.yqfsoft.util.FilterUtil;
import com.yqfsoft.util.globalexception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceCenterBoot.class)
public class VsoftTest {
  @Resource
  private ProductMapper productMapper;
  @Resource
  private UserService userService;
  @Resource
  private DefaultPasswordEncoder dpe;
  @Resource
  private ClientMapper clientMapper;

  @Test
  public void test5(){
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add("1");
    list.add("2");
    list.add("2");
    List<String> collect = list.stream().filter(a -> a.startsWith("1")).collect(Collectors.toList());
    log.info(collect.toString());
    String s1 = "杨清峰";
    String s2 = "金莹";
    log.info(String.format("s1的值：%s,s2的值：%s,",s1,s2));
//    long time = System.currentTimeMillis();
//
//    System.err.println(time);
//    Product p = new Product();
//    p.setId("12");
//    //p.setCategoryId(0);
//    System.err.println(p);
//    boolean b = productMapper.updateProductInfo("11", p);
// //   Product b = productMapper.selectOneById("11");
////    Integer b = productMapper.selectCount();
//    System.err.println("*******" + b);
  }
  @Test
  public void test8(){
    // 分页 (#{currentPage} - 1) * #{size},#{size};
    // 客户分页
    List<ClientVo> list = clientMapper.selectAllClientList();
    List<String> newlist = new ArrayList<>();
    for (ClientVo client:list){
      newlist.add((client.getUsername()));
    }
    String s = newlist.toString().replace("[","").replace("]","");
    List<ClientVo> list1 = clientMapper.selectClientList(newlist);
    log.info("数据：" + list);
    System.out.println(newlist);
    System.out.println(list1);
  }
  @Test
  public void findUser(){
    User user = userService.findUsernameByUsernameTelefonoOrEmail("625066660");
    System.out.println(user);
  }

  @Test
  public void test7(){

//    System.err.println(GlobalVariableConfig.PATHPRODUCTIMGURL);
//    System.err.println(GlobalVariableConfig.EXCELIMPORTSIZE);
    String s = GlobalVariable.getPATHPRODUCTIMGURL();
    Integer s1 = GlobalVariable.getEXCELIMPORTSIZE();
    String s2 = GlobalVariable.getPATHNGINX();
    System.err.println(s);
    System.err.println(s2 + s1);
    System.err.println(s1);
  }

  @Test
  public void test6(){
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("n");
    list.add("c");
    System.err.println(list.get(2));
  }

  @Test
  public void test4(){
    String s = ".jpg";
    Set<String> image = FileNameUtil.imageExtnameList();
    image.add(".jpg");
    for (String f : image ){
      System.err.println(f);
    }
    boolean contains = image.contains(s);

  }
  @Test
  public void test3()  {
    String ext = "Ini";
    List<String> list = new ArrayList<>();
    list.add("ini");
    list.add("bmp");
    boolean contains = list.contains(ext);
    int size = list.size();
    System.err.println(contains + "" + size);
  }


//  @Resource
//  private RedisUtil redisUtil;
//
//  @Test
//  public void testredisUtil(){
//    redisUtil.set("name","QINGFENG YANG LIU");
//  }

//  代码生成器
  @SuppressWarnings("deprecation")
  @Test
  public void test2(){
    // 1、创建代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 2、全局配置
    GlobalConfig gc = new GlobalConfig();
    // String projectPath = System.getProperty("product.dir");
    // System.out.println(projectPath);
    // projectPath 相对路径
    // gc.setOutputDir(projectPath + "/src/main/java");
    // 推荐写当前项目的绝对路径
    gc.setOutputDir("D:\\demo\\restaurante\\service" + "\\src\\main\\java");

    gc.setAuthor("Oscar Yang Liu");// 作者
    gc.setOpen(false); //生成后是否打开资源管理器
    gc.setFileOverride(false); //重新生成时文件是否覆盖
    /*
     * mp生成service层代码，默认接口名称第一个字母有 I
     * UcenterService
     * */
    gc.setServiceName("%sService"); //去掉Service接口的首字母I

    gc.setIdType(IdType.ID_WORKER_STR); //主键策略 自增啊 等等 这里是要和实际开发修改 具体的查一下
    gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
    gc.setSwagger2(true);//开启Swagger2模式
    mpg.setGlobalConfig(gc);
    // 3、数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://www.yqfsoft.com:3306/yqfsoftrestaurante?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("qingfengmysql");
    dsc.setDbType(DbType.MYSQL);
    mpg.setDataSource(dsc);

    // 4、包配置 数据生成到当前模块
    PackageConfig pc = new PackageConfig();
    // pc.setModuleName("service"); //当前模块名
    pc.setParent("com.yqfsoft.servicecenter.client");// 自定义包名
    pc.setController("controller");
    pc.setEntity("entity");
    pc.setService("service");
    pc.setMapper("mapper");
    mpg.setPackageInfo(pc);

    // 5、策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("client"); // 类对应表的名称
    // 如果是多张表可以 strategy.setInclude("edu_teacher","xxxx","xxxx");
    strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
    strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

    strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
    strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

    strategy.setRestControllerStyle(true); //restful api风格控制器
    strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

    mpg.setStrategy(strategy);

    // 6、执行
    mpg.execute();
  }



  @Test
  public void passwordMatchTest(){
    DefaultPasswordEncoder pe = new DefaultPasswordEncoder();
    String encode1 = pe.encode("111111");
    String encode2 = pe.encode("111111");
    boolean matches1 = pe.matches("111111", encode1);
    boolean matches2 = pe.matches("111111",encode2);
    System.out.println(encode1);
    System.out.println(encode2);
    System.out.println(matches1);
    System.out.println(matches2);
  }
//  @Resource
//  private RedisUtil redisUtil;

//  @org.junit.Test
//  public void test05(){
//    RedisTemplate redisTemplate = new RedisTemplate();
//    redisTemplate.opsForValue().set("age",18);
//  }
  @Test
  public void test06(){
    String str = "1a";
    str = str.toUpperCase();
    System.out.println(str);
  }
  @Test
  public void test07(){
    String str = "  1a  ";
    str = str.trim();
    System.out.println(str);
  }



  @Test
  public void checkUtil(){
    System.out.println("********** 没个字都首字母大写************");
    String dada = "     fsJKJKje  KsJj         dmsa  ";
    dada = FilterUtil.StringFirstUpperCaseEveryoneFilter(dada);
    System.out.println(dada);
    System.out.println("********** 字母大写************");
    String stttt= " 21 bbbb dfdse ][";
    stttt = FilterUtil.stringUpperCaseFilter(stttt);
    System.out.println(stttt);
    System.out.println("********** 清楚内容中所有的空格************");
    String str = "  6  d d";
    str = FilterUtil.removeAllBlankFilter(str);
    System.out.println(str);
    System.out.println("**********清楚内容中过多的空格************");
    String str1 = "";
    str1 = FilterUtil.removeRedundantBlankFilter(str1);
    System.out.println(str1);
    System.out.println("**********判断是不是Email************");
    String email = "dds@173.com";
    Boolean isEmail1 = FilterUtil.isEmail(email);
    System.out.println("判断输入的Email是否合法：" + email + " " + isEmail1);
    System.out.println("**********密码规则 8位 字母大小写特殊符号等等************");
    String password = "aBaaaaaa";
    Boolean isRole = FilterUtil.checkPassword(password);
    System.out.println("密码规则：" + isRole);
  }
  @Test
  public void teststr(){
    String str1 = "";
    String str2 = null;
    System.out.println(str1);
    System.out.println(str2);
  }

  @Test
  public void testIsnumber(){
    String str1 = null;
    String str2 = "";
    boolean empty1 = StringUtils.isEmpty(str1);
    boolean empty2 = StringUtils.isEmpty(str2);

    String str = "3.1415926";
    boolean number = FilterUtil.isNumber(str);
  }
}
