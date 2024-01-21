package com.arpan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

import javax.management.AttributeValueExp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class QuizUtil {
    private final HashMap<Character, String> fileMap;
    private final String path = "src/main/resources/";

    public QuizUtil() {
        fileMap = new HashMap<>();
        fileMap.put('0', "zero.txt");
        fileMap.put('1', "one.txt");
        fileMap.put('2', "two.txt");
        fileMap.put('3', "three.txt");
        fileMap.put('4', "four.txt");
        fileMap.put('5', "five.txt");
        fileMap.put('6', "six.txt");
        fileMap.put('7', "seven.txt");
        fileMap.put('8', "eight.txt");
        fileMap.put('9', "nine.txt");
    }

    public void startGame() {
        CleanTerminal.clearScreen();

        Path path1 = Path.of(path + "art.txt");



        // Base URL
        StringBuilder baseURL = new StringBuilder("https://opentdb.com/api.php?");

        Scanner sc = new Scanner(System.in);

        int numberOfQuestions;
        int category;
        try {
            String art = Files.readString(path1);
            System.out.println(art);
            System.out.print("How many question do you want to Play(1-50): ");
            numberOfQuestions = sc.nextInt();

            for (Subjects subject : Subjects.values()) {
                System.out.println(subject.toString() + " " + subject.getValue());

            }

            System.out.print("Which category do you want to play? e.g 10 (0 for any) ");
            category = sc.nextInt();

            if ((numberOfQuestions >= 1 && numberOfQuestions <= 50) && (category == 0 || category >= 9 && category <= 32)) {
                baseURL.append("amount=").append(numberOfQuestions);
                baseURL.append("&category=").append(category == 0 ? "any" : category);
                baseURL.append("&type=boolean");
                List<TriviaQuestion> questions = getResponse(baseURL);
                if (questions.isEmpty()) {
                    System.out.println("Sorry some problems are there");
                } else {
                    Path gameIntro = Path.of(path + "art.txt");
                    String content = Files.readString(gameIntro);
                    playGame(questions);
                }
            } else {
                System.out.println("Wrong Input!!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong input");
        } catch (IOException e) {
            System.out.println("art.txt not found");
        }
    }

    private List<TriviaQuestion> getResponse(StringBuilder url) {
        try {
            // Create url object
            URL apiURL = URI.create(url.toString()).toURL();
            // open connection
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            // set request method
            connection.setRequestMethod("GET");

            // check Response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                ObjectMapper objectMapper = new ObjectMapper();
                Response triviaResponse = objectMapper.readValue(response.toString(), Response.class);
                return triviaResponse.getTriviaQuestions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void playGame(List<TriviaQuestion> questions) {
        Scanner sc = new Scanner(System.in);
        CleanTerminal.clearScreen();
        System.out.println("Give answer in (T)rue or (F)alse");
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            System.out.printf("Q%d: %s? ", i + 1, questions.get(i).getQuestion());
            String answer = sc.nextLine();
            if (Character.toUpperCase(answer.charAt(0)) == questions.get(i).getCorrectAnswer().charAt(0)) {
                System.out.println("correct answer!!");
                ++score;
            } else {
                System.out.println("Wrong answer!!");
            }
        }


        printScoreText(score, questions.size());


    }

    private void printScoreText(int score, int total) {
        CleanTerminal.clearScreen();
        try {
            Path scorePath = Path.of(this.path + "score.txt");
            Path slashPath = Path.of(this.path + "slash.txt");

            System.out.println(Files.readString(scorePath));

            String scoreString = String.valueOf(score);
            String totalString = String.valueOf(total);

            printScore(scoreString);
            System.out.print(Files.readString(slashPath));
            printScore(totalString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printScore(String score) {

        try {
            for (int i = 0; i < score.length(); i++) {
                Path fileName = Path.of(path + fileMap.get(score.charAt(i)));
                String content = Files.readString(fileName);
                System.out.print(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
