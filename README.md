## pd-sb-core
##### 注解
````java
@com.pd.spring.ip.PdIP
@com.pd.spring.redis.PdCache
@com.pd.spring.redis.PdDelCache
````
##### 自定义序列化 protostuff
```
使用protostuff-core和protostuff-runtime完成序列化
className: com.pd.spring.utils.SerializeUtils

不使用构造方法创建Java对象
org.springframework.objenesis.ObjenesisStd

<!-- protostuff-core 序列化 -->
<dependency>
    <groupId>com.dyuproject.protostuff</groupId>
    <artifactId>protostuff-core</artifactId>
    <version>1.1.3</version>
</dependency>

<!-- protostuff-runtime 序列化 -->
<dependency>
    <groupId>com.dyuproject.protostuff</groupId>
    <artifactId>protostuff-runtime</artifactId>
    <version>1.1.3</version>
</dependency>
```
##### 结合spring框架自定义注解
```
使用spring中的aop处理自定义注解的处理逻辑
com.pd.spring.redis.aspect.PdAspect

<!-- aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

使用spring框架解析自定义注解中的el表达式
private static String getKey(String keyName, ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    String[] parametersName = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
    return ParseSpringEl.getKey(keyName, parametersName, args);
}

public static String getKey(String key, String[] parametersName, Object[] args) {
    if (key == null || parametersName == null || args == null) {
        return null;
    }
    if (parametersName.length < 1 || args.length < 1) {
        return null;
    }
    key = key.replace("#{", "#");
    key = key.substring(0, key.lastIndexOf("}"));
    ExpressionParser expressionParser = new SpelExpressionParser();
    Expression expression = expressionParser.parseExpression(key);
    EvaluationContext context = new StandardEvaluationContext();
    for (int i = 0; i < args.length; i++) {
        context.setVariable(parametersName[i], args[i]);
    }
    return expression.getValue(context, String.class);
}

自定义注解相关说明

@Retention：注解的保留位置　　　　　　　　　
@Retention(RetentionPolicy.SOURCE)   //注解仅存在于源码中，在class字节码文件中不包含
@Retention(RetentionPolicy.CLASS)     // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target:注解的作用目标　　　　　　
@Target(ElementType.TYPE)   //接口、类、枚举、注解
@Target(ElementType.FIELD) //字段、枚举的常量
@Target(ElementType.METHOD) //方法
@Target(ElementType.PARAMETER) //方法参数
@Target(ElementType.CONSTRUCTOR)  //构造函数
@Target(ElementType.LOCAL_VARIABLE)//局部变量
@Target(ElementType.ANNOTATION_TYPE)//注解
@Target(ElementType.PACKAGE) ///包   
@Document：说明该注解将被包含在javadoc中
@Inherited：说明子类可以继承父类中的该注解

```
##### 对jedis的封装
```
继承redis.clients.jedis.Jedis
实现org.springframework.cglib.proxy.MethodInterceptor接口
public class JedisClusterAdaptor extends JedisCluster implements MethodInterceptor {
@Override
public Object intercept(Object enhance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    Object result = methodProxy.invokeSuper(enhance, args);
    return result;
 }
}
```
##### 对jedis的封装

##### 读取配置文件 @ConfigurationProperties
```
@ConfigurationProperties注解主要用来把properties配置文件转化为bean来使用的，而@EnableConfigurationProperties注解的
作用是@ConfigurationProperties注解生效。如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置
文件转化的bean的

@Component
/*prefix定义配置文件中属性*/
@ConfigurationProperties(prefix="local")
public class ComponentProperties {}

@Component
@EnableConfigurationProperties(ComponentProperties.class)
public class ComponentConfiguration {
    @Autowired
    private ComponentProperties properties
}

ps:spring-boot 1.5,spring-boot 2.0 可以这样用

@Component
public class ComponentConfiguration {
    @Autowired
    private ComponentProperties properties
}

```
##### @ModelAttribute
```
@ModelAttribute一个具有如下三个作用：
①绑定请求参数到命令对象：放在功能处理方法的入参上时，用于将多个请求参数绑定到一个命令对象，从而简化绑
定流程，而且自动暴露为模型数据用于视图页面展示时使用；

②暴露表单引用对象为模型数据：放在处理器的一般方法（非功能处理方法）上时，是为表单准备要展示的表单引用
对象，如注册时需要选择的所在城市等，而且在执行功能处理方法（@RequestMapping 注解的方法）之前，自动添加
到模型对象中，用于视图页面展示时使用；

③暴露@RequestMapping 方法返回值为模型数据：放在功能处理方法的返回值上时，是暴露功能处理方法的返回值为
模型数据，用于视图页面展示时使用。

使用：
BaseModel{
 @ModelAttribute("ip")
 public String getClientIpAddress(HttpServletRequest request) {}
}

@RestController
public class TestModelAttributeController extends BaseModel {
    @GetMapping("/test")
    @ResponseBody
    public String userId(@ModelAttribute("userId") Integer userId) {
        System.out.println(userId);
        return "success";
    }
}

```
##### HandlerMethodArgumentResolver 自定义注解（参数解析器）
```
自定义注解类
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface IP {
}

实现 HandlerMethodArgumentResolver类，重写
@Override
public boolean supportsParameter(MethodParameter methodParameter) {
    return false;
}

@Override
public Object resolveArgument(MethodParameter methodParameter,
                              ModelAndViewContainer modelAndViewContainer,
                              NativeWebRequest nativeWebRequest,
                              WebDataBinderFactory webDataBinderFactory) throws Exception {
    return null;
}


继承WebMvcConfigurerAdapter类
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
    @Bean
    public IpResolver ipResolver() {
        return new IpResolver();
    }
}

```
##### AutoConfig