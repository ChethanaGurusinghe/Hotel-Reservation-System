package controller.customerController;

public interface CustomerManagementService {
    void addCustomerDetails(String customerId,String customerName,String email,String phoneNO,String address,Integer numberOfGuests);
    void deleteCustomerDetails(String customerId);
    void updateCustomerDetails(String customerName,String email,String phoneNO,String address,Integer numberOfGuests,String customerId);
}
