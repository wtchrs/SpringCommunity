package wtchrs.SpringCommunity.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCreateRequest {

    private String name;

    private String description;

}
