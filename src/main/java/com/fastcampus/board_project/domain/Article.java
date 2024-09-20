package com.fastcampus.board_project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{ // entity를 구성하는 필드 작성
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter 
  @ManyToOne(optional = false)
  @JoinColumn(name = "userId")
  private UserAccount userAccount; // 유저 정보 (ID)

  @Setter @Column(nullable = false) private String title; // 제목
  @Setter @Column(nullable = false, length = 10000) private String content; // 내용

//  @Setter private String hashtag; // 해시태그
  @ToString.Exclude
  @JoinTable( // 게시글을 기준으로 hashtag가 생성 되는지 결정되는거라 @JoinTable을 써서 기준이 되는 필드에 붙여줘야 한다.
          name = "article_hashtag",
          joinColumns = @JoinColumn(name = "articleId"), // 게시글에는 어떤 기준으로 join을 할 것이냐
          inverseJoinColumns = @JoinColumn(name = "hashtagId") //
  )
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<Hashtag> hashtags = new LinkedHashSet<>();


  @ToString.Exclude
  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 양방향 바인딩
  

//=======================================================================================
// entity의 기본 기능을 만족시키기위한 코드

  // 모드 JPA entity는 hibernate 구현체를 사용하는 경우: 기본생성자가 있어야한다
  protected Article() { // 평소에는 접근을 막기위해 protected (ex: new 인스턴스화)
    // ( entity이기 때문에 private 기본 생성자는 오류가 난다.)

  }
/*
  원래 넣어야 하는, 즉 도메인과 관련이 있는 정보만 오픈하는 방식으로 생성자를 통해 만들 수 있게끔 한다.
  id는 자동생성 + auto increment 
  일자/ 혹은 생성자는 메타데이터
  factory method를 이용할 것이기때문에 private를 걸어준다.

 */
  private Article(UserAccount userAccount, String title, String content) {
    this.userAccount = userAccount;
    this.title = title;
    this.content = content;
  }

  // new를 쓰지않고 사용하게 의도 전달 (가이드 개념): 제목, 본문, 해시태그를 넣어주세요
  public static Article of(UserAccount userAccount, String title, String content) {
    return new Article(userAccount, title, content);
  }

  public void addHashtag(Hashtag hashtag) { // 이러한 메소드들이 없다면 getHashtags().~~ 이런식으로 써야 하기 때문에 정리된 메소드 작성
    this.getHashtags().add(hashtag);
  }

  public void addHashtags(Collection<Hashtag> hashtags){
    this.getHashtags().addAll(hashtags);
  }

  public void clearHashtags() {
    this.getHashtags().clear();
  }
/*
  만약에 list에 담아서 or collection에 담아서 사용할때
  중복 요소를 제거하거나, 정렬을 해야할때 비교 할 것이 필요함
  동등성, 동일성 검사를 위한 데이터가 필요함
  Lombok @equalsandhashcode를 사용하면 필드를 모두 비교하게됨


  아래 메소드로 id만 동등성만 검사

  데이터 베이스에 데이터를 연결시키지 않았을때 만든 엔티티는 null일 수 있기 때문에
  id != null 조건 추가 (아직 영속화되지 않은 엔티티는 모두 동등성 검사를 탈락한다)
 */

  @Override
  public boolean equals(Object o) { // 받이들인 Object o가
    if (this == o) return true; // article 인지 확인
    if (!(o instanceof Article that)) return false;
    return this.getId() != null && this.getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

}

/*
  정적 팩토리 메소드: from/ of
  dto/entity의 캡슐화를 위해 사용
  DTO와 Entity간에는 자유롭게 형 변환이 가능해야 하는데, 정적 팩토리 메소드를 사용하면
  내부 구현을 모르더라도 쉽게 변환 가능

  ex):
  StudentDto studentDto = new StudentDto(student.getName(), student.getGrade()); // 생성자를 쓴 경우

  Student studentDto = studentDto.from(student); // static factory method

  .from(): 하나의 매개 변수를 받아서 객체생성
  .of(): 여러개의 매개 변수를 받아서 객체생성

 */


