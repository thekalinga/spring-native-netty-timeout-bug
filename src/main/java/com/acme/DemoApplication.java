package com.acme;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

// graal reflection hint
@TypeHint(types = {GetResponse.class, Headers.class}, access = { TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.PUBLIC_METHODS })
@SpringBootApplication(proxyBeanMethods = false)
public class DemoApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(DemoApplication.class)
        .properties("spring.output.ansi.enabled=always", "logging.level.com.acme=debug")
        .bannerMode(Banner.Mode.OFF)
        .web(WebApplicationType.NONE)
        .run(args);
  }

  @Bean
  ApplicationRunner onInit(ApiAccessor accessor) {
    return args -> System.out.println(accessor.access().block());
  }

}
