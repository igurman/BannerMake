package com.gurman;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

class Tools {
    static void saveJpg(BufferedImage image, String name, String exp, String newSaveDir_in, float compress) throws IOException {

        //создать выходной файл
        File compressedImageFile = new File(newSaveDir_in + "\\" + name + "." + exp);

        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(compress);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }

    static void checkFolder(){
        if (!Files.exists(Paths.get(".\\res"))) {
            final boolean mkdirs1 = new File(".\\res").mkdirs();
            final boolean mkdirs2 = new File(".\\res\\images").mkdirs();
            final boolean mkdirs3 = new File(".\\res\\background").mkdirs();
            final boolean mkdirs4 = new File(".\\res\\overhand").mkdirs();
            final boolean mkdirs5 = new File(".\\res\\out").mkdirs();
        }
    }
}
