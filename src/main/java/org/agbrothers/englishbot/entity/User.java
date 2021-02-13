package org.agbrothers.englishbot.entity;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column(unique = true, nullable = false)
    private String chatId;
    @Column
    private String stateId;

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
        int result = id.hashCode();
        result = 31 * result + chatId.hashCode();
        result = 31 * result + stateId.hashCode();
        return result;
    }
}
