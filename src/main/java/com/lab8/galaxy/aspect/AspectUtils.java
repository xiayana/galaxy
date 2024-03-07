package com.lab8.galaxy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectUtils {
   /* @Autowired
    private RedisUtil redisUtil;
    private DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
    private ExpressionParser parser = new SpelExpressionParser();

    @Before(value = "@annotation(com.lab8.engine.annotation.CheckValidateParam)")
    public void validateParamsInRedis(JoinPoint join){
        log.info("进入切面");
        MethodSignature signature = (MethodSignature) join.getSignature();
        Method method = signature.getMethod();
        CheckValidateParam[] annotationsByType = method.getAnnotationsByType(CheckValidateParam.class);
        Object value=null;
        for (CheckValidateParam checkValidate:annotationsByType){
            String key = checkValidate.key();
            EvaluationContext context;
            Expression expression = parser.parseExpression(key);
            if(key.contains("#")&&"#".equals(key)==false){
                context= new StandardEvaluationContext();
                String[] parameterNames = discoverer.getParameterNames(method);
                Object[] args = join.getArgs();
                for (int i=0;i<parameterNames.length;i++){
                    context.setVariable(parameterNames[i],args[i]);
                }
                value = expression.getValue(context);
            }
            log.info("切面参数"+value);
            if(value!=null){
                boolean b = redisUtil.setNxEx(value.toString(), value.toString(), 50);
                if(b){
                    System.out.println("执行其他操作");
                }else {
                    System.out.println("不执行操作");
                }
            }
        }
    }*/
}
