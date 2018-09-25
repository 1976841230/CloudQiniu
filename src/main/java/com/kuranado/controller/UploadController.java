package com.kuranado.controller;

import com.kuranado.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Xinling Jing
 * @Date: 2018/8/21 0021 下午 7:22
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping("/img")
	public void uploadImg() {
		uploadService.uploadImg();
	}

}
