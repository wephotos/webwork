package com.github.wephotos.webwork.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.util.Iterator;

/**
 * 图片压缩工具类
 *
 * @author Aaron.tian
 */
public final class ImageUtils {

    /**
     * 按照指定的参数压缩图片，返回压缩后的文件名
     *
     * @param oldFile   原图片文件路径
     * @param width     压缩宽
     * @param height    压缩高
     * @param quality   压缩质量
     * @param smallIcon 压缩后的名称后缀
     * @return 压缩后的图片名
     * @throws IOException #{@link IOException}
     */
    public static String compression(String oldFile, int width, int height, float quality, String smallIcon) throws IOException {
        Image srcImage = ImageIO.read(new File(oldFile));
        int[] xy = resize(srcImage, width, height);
        String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
        String newImage = filePrex + smallIcon + oldFile.substring(filePrex.length());
        BufferedImage bufferedImage = createBufferedImage(srcImage, xy[0], xy[1]);
        FileOutputStream ouStream = new FileOutputStream(newImage);
        outputCompressionImg(bufferedImage, quality, ouStream);
        return newImage;
    }

    /**
     * 压缩图片流
     *
     * @param input   图片输入流
     * @param width   宽
     * @param height  高
     * @param quality 质量 0-1
     * @return 压缩后的字节组
     * @throws IOException IO异常
     */
    public static byte[] compressionStream(InputStream input, int width, int height, float quality) throws IOException {
        Image srcImage = ImageIO.read(input);
        int[] xy = resize(srcImage, width, height);
        BufferedImage bufferedImage = createBufferedImage(srcImage, xy[0], xy[1]);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.available());
        outputCompressionImg(bufferedImage, quality, baos);
        return baos.toByteArray();
    }

    /**
     * 可访问图像数据缓冲区的 Image
     *
     * @param img    原图像
     * @param width  宽
     * @param height 高
     * @return 缓冲图像
     */
    private static BufferedImage createBufferedImage(Image img, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(img, 0, 0, width, height, null);
        return bufferedImage;
    }

    /**
     * 按比例调整目标大小
     *
     * @param img    图片
     * @param width  宽
     * @param height 高
     * @return 调整后的宽和高
     */
    private static int[] resize(Image img, int width, int height) {
        int srcW = img.getWidth(null);
        int srcH = img.getHeight(null);
        if (width < srcW && height < srcH) {
            if (width / (double) srcW > height / (double) srcH) {
                height = (int) (srcH * (width / (double) srcW));
            } else {
                width = (int) (srcW * (height / (double) srcH));
            }
        } else {
            width = srcW;
            height = srcH;
        }
        return new int[]{width, height};
    }

    /**
     * 将图片压缩到指定的图片文件下
     *
     * @param bufferedImage 图像
     * @param quality       压缩质量0-1的数字
     * @param ouStream      输出流
     * @throws IOException #{@link IOException}
     */
    private static void outputCompressionImg(BufferedImage bufferedImage, float quality, OutputStream ouStream) throws IOException {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = (ImageWriter) iter.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        param.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ColorModel.getRGBdefault();
        param.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                colorModel.createCompatibleSampleModel(16, 16)));
        IIOImage image = new IIOImage(bufferedImage, null, null);
        try {
            writer.setOutput(ImageIO.createImageOutputStream(ouStream));
            writer.write(null, image, param);
        } finally {
            ouStream.close();
        }
    }

}
