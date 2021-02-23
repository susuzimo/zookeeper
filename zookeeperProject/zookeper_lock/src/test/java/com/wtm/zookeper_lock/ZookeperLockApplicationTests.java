package com.wtm.zookeper_lock;

import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZookeperLockApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		String CONNECTSTRING="10.80.16.183:2181";
		//创建zk连接
		ZkClient zkClient= new ZkClient(CONNECTSTRING);
		final String PATH3="/lock3";
		zkClient.createEphemeral(PATH3);

		Thread.sleep(10000);
		for(int i=0;i<10;i++){
			zkClient.createEphemeralSequential(PATH3+"/","lock");
		}
		List<String> children = zkClient.getChildren(PATH3);
		for(String c:children){
			System.out.println(c);
		}
	}

}
