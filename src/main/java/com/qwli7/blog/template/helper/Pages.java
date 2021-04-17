package com.qwli7.blog.template.helper;

import com.qwli7.blog.entity.dto.PageDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qwli7
 * @date 2021/3/3 9:07
 * 功能：blog
 **/
public class Pages {


    public List<Integer> startPage(PageDto<?> pageDto, Integer fixedCount) {
        Integer totalPage = pageDto.getTotalPage();
        Integer currentPage = pageDto.getPage();
        List<Integer> pages = new ArrayList<>();
        //总页数小于固定数量，全部显示
        int start;
        int end;
        if (totalPage < fixedCount) {
            start = 1;
            end = totalPage;
        } else {
            //fixedCount  = 6 当前页往前减 3，当前页往后加 2
            //fixedCount = 5 当前页往前减 2，当前页往后加 2
            //固定数目为偶数
            if(fixedCount%2 == 0) {
                start = currentPage - fixedCount/2;
                end = currentPage + (fixedCount/2) - 1;
            } else { //固定数目为奇数
                start = currentPage - (fixedCount/2);
                end = currentPage + (fixedCount/2);
            }
            if (start < 1) {
                start = 1;
                end = start + fixedCount-1;
                if(end > totalPage) {
                    end = totalPage;
                }
            }

            //不足就前后补
            if (end > totalPage) {
                end = totalPage;
                start = end - (fixedCount-1);
                if(start < 1) {
                    start = 1;
                }
            }
        }
        for(int i = start; i <=end ; i++) {
            pages.add(i);
        }
        return pages;
    }
}
