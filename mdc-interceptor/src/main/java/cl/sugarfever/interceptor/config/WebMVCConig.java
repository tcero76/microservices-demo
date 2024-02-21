package cl.sugarfever.interceptor.config;

import cl.sugarfever.interceptor.MDCHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMVCConig implements WebMvcConfigurer {

    private final MDCHandlerInterceptor mdcHandlerInterceptor;

    public WebMVCConig(MDCHandlerInterceptor mdcHandlerInterceptor) {
        this.mdcHandlerInterceptor = mdcHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcHandlerInterceptor);
    }
}
