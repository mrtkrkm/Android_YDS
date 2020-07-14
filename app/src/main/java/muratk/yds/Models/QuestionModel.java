package muratk.yds.Models;

/**
 * Created by Murat on 23/11/15.
 */
public class QuestionModel {
    private  String ingilizce;
    private  String turkce;

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public QuestionModel() {
    }

    public QuestionModel(String ingilizce, String turkce) {
        this.ingilizce = ingilizce;
        this.turkce = turkce;
    }
}
