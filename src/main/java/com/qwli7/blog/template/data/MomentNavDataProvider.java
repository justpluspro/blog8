package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.MomentNav;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 动态导航 data provider
 * @author liqiwen
 * @since 3.5
 */
@Component
public class MomentNavDataProvider extends AbstractDataProvider<MomentNav> {

    private final MomentService momentService;

    public MomentNavDataProvider(MomentService momentService) {
        super("momentNav");
        this.momentService = momentService;
    }

    @Override
    public MomentNav queryData(Map<String, String> attributeMap) {
        int id = 0;
        try {
            id = Integer.parseInt(attributeMap.get("id"));
        } catch (NumberFormatException e){
            // ignored exception
        }
        if(id < 0) {
            return new MomentNav();
        }
        return momentService.findMomentNav(id).orElse(new MomentNav());
    }
}
