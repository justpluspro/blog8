package com.qwli7.blog.event;

import com.qwli7.blog.entity.Moment;
import org.springframework.context.ApplicationEvent;

/**
 * 动态发布事件
 * @author liqiwen
 * @since 1.2
 */
public class MomentPostEvent extends ApplicationEvent {

    /**
     * 当前待发布的动态
     */
    private final Moment moment;

    public MomentPostEvent(Object source, Moment moment) {
        super(source);
        this.moment = moment;
    }

    public Moment getMoment() {
        return moment;
    }
}
