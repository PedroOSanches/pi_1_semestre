package br.maua.config;

import java.util.List;

import javax.swing.*;
import java.awt.Image;
import java.util.Objects;

public class IconUtil {

    public static final List<Image> ICONS = List.of(
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua16.png"))).getImage(),
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua32.png"))).getImage(),
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua48.png"))).getImage(),
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua64.png"))).getImage(),
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua128.png"))).getImage(),
            new ImageIcon(Objects.requireNonNull(IconUtil.class.getResource("/assets/logoMaua256.png"))).getImage()
    );
}