## pd-sb-core
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
@ConfigurationProperties注解主要用来把properties配置文件转化为bean来使用的，而@EnableConfigurationProperties注解的作用是
@ConfigurationProperties注解生效。如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的

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