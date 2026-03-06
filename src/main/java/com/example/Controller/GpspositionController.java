package com.example.Controller;

import java.text.ParseException;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;

import com.alibaba.fastjson.JSON;
import com.example.POJO.gpsposition;
import com.example.Service.GpsToTrajectoryService;
import com.example.Service.GpspositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;


@RestController
@RequestMapping("/Xunjian")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class GpspositionController {
	
	@Autowired
	private GpspositionService gpsService;
	@Autowired
	private GpsToTrajectoryService gttService;
	
	@RequestMapping("/start")
	@ResponseBody
	public JSONObject gpsSaveAction(@RequestBody String str) throws ParseException {

		JSONObject obj=new JSONObject();
		JSONObject json_parameter = JSON.parseObject(str);

		String id= UUID.randomUUID().toString();
		String taskcode = json_parameter.getString("taskcode");
		String lng = json_parameter.getString("lng");
		String lat = json_parameter.getString("lat");
		String createtime = json_parameter.getString("time");
		String people= json_parameter.getString("people");
		/*String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		String createtime = request.getParameter("createtime");
		String people=request.getParameter("people");*/
		System.out.println("---WGS"+lng+"---"+lat);
		
		//DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		gpsposition gps = new gpsposition();
		
		double[] res = new double[2];
		res[0]= Double.parseDouble(lng);
		res[1]= Double.parseDouble(lat);


		gps.setId(id);
		gps.setTaskcode(taskcode);
		gps.setLng(res[0]);
		gps.setLat(res[1]);
		gps.setCreatetime(createtime);

		gps.setPeople(people);

		System.out.println(gps);
		System.out.println("taskcode:"+taskcode);
		
		try {
			//将每次的经纬度存入轨迹临时表
			gpsService.insert(gps);
			//将临时轨迹表里的数据存入轨迹表
			gttService.dealGps(taskcode);
			obj.put("code",1);
			return obj;
		} catch (Exception e) {
			obj.put("code",0);
			return obj;
		}

	}
	
	@RequestMapping("/gpsToTrajectoryAction")
	@ResponseBody
	public void  gpsToTrajectoryAction(@RequestBody String str) {
		JSONObject json_parameter = JSON.parseObject(str);
		String taskcode = json_parameter.getString("taskcode");

		gttService.dealGps(taskcode);
	}
	
	
}
