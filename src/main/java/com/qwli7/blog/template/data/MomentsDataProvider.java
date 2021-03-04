package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author qwli7 
 * @date 2021/3/4 9:43
 * 功能：blog
 **/
@Component
public class MomentsDataProvider extends AbstractDataProvider<PageDto<Moment>> {

    private final MomentService momentService;

    public MomentsDataProvider(MomentService momentService) {
        super("moments");
        this.momentService = momentService;
    }

    @Override
    public PageDto<Moment> queryData(Map<String, String> attributeMap) {

        MomentQueryParam queryParam = new MomentQueryParam();

        int page;

        String currentPage = attributeMap.get("currentPage");
        try{
            page = Integer.parseInt(currentPage);
            if(page < 1) {
                page = 1;
            }
        } catch (NumberFormatException ex){
            page = 1;
        }
        int size;
        String pageSize = attributeMap.get("pageSize");
        try{
            size = Integer.parseInt(pageSize);
            if(size < 0) {
                size = 10;
            }
        } catch (NumberFormatException ex){
            size = 10;
        }

        queryParam.setPage(page);
        queryParam.setSize(size);
//        queryParam.setQuery(attributeMap.getOrDefault("query", ""));

        return momentService.selectPage(queryParam);
    }
}
