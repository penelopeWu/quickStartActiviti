package com.penelope.activiti.flow;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class StudentLeaveProcess {

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
				.addClasspathResource("diagrams/StudentLeaveProcess.bpmn")// 加载资源文件
				.addClasspathResource("diagrams/StudentLeaveProcess.png").name("学生请假流程").deploy();// 部署

		System.out.println("流程部署id:  " + deployment.getId());
		System.out.println("流程部署名称：    " + deployment.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void start() {
		ProcessInstance instance = processEngine.getRuntimeService()// 运行时service
				.startProcessInstanceByKey("studentLeaveProcess");
		System.out.println("流程ID：" + instance.getId());
		System.out.println("流程定义ID：" + instance.getProcessDefinitionId());
	}

	/**
	 * 查看任务
	 */
	@Test
	public void findTask() {
		List<Task> taskList = processEngine.getTaskService()// 任务相关service
				.createTaskQuery()// 创建任务查询
				.taskAssignee("张颂威")// 指定某个人
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
		processEngine.getTaskService().complete("30002");
	}

	/**
	 * 查询任务状态
	 */
	@Test
	public void processStatus() {
		ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId("25003").singleResult();
		if (instance == null) {
			System.out.println("the process is over.");
		} else {
			System.out.println("the process is running.");
		}
	}

	/**
	 * 历史任务查询，act_hi_taskinst
	 */
	@Test
	public void historyTaskList() {
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
				.taskAssignee("penelope").finished().list();
		for (HistoricTaskInstance hi : list) {
			System.out.println("ID:" + hi.getId());
			System.out.println("ProcessInstanceId:" + hi.getProcessInstanceId());
			System.out.println("Assignee:" + hi.getAssignee());
			System.out.println("CreateTime:" + hi.getCreateTime());
			System.out.println("EndTime:" + hi.getEndTime());
			System.out.println("-----------------");
		}
	}
	
	/**
	 * 查询历史流程实例，act_hi_procinst
	 */
	@Test
	public void getHistoryProcessInstance(){
		HistoricProcessInstance inst = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId("5001")
				.singleResult();
		System.out.println("Id: " + inst.getId());
		System.out.println("StartTime: "+inst.getStartTime());
		System.out.println("EndTime: "+inst.getEndTime());

	}

}
