package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.service.MomentService;

import java.util.Map;

/**
 * 最近动态数据提供者
 * @author liqiwen
 * @since 2.5
 */
public class LatestMomentsDataProvider extends AbstractDataProvider<MomentArchive> {

    private final MomentService momentService;

    public LatestMomentsDataProvider(MomentService momentService) {
        super("latestMoment");
        this.momentService = momentService;
    }

    @Override
    public MomentArchive queryData(Map<String, String> attributeMap) {
        return momentService.findLatestMoments();
    }
}
