import javax.swing.*;
// import javafx.application.*;

/**
 * Create by tuke on 2019-05-07
 *
 * javac ShowEnv.java
 * java ShowEnv
 */
public class ShowEnv {

    public static void main(String[] args){
        System.out.println("System.getProperties()");
        System.getProperties().forEach((k, v) -> {
            System.out.printf("\t%s:\t%s\n", k, v);
        });

        System.out.println("System.getenv()");
        System.getenv().forEach((k, v) -> {
            System.out.printf("\t%s:\t%s\n", k, v);
        });

        while(true){
            try {
                Thread.sleep(1_000);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}

