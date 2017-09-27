package com.penelope.procdef;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {
	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 查询流程定义集合，查act_re_procdef
	 */
	@Test
	public void list() {
		String processDefinitionKey = "myFirstProcess";
		List<ProcessDefinition> pdlist = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)// 通过key查询
				.list();

		for (ProcessDefinition pd : pdlist) {
			System.out.println("ID:" + pd.getId());
			System.out.println("Name:" + pd.getName());
			System.out.println("Version:" + pd.getVersion());
			System.out.println("DeploymentId:" + pd.getDeploymentId());
			System.out.println("DiagramResourceName:" + pd.getDiagramResourceName());
			System.out.println("----------------------------------------------------------\n");
		}
	}

	/**
	 * 根据流程部署ID和资源文件名称查询流程图片
	 * 
	 * @throws IOException
	 */
	@Test
	public void getImageById() throws IOException {
		InputStream inputStream = processEngine.getRepositoryService()// 获取service
				.getResourceAsStream("10001", "diagrams/demo1.png");// 根据流程部署ID和资源名称获取输入流

		FileUtils.copyInputStreamToFile(inputStream, new File("G:/demo1.png"));
	}

	/**
	 * 查询最新版本的流程定义
	 */
	@Test
	public void latestVersionList() {
		List<ProcessDefinition> listAll = processEngine.getRepositoryService()// 获取service
				.createProcessDefinitionQuery()// 流程定义查询
				.orderByProcessDefinitionVersion()// 根据流程定义版本查询
				.asc()// 升序
				.list();// 获取列表
		// 定义有序map，相同的key，假如添加map的值 后面的值会覆盖前面相同key的值
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();

		// 遍历几个，根据key来覆盖前面的值，从而保证最新的Key覆盖前面的所有老的Key的值
		for (ProcessDefinition pd : listAll) {
			map.put(pd.getKey(), pd);
		}

		List<ProcessDefinition> result = new LinkedList<ProcessDefinition>(map.values());
		for (ProcessDefinition pd : result) {
			System.out.println("Id: " + pd.getId());
			System.out.println("Name: " + pd.getName());
			System.out.println("Key: " + pd.getKey());
			System.out.println("Version: " + pd.getVersion());
			System.out.println("-------------------------------------");

		}
	}
	
	/**
	 * 流程定义删除
	 */
	@Test
	public void deleteById(){
		processEngine.getRepositoryService()//获取service
			.deleteDeployment("17051");//删除流程定义
		System.out.println("delete ok.");
	}
	
	/**
	 * 级联删除 已经在使用的流程实例信息也会被级联删除
	 * 开发中使用这种方式删除
	 */
	@Test
	public void deleteCascade(){
	    processEngine.getRepositoryService()
	        .deleteDeployment("17501", true); // 默认是false true就是级联删除
	    System.out.println("delete cascade OK!");
	}
	
	/**
	 * 一下子把所有Key相同的流程定义全部删除；
	 */
	@Test
	public void deleteByKey(){
		List<ProcessDefinition> pdList = processEngine.getRepositoryService()//获取service
				.createProcessDefinitionQuery()//查询
				.processDefinitionKey("mySecondProcess")//根据key查询
				.list();
		
		for(ProcessDefinition pd : pdList){
			processEngine.getRepositoryService()
				.deleteDeployment(pd.getDeploymentId(), true);
		}
	}
	
	/**
	 * 首先说下结论，流程定义是不能修改的；
	 * 通过增加版本号的方式。来实现“修改”的
	 */
	
}
