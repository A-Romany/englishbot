package persistence;
import javax.persistence.*;

public class UserWords {
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    @Column
    private Integer userId;
    @Column
    private Integer wordId;

}
