package com.penelope.procdef;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class DeployProcessDef {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deployWithClassPath() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()// 创建部署
				.addClasspathResource("diagrams/demo2.bpmn")// 加载资源文件
				.addClasspathResource("diagrams/demo2.png")// 加载资源文件
				.name("demo2")// 部署名称
				.deploy();// 部署

		System.out.println("流程部署id:  " + deployment.getId());
		System.out.println("流程部署名称：    " + deployment.getName());
	}

	/**
	 * 使用zip方式部署流程定义
	 */
	@Test
	public void deployWithZip() {
		InputStream inputStream = this.getClass()// 获取当前Class对象
				.getClassLoader()// 获取类加载器
				.getResourceAsStream("diagrams/demo1.zip");// 获取指定文件资源流

		ZipInputStream zipInputStream = new ZipInputStream(inputStream);// 实例化zip输入流

		Deployment deployment = processEngine.getRepositoryService()// 获取部署相关的service
				.createDeployment()// 创建部署
				.addZipInputStream(zipInputStream)// 添加zip输入流
				.name("demo1流程")// 流程名称
				.deploy();// 部署

		System.out.println("流程部署ID：" + deployment.getId());
		System.out.println("流程部署时间：" + deployment.getDeploymentTime());
		System.out.println("流程部署名称：" + deployment.getName());
	}

}
