package com.example.Service.ServiceImpl;

import com.example.Dao.gpspositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.POJO.gpsposition;
import com.example.Service.GpspositionService;

@Service
public class GpspositionServiceImpl implements GpspositionService{
	
	@Autowired
	private gpspositionMapper gpsMapper;


	@Override
	public void insert(gpsposition record) {
		System.out.println("到了这儿没？");
		gpsMapper.insert(record);
		System.out.println("到了这儿啦！");

	}

	@Override
	public String getTaskcode() {
		return gpsMapper.getTaskcode();
	}

	@Override
	public void updateByPrimaryKey(gpsposition record) {
		//System.out.println("到了这儿没？");
		gpsMapper.updateByPrimaryKey(record);
		//System.out.println("到了这儿啦！");
	}

	@Override
	public void deleteguiji(String taskcode) {
		gpsMapper.deleteguiji(taskcode);
	}


}
