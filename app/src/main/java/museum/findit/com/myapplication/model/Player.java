package museum.findit.com.myapplication.model;

/**
 * Created by doniramadhan on 2016-10-23.
 */
public class Player {
    public Integer order;
    public Integer percentage;
    public Integer score;
    public String username;
    public String gameId;
    int noOfQuestionsAnswered;
    int totalNoOfQuestions;
    int currentItemIndex;
    int currentQuestionIndex;
    double currentScore;


    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getNoOfQuestionsAnswered() {
        return noOfQuestionsAnswered;
    }

    public void setNoOfQuestionsAnswered(int noOfQuestionsAnswered) {
        this.noOfQuestionsAnswered = noOfQuestionsAnswered;
    }

    public int getTotalNoOfQuestions() {
        return totalNoOfQuestions;
    }

    public void setTotalNoOfQuestions(int totalNoOfQuestions) {
        this.totalNoOfQuestions = totalNoOfQuestions;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    public void setCurrentItemIndex(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public double getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(double currentScore) {

        this.currentScore+= currentScore;
    }

    public void clear(){

          order=0;
          percentage=0;
          score=0;
          username="";
          gameId="";
         noOfQuestionsAnswered=0;
         totalNoOfQuestions=0;
    }

}
