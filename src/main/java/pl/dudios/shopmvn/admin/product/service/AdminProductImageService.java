package pl.dudios.shopmvn.admin.product.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.dudios.shopmvn.admin.common.utils.SlugifyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductImageService {

    @Value("${app.uploadDir}")
    private String UPLOAD_DIR;
    //  private String UPLOAD_DIR = "./data/productImages/";

    public String uploadImage(String fileName, InputStream inputStream) {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("Cant createDirectories siema pawel", e);
            }
        }

            System.out.println("!Files.exists(uploadPath)");
            System.out.println(!Files.exists(uploadPath));
        String newFileName = SlugifyUtils.slugifyFileName(fileName);
        newFileName = ExistingFileRenameUtils.renameFileIfExists(Path.of("/opt/app"+UPLOAD_DIR), newFileName);
        System.out.println("newFileName :" + newFileName);
        Path filePath = Paths.get("/opt/app"+UPLOAD_DIR).resolve(newFileName);
        System.out.println("File path: " + filePath.toString());
        if (Files.exists(filePath)) {
            System.out.println("File successfully uploaded to: " + filePath);
        } else {
            System.out.println("File upload failed. Could not find file at: " + filePath);
        }
        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            System.out.println("+++++++++++++++++");
            inputStream.transferTo(outputStream);
            System.out.println("++++++++po outputStream+++++++++");
        } catch (IOException e) {
            //throw new RuntimeException("Cant save file siema pawel", e);
            System.out.println("++++++++po ption(\"Cant save file siema pawel\", e);+++++++++");
        }

        return newFileName;
    }

    public Resource serveFiles(String fileName) {
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        return resourceLoader.getResource("/opt/app"+UPLOAD_DIR + fileName);
    }
}
