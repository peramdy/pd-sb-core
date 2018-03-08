package com.pd.spring.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author pd 2018/2/7.
 *         解析spring的el表达式
 */
public class ParseSpringEl {


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

}
