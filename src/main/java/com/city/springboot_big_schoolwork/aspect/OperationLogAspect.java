package com.city.springboot_big_schoolwork.aspect;

import com.city.springboot_big_schoolwork.annotation.OperationLog;
import com.city.springboot_big_schoolwork.entity.SysLog;
import com.city.springboot_big_schoolwork.service.SysLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long duration = System.currentTimeMillis() - start;

        SysLog log = new SysLog();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                log.setUserId((Long) session.getAttribute("userId"));
                log.setUsername((String) session.getAttribute("username"));
            }
            log.setMethod(request.getMethod() + " " + request.getRequestURI());
        }

        String op = operationLog.value();
        if (op.isEmpty()) {
            String methodName = point.getSignature().getName();
            op = methodName.contains("save") ? "保存数据" :
                 methodName.contains("delete") ? "删除数据" :
                 methodName.contains("update") ? "更新数据" :
                 methodName.contains("import") ? "导入数据" :
                 methodName.contains("export") ? "导出数据" : methodName;
        }
        log.setOperation(op);
        log.setDuration(duration);

        try {
            sysLogService.save(log);
        } catch (Exception e) {
            // 日志记录失败不影响主流程
        }

        return result;
    }
}
