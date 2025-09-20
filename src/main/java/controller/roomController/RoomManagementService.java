package controller.roomController;

public interface RoomManagementService {

    public void addRoomDetails(String roomId,String type,Double pricePerNight,Integer maxGuests,Boolean available,String description,Integer floor);
    public void deleteRoomDetails(String roomId);
    public void updateRoomDetails(String type,Double pricePerNight,Integer maxGuests,Boolean availability,String description,Integer floor,String roomId);
}
