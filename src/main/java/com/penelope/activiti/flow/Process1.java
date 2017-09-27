package com.penelope.activiti.flow;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Process1 {

	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()// 创建部署
				.addClasspathResource("diagrams/demo1.bpmn")// 加载资源文件
				.addClasspathResource("diagrams/demo1.png")
				.name("demo1")
				.deploy();// 部署

		System.out.println("流程部署id:  " + deployment.getId());
		System.out.println("流程部署名称：    " + deployment.getName());
	}

	/**
	 * 启动流程实例
	 * 一般启动流程实例的时候，我们用Key来启动。
	 * 这样启动的时候 就是用的   最新版本  的流程定义来启动流程实例的；
	 */
	@Test
	public void start() {
		ProcessInstance instance = processEngine.getRuntimeService()// 运行时service
				.startProcessInstanceByKey("myFirstProcess");
		System.out.println("流程ID：" + instance.getId());
		System.out.println("流程定义ID：" + instance.getProcessDefinitionId());
		System.out.println("Version：" + instance.getProcessDefinitionVersion());

	}

	/**
	 * 查看任务
	 */
	@Test
	public void findTask() {
		List<Task> taskList = processEngine.getTaskService()// 任务相关service
				.createTaskQuery()// 创建任务查询
				.taskAssignee("penelope")// 指定某个人
				.list();
		for (Task task : taskList) {
			System.out.println("任务ID：" + task.getId());
			System.out.println("任务名称：" + task.getName());
			System.out.println("任务创建时间：" + task.getCreateTime());
			System.out.println("任务委派人：" + task.getAssignee());
			System.out.println("流程实例ID：" + task.getProcessInstanceId());

		}
	}

	/**
	 * 完成任务
	 */
	@Test
	public void completeTask() {
		processEngine.getTaskService().complete("5004");
	}
	
	/**
	 * 查询流程状态（正在执行或者已经执行结束)
	 */
	@Test
	public void processStatus(){
		ProcessInstance pi=processEngine.getRuntimeService() // 获取运行时Service
		        .createProcessInstanceQuery() // 创建流程实例查询
		        .processInstanceId("35001") // 用流程实例ID查询
		        .singleResult();
		    if(pi!=null){
		        System.out.println("流程正在执行！");
		    }else{
		        System.out.println("流程已经执行结束！");
		    }
	}
}
