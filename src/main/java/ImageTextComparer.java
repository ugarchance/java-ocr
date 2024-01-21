import net.sourceforge.tess4j.*;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ImageTextComparer {
    private ITesseract tesseract;
    private LevenshteinDistance levenshteinDistance;

    public ImageTextComparer() {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata\\");
        levenshteinDistance = new LevenshteinDistance();
    }

    public void processImagesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        HashMap<String, String> imagePathToText = new HashMap<>();
        HashSet<String> imagesToRemove = new HashSet<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    String text = tesseract.doOCR(image);
                    checkSimilarityAndMark(file.getPath(), text, imagePathToText, imagesToRemove);
                } catch (TesseractException e) {
                    System.err.println("OCR hatası, dosya atlanıyor: " + file.getPath());
                } catch (IOException e) {
                    System.err.println("Dosya okuma hatası: " + file.getPath());
                }
            }
        }

        deleteMarkedImages(imagesToRemove);
    }

    private void checkSimilarityAndMark(String currentImagePath, String currentText,
                                        HashMap<String, String> imagePathToText,
                                        HashSet<String> imagesToRemove) {
        for (String storedImagePath : imagePathToText.keySet()) {
            String storedText = imagePathToText.get(storedImagePath);
            int distance = levenshteinDistance.apply(currentText, storedText);
            double similarity = 1 - (double) distance / Math.max(currentText.length(), storedText.length());
            if (similarity >= 0.70) {
                imagesToRemove.add(currentImagePath);
                return;
            }
        }
        imagePathToText.put(currentImagePath, currentText);
    }

    private void deleteMarkedImages(HashSet<String> imagesToRemove) {
        for (String imagePath : imagesToRemove) {
            try {
                Files.deleteIfExists(Paths.get(imagePath));
                System.out.println("Silindi: " + imagePath);
            } catch (IOException e) {
                System.err.println("Dosya silme hatası: " + imagePath);
            }
        }
    }
}
