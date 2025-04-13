package org.baebe.coffeetrading.domains.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class FileBaseEntity extends BaseTimeEntity {

    @Column(name = "UPLOAD_FILE_NAME", nullable = false)
    private String uploadFileName;

    @Column(name = "ORIGINAL_FILE_NAME", nullable = false)
    private String originalFileName;

    @Column(name = "UPLOAD_PATH", nullable = false)
    private String fileUploadPath;
}
