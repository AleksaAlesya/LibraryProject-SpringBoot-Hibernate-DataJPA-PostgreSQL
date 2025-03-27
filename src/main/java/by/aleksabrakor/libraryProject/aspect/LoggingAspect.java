package by.aleksabrakor.libraryProject.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //    Метод будет срабатывать перед выполнением всех методов из папки (AOP)
//    @Before("execution(* by.aleksabrakor.libraryProject..*.*(..))") // все методы
    @Before("execution(* by.aleksabrakor.libraryProject.services.*.*(..))") // все методы сервиса
    public void logServiceMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();  // Получаем имя метода
        String className = joinPoint.getTarget().getClass().getSimpleName(); // Получаем имя класса
        logger.info("Вызван метод: {}.{}", className, methodName);
    }
}
