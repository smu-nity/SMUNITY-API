package com.smunity.graduation.domain.qna.entity;

import com.smunity.graduation.domain.accounts.entity.User;
import com.smunity.graduation.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qna_question")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean anonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne(mappedBy = "question", fetch = FetchType.LAZY)
    private Answer answer;

    public boolean isAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        anonymous = anonymous;
    }

    public void setTitle(String title) {
        title = title;
    }

    public void setContent(String content) {
        content = content;
    }
}
