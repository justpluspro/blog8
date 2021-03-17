package com.qwli7.blog.file;

import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qwli7
 * @date 2021/3/17 10:05
 * 功能：blog
 **/
public class CommandProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    public void processCommand(List<String> cmd) {

    }


    public static void main(String[] ar) throws IOException {

        try {

            CommandProcessor commandProcessor = new CommandProcessor();

            ProcessBuilder processBuilder = new ProcessBuilder();

            //图片转换
            String command = "gm convert -quality 30% -flatten C:\\Users\\admin\\Desktop\\test.png 3.jpg";


            //获取视频信息
            String command2 = "ffmpeg -i C:\\Users\\admin\\Desktop\\video.mp4 -hide_banner";

//            processBuilder.command(command);
//
//            Process process = processBuilder.start();
            Process process = Runtime.getRuntime().exec(command2);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder resultBuilder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line).append("\r");
            }
            System.out.println("resultBuilder:" + resultBuilder.toString());

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
