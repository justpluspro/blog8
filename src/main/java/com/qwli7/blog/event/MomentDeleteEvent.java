package com.qwli7.blog.event;

import com.qwli7.blog.entity.Moment;
import org.springframework.context.ApplicationEvent;

/**
 * 动态发布事件
 * @author liqiwen
 * @since 1.2
 */
public class MomentDeleteEvent extends ApplicationEvent {

    /**
     * 当前发布的动态
     */
    private final Moment moment;

    public Moment getMoment() {
        return moment;
    }

    public MomentDeleteEvent(Object source, Moment moment) {
        super(source);
        this.moment = moment;
    }
}
