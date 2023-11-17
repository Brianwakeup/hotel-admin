package cn.itcast.hotel.constans;

/**
 * @Package:cn.itcast.hotel.constans
 * @Auther:Brianwei
 * @date:2023/11/15:9:27
 * @discribe:
 */
public class MqConstans {

    /**
     * 交换机名称
     */
    public static final String HOTEL_EXCHANGE = "hotel.topic";

    /**
     * 监听新增和修改的队列
     */
    public static final String HOTEL_INSERT_QUEUE = "hotel.insert.queue";

    /**
     * 监听删除的队列
     */
    public static final String HOTEL_DELETE_QUEUE = "hotel.delete.queue";

    /**
     * 新增和修改的rountingkey
     */
    public static final String HOTEL_INSERT_KEY = "hotel.insert";

    /**
     * 删除的rountingkey
     */
    public static final String HOTEL_DELETE_KEY = "hotel.delete";
}
