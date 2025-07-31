package com.Clientin.Clientin.aspect;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    private final LogService logService;

    // Log methods annotated with @Loggable
    @Around("@annotation(com.Clientin.Clientin.annotation.Loggable)")
    public Object logAnnotatedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Loggable loggable = method.getAnnotation(Loggable.class);
        
        if (loggable.exclude()) {
            return joinPoint.proceed();
        }
        
        long startTime = System.currentTimeMillis();
        String methodName = method.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String entity = loggable.entity().isEmpty() ? extractEntityFromController(className) : loggable.entity();
        Object[] args = joinPoint.getArgs();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            String message = loggable.message().isEmpty() 
                ? String.format("Successfully executed %s.%s", className, methodName)
                : loggable.message();
            
            if (loggable.logParams() && args.length > 0) {
                message += " with params: " + Arrays.toString(args);
            }
            
            if (loggable.logReturn() && result != null) {
                message += " returned: " + result.toString();
            }
            
            if (loggable.logExecutionTime()) {
                message += String.format(" (execution time: %d ms)", executionTime);
            }
            
            logService.logWithHttpInfo(
                loggable.level(),
                loggable.action(),
                entity,
                extractEntityId(result, args),
                getCurrentUserId(),
                message,
                getCurrentEndpoint(),
                getCurrentHttpMethod(),
                200,
                executionTime
            );
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            logService.logWithHttpInfo(
                Log.LogLevel.ERROR,
                loggable.action(),
                entity,
                null,
                getCurrentUserId(),
                String.format("Error in %s.%s: %s", className, methodName, e.getMessage()),
                getCurrentEndpoint(),
                getCurrentHttpMethod(),
                500,
                executionTime
            );
            
            throw e;
        }
    }

    // Log all controller method executions
    @Around("execution(* com.Clientin.Clientin.controller.*.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String entity = extractEntityFromController(className);
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log successful execution
            logService.logWithHttpInfo(
                Log.LogLevel.INFO,
                getActionFromMethod(methodName),
                entity,
                null,
                getCurrentUserId(),
                String.format("Successfully executed %s.%s", className, methodName),
                getCurrentEndpoint(),
                getCurrentHttpMethod(),
                200,
                executionTime
            );
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log error execution
            logService.logWithHttpInfo(
                Log.LogLevel.ERROR,
                getActionFromMethod(methodName),
                entity,
                null,
                getCurrentUserId(),
                String.format("Error in %s.%s: %s", className, methodName, e.getMessage()),
                getCurrentEndpoint(),
                getCurrentHttpMethod(),
                500,
                executionTime
            );
            
            throw e;
        }
    }

    // Log all service method executions for CRUD operations
    @Around("execution(* com.Clientin.Clientin.service.*.create*(..)) || " +
            "execution(* com.Clientin.Clientin.service.*.update*(..)) || " +
            "execution(* com.Clientin.Clientin.service.*.delete*(..)) || " +
            "execution(* com.Clientin.Clientin.service.*.save*(..))")
    public Object logServiceCrudMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String entity = extractEntityFromService(className);
        Object[] args = joinPoint.getArgs();
        
        try {
            Object result = joinPoint.proceed();
            
            // Try to extract entity ID from result or arguments
            String entityId = extractEntityId(result, args);
            
            logService.logAction(
                Log.LogLevel.INFO,
                getActionFromMethod(methodName),
                entity,
                entityId,
                getCurrentUserId(),
                String.format("Successfully executed %s.%s", className, methodName)
            );
            
            return result;
        } catch (Exception e) {
            logService.logError(
                entity,
                null,
                getCurrentUserId(),
                String.format("Error in %s.%s: %s", className, methodName, e.getMessage()),
                Arrays.toString(args)
            );
            throw e;
        }
    }

    // Log repository method executions for important operations
    @AfterReturning(pointcut = "execution(* com.Clientin.Clientin.repository.*.save*(..)) || " +
                              "execution(* com.Clientin.Clientin.repository.*.delete*(..))",
                    returning = "result")
    public void logRepositoryMethods(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String entity = extractEntityFromRepository(className);
        
        logService.logAction(
            Log.LogLevel.DEBUG,
            getActionFromMethod(methodName),
            entity,
            null,
            getCurrentUserId(),
            String.format("Repository operation: %s.%s", className, methodName)
        );
    }

    // Log exceptions
    @AfterThrowing(pointcut = "execution(* com.Clientin.Clientin..*(..))", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logService.logAction(
            Log.LogLevel.ERROR,
            Log.LogAction.PROCESS,
            "System",
            null,
            getCurrentUserId(),
            String.format("Exception in %s.%s: %s", className, methodName, exception.getMessage()),
            exception.getStackTrace().toString()
        );
    }

    // Helper methods
    private Log.LogAction getActionFromMethod(String methodName) {
        methodName = methodName.toLowerCase();
        if (methodName.contains("create") || methodName.contains("save") || methodName.contains("add")) {
            return Log.LogAction.CREATE;
        } else if (methodName.contains("update") || methodName.contains("edit") || methodName.contains("modify")) {
            return Log.LogAction.UPDATE;
        } else if (methodName.contains("delete") || methodName.contains("remove")) {
            return Log.LogAction.DELETE;
        } else if (methodName.contains("get") || methodName.contains("find") || methodName.contains("search")) {
            return Log.LogAction.READ;
        } else if (methodName.contains("login")) {
            return Log.LogAction.LOGIN;
        } else if (methodName.contains("logout")) {
            return Log.LogAction.LOGOUT;
        } else if (methodName.contains("export")) {
            return Log.LogAction.EXPORT;
        } else if (methodName.contains("import")) {
            return Log.LogAction.IMPORT;
        } else if (methodName.contains("validate")) {
            return Log.LogAction.VALIDATE;
        } else {
            return Log.LogAction.PROCESS;
        }
    }

    private String extractEntityFromController(String className) {
        return className.replace("Controller", "");
    }

    private String extractEntityFromService(String className) {
        return className.replace("Service", "");
    }

    private String extractEntityFromRepository(String className) {
        return className.replace("Repository", "");
    }

    private String extractEntityId(Object result, Object[] args) {
        // Try to extract ID from result object if it has an getId() method
        if (result != null) {
            try {
                var method = result.getClass().getMethod("getId");
                Object id = method.invoke(result);
                return id != null ? id.toString() : null;
            } catch (Exception e) {
                // Ignore and try args
            }
        }
        
        // Try to extract ID from first argument if it has an getId() method
        if (args != null && args.length > 0 && args[0] != null) {
            try {
                var method = args[0].getClass().getMethod("getId");
                Object id = method.invoke(args[0]);
                return id != null ? id.toString() : null;
            } catch (Exception e) {
                // Ignore
            }
        }
        
        return null;
    }

    private String getCurrentUserId() {
        // TODO: Implement based on your authentication mechanism
        // This is a placeholder - you'll need to adapt this to your security context
        try {
            // If using Spring Security, you might do:
            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            //     return ((UserDetails) auth.getPrincipal()).getUsername();
            // }
            return "system"; // Default for now
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getCurrentEndpoint() {
        try {
            ServletRequestAttributes requestAttributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                return request.getRequestURI();
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    private String getCurrentHttpMethod() {
        try {
            ServletRequestAttributes requestAttributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                return request.getMethod();
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }
}
