package com.lx.emp.dao;

import com.lx.emp.pojo.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer empId);
    int deleteBatchById(List<Integer> list);
    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    @Select("select e.*,d.dept_name from tb_emp e,tb_dept d where  e.dept_id = d.dept_id and e.emp_no like concat('%',#{no},'%') and e.emp_name like concat('%',#{name},'%') and e.dept_id like concat('%',#{deptId},'%')  and e.is_deleted = 0 order by emp_id")
    List<Employee> findAll(@Param("name") String name,
                           @Param("no") String no,
                           @Param("deptId") String deptId);

    @Select("select * from tb_emp where emp_no = #{empNO} and is_deleted = 0")
    Employee selectByEmpNO(String empNO);

    @Select("select * from tb_emp where emp_id = #{empId}  and is_deleted = 0")
    Employee selectByEmpId(Integer empId);

    @Update("UPDATE tb_emp set emp_password = #{password} where emp_no = #{no}")
    int changePassword(@Param("no") String no,@Param("password") String password);


}