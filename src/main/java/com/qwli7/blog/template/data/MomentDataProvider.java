package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.exception.ResourceNotFoundException;
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
        if(!attributeMap.containsKey("id")) {
            throw new ResourceNotFoundException("moment.notFound", "动态未找到");
        }
        int momentId;
        try {
            momentId = Integer.parseInt(attributeMap.get("id"));
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException("moment.notFound", "动态未找到");
        }
        final Optional<Moment> momentOp =
                momentService.getMoment(momentId);
        return momentOp.get();
    }
}
