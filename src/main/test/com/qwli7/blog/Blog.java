package com.qwli7.blog;


import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Blog {


    @Resource
    private MomentService momentService;

    @Resource
    private BlogProperties blogProperties;



    @Test
    public void contextLoad() {
        System.out.println("contextLoad..");
    }


    @Test
    public void testMomentArchive() {
        MomentQueryParam queryParam = new MomentQueryParam();
        queryParam.setPage(1);
        queryParam.setSize(blogProperties.getDefaultPageSize());

        final PageDto<MomentArchive> momentArchivePageDto = momentService.findArchivePage(queryParam);
        System.out.println(momentArchivePageDto);
    }
}
