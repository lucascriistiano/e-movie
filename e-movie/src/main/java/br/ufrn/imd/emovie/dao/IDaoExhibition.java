package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.model.Exhibition;

public interface IDaoExhibition extends IDaoGeneric<Exhibition> {

	List<Exhibition> listByMovieId(Integer idMovie);

}