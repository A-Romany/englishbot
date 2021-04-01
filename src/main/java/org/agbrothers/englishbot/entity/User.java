package org.agbrothers.englishbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "bot_user")
public class User {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column(unique = true, nullable = false)
    private String chatId;
    @Column
    private String stateId;

    @OneToOne(mappedBy = "user")
    private Lesson lesson;

    public User() {

    }

    public User(String chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!chatId.equals(user.chatId)) return false;
        return stateId.equals(user.stateId);
    }

    @Override
    public int hashCode() {
        int result = 31;
        if(id != null) {
            result = 31 * id.hashCode();
        }
        if(chatId != null) {
            result = 31 * result + chatId.hashCode();
        }
        if(stateId != null) {
            result = 31 * result + stateId.hashCode();
        }
        return result;
    }
}
