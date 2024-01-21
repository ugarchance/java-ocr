
import net.sourceforge.tess4j.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.rendering.*;

import java.awt.image.BufferedImage;
import java.io.*;

public class Driver {
    public static void main(String[] args) {
        try {
            // PDF dosyasını aç
            PDDocument document = PDDocument.load(new File("C:\\Users\\Ugar\\Desktop\\veri madenciligi final butun sorular.pdf"));

            // Tesseract örneği oluştur
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata\\");

            // Çıktı metin dosyasını oluştur
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");

            // PDF'deki her sayfayı dön
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                // Sayfayı bir görsel olarak oluştur
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);

                // OCR işlemi
                String text = tesseract.doOCR(image);
                text.replace("Onceki","");
                text.replace("Bos","");
                text.replace("birak","");
                text.replace("©","");
                text.replace("—","");
                text.replace("kapat","");
                text.replace(")","");
                text.replace("(","");


                writer.println(text);
            }

            // Kaynakları serbest bırak
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
