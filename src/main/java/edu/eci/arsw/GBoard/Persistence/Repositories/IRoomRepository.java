package edu.eci.arsw.GBoard.Persistence.Repositories;

import  edu.eci.arsw.GBoard.Persistence.GBoardException;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.GBoard.model.Room;
import edu.eci.arsw.GBoard.model.User;

@Repository
public interface IRoomRepository extends DAO<Room, String>{

	public void addUser(User user, String room) throws GBoardException;
}
