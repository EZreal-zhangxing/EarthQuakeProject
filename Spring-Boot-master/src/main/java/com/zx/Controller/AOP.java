package com.zx.Controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOP {
	
	@Before("execution(* com.zx.Controller.*.*(..))")
	public void beforeMethod(JoinPoint jp){
		System.out.println("【"+jp.getSignature().getName()+"】 方法执行前");
	}
	
//	@AfterReturning(value="execution(* com.zx.Controller.*.*(..))",returning="result")
//	public void aroundMethod(JoinPoint jp, Object result){
//		System.out.println("【"+jp.getSignature().getName()+"】 方法执行");
//		System.out.println("【"+result.toString()+"】 返回结果");
//	}
	//execution表达式 返回值 包名package(..两个句点表示同级所有包和子包) 类* 方法名*(..)所有方法
	@After("execution(* com.zx.Controller.*.*(..))")
	public void afterMethod(JoinPoint jp){
		System.out.println("【"+jp.getSignature().getName()+"】 方法执行后");
	}
}
