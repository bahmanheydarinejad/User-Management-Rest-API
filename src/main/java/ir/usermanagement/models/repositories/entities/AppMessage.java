package ir.usermanagement.models.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AppMessage extends AppBaseEntity {

    @EqualsAndHashCode.Include
    private Integer code;

    private Integer httpStatusCode;

    @OneToMany(mappedBy = "appMessage", fetch = FetchType.EAGER)
    private List<AppMessageDescription> descriptions;

    public String getDescriptionByLanguage(String language) {
        List<AppMessageDescription> descriptions = getDescriptions();
        if (Objects.isNull(descriptions))
            return "";
        return descriptions.stream().filter(message -> message.getLanguage().equalsIgnoreCase(language)).map(message -> message.getDescription()).findFirst().orElse("");
    }
}
