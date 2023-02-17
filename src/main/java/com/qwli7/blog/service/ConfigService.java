package com.qwli7.blog.service;

import com.qwli7.blog.entity.GlobalConfig;
import com.qwli7.blog.entity.vo.ConfigBean;

/**
 * @author qwli7
 * @date 2023/2/17 17:24
 * 功能：blog8
 **/
public interface ConfigService {


    GlobalConfig loadConfig();

    void updateConfig(ConfigBean configBean);
}
