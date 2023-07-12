package wtchrs.SpringCommunity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImageResizeService {

    @Value("${file.image.size}")
    private int MAX_IMAGE_SIZE;

    public BufferedImage resize(BufferedImage image) {
        if (image.getWidth() <= MAX_IMAGE_SIZE && image.getHeight() <= MAX_IMAGE_SIZE) return image;
        int scaledWidth = image.getWidth() * MAX_IMAGE_SIZE / Math.max(image.getWidth(), image.getHeight());
        int scaledHeight = image.getHeight() * MAX_IMAGE_SIZE / Math.max(image.getWidth(), image.getHeight());
        return resize(image, scaledWidth, scaledHeight);
    }

    public BufferedImage resize(BufferedImage image, int w, int h) {
        Image scaledImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        resultImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        return resultImage;
    }
}
