package com.example.Service.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.POJO.gpsposition;
import com.example.POJO.trajectoryWithBLOBs;
import com.example.Dao.trajectoryMapper;
import com.example.Service.GpsToTrajectoryService;
import com.example.Dao.gpspositionMapper;


@Service
public class GpsToTrajectoryServiceImpl implements GpsToTrajectoryService{
	
	@Autowired
	private trajectoryMapper trajectoryMapper;


	@Autowired
	private gpspositionMapper gpspositionMapper;

	@Override
	public void dealGps(String taskcode) {
		List<gpsposition> gpsList = gpspositionMapper.selectByTaskcode(taskcode);
		System.out.println("gpList:"+gpsList);
		String time = gpsList.get(0).getCreatetime();
		/*try {
			time = gpsList.get(0).getCreatetime();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String pathxs = "";
		String pathys = "";
		for(int i=0;i<gpsList.size();i++){
			if(i<gpsList.size()-1){
				pathxs += Double.toString(gpsList.get(i).getLng())+",";
				pathys += Double.toString(gpsList.get(i).getLat())+",";
			}
			else{
				pathxs += Double.toString(gpsList.get(i).getLng());
				pathys += Double.toString(gpsList.get(i).getLat());
			}
		}
		byte[] pathx = pathxs.getBytes();
		byte[] pathy = pathys.getBytes();
		trajectoryWithBLOBs record = new trajectoryWithBLOBs();
		System.out.println("pathx:"+pathx);
		System.out.println("pathy:"+pathy);
		record.setCreatetime(time);
		record.setPathx(pathx);
		record.setPathy(pathy);
		record.setTaskcode(taskcode);

		System.out.println("record前:"+record);

		System.out.println("trajectoryMapper.getTaskcodeHis():"+trajectoryMapper.getTaskcodeHis().size());

		if (trajectoryMapper.getTaskcodeHis().size() == 0) {
			//插入
			System.out.println("准");
			String id= UUID.randomUUID().toString();
			record.setId(id);
			//System.out.println("准");
			System.out.println("record后:"+record);
			trajectoryMapper.insert(record);
			System.out.println("准完");
		}else {
			for (int i = 0; i < trajectoryMapper.getTaskcodeHis().size(); i++) {

				if(!taskcode.equals(trajectoryMapper.getTaskcodeHis().get(i).getTaskcode())&&i!=trajectoryMapper.getTaskcodeHis().size()-1){

				}else if (!taskcode.equals(trajectoryMapper.getTaskcodeHis().get(i).getTaskcode())&&i==trajectoryMapper.getTaskcodeHis().size()-1){
					//插入
					System.out.println("准");
					String id= UUID.randomUUID().toString();
					record.setId(id);
					//System.out.println("准");
					System.out.println("record后:"+record);
					trajectoryMapper.insert(record);
					System.out.println("准完");
					break;
				} else{
					//更新
					System.out.println("准备更新啦");
					trajectoryMapper.updateByPrimaryKeyWithBLOBs(record);
					System.out.println("更新完成啦");
					break;
				}
			}
		}








	}


	

}
