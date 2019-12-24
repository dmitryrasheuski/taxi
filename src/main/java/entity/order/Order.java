package entity.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private long id;
    private long idUser;
    private long idCar;
    private String from;
    private String where;
    private String comments;
}
