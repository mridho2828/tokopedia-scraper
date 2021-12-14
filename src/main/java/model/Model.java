package model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Model {
    private String name;
    private String description;
    private String imageLink;
    private int price;
    private String rating;
    private String merchant;
}
