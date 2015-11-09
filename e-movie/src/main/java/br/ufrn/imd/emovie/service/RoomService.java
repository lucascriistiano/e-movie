package br.ufrn.imd.emovie.service;

import java.util.List;

import br.ufrn.imd.emovie.dao.DaoRoom;
import br.ufrn.imd.emovie.dao.IDaoRoom;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Room;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class RoomService {

	private static RoomService roomService;
	private IDaoRoom daoRoom;
	
	private RoomService() {
		this.daoRoom = new DaoRoom();
	}
	
	public static RoomService getInstance() {
		if(roomService == null) {
			roomService = new RoomService();
		}
		
		return roomService;
	}
	
	public Room find(Integer id) throws DaoException {
		return daoRoom.getById(id);
	}
	
	public List<Room> listAll() throws DaoException {
		return daoRoom.getAll();
	}
	
	public void create(Room room) throws ServiceException, DaoException {
		daoRoom.create(room);
	}
	
	public void update(Room room) throws ServiceException, DaoException {
		daoRoom.update(room);
	}
	
	public void delete(Room room) throws DaoException {
		daoRoom.delete(room);
	}
	
	public void delete(Integer id) throws DaoException {
		daoRoom.delete(id);
	}
	
}