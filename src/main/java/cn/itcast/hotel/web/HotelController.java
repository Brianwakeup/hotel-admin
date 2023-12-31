package cn.itcast.hotel.web;

import cn.itcast.hotel.constans.MqConstans;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Random;

@RestController
@RequestMapping("hotel")
public class HotelController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IHotelService hotelService;

    @GetMapping("/{id}")
    public Hotel queryById(@PathVariable("id") Long id){
        return hotelService.getById(id);
    }

    @GetMapping("/list")
    public PageResult hotelList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1") Integer size
    ){
        Page<Hotel> result = hotelService.page(new Page<>(page, size));

        return new PageResult(result.getTotal(), result.getRecords());
    }

    @PostMapping
    public void saveHotel(@RequestBody Hotel hotel){
        long min = 10000L; // 最小值
        long max = 1000000L; // 最大值
        Random random = new Random();
        long range = max - min + 1;
        long generatedLong = (long) (random.nextDouble() * range) + min;
        hotel.setId(generatedLong);
        hotelService.save(hotel);
        rabbitTemplate.convertAndSend(MqConstans.HOTEL_EXCHANGE,MqConstans.HOTEL_INSERT_KEY,hotel.getId());
    }

    @PutMapping()
    public void updateById(@RequestBody Hotel hotel){
        if (hotel.getId() == null) {
            throw new InvalidParameterException("id不能为空");
        }
        hotelService.updateById(hotel);
        rabbitTemplate.convertAndSend(MqConstans.HOTEL_EXCHANGE,MqConstans.HOTEL_INSERT_KEY,hotel.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        hotelService.removeById(id);
        rabbitTemplate.convertAndSend(MqConstans.HOTEL_EXCHANGE,MqConstans.HOTEL_DELETE_KEY,id);
    }
}
