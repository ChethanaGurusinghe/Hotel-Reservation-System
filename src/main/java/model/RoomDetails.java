package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDetails {
    private String roomId;
    private String type;
    private Double pricePerNight;
    private Integer maxGuests;
    private Boolean availability;
    private String description;
    private Integer floor;

}
