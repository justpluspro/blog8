package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.service.MomentService;

import java.util.Map;
import java.util.Optional;

/**
 * 单篇动态数据提供者
 * @author liqiwen
 * @since 2.5
 */
public class MomentDataProvider extends AbstractDataProvider<Moment> {

    private final MomentService momentService;


    public MomentDataProvider(MomentService momentService) {
        super("moment");
        this.momentService = momentService;
    }

    @Override
    public Moment queryData(Map<String, String> attributeMap) {
        final String id = attributeMap.get("id");
        final Optional<Moment> momentOp =
                momentService.getMoment(Integer.parseInt(id));
        return momentOp.get();
    }
}
