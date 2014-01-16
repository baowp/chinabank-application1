package cn.com.chinabank.app1.service.impl;

import cn.com.chinabank.shared.dto.BookDto;
import cn.com.chinabank.shared.service.DubboSampleService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

//@com.alibaba.dubbo.config.annotation.Service(protocol = "learn2", version = "1.0.0")
@Service("dubboSampleService")
public class DubboSampleServiceImpl implements DubboSampleService {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(this.getClass());

    @Override
    public Serializable execute(String s) {
        String str = "application1 executed: " + s;
        logger.info(str);
        return str;
    }

    @Override
    public Object execute(BookDto book) {
        logger.info(String.format("application1 executed: %1$s", book));
        return String.format("[%1$s]", book);
    }

}
