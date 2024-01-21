package com.arpan;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class Response {
    @JsonProperty("response_code")
    private int responseCode;

    @JsonProperty("results")
    private List<TriviaQuestion> triviaQuestions;

    public List<TriviaQuestion> getTriviaQuestions() {
        return this.triviaQuestions;
    }


    @Override
    public String toString() {
        return "%s".formatted(triviaQuestions);
    }
}

class TriviaQuestion {
    @JsonProperty("type")
    public String type;
    @JsonProperty("difficulty")
    public String difficulty;
    @JsonProperty("category")
    public String category;
    @JsonProperty("question")
    private String question;

    @JsonProperty("correct_answer")
    private String correctAnswer;

    @JsonProperty("incorrect_answers")
    private List<String> incorrectAnswer;

    public String getQuestion() {
        return StringEscapeUtils.unescapeHtml4(this.question);
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    @Override
    public String toString() {
        return StringEscapeUtils.unescapeHtml4(this.question);
    }
}