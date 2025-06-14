package org.baebe.coffeetrading.commons.utils.file;

import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.DIRECTORY_CREATE_FAILED;
import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.FILE_DELETE_FAILED;
import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.FILE_NOT_FOUND;
import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.FILE_UPLOAD_FAILED;
import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.UNSUPPORTED_FILE_FORMAT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUtils {

    @Value("${spring.servlet.multipart.location}")
    private static String uploadPath;

    private static final List<String> allowedContentTypes = List.of(
//        MediaType.IMAGE_GIF_VALUE,
        MediaType.IMAGE_JPEG_VALUE,
        MediaType.IMAGE_PNG_VALUE
//        "application/pdf",
//        "application/zip",
//        "application/vnd.ms-excel",
//        "application/msword",
//        "video/mp4"
    );

    public static void saveFile(MultipartFile file) {
        validateFile(file);
        
        String location = getDailySavedPath();
        createDirectoryIfNotExists(location);
        
        String originalFileName = file.getOriginalFilename();
        String encryptedFileName = fileNameToUUID(originalFileName);
        String fullPath = location + encryptedFileName;

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            log.error("File upload failed. Original filename: {}", originalFileName, e);
            throw new CoreException(FILE_UPLOAD_FAILED);
        }
    }

    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            boolean deleted = Files.deleteIfExists(path);
            if (!deleted) {
                log.warn("File not found for deletion: {}", filePath);
            }
        } catch (IOException e) {
            log.error("File deletion failed. Path: {}", filePath, e);
            throw new CoreException(FILE_DELETE_FAILED);
        }
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CoreException(FILE_NOT_FOUND);
        }

        String contentType = file.getContentType();
        if (!allowedContentTypes.contains(contentType)) {
            throw new CoreException(UNSUPPORTED_FILE_FORMAT);
        }
    }

    // UUID 암호화가 필요할 시 사용
//    private String generateEncryptedFileName(String originalFileName) {
//        try {
//            String extension = getFileExtension(originalFileName);
//            String uuid = UUID.randomUUID().toString();
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hash = md.digest(uuid.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            return hexString.toString() + "." + extension;
//        } catch (NoSuchAlgorithmException e) {
//            log.error("Failed to generate encrypted filename", e);
//            throw new CoreException(FILE_NAME_ENCRYPT_FAILED);
//        }
//    }

    public static String fileNameToUUID(String originalFileName) {

        String ext = getFileExtension(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1) : "";
    }

    private static String getDailySavedPath() {
        LocalDate today = LocalDate.now();
        return new StringBuilder(uploadPath)
            .append(today.getYear())
            .append("/")
            .append(today.getMonthValue())
            .append("/")
            .append(today.getDayOfMonth())
            .append("/")
            .toString();
    }

    private static void createDirectoryIfNotExists(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            log.error("Failed to create directory: {}", path, e);
            throw new CoreException(DIRECTORY_CREATE_FAILED);
        }
    }
} 