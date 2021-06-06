package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：BlackIpMapper
 **/
@Mapper
public interface BlackIpMapper {

    /**
     * 插入黑名单
     * @param blackIp blackIp
     */
    void insert(BlackIp blackIp);

    /**
     * 删除黑名单
     * @param id id
     */
    void deleteById(int id);

    /**
     * 获取所有的黑名单
     * @return List
     */
    List<BlackIp> findAll();

    /**
     * 查询黑名单根据 ip
     * @param ip ip
     * @return BlackIp
     */
    Optional<BlackIp> findByIp(String ip);

    /**
     * 查询黑名单根据 id
     * @param id id
     * @return BlackIp
     */
    Optional<BlackIp> findById(int id);

    /**
     * 统计黑名单的数量
     * @param queryParam queryParam
     * @return int
     */
    int count(CommonQueryParam queryParam);

    /**
     * 查询黑名单列表
     * @param queryParam queryParam
     * @return List
     */
    List<BlackIp> findPage(CommonQueryParam queryParam);
}
