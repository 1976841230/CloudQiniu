package com.kuranado.entity;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Xinling Jing
 * @Date: 2018/8/22 0022 下午 8:02
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
@Data
public class QiniuKey {

	private String accessKey;
	private String secretKey;
	private String bucketName;

}
