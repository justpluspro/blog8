package com.qwli7.blog;

import org.testng.annotations.Test;

/**
 * @author qwli7
 * @date 2023/2/17 13:50
 * 功能：blog8
 **/
public class StringTest {

    @Test
    public void test_get_last_path() {

        String filePath = "/57_2022-11-27-15-51-21_2022-11-27-15-51-28.mp4/200/";

        System.out.println(filePath.substring(filePath.lastIndexOf("/")+1));

    }
}
