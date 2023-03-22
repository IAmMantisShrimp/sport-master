package com.example.backstage.util;

/**
 * @author HuaRunSheng
 * @date 2022/10/26 16:14
 * @description :
 */


import com.example.backstage.entity.DrownedMan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 生成指定时间段内的随机日期：如1999-11-30 00:00:00到现在
 *
 * @author soberw
 */
public class DrownedManUtil {
    public static void main(String[] args) throws ParseException {

        for (int i = 0; i < 100; i++) {

            Integer age = generateAge();
            Integer conSwim = generateCanSwimming(age);
            Integer death = generateDeath(age, conSwim);
            String surrounding = generateSurrounding();
            Date date = generateDeathTime();
            System.out.println("年龄: "+age+"  "
                    + (conSwim == 1 ? "会游泳" : "不会游泳")+"  "
                    + (death == 1 ? "死亡" : "未死亡") +"  "
                    + surrounding + "  "+ date);
            //System.out.println(generateSex() == 1 ? "男" : "女");
            //System.out.println(generateAge());
        }
        ////获取当前时间
        //Calendar calendar = Calendar.getInstance();
        //long now = calendar.getTimeInMillis();
        //
        ////设置指定时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2018-11-30 00:00:00");
        //long before = date.getTime();
        //
        //Random random = new Random();
        //
        ////产生long类型指定范围随机数
        //long randomDate = before + (long) (random.nextFloat() * (now - before + 1));
        //
        ////格式化输出日期
        //System.out.printf("从1999-11-30 00:00:00到现在的一个随机日期为:%1$tF %1$tT", randomDate);

    }
    public static DrownedMan generateDrownedMan(){
        DrownedMan drownedMan=new DrownedMan();
        drownedMan.setName(ChineseUtil.getRandomChineseName());
        drownedMan.setAge(generateAge());
        drownedMan.setSex(generateSex());
        drownedMan.setCanSwim(generateCanSwimming(drownedMan.getAge()));
        drownedMan.setDeath(generateDeath(drownedMan.getAge(),drownedMan.getCanSwim()));
        drownedMan.setSurrounding(generateSurrounding());
        drownedMan.setDrowningTime(generateDeathTime());
        return drownedMan;
    }

    /**
     * 是否会游泳,0不会,1会, 小于十岁肯定不会,大于80不会
     * @param age
     * @return
     */
    public static Integer generateCanSwimming(int age){
        if (age < 10){
            return 0;
        }
        Random random=new Random();
        if (random.nextInt(10) < 8){
            return 0;
        }
        return 1;
    }

    /**
     * 环境:"池塘"12%,"河道"40%,"泳池"5%,"水库"15%,"湖畔"10%,"海域"8%,"其他"10%
     * @return
     */
    public static String generateSurrounding(){
        String surrounding[]={"河道","池塘","泳池","水库","湖畔","海域","其他"};
        Random random=new Random();
        int i = random.nextInt(100);
        if (i<40){
            return surrounding[0];
        }else if (i < 52){
            return surrounding[1];
        }else if (i < 57){
            return surrounding[2];
        }else if (i < 72){
            return surrounding[3];
        }else if (i < 82){
            return surrounding[4];
        }else if (i < 90){
            return surrounding[5];
        }else {
            return surrounding[6];
        }

    }
    /**
     * 死亡率86%
     * @param age
     * @param canSwim
     * @return
     */
    public static Integer generateDeath(int age, int canSwim){
        Random random=new Random();
        int i = random.nextInt(100);
        if (age < 18){
            if (canSwim == 1){
                if (i < 70)
                    return 1;
                return 0;
            }else{
                if (i < 90)
                    return 1;
                return 0;
            }

        }
        else{
            // 会游泳,且大于18水,60%能活
            if (canSwim==1 && i >40){
                return 0;
            }
            // 不会游泳,且大于18,20%能活
            else if (canSwim != 1 && i>80)
                return 0;
            return 1;
        }

    }
    /**
     * "2018-11-30 00:00:00" 开始
     * 生成死亡时间,死亡时间再8点到21点
     * @return
     */
    public static Date generateDeathTime(){
        Date date = generateDeathTime("2018-11-30 7:00:00");
        //System.out.println(date);
        //System.out.println(date.getHours());
        // 0.7的概率在10点至17点
        boolean randomNum = Math.random() * 10 < 7;
        int hours = date.getHours();
        if (randomNum){
            //System.out.println("死亡时间在10点到17点");
            while (hours < 10 || hours > 17){
                date = generateDeathTime("2018-11-30 7:00:00");
                hours = date.getHours();
            }
        }else{
            //System.out.println("死亡时间在8点到21点");
            while (hours < 8 || hours > 21){
                date = generateDeathTime("2018-11-30 7:00:00");
                hours = date.getHours();
            }
        }
        return date;
    }

    public static Integer generateSex(){
        Random random=new Random();
        int i = random.nextInt(10);
        if (i<8){
            return 1;
        }
        return 0;
    }
    /**
     * 溺水最多的是未成年人,为95;
     * @return
     */
    public static Integer generateAge(){
        Random random=new Random();
        int age = 17;
        int i = random.nextInt(100);
        if (i>90){
            // 10%为成年人
            age = random.nextInt(50)+18;
        }else if (i<20){
            // 20% 为小于五岁的人
            age = random.nextInt(5)+1;
        }else{
            // 70% 为大于五岁的未成年人
            age = random.nextInt(14)+5;
        }
        return age;
    }
    public static Date generateDeathTime(String startTime){
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        //设置指定时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long before = date.getTime();

        Random random = new Random();

        //产生long类型指定范围随机数
        long randomDate = before + (long) (random.nextFloat() * (now - before + 1));
        // 格式化
        //System.out.println(sdf.format(new Date(randomDate)));
        return (new Date(randomDate)) ;
    }
}

