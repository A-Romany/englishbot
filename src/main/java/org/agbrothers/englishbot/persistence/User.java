package org.agbrothers.englishbot.persistence;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id @GeneratedValue
    @Column
    private Long id;//TODO: AUTO GENERATE
    @Column (unique = true, nullable = false)
    private String chatId;//TODO: UNIQUE
    @Column
    private String stateId;

    //TODO: EQUALS AND HASHCODE
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


    //TODO: TEST

}
