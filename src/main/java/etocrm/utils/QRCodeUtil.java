package etocrm.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Hashtable;

public final class QRCodeUtil extends LuminanceSource {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    // 二维码颜色
    private static final int BLACK = 0xFF303133;
    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

    private final BufferedImage image;
    private final int left;
    private final int top;

    public QRCodeUtil(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public QRCodeUtil(BufferedImage image, int left, int top, int width, int height) {
        super(width, height);
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        if (left + width > sourceWidth || top + height > sourceHeight) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        for (int y = top; y < top + height; y++) {
            for (int x = left; x < left + width; x++) {
                if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                    image.setRGB(x, y, 0xFFFFFFFF); // = white
                }
            }
        }
        this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
        this.left = left;
        this.top = top;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        image.getRaster().getDataElements(left, top + y, width, 1, row);
        return row;
    }

    @Override
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        image.getRaster().getDataElements(left, top, width, height, matrix);
        return matrix;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    @Override
    public LuminanceSource crop(int left, int top, int width, int height) {
        return new QRCodeUtil(image, this.left + left, this.top + top, width, height);
    }

    @Override
    public boolean isRotateSupported() {
        return true;
    }

    @Override
    public LuminanceSource rotateCounterClockwise() {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
        BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(image, transform, null);
        g.dispose();
        int width = getWidth();
        return new QRCodeUtil(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    /**
     * @param text   内容
     * @param width  宽
     * @param height 高
     * @return 生成的二维码base64
     */
    public static String genBase64QR(String text, int width, int height) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 指定编码格式
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);// 指定纠错等级
            hints.put(EncodeHintType.MARGIN, 1); // 白边大小，取值范围0~4
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage bufferedImage = toBufferedImage(bitMatrix);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            logger.error("二维码生成失败.", e);
            throw new RuntimeException(e.getMessage());
        }
    }


}