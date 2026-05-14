package org.lms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.lms.annotation.Permission;
import org.lms.exception.LmsException;
import org.lms.service.UserGroupService;
import org.lms.util.RequestContextHolder;
import org.lms.util.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    UserGroupService userGroupService;

    @Around("@annotation(org.lms.annotation.Permission)")
    public Object checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        String[] roles = permission.role();
        String userId = RequestContextHolder.getUserId();

        // 根据 userId 查询所属的权限组编码
        Set<String> allowRoles = userGroupService.queryGroupCodeByUserId(userId);

        // 包含任何一个角色就通过
        boolean pass = false;
        for (String role : roles) {
            if (allowRoles.contains(role)) {
                pass = true;
                break;
            }
        }

        if (!pass) {
            throw new LmsException(RetCode.NO_PERMISSION.getCode(), RetCode.NO_PERMISSION.getMsg());
        }

        return joinPoint.proceed();
    }
}
