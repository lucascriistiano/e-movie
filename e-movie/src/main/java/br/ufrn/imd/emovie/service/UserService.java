package br.ufrn.imd.emovie.service;

import java.util.List;
import java.util.Random;

import br.ufrn.imd.emovie.dao.DaoUser;
import br.ufrn.imd.emovie.dao.IDaoUser;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class UserService {

	private static UserService userService;
	private IDaoUser daoUser;
	
	
	private UserService() {
		this.daoUser = new DaoUser();
	}
	
	public static UserService getInstance() {
		if(userService == null) {
			userService = new UserService();
		}
		
		return userService;
	}
	
	public User find(Integer id) throws DaoException {
		return daoUser.getById(id);
	}
	
	public List<User> listAll() throws DaoException {
		return daoUser.getAll();
	}
	
	public void create(User user) throws ServiceException, DaoException {
		daoUser.create(user);
	}
	
	public void update(User user) throws ServiceException, DaoException {
		daoUser.update(user);
	}
	
	public void delete(User user) throws DaoException {
		daoUser.delete(user);
	}
	
	public void delete(Integer id) throws DaoException {
		daoUser.delete(id);
	}
	
	public User checkLogin(User user) throws DaoException {
		return daoUser.checkLogin(user);
	}
	
	public String generatePassword() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < 10; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
}