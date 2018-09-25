package com.kuranado.service.impl;

import com.google.gson.Gson;
import com.kuranado.entity.QiniuKey;
import com.kuranado.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Xinling Jing
 * @Date: 2018/8/21 0021 下午 7:15
 */
@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private QiniuKey qiniuKey;

	@Override
	public boolean uploadImg() {

		// Zone.zone0() 对应华东地区的机房
		Configuration configuration = new Configuration(Zone.zone0());
		UploadManager uploadManager = new UploadManager(configuration);
		String localFilePath = "C:\\Users\\Administrator\\Desktop\\车型配置.png";
		// 默认不指定 key 的情况下，以文件内容的 hash 值作为文件名存储到七牛云空间中
		String key = null;
		Auth auth = Auth.create(qiniuKey.getAccessKey(), qiniuKey.getSecretKey());
		String upToken = auth.uploadToken(qiniuKey.getBucketName());
		try {
			Response response = uploadManager.put(localFilePath, key, upToken);
			// 解析上传结果
			DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println("文件名:" + defaultPutRet.hash + "\nkey:" + defaultPutRet.key);
		} catch (QiniuException e) {
			e.printStackTrace();
			System.out.println("上传失败");
		}
		return false;

	}
}
