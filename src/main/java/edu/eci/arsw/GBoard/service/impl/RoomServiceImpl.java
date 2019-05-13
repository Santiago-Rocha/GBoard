package edu.eci.arsw.GBoard.service.impl;

import edu.eci.arsw.GBoard.Persistence.GBoardException;
import edu.eci.arsw.GBoard.Persistence.Repositories.IRoomRepository;
import edu.eci.arsw.GBoard.Persistence.Repositories.IUserRepository;
import edu.eci.arsw.GBoard.model.Room;
import edu.eci.arsw.GBoard.model.RoomType;
import edu.eci.arsw.GBoard.model.User;
import edu.eci.arsw.GBoard.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component("room")
public class RoomServiceImpl implements IRoomService {

    @Autowired
    IRoomRepository roomRepository;
    @Autowired
    IUserRepository userRepository;

    @Override
    public List<Room> getRooms() throws GBoardException {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomByTitle(String title) throws GBoardException {
        return roomRepository.find(title);
    }

    @Override
    public String save(Room room) throws GBoardException {
        return roomRepository.save(room);
    }

    @Override
    public String createRoom(String roomName, String nick) throws GBoardException {
        User user= userRepository.find(nick);
        ArrayList<User> users= new ArrayList<>();
        users.add(user);
        RoomType type= new RoomType("publica");
        Room room= new Room(roomName, user, users, null, type, "");
        return roomRepository.save(room);
    }

    @Override
    public void updateRoom(Room room) throws GBoardException {
        roomRepository.upadate(room);
    }

    @Override
    public void addUserToRoom(User user, String room) throws GBoardException {
        roomRepository.addUser(user,room);
    }

    @Override
    public String joinRoom(String roomName, String nick) throws GBoardException {
        roomRepository.addUser(userRepository.find(nick), roomName);
        return roomName;
    }

    @Override
    public List<Room> searchProgress(String title) throws GBoardException {
        return roomRepository.searchProgress(title);
    }
}
