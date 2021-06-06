package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;

import java.util.Map;

/**
 * @author qwli7 
 * @date 2021/3/4 9:43
 * 功能：动态列表数据 Provider
 **/
public class MomentsDataProvider extends AbstractDataProvider<PageDto<MomentArchive>> {

    private final MomentService momentService;

    public MomentsDataProvider(MomentService momentService) {
        super("moments");
        this.momentService = momentService;
    }

    @Override
    public PageDto<MomentArchive> queryData(Map<String, String> attributeMap) {
        MomentQueryParam queryParam = new MomentQueryParam();
        int page;
        try {
            page = Integer.parseInt(attributeMap.get("page"));
        } catch (NumberFormatException ex){
            page = 1;
        }
        queryParam.setPage(Math.max(page, 1));
        int size;
        try{
            size = Integer.parseInt(attributeMap.get("size"));
        } catch (NumberFormatException ex){
            size = 10;
        }
        queryParam.setSize(size < 10 || size > 20 ? 20 : size);
        queryParam.setQuery(attributeMap.getOrDefault("query", ""));
        return momentService.findArchivePage(queryParam);
    }
}
