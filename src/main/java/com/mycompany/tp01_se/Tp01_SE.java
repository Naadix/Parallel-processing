package com.mycompany.tp01_se;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



class Result {
    int nbrLt = 0; 
    int nbrCh = 0;
    int nbrLn = 0;
}

public class Tp01_SE {

    public static void main(String[] args) {
        
         long overallStartTime = System.currentTimeMillis();
        Result result = new Result(); // object n5zen fih result 

        // n5zen content nta3 file fobject fileContent
        
        StringBuilder fileContent = new StringBuilder();
        try (FileReader in = new FileReader("D:\\input.txt")) {
            int c;
            while ((c = in.read()) != -1) {
                fileContent.append((char) c); // code ASCII nta3 car nrj3 value nta3ha car wn5zen
            }
        } catch (IOException e) {
            System.out.println("This is IO Exception : " + e.getMessage());
            return;
        }

        final String content = fileContent.toString(); //text bch ywli const
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        // Thread 1
        Thread thread1 = new Thread(() -> {
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println("Thread 1 started at: " + startTime.format(formatter));

            for (char ch : content.toCharArray()) {
                if (Character.isLetter(ch)) {
                    result.nbrLt++;
                }
            }
            LocalDateTime endTime = LocalDateTime.now();
            System.out.println("Thread 1 ended at: " + endTime.format(formatter));
        });

        // Thread 2
        Thread thread2 = new Thread(() -> {
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println("Thread 2 started at: " + startTime.format(formatter));


            for (char ch : content.toCharArray()) {
                if (Character.isDigit(ch)) {
                    result.nbrCh++;
                }
            }
              LocalDateTime endTime = LocalDateTime.now();
            System.out.println("Thread 2 ended at: " + endTime.format(formatter));
        });

        // Thread 3
        Thread thread3 = new Thread(() -> {
           LocalDateTime startTime = LocalDateTime.now();
            System.out.println("Thread 3 started at: " + startTime.format(formatter));

            for (char ch : content.toCharArray()) {
                if (ch == '\n') {
                    result.nbrLn++;
                }
            }
            //ychouf a5ir letter fi last line ida \n yzid or no tani yzid
            if (!content.isEmpty() && content.charAt(content.length() - 1) != '\n')  {
                result.nbrLn++;
            }
            System.out.println("---------------------------");

             LocalDateTime endTime = LocalDateTime.now();
            System.out.println("Thread 3 ended at: " + endTime.format(formatter));

        });

        // Thread 4
        Thread thread4 = new Thread(() -> {
            try {
                 LocalDateTime startTime = LocalDateTime.now();
                System.out.println("Thread 4 started at: " + startTime.format(formatter));

                // execut all statment li fi thread
                thread1.join();
                thread2.join();
                thread3.join();
                
                 LocalDateTime endTime = LocalDateTime.now();
                System.out.println("Thread 4 ended at: " + endTime.format(formatter));

                System.out.println("---------------------------");
                System.out.println("nombre de caracteres  : " + result.nbrLt);
                System.out.println("nombre de chiffres  : " + result.nbrCh);
                System.out.println("nombre de ligne: " + result.nbrLn);

            } catch (InterruptedException e) {
                System.out.println("Thread interrompu : " + e.getMessage());
            }
        });

        // running lobject nta3 thread 
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        
        long overallEndTime = System.currentTimeMillis(); 
        double overallTimeInSeconds = (overallEndTime - overallStartTime) / 1000.0; 
        System.out.println("Time taken for implementation parallel: " + overallTimeInSeconds + " s");
        System.out.println("---------------------------");

    }
}
