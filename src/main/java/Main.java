import net.sourceforge.tess4j.*;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ImageTextComparer imageTextComparer = new ImageTextComparer();

        String outputPdfPath = "output.pdf";
        //path to image
        String folder_path = "path-your-image-folder";
        //delete same photos
        imageTextComparer.processImagesInFolder(folder_path);
        //add to photo on pdf
        ImagesToPdfConverter.convertImagesToPdf(folder_path, outputPdfPath);


    }
}