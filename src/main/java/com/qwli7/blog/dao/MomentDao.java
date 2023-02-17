package com.qwli7.blog.dao;

import com.qwli7.blog.entity.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author qwli7
 * @date 2023/2/17 16:43
 * 功能：blog8
 **/
@Repository
public interface MomentDao extends JpaRepository<Moment, Integer>, JpaSpecificationExecutor<Moment> {
}
