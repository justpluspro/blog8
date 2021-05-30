package com.qwli7.blog;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TestFFmpeg {

    private String ffmpegPath = "/Users/liqiwen/develop/ffmpeg-20200831-4a11a6f-macos64-static/bin/ffmpeg";

    public static void main(String[] args) {


        File inputFile = new File("/Users/liqiwen/Desktop/video.mp4");
        File outputFile = new File("/Users/liqiwen/Desktop/video_" + System.currentTimeMillis() + ".mp4");

        try {

            if (Files.notExists(outputFile.toPath())) {
                Files.createFile(outputFile.toPath());
            }

            TestFFmpeg testFFmpeg = new TestFFmpeg();
            testFFmpeg.compressVideo(inputFile, outputFile);

        } catch (Exception ex){
            ex.printStackTrace();
            try {
                Files.deleteIfExists(outputFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void compressVideo(File inputFile, File outputFile) throws IOException, InterruptedException {

        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(inputFile.getAbsolutePath());
        command.add("-r");
        command.add("20");
        command.add(outputFile.getAbsolutePath());

        doProcess(command);

    }

    private void doProcess(List<String> command) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
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

    }

}
