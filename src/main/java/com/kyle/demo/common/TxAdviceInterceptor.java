package com.kyle.demo.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//如果不想在每个方法上使用@Tranactional注解，那么就进行这样的配置
//如果使用@Transaction注解，则不需要进行配置
@Aspect
@Configuration
public class TxAdviceInterceptor {

	
	//事务的超时时间
	private static final String AOP_EXPRESSION = "execution(* com.kyle.demo.service.impl.*.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor txAdvice(){
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		//只读事务 ,
		RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
		readOnlyTx.setReadOnly(true);
		//在无transaction状态下执行；如果当前已有transaction，则将当前transaction挂起.
		readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		
		//读写事务
		RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
		requiredTx.setRollbackRules(
				Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		//在有transaction状态下执行；如当前没有transaction，则创建新的transaction.
		requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//事务的超时时间,当为默认值 TransactionDefinition.TIMEOUT_DEFAULT时,会使用底层系统的默认超时时间.
		requiredTx.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
		
		Map<String, TransactionAttribute> txMap = new HashMap<>();
		
		txMap.put("add*", requiredTx);  
        txMap.put("save*", requiredTx);  
        txMap.put("reg*", requiredTx);  
        txMap.put("insert*", requiredTx);  
        txMap.put("update*", requiredTx);  
        txMap.put("modify*", requiredTx);  
        txMap.put("edit*", requiredTx);  
        txMap.put("delete*", requiredTx);
        txMap.put("del*", requiredTx);  
        txMap.put("remove*", requiredTx);
        txMap.put("audit", requiredTx);  
        
        txMap.put("get*", readOnlyTx);  
        txMap.put("query*", readOnlyTx);  
        txMap.put("find*", readOnlyTx);  
        txMap.put("load*", readOnlyTx);  
        txMap.put("search*", readOnlyTx);  
       
        source.setNameMap(txMap);
        
        TransactionInterceptor  txAdvice = new TransactionInterceptor(transactionManager, source);
        
        return txAdvice;
        
		
	}
	
	@Bean
	public Advisor txAdviceAdvisor(){
		
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_EXPRESSION);
		
		DefaultPointcutAdvisor ponitcutAdvicor = new DefaultPointcutAdvisor(pointcut, txAdvice());
		
		return ponitcutAdvicor;
	}
	
	
}
