package com.jhmis.common.utils.captcha;

import com.jhmis.common.utils.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class CaptchaUtil {
    public static final String CAPTCHA_CACHE = "captchaCache";
    public static final String CAPTCHA_CACHE_ID_ = "captchaCode_";

    private static int defaultWidth = 130;
    private static int defaultHeight = 38;

    public static boolean valid(String captchaToken,String captchaCode){
        String code = (String) CacheUtils.get(CAPTCHA_CACHE,CAPTCHA_CACHE_ID_+captchaToken);
        boolean ret = captchaCode.toUpperCase().equals(code);
        if(ret){
            CacheUtils.remove(CAPTCHA_CACHE,CAPTCHA_CACHE_ID_+captchaToken);
        }
        return ret;
    }

    public static CaptchaDTO createImage(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
		/*
		 * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
		int w = defaultWidth;
		int h = defaultHeight;
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
            w = NumberUtils.toInt(width);
            h = NumberUtils.toInt(height);
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

		/*
		 * 生成背景
		 */
        createBackground(g,w,h);

		/*
		 * 生成字符
		 */
        String s = createCharacter(g,w,h);
        String captchaToken = UUID.randomUUID().toString().replaceAll("-", "");
        //写入到cache
        CacheUtils.put(CAPTCHA_CACHE,CAPTCHA_CACHE_ID_+captchaToken,s,10 * 60 * 60 * 1000);
        g.dispose();
        //输出到base64字符串
        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        String captchaImage = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        CaptchaDTO dto = new CaptchaDTO();
        dto.setCaptchaToken(captchaToken);
        dto.setCaptchaImage(captchaImage);
        return dto;
    }

    private static Color getRandColor(int fc,int bc) {
        int f = fc;
        int b = bc;
        Random random=new Random();
        if(f>255) {
            f=255;
        }
        if(b>255) {
            b=255;
        }
        return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f));
    }

    private static void createBackground(Graphics g, int w, int h) {
        // 填充背景
        g.setColor(getRandColor(220,250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandColor(40,150));
            Random random = new Random();
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    private static String createCharacter(Graphics g,int w,int h) {
        char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
        String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"};
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        int fontSize = (h-4>=26)?h-4:26;
        for (int i = 0; i < 4; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
            g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,fontSize));
            //g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
            g.drawString(r,  (w / 5) * i + w/10, h / 2 + fontSize / 2 - random.nextInt(fontSize / 4));
            s.append(r);
        }
        return s.toString();
    }
}
