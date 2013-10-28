package cn.com.chinabank.app1.service.impl;

import java.io.Serializable;

import cn.com.chinabank.shared.service.DubboSampleService;

@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
public class DubboSampleServiceImpl implements DubboSampleService {

	private final org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(this.getClass());

	@Override
	public Serializable execute(String s) {
		String str = "application1 executed: " + s;
		logger.info(str);
		return str;
	}

}
