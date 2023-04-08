package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.MomentArchiveDetail;
import com.qwli7.blog.entity.vo.MomentNavQueryParam;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/17 16:43
 * 功能：blog8
 **/
@Mapper
public interface MomentMapper {

    Optional<Moment> findById(@Param("id") Integer id);

    Optional<Moment> findLatestMoment(MomentQueryParams momentQueryParams);

    void insert(Moment moment);
    void deleteById(@Param("id") Integer id);

    Integer count(MomentQueryParams momentQueryParams);

    Integer countByDate(MomentQueryParams momentQueryParams);

    List<MomentArchiveDetail> findArchiveMoments(MomentQueryParams momentQueryParams);

    Optional<Moment> findPreMoment(MomentNavQueryParam momentNavQueryParam);

    Optional<Moment> findNextMoment(MomentNavQueryParam momentNavQueryParam);
}
