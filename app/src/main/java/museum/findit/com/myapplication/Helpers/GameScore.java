package museum.findit.com.myapplication.Helpers;

/**
 * Created by antongregory on 24/10/2016.
 */

public class GameScore {

    static double itemScore;
    static double quizScore;
    static double percentage;



    /**
     *
     * @param timeTaken time taken to find an item in seconds
     * @return return the score calculated
     */
    public static double itemScoreCalculator(double timeTaken){
        double itemscore,base;


        if(timeTaken<=0){
            timeTaken=2;
        }

        if (timeTaken<=20){

            itemscore=420-timeTaken;
        }
        else if (timeTaken>20 && timeTaken <=180){
            itemscore=baseTweak(20,timeTaken);
            itemscore+=300;
        }
        else if(timeTaken>180 && timeTaken<=900){
            itemscore=baseTweak(180 , timeTaken ) ;

            itemscore+=200;
        }
        else if(timeTaken>900 && timeTaken<=1800){
            itemscore=baseTweak(900,timeTaken);
            itemscore+=100;

        }

        else if (timeTaken>1800 && timeTaken<=3600){
            System.out.println("in base 149");
            base=149;
            itemscore=base - minuteCalculator(timeTaken);
        }
        else {
            base=149;
            itemscore=base - (1.5*minuteCalculator(timeTaken));
            if(itemscore<0){
                itemscore=0;
            }
        }
        return itemscore;

    }

    static double baseTweak(double minimum,double timeTaken){
           return (minimum / timeTaken )*100 ;

    }


    public static double quizScoreCalculator(double timeTaken){
        double base =150;
        double quizScore;
        if(timeTaken<15){
            quizScore=base-timeTaken;
        }
        else if(timeTaken>=15 && timeTaken<30){
            quizScore=base-(1.5*timeTaken);
        }
        else{
           quizScore=base-(1.8*timeTaken);
            if(quizScore< 10)
                quizScore=10;
        }

    return quizScore;
    }

    /**
     *
     * @param questions total no of questions
     * @param correct  number of correct ones
     * @return
     */
    public static int percentageCalculator(int questions,int correct){
       return (correct * 100) / questions;
    }

    static double minuteCalculator(double seconds){


        double minute;
        minute=seconds/60;
        return Math.ceil(minute);



    }
}
