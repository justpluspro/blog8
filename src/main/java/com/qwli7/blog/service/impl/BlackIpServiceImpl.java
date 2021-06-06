package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.BlackIpMapper;
import com.qwli7.blog.service.BlackIpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：BlackIpServiceImpl
 **/
@Service
public class BlackIpServiceImpl implements BlackIpService {

    private final BlackIpMapper blackIpMapper;

    public BlackIpServiceImpl(BlackIpMapper blackIpMapper) {
        this.blackIpMapper = blackIpMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PageDto<BlackIp> findPage(CommonQueryParam queryParam) {
        int count = blackIpMapper.count(queryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }
        return new PageDto<>(queryParam, count, blackIpMapper.findPage(queryParam));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isBlackIp(String ip) {
        return blackIpMapper.findByIp(ip).isPresent();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(BlackIp blackIp) {
        if(blackIpMapper.findByIp(blackIp.getIp()).isPresent()) {
            throw new LogicException("blackIp.exists", "黑名单存在");
        }
        blackIpMapper.insert(blackIp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(int id) {
        final Optional<BlackIp> blackIpOp = blackIpMapper.findById(id);
        if(!blackIpOp.isPresent()) {
            throw new LogicException("blackIp.notExists", "黑名单不存在");
        }
        blackIpMapper.deleteById(blackIpOp.get().getId());
    }
}
