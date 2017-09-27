package com.penelope.activiti.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest01  {

	/**
	 * 生成activiti需要的25张表
	 */
	@Test
	public void testCreateTable(){
		ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		
		/**
		 * 引擎配置
		 */
		conf.setJdbcDriver("com.mysql.jdbc.Driver");
		conf.setJdbcUrl("jdbc:mysql://localhost:3306/db_activiti");
		conf.setJdbcUsername("root");
		conf.setJdbcPassword("root");
		
		/**
		 * 配置模式，使用true，自动创建更新表。
		 * false,不能自动创建表
		 * create-drop：先删除表再创建表
		 * true：自动创建和更新表
		 */
		conf.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		/**
		 * 获取流程引擎对象
		 */
		ProcessEngine processEngine = conf.buildProcessEngine();
	}
	
	
	/**
	 * 生成activiti需要的25张表,使用配置文件activiti.cfg.xml
	 */
	@Test
	public void testCreateTableWithXml(){
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine engine = config.buildProcessEngine();
	}
	
	

}
