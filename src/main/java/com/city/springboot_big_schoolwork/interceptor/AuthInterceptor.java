package com.city.springboot_big_schoolwork.interceptor;

import com.city.springboot_big_schoolwork.entity.Teacher;
import com.city.springboot_big_schoolwork.mapper.TeacherMapper;
import com.city.springboot_big_schoolwork.mapper.SysUserRoleMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    private static final String[] PUBLIC_PATHS = {
            "/login", "/api/login", "/api/logout", "/api/currentUser",
            "/static/", "/css/", "/js/", "/error"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // 放行公开路径
        for (String path : PUBLIC_PATHS) {
            if (uri.equals(path) || uri.startsWith(path)) return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // 未登录：如果是页面请求跳转到登录页，如果是API返回401
            if (uri.startsWith("/api/")) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
                return false;
            }
            response.sendRedirect("/login");
            return false;
        }

        // 管理员放行
        Long userId = (Long) session.getAttribute("userId");
        List<String> roles = sysUserRoleMapper.getRoleCodesByUserId(userId);
        if (roles.contains("ADMIN")) return true;

        // 非管理员根据角色限制访问
        boolean isTeacher = roles.contains("TEACHER");
        boolean isSecretary = roles.contains("SECRETARY");

        // 秘书放行（秘书有大部分管理权限）
        if (isSecretary) return true;

        // 教师权限：可查看大部分数据，可录入成绩、管理自己的排课
        if (isTeacher) {
            Teacher teacher = teacherMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Teacher>()
                            .eq(Teacher::getUserId, userId).last("LIMIT 1"));
            Long teacherId = teacher != null ? teacher.getId() : null;

            // GET 请求：教师可查看以下内容
            String[] allowedGet = {
                    // 个人信息
                    "/teacher", "/teacher/list", "/teacher/all",
                    // 课程、成绩、排课、考试
                    "/course/", "/grade/", "/scheduling/", "/exam/",
                    // 学生、教室、教材、选课
                    "/student/", "/classroom/", "/textbook/", "/selection/",
                    // 系统基础数据
                    "/system/dept/", "/system/semester/", "/system/class/",
                    // 首页
                    "/"
            };
            boolean isGet = "GET".equals(request.getMethod());
            if (isGet) {
                for (String path : allowedGet) {
                    if (uri.equals(path) || uri.startsWith(path)) return true;
                }
            }
            // POST 请求：教师仅可执行本人业务动作（成绩录入/排课保存/考试发布/改本人资料），其余一律 403
            if (!isGet) {
                if (uri.startsWith("/grade/save")
                        || uri.startsWith("/scheduling/save")
                        || uri.startsWith("/exam/save")
                        || uri.startsWith("/teacher/save")) return true;
                response.setStatus(403);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":403,\"message\":\"权限不足：教师无权操作该功能\"}");
                return false;
            }
        }

        // 其余角色（如学生）不允许进入管理端
        response.setStatus(403);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":403,\"message\":\"该账号无权访问管理端\"}");
        return false;
    }
}
