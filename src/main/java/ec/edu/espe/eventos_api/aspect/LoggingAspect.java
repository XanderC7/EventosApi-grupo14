package ec.edu.espe.eventos_api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// Registrar automaticamente la ejecucion de los metodos de negocio
@Aspect
@Component
public class LoggingAspect {

    // Se ejecuta antes de cualquier metodo dentro del paquete service
    @Before("execution(* ec.edu.espe.eventos_api.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("AOP Antes - Iniciando ejecucion del metodo: " + joinPoint.getSignature().getName());
    }

    // Se ejecuta despues de cualquier metodo dentro del paquete service
    @After("execution(* ec.edu.espe.eventos_api.service.*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        System.out.println("AOP Despues - Finalizando ejecucion del metodo: " + joinPoint.getSignature().getName());
    }
}
