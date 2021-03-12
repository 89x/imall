package com.barry.service;

import com.barry.pojo.Carousel;
import com.barry.pojo.Category;

import java.util.List;

public interface CarouselService {
    public List<Carousel> queryAll(Integer isShow);

}
