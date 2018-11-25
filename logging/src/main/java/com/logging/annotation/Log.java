package com.logging.annotation;

import com.logging.LogLevel;
import com.logging.LogType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Repeatable(value = Logs.class)
public @interface Log {

	LogType[] types() default {LogType.SIGNATURE, LogType.PERFORMANCE};

	LogLevel level() default LogLevel.DEBUG;

	String prefix() default StringUtils.EMPTY;

}
