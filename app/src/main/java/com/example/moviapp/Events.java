package com.example.moviapp;

import com.example.moviapp.model.Movie;

class Events {
    public static class SelectedMovie {
        private final Movie movie;
        public SelectedMovie(Movie m) {
            this.movie = m;
        }
        public Movie getMovie() {
            return movie;
        }
    }
    public static class PreferenceChanged {
        private final String new_value;
        public PreferenceChanged(String m) {
            this.new_value = m;
        }
        public String getNew_value() {
            return new_value;
        }
    }
}
