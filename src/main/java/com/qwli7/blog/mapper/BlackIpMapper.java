package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:49
 * 功能：blog8
 **/
@Mapper
public interface BlackIpMapper {

    void insert(BlackIp blackIp);

    void deleteById(int id);

    List<BlackIp> selectAll();

    Optional<BlackIp> selectByIp(String ip);

    Optional<BlackIp> selectById(int id);

    int count(CommonQueryParam queryParam);

    List<BlackIp> selectPage(CommonQueryParam queryParam);
}
