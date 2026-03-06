package com.example.Dao;

import java.util.List;
import java.util.Map;

import com.example.POJO.trajectory;
import com.example.POJO.trajectoryWithBLOBs;
import org.apache.ibatis.annotations.*;
import org.bouncycastle.voms.VOMSAttribute;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface trajectoryMapper {

    //根据taskcode查询轨迹
    @Select("select * from TRAJECTORY where taskcode = #{taskcode, jdbcType=VARCHAR}")
	List<trajectoryWithBLOBs> selectPathByTaskcode(@Param("taskcode")String taskcode);
	
	List<Map<String,Object>> selectPath(String today);

	//根据id删除信息
	@Delete(" delete from TRAJECTORY where id = #{id,jdbcType=VARCHAR}")
    void deleteByPrimaryKey(@Param("id") String id);

	//插入轨迹数据
    @Insert("insert into TRAJECTORY (id, taskcode, createtime, checkmile, pathx, pathy)\n" +
            "    values (#{id,jdbcType=VARCHAR}, #{taskcode,jdbcType=VARCHAR},  #{createtime,jdbcType=VARCHAR},\n" +
            "      #{checkmile,jdbcType=VARCHAR}, #{pathx,jdbcType=LONGVARBINARY}, #{pathy,jdbcType=LONGVARBINARY}\n" +
            "      )")
    void insert(trajectoryWithBLOBs record);

    //int insertSelective(trajectoryWithBLOBs record);

    //根据id查询信息
    @Select("select * from TRAJECTORY where id = #{id,jdbcType=VARCHAR}")
    trajectoryWithBLOBs selectByPrimaryKey(@Param("id") String id);

    //int updateByPrimaryKeySelective(trajectoryWithBLOBs record);


    //更新轨迹信息
    @Update("update TRAJECTORY\n" +
            "    set  pathx = #{pathx,jdbcType=LONGVARBINARY},\n" +
            "      pathy = #{pathy,jdbcType=LONGVARBINARY}\n" +
            "    where taskcode = #{taskcode,jdbcType=VARCHAR}")
    void updateByPrimaryKeyWithBLOBs(trajectoryWithBLOBs record);

    //更新除数组外的信息
    @Update("update TRAJECTORY\n" +
            "    set taskcode = #{taskcode,jdbcType=VARCHAR},\n" +
            "        createtime = #{createtime,jdbcType=VARCHAR},\n" +
            "      checkmile = #{checkmile,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=VARCHAR}")
    void updateByPrimaryKey(trajectory record);

    //查询任务id
    @Select("select taskcode from TRAJECTORY")
    List<trajectory> getTaskcodeHis();

    //根据taskcode查询id
    @Select("select id from TRAJECTORY where taskcode=#{taskcode}")
    String getidBytaskcode(@Param("taskcode")String taskcode);


    
}