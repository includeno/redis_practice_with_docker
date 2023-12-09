package base;

import base.config.JedisHelper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.Iterator;
import java.util.List;

public class JedisGeoCommand {
    public static void main(String[] args) {
        //连接到Redis服务器
        Jedis jedis = JedisHelper.getJedis();
        //设置4个地理位置
        jedis.geoadd("pos",(long)120.52,(long)30.40,"pos1" );
        jedis.geoadd("pos",(long)120.52,(long)31.53,"pos2" );
        jedis.geoadd("pos",(long)122.12,(long)30.40,"pos3" );
        jedis.geoadd("pos",(long)122.12,(long)31.53,"pos4" );
        //如下输出[(120.00000089406967,30.000000249977013), (122.00000077486038,30.000000249977013)]
        System.out.println(jedis.geopos("pos","pos1","pos3"));
        //获取指定地点200公里范围内的地理位置
        List<GeoRadiusResponse> geoList = jedis.georadius("pos", 120.52, 30.40, 200, GeoUnit.KM);
        Iterator<GeoRadiusResponse> it = geoList.iterator();
        //循环输出pos1,pos2和pos3
        while(it.hasNext()){
            System.out.println(it.next().getMemberByString());
        }
        //计算距离,输出111.2264
        System.out.println(jedis.geodist("pos","pos1","pos2",GeoUnit.KM));
    }
}

