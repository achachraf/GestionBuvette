/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author achra
 */

@Named(value = "fileUpload")
@SessionScoped
public class FileUpload implements Serializable  {

    private Part uploadedFile;
    private String folder = "C:\\Users\\achra\\Documents\\NetBeansProjects\\GestionBuvette1\\GestionBuvette1-war\\web\\resources\\uploads";

    public FileUpload() {
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    
    public void saveFile() {
        System.out.println(uploadedFile);
        try (InputStream input = uploadedFile.getInputStream()) {
            String extension = getFileExtension(uploadedFile.getSubmittedFileName());
            String fileName = getRandomString() + "." + extension;
            Files.copy(input, new File(folder, fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 25;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
    
    public String getFileExtension(String filename){
        return filename.substring(filename.lastIndexOf(".")+1);
    }

}
