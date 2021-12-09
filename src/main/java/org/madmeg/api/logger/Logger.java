package org.madmeg.api.logger;

import org.madmeg.api.Color;

import java.util.Scanner;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class Logger {
    private String prefix;
    private Scanner sc;

    public Logger(String prefix){
        this.prefix = prefix;
        this.sc = new Scanner(System.in);
    }


    public void printError(String text){
        System.out.println(Color.RED +"["+ prefix +"] " + text + Color.RESET);
    }

    public void printSuccess(String text){
        System.out.println(Color.GREEN +"["+ prefix +"] " + text + Color.RESET);
    }


    public void print(String text){
        System.out.println("["+ prefix +"] " + text);
    }

    public void print(String text, Color color){
        System.out.println(color +"["+ prefix +"] " + text + Color.RESET);
    }

    public void printCommand(String text){
        System.out.println(Color.CYAN +"["+ prefix +"] " + text + Color.RESET);
    }

    public String readLine(){
        return sc.nextLine();
    }

}
