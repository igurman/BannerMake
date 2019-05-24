package com.gurman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

class BannerMake {

    private PropertySettings s = new PropertySettings("properties.properties");
    private String dir_out = new File(".\\res\\out").getAbsolutePath();
    String[] img_stg = s.getSettings("img").split(";");
    int count_out = 0;
    int max_out = s.getSettingsInt("max.out");

    void run() {
        File[] arrBgd = new File(".\\res\\background").listFiles();
        File[] arrOvh = new File(".\\res\\overhand").listFiles();
        File[] arrImg = new File(".\\res\\images").listFiles();
        File empty = new File("");

        assert arrImg != null;



        //сделать расчёт подмножества вариантов картинок
        ArrayList results = new Permutation(arrImg.length, img_stg.length, s.getSettingsBool("permutation")).build();

        //перемешать результаты расчёта
        if (s.getSettingsBool("shuffle")) Collections.shuffle(results);

        //добавить картинки на фон
        if (s.getSettingsInt("method") == 1) {
            for (Object result : results) {
                make((int[]) result, arrImg, empty, empty, false, false);
            }
        }

        //добавить картинки на бакграунд
        if (s.getSettingsInt("method") == 2) {
            assert arrBgd != null;
            for (File fileBgd : arrBgd) {
                for (Object result : results) {
                    make((int[]) result, arrImg, fileBgd, empty, true, false);
                }
            }
        }

        //добавить картинки и оверхенд
        if (s.getSettingsInt("method") == 3) {
            assert arrOvh != null;
            for (File fileOvh : arrOvh) {
                for (Object result : results) {
                    make((int[]) result, arrImg, empty, fileOvh, false, true);
                }
            }
        }

        //добавить картинки на бакграунд и оверхенд
        if (s.getSettingsInt("method") == 4) {
            assert arrBgd != null;
            assert arrOvh != null;
            for (File fileOvh : arrOvh) {
                for (File fileBgd : arrBgd) {
                    for (Object result : results) {
                        make((int[]) result, arrImg, fileBgd, fileOvh, true, true);
                    }
                }
            }
        }

        //добавить картинки на случайный бакграунд
        if (s.getSettingsInt("method") == 5) {
            assert arrBgd != null;
            for (Object result : results) {
                Random rand = new Random();
                File fileBgd = new File(String.valueOf(arrBgd[rand.nextInt(arrBgd.length)]));
                make((int[]) result, arrImg, fileBgd, empty, true, false);
            }
        }

        //добавить картинки и случайный оверхенд
        if (s.getSettingsInt("method") == 6) {
            assert arrOvh != null;
            Random rand = new Random();
            File fileOvh = new File(String.valueOf(arrOvh[rand.nextInt(arrOvh.length)]));
            for (Object result : results) {
                make((int[]) result, arrImg, empty, fileOvh, false, true);
            }
        }

        //добавить картинки на случайный бакграунд и случайный оверхенд
        if (s.getSettingsInt("method") == 7) {
            assert arrOvh != null;
            assert arrBgd != null;
            Random rand = new Random();
            File fileBgd = new File(String.valueOf(arrBgd[rand.nextInt(arrBgd.length)]));
            File fileOvh = new File(String.valueOf(arrOvh[rand.nextInt(arrOvh.length)]));
            for (Object result : results) {
                make((int[]) result, arrImg, fileBgd, fileOvh, true, true);
            }
        }

    }

    private void make(int[] pics, File[] arrImg, File path_bgd, File path_ovh, boolean bgd, boolean ovh) {
        try {

            //создать пустой макет и залить белым фоном
            int w = s.getSettingsInt("width");
            int h = s.getSettingsInt("height");
            BufferedImage maket = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = maket.createGraphics();
            g.fillRect(0, 0, w, h);
            g.setColor(Color.decode(s.getSettings("color")));

            if (bgd) {
                BufferedImage bf_bgd = ImageIO.read(path_bgd);
                g.drawImage(bf_bgd, 0, 0, null);
            }


            //добавить картинки на макет
            for (int i = 0; i < img_stg.length; i++) {
                BufferedImage img = ImageIO.read(arrImg[pics[i] - 1]);
                String[] img_xy = img_stg[i].split(",");
                g.drawImage(img, Integer.parseInt(img_xy[0]), Integer.parseInt(img_xy[1]), null);
                img.flush();
            }


            //добавить overhand
            if (ovh) {
                BufferedImage bf_ovh = ImageIO.read(path_ovh);
                g.drawImage(bf_ovh, 0, 0, null);
            }


            //сохранить картинку

            count_out ++;
            if(count_out > max_out) System.exit(0);
            Tools.saveJpg(maket, path_bgd.getName() + Arrays.toString(pics), "jpg", dir_out, s.getSettingsFloat("compress"));
            maket.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
