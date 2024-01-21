import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

public class ImagesToPdfConverter {

    public static void convertImagesToPdf(String imagesFolderPath, String outputPdfPath) {
        try (PDDocument document = new PDDocument()) {
            Files.walk(Paths.get(imagesFolderPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$"))
                    .forEach(path -> {
                        try {
                            BufferedImage image = ImageIO.read(path.toFile());

                            // Resmin boyutlarına göre bir PDF sayfası oluştur
                            PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                            document.addPage(page);

                            PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);

                            // Resmi orijinal boyutunda sayfaya yerleştir
                            contentStream.drawImage(pdImage, 0, 0, image.getWidth(), image.getHeight());
                            contentStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            document.save(outputPdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
