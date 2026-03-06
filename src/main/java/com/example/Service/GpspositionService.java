package com.example.Service;


import com.example.POJO.gpsposition;

public interface GpspositionService {

	public void insert(gpsposition record);

	//获取taskcode
	public String getTaskcode();

	public void updateByPrimaryKey(gpsposition record);


	//任务结束删除临时轨迹
	void deleteguiji(String taskcode);
}
