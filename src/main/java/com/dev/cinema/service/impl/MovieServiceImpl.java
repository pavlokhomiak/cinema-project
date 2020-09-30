package com.dev.cinema.service.impl;

import com.dev.cinema.dao.MovieDao;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDao movieDao;

    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    public List<Movie> getAll() {
        return movieDao.getAll();
    }
}
