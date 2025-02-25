package ru.nightmare.diplomny;

import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession
@EnableSpringWebSession
public class WebSecurityConfig {
}
