package com.city.springboot_big_schoolwork.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.city.springboot_big_schoolwork.entity.StudentSelection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentSelectionMapper extends BaseMapper<StudentSelection> {

    @Select("SELECT ss.id, ss.student_id AS studentId, ss.course_id AS courseId, " +
            "ss.semester_id AS semesterId, ss.phase_id AS phaseId, " +
            "ss.selection_status AS selectionStatus, ss.is_lottery AS isLottery, ss.selected_at AS selectedAt, " +
            "s.name AS studentName, s.student_no AS studentNo, " +
            "c.course_name AS courseName, c.course_no AS courseNo " +
            "FROM student_selection ss " +
            "LEFT JOIN student s ON ss.student_id = s.id " +
            "LEFT JOIN course c ON ss.course_id = c.id " +
            "ORDER BY ss.selected_at DESC")
    List<Map<String, Object>> selectWithDetails();
}
