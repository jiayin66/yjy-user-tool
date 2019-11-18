package com.yjy.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="yjy")
@Getter
@Setter
@Component
public class InitConfig {
	private Map<String,String> sexMap;
	private Map<String,String> stationNumMap;

}
