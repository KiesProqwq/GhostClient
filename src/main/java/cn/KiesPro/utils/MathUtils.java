package cn.KiesPro.utils;

public class MathUtils {
    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
    
}
