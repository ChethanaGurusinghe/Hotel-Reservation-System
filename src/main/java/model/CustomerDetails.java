package model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDetails {

    private String customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer numberOfGuests;
}
