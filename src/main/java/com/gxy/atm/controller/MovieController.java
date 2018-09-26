package com.gxy.atm.controller;

import com.gxy.atm.entity.Movie;
import com.gxy.atm.entity.ShowDay;
import com.gxy.atm.entity.ShowTime;
import com.gxy.atm.mapper.MovieMapper;
import com.gxy.atm.mapper.ShowDayMapper;
import com.gxy.atm.mapper.ShowTimeMapper;
import com.gxy.atm.util.AjaxResult;
import com.gxy.atm.util.RedisUtilSentinel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "api")
public class MovieController {

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private ShowDayMapper showDayMapper;

    @Resource
    private ShowTimeMapper showTimeMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 使用了 redis缓存 返回电影详情
     * @return
     */
    @ResponseBody
    @PostMapping(value = "listMovies.do")
    public AjaxResult listMovies () {

        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();

        Object listMovies = stringObjectValueOperations.get("listMovies");
        if (listMovies != null) {
            return AjaxResult.success(listMovies);
        }

        List<Movie> movieList = movieMapper.listMovies();

        stringObjectValueOperations.set("listMovies", movieList, 20, TimeUnit.SECONDS);

        return AjaxResult.success(movieMapper.listMovies());
    }

    //电影详情
    @ResponseBody
    @PostMapping(value = "getMovieInfo.do")
    public AjaxResult getMovieInfo(@RequestParam("id") Integer id){
        return AjaxResult.success(movieMapper.selectByPrimaryKey(id));
    }

    //放映日期
    @ResponseBody
    @PostMapping(value = "listShowDay.do")
    public AjaxResult listShowDay(@RequestParam("id") Integer id){
        return AjaxResult.success(showDayMapper.listByMovieIdShowDay(id));
    }

    //放映时间段
    @ResponseBody
    @PostMapping(value = "listShowTime.do")
    public AjaxResult listShowTime(@RequestParam("id") Integer id){

        ArrayList<List<ShowTime>> showTimeList = new ArrayList<>();

        List<ShowDay> showDays = showDayMapper.listByMovieIdShowDay(id);

        for (ShowDay showDay:
             showDays) {

            List<ShowTime> showTimes = showTimeMapper.listShowTimeByDayId(showDay.getId());

            showTimeList.add(showTimes);

        }

        return AjaxResult.success(showTimeList);
    }

    @PostMapping(value = "getMovieByShowTimeId.do")
    @ResponseBody
    public AjaxResult getMovieByShowTimeId (@RequestParam("showTimeId") Integer showTimeId) {
        return AjaxResult.success(movieMapper.getMovieByShowTimeId(showTimeId));
    }


    /**
     * 获得放映日期和时间段
     * @param showTimeId
     * @return
     */
    @PostMapping(value = "getShowMsgById.do")
    @ResponseBody
    public AjaxResult getShowMsgById (@RequestParam("showTimeId") Integer showTimeId) {
        return AjaxResult.success(showTimeMapper.getShowMsgById(showTimeId));
    }

}
