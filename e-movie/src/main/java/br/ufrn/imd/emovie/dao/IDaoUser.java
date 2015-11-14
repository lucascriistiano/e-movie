package br.ufrn.imd.emovie.dao;

import br.ufrn.imd.emovie.model.User;

public interface IDaoUser extends IDaoGeneric<User> {

	User checkLogin(User user);

}