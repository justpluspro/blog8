package com.qwli7.blog.service.impl;

import com.qwli7.blog.mapper.BlackIpMapper;
import com.qwli7.blog.service.BlackIpService;
import org.springframework.stereotype.Service;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class BlackIpServiceImpl implements BlackIpService {

    private final BlackIpMapper blackIpMapper;

    public BlackIpServiceImpl(BlackIpMapper blackIpMapper) {
        this.blackIpMapper = blackIpMapper;
    }
}
