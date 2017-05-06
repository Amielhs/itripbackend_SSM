package cn.itrip.common;

import java.math.BigDecimal;


public class BigDecimalUtil {

    /**
     * BigDecimalOprations + - * /
     */
    public enum BigDecimalOprations{
        add,subtract,multiply,divide
    }

    /**
     * OperationASMD + - * / add substract multiiply divide
     * @param numOne [String Integer Long Double Bigdecimal]
     * @param numTwo [String Integer Long Double Bigdecimal]
     * @param bigDecimalOpration
     * @param scale
     * @param roundingMode
     * @return
     * @throws Exception
     */
    public static BigDecimal OperationASMD(Object numOne,Object numTwo,BigDecimalOprations bigDecimalOpration,int scale,int roundingMode) throws Exception{
        BigDecimal num1 = new BigDecimal(String.valueOf(numOne)).setScale(scale,roundingMode);
        BigDecimal num2 = new BigDecimal(String.valueOf(numTwo)).setScale(scale,roundingMode);
        switch (bigDecimalOpration){
            case add: return num1.add(num2).setScale(scale,roundingMode);
            case subtract: return num1.subtract(num2).setScale(scale,roundingMode);
            case multiply: return num1.multiply(num2).setScale(scale,roundingMode);
            case divide: return num1.divide(num2, scale, roundingMode);
        }
        return null;
    }
    /* Code Demo Exp */
    public static void main(String[] args){
        try {
            System.out.println(BigDecimalUtil.OperationASMD(10,4, BigDecimalOprations.divide,2,BigDecimal.ROUND_DOWN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}