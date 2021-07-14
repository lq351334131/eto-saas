package etocrm.model.user;

import lombok.Data;

@Data
public class LoginUserVO extends TokenValue {

    private Long id;

    private Long orgId;

    private String name;

    private String uid;


}
