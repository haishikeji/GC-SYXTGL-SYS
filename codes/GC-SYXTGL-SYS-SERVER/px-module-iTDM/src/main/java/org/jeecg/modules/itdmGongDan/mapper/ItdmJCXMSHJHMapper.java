package org.jeecg.modules.itdmGongDan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.itdmGongDan.vo.ItdmJCXMAndSYJHVO;
import org.jeecg.modules.itdmGongDan.vo.ItdmJHYQueryListVO;
import org.jeecg.modules.itdmGongDan.vo.ItdmXMJLQueryListVO;

import java.util.List;

public interface ItdmJCXMSHJHMapper {


    /**
     * 查询改委托id对应的test_plan的状态（为0或者为空时该计划排期还未确定，说明计划排期未完成）
     */
    @Select("select b.status " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "left join itdm_weituo_yangpin d on a.yangpin_id = d.id " +
            "left join itdm_device e on b.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId}" +
            "and (b.status =0 or b.status is null)")
    List<Integer> getTestPlanStatus(@Param("weituoId") String weituoId);


    /**
     * 项目经理查询列表——二级列表——通过登录用户查询
     */
    @Select("select a.weituo_id, m.weituo_yangpin_id, m.test_items_id, a.test_items, " +
            "m.jihua_shiyan_id, b.pm, " +
            "m.shebei_id, m.start_date as jihuaStartDate, m.end_date as jihuaEndDate, " +
            "b.status, b.report_no, m.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, e.device_name, " +
            "m.jihua_shiyan_id as parent, " +
            "m.shiiyan_type, m.worker, m.yangpin_count, m.neirong, m.shiji_start_date,m.id " +
            "from itdm_gongdan_master m " +
            "left join itdm_test_plan b on m.jihua_shiyan_id = b.id " +
            "left join itdm_weituo_yangpin_extend a  on m.test_items_id = a.id " +
            "left join itdm_weituo_yangpin d on m.weituo_yangpin_id = d.id " +
            "left join itdm_device e on m.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId} and m.jihua_shiyan_id " +
            "in (select b.id from itdm_test_plan b " +
            "left join itdm_weituo_info f on b.weituo_id = f.id " +
            "where b.weituo_id = #{weituoId} and b.pm = #{realName} )")
    List<ItdmXMJLQueryListVO> getXMJLSecondListByUserName(@Param("weituoId") String weituoId, @Param("realName") String realName);

    /**
     * 项目经理查询列表——一级列表——通过登录用户查询
     */
    @Select("select a.weituo_id, a.yangpin_id, a.id as testItemsId, a.test_items, " +
            "b.id as testPlanId, b.shebei_id, b.pm, b.jihua_start_date, b.jihua_end_date, b.status, b.report_no, b.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, e.device_name " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "left join itdm_weituo_yangpin d on a.yangpin_id = d.id " +
            "left join itdm_device e on b.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId} and b.pm = #{realName}")
    List<ItdmXMJLQueryListVO> getXMJLFirstListByUserName(@Param("weituoId") String weituoId, @Param("realName") String realName);

    /**
     * 项目经理查询列表——二级列表
     */
    @Select("select a.weituo_id, m.weituo_yangpin_id, m.test_items_id, a.test_items, " +
            "m.jihua_shiyan_id, b.pm, " +
            "m.shebei_id, m.start_date as jihuaStartDate, m.end_date as jihuaEndDate, " +
            "b.status, b.report_no, m.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, e.device_name, " +
            "m.jihua_shiyan_id as parent, " +
            "m.shiiyan_type, m.worker, m.yangpin_count, m.neirong, m.shiji_start_date,m.id " +
            "from itdm_gongdan_master m " +
            "left join itdm_test_plan b on m.jihua_shiyan_id = b.id " +
            "left join itdm_weituo_yangpin_extend a  on m.test_items_id = a.id " +
            "left join itdm_weituo_yangpin d on m.weituo_yangpin_id = d.id " +
            "left join itdm_device e on m.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId}")
    List<ItdmXMJLQueryListVO> getXMJLSecondList(@Param("weituoId") String weituoId);

    /**
     * 项目经理查询列表——一级列表
     */
    @Select("select a.weituo_id, a.yangpin_id, a.id as testItemsId, a.test_items, " +
            "b.id as testPlanId, b.shebei_id, b.pm, b.jihua_start_date, b.jihua_end_date, b.status, b.report_no, b.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, e.device_name " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "left join itdm_weituo_yangpin d on a.yangpin_id = d.id " +
            "left join itdm_device e on b.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId}")
    List<ItdmXMJLQueryListVO> getXMJLFirstList(@Param("weituoId") String weituoId);


    /**
     * 计划员查询列表
     */
    @Select("select a.weituo_id, a.yangpin_id, a.id as testItemsId, a.test_items, " +
            "b.id as testPlanId, b.shebei_id, b.pm, b.jihua_start_date, b.jihua_end_date, b.status, b.report_no, b.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, e.device_name " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "left join itdm_weituo_yangpin d on a.yangpin_id = d.id " +
            "left join itdm_device e on b.shebei_id = e.id " +
            "where a.weituo_id = #{weituoId}")
    List<ItdmJHYQueryListVO> getAll3(@Param("weituoId") String weituoId);

    /**
     * 计划员查询列表
     * shenhe_status 审核状态（0未审核1审核通过2审核拒绝3保存）——231123
     * complete_status 完结状态（0未完结1已完结）
     */
    @Select("select a.weituo_id, a.yangpin_id, a.id as testItemsId, a.test_items, " +
            "b.id as testPlanId, b.shebei_id, b.pm, b.jihua_start_date, b.jihua_end_date, b.status, b.report_no, b.chuanganqi, " +
            "d.sample_name, d.sample_model_specification, " +
            "i.shenhe_status " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "left join itdm_weituo_yangpin d on a.yangpin_id = d.id " +
            "left join itdm_weituo_info i on a.weituo_id = i.id " +
            "where a.weituo_id in (select id from itdm_weituo_info where itdm_weituo_info.complete_status = 0 " +
            "and itdm_weituo_info.shenhe_status = '1' )")
    List<ItdmJHYQueryListVO> getAllWithoutWeituoId();

    /**
     * 计划员查询列表(测试)
     */
    @Select("select a.weituo_id, a.yangpin_id, a.test_items, a.id as testItemsId, " +
            "b.id as testPlanId, b.shebei_id, b.pm, b.jihua_start_date, b.jihua_end_date, b.status, b.report_no, b.chuanganqi " +
            "from itdm_weituo_yangpin_extend a " +
            "left join itdm_test_plan b on a.id = b.test_items_id " +
            "where a.weituo_id = #{weituoId}")
    List<ItdmJCXMAndSYJHVO> getAll(@Param("weituoId") String weituoId);

}
