package wtchrs.SpringCommunity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wtchrs.SpringCommunity.interceptor.CommonAttributeInterceptor;
import wtchrs.SpringCommunity.service.ImageStoreService;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.image.dir}")
    private String imageDir;

    private final LocalDateTimeFormatter formatter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ImageStoreService.IMAGE_URL_PREFIX + "**")
                .addResourceLocations("file:" + imageDir);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonAttributeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(formatter);
    }

}
