package com.lab8.galaxy.utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class FileUtil {

    /**
     * 将 MultipartFile 保存到指定的路径，并根据操作系统调整路径格式，并添加指定的文件后缀。
     *
     * @param name 文件名
     * @param multipartFile MultipartFile 对象
     * @param path 要保存的路径
     * @param suffix 文件后缀（不包含点），例如 "png" 或 "jpg"
     * @return 保存后的文件完整路径
     * @throws IOException 如果保存过程中发生错误
     */
    public static String saveFile(String name, MultipartFile multipartFile, String path, String suffix) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String adjustedPath = os.contains("win") ? path.replace("/", "\\") : path;

        Path directoryPath = Paths.get(adjustedPath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String fileName = name + "." + suffix;
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(multipartFile.getInputStream(), filePath);

        return filePath.toString();
    }
    /**
     * 获取字符串中最后一个点（.）之后的内容。
     *
     * @param str 输入的字符串
     * @return 字符串中最后一个点之后的内容
     */
    public static String getLastPartAfterDot(String str) {
        int lastDotIndex = str.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == str.length() - 1) {
            return ""; // 没有找到点，或者点是字符串的最后一个字符
        }
        return str.substring(lastDotIndex + 1);
    }
    public static String getNewUUID() {
        return UUID.randomUUID().toString();
    }
    /**
     * 将图片文件转换为 Base64 编码的字符串。
     *
     * @param imagePath 图片文件的路径
     * @return Base64 编码的字符串
     * @throws IOException 如果读取文件时发生错误
     */
    public static String convertImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}