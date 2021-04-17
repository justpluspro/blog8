package com.qwli7.blog.service;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：BlackIpService
 **/
public interface BlackIpService {

    /**
     * 分页查询黑名单
     * @param queryParam queryParam
     * @return PageDto
     */
    PageDto<BlackIp> selectPage(CommonQueryParam queryParam);

    /**
     * 是否是黑名单
     * @param ip ip
     * @return ip
     */
    boolean isBlackIp(String ip);

    /**
     * 保存黑名单
     * @param blackIp blackIp
     */
    void save(BlackIp blackIp);

    /**
     * 删除黑名单
     * @param id id
     */
    void delete(int id);
}
