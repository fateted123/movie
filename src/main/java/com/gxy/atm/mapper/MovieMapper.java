package com.gxy.atm.mapper;

import com.gxy.atm.entity.Movie;

import java.util.List;

public interface MovieMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Movie record);

    List<Movie> listMovies();

    Movie selectByPrimaryKey(Integer id);

    Movie getMovieByShowTimeId(Integer showTimeId);


}