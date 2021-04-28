package com.github.wephotos.webwork.logging.service;

import com.github.wephotos.webwork.logging.entity.WebworkLog;
import com.github.wephotos.webwork.logging.mapper.WebworkLogMapper;
import com.github.wephotos.webwork.utils.WebworkUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xc
 * @date 2021-04-28 22:01
 */
@Service
public class WebworkLogService {
    @Resource
    private WebworkLogMapper webworkLogMapper;

    public int saveLog() {
        WebworkLog log = new WebworkLog();
        log.setId(WebworkUtils.uuid());
        log.setIp("");
        log.setOperator("");
        log.setType("");
        log.setLevel("");
        log.setBrowser("");
        log.setUrl("");
        log.setCreateTime(new Date());
        log.setContent("");
        return webworkLogMapper.insert(log);
    }
}
