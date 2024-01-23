#Java OCR Project

This project is designed to detect and delete similar images using OCR (Optical Character Recognition) technology and write them to a PDF file. It is developed in Java and integrates the Tess4J library for OCR processes.

Key Features
Image Comparison: The project processes images in a folder through the ImageTextComparer class, comparing texts obtained via OCR. Images with similar texts are identified and deleted. The functionality is detailed in the ImageTextComparer.java file.

PDF Conversion: The ImagesToPdfConverter class takes images from a specified folder and converts them into a PDF file. This process is carried out using the Apache PDFBox library. The ImagesToPdfConverter.java file contains this process.

Main Functionality: The Main class serves as the project's main entry point, bringing together the functionalities mentioned above. This integration can be seen in the Main.java file.

Technologies and Libraries Used
Java: The project is written in the Java programming language.
Tess4J: The Tess4J library is used for OCR processes.
Apache PDFBox: The Apache PDFBox library is integrated for creating and processing PDFs.
Maven: Maven is used for dependency management and project configuration. Details can be found in the pom.xml file.
Installation and Execution
To run the project, Java and Maven must be installed on your system. After cloning the project, you can run it by installing dependencies with Maven.
