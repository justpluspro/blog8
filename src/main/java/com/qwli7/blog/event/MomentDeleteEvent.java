package com.qwli7.blog.event;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.Tag;
import org.springframework.context.ApplicationEvent;

public class MomentDeleteEvent extends ApplicationEvent {

    private final Moment moment;

    public Moment getMoment() {
        return moment;
    }

    public MomentDeleteEvent(Object source, Moment moment) {
        super(source);
        this.moment = moment;
    }
}
