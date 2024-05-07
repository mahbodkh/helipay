package app.helipay.se.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

import static app.helipay.se.service.SlugHandler.getSlugger;


@Entity
@Table(name = "se_posts")
@Getter
@Setter
@ToString(exclude = "html")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity
{

  public enum StatusType
  {
    ACTIVE
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NaturalId
  @Column(nullable = false, unique = true)
  private String slug; // url and unique
  @Column
  private String title;
  @Column
  private String excerpt;
  @Lob
  @Column(name = "content", length = 512)
  private String content;
  @Lob
  @Column(name = "html", length = 512)
  private String html;
  @Builder.Default
  @Column
  private Boolean featured = false;
  @Column
  private String page;
  @Column
  private String locale; // en, ir, ru, fr, ...
  @Column
  private Long author;
  @Column
  @Enumerated(EnumType.STRING)
  private StatusType status;
  @Column
  private String customTemplate; // e.g, template name

  @Column
  private String coverImageUrl;

  //  private List<String> imageUrls = new ArrayList<>();
  @Setter(AccessLevel.NONE)
  @Version
  @Column(name = "version")
  private Integer version;
  @Column(name = "published")
  private Date published;
  @Column(name = "created")
  @CreationTimestamp
  private Date created;
  @Column(name = "changed")
  @UpdateTimestamp
  private Date changed;

  @Builder.Default
//  @Type(type = "jsonb")
  @Column(name = "properties")
  private Map<String, Object> properties = new HashMap<>();

  // <div>
  //     <h1> صفحه اصلی </h1>
  //     <p> title </p>
  //     <text> This ... for the important project.</text>
  //     <text> This ... for the important project.</text>
  //     <br/>
  //     <img
  //         src="https://images.pexels.com/photos/20787/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350"
  //         alt="new"
  //     />
  // </div>

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PostEntity)) {
      return false;
    }
    PostEntity that = (PostEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }

  @Transient
  public static PostEntity getPostEntityBasic(String title, String slug, String status) {
    slug = getSlugger(title, slug);
    return PostEntity.builder()
                     .title(title)
                     .slug(slug)
                     .status(getStatus(status))
                     .build();
  }

  private static StatusType getStatus(String status) {
    return status != null ? StatusType.valueOf(status) : StatusType.ACTIVE;
  }

}