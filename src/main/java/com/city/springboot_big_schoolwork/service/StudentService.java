package com.city.springboot_big_schoolwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.city.springboot_big_schoolwork.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

public interface StudentService extends IService<Student> {
    void importStudents(MultipartFile file);
    void exportStudents(HttpServletResponse response, Map<String, Object> params);
}
