package vn.com.panda.learncardriving.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE department SET deleted = true, deleted_date = current_timestamp WHERE id=?")
@Where(clause = "deleted = false")
public class Department extends BaseEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 200)
    private String phone;

    @Column(length = 500)
    private String address;
}
