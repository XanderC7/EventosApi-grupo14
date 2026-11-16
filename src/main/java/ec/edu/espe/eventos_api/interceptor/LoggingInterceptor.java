package ec.edu.espe.eventos_api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// Interceptor HTTP para registrar solicitudes y medir el tiempo de ejecucion
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Se ejecuta antes de que la peticion llegue al controlador
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long tiempoInicio = System.currentTimeMillis();
        request.setAttribute("tiempoInicio", tiempoInicio);
        System.out.println("Interceptor HTTP - Peticion entrante: " + request.getMethod()
                + " " + request.getRequestURI() + " desde IP: " + request.getRemoteAddr());
        return true;
    }

    // Se ejecuta al finalizar la peticion para calcular cuanto tardo
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        if (tiempoInicio != null) {
            long duracion = System.currentTimeMillis() - tiempoInicio;
            System.out.println("Interceptor HTTP - Respuesta enviada con estado: " + response.getStatus()
                    + " en " + duracion + " ms");
        }
    }
}
