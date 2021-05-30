package com.qwli7.blog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestGm {
    public static void main(String[] args) {
        try {
            String gmPath = "/usr/local/GraphicsMagick-1.3.28/bin/gm";
            List<String> commands = new ArrayList<>(2);
            commands.add(gmPath);
            commands.add("-version");
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(commands);
            final Process process = processBuilder.start();
            final InputStream inputStream = process.getInputStream();
            final OutputStream outputStream = process.getOutputStream();
            final InputStream errorStream = process.getErrorStream();
            process.waitFor();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer inputStreamBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                inputStreamBuffer.append(line);
            }

            System.out.println("inputStream：" + inputStreamBuffer.toString());

            BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(errorStream));
            StringBuffer errorStreamBuffer = new StringBuffer();
            String line3;
            while ((line3 = bufferedReader3.readLine()) != null) {
                errorStreamBuffer.append(line3);
            }
            System.out.println("errorStream：" + errorStreamBuffer.toString());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
