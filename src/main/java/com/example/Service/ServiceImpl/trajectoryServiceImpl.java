package com.example.Service.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Service.trajectoryService;
import com.example.Dao.trajectoryMapper;


@Service("trajectoryService")
public class trajectoryServiceImpl implements trajectoryService{
	@Autowired
	private trajectoryMapper trajectoryMapper;
	
	@Override
	public Object selectTodayTrajectory(String time) {
		// TODO Auto-generated method stub
		return trajectoryMapper.selectPath(time);
	}
	
	/**
	 * ouyang
	 */
	@Override
	public List<Map<String, Object>> selectPath(String today) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> reMap = trajectoryMapper.selectPath(today);
		return reMap;
	}

	//根绝任务查询轨迹
	@Override
	public Object selectPathByTaskcode(String takscode) {
		// TODO Auto-generated method stub
		return trajectoryMapper.selectPathByTaskcode(takscode);
	}

}
