package com.example.Service;

import java.util.List;
import java.util.Map;

public interface trajectoryService {
	
	public Object selectTodayTrajectory(String time);
	
	/**
	 * 
	 */
	public List<Map<String,Object>> selectPath(String today);

	//根据任务查询轨迹
	public Object selectPathByTaskcode(String takscode);
}
