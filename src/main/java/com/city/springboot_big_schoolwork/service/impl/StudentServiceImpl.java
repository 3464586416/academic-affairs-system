package com.city.springboot_big_schoolwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.city.springboot_big_schoolwork.entity.Student;
import com.city.springboot_big_schoolwork.entity.SysClass;
import com.city.springboot_big_schoolwork.entity.SysDepartment;
import com.city.springboot_big_schoolwork.mapper.StudentMapper;
import com.city.springboot_big_schoolwork.service.StudentService;
import com.city.springboot_big_schoolwork.service.SysClassService;
import com.city.springboot_big_schoolwork.service.SysDepartmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Override
    public void importStudents(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Student student = new Student();
                student.setStudentNo(getCellValue(row.getCell(0)));
                student.setName(getCellValue(row.getCell(1)));
                student.setGender(getCellValue(row.getCell(2)));
                student.setEmail(getCellValue(row.getCell(3)));
                student.setPhone(getCellValue(row.getCell(4)));
                student.setEnrollmentYear(getCellValue(row.getCell(5)));
                student.setEnrollStatus("在读");
                student.setStatus(1);
                save(student);
            }
        } catch (IOException e) {
            throw new RuntimeException("导入失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void exportStudents(HttpServletResponse response, java.util.Map<String, Object> params) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("学生信息");
            Row header = sheet.createRow(0);
            String[] headers = {"学号", "姓名", "性别", "邮箱", "电话", "入学年份", "学籍状态"};
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            List<Student> students = list(wrapper);
            for (int i = 0; i < students.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Student s = students.get(i);
                row.createCell(0).setCellValue(s.getStudentNo());
                row.createCell(1).setCellValue(s.getName());
                row.createCell(2).setCellValue(s.getGender());
                row.createCell(3).setCellValue(s.getEmail());
                row.createCell(4).setCellValue(s.getPhone());
                row.createCell(5).setCellValue(s.getEnrollmentYear());
                row.createCell(6).setCellValue(s.getEnrollStatus());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("导出失败: " + e.getMessage(), e);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
