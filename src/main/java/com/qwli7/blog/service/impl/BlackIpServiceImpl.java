package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.BlackIpMapper;
import com.qwli7.blog.service.BlackIpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<BlackIp> selectAll() {
        return blackIpMapper.selectAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(BlackIp blackIp) {
        if(blackIpMapper.selectByIp(blackIp.getIp()).isPresent()) {
            throw new LogicException("blackIp.exists", "黑名单存在");
        }
        blackIpMapper.insert(blackIp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(int id) {
        final Optional<BlackIp> blackIpOp = blackIpMapper.selectById(id);
        if(!blackIpOp.isPresent()) {
            throw new LogicException("blackIp.notExists", "黑名单不存在");
        }
        blackIpMapper.deleteById(blackIpOp.get().getId());
    }
}
