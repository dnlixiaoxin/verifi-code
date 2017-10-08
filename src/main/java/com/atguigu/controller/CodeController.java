package com.atguigu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.SecKill_redisByScript;
import com.atguigu.bean.AJAXResult;
import com.atguigu.util.GetCode;
import com.atguigu.util.JedisPoolUtil;
import com.atguigu.util.VerifyCodeConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Controller
public class CodeController {

	@RequestMapping({ "", "/" })
	public String index() {
		return "index";
	}
	
	@RequestMapping("/seckill")
	public String doseckill() {
		return "SecKill";
	}
	@ResponseBody
	@RequestMapping("/doseckill")
	public Object doseckill(String prodid) {
		AJAXResult result = new AJAXResult();
		String userid = String.valueOf(new Random().nextInt(50000));
        try {
			boolean doSecKill = SecKill_redisByScript.doSecKill(userid, prodid);
			result.setSuccess(doSecKill);
		} catch (IOException e) {
			e.printStackTrace();
		}
       
		return result;
	}

//	@ResponseBody
//	@RequestMapping("/doseckill")
//	public Object doseckill(String prodid) {
//		AJAXResult result = new AJAXResult();
//		Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
//		try {
//			String userid = String.valueOf(new Random().nextInt(50000));
//			String qtkey="sk:"+prodid+":qt";
//			String usrkey="sk:"+prodid+":usr";
//			
//			if(jedis.sismember(usrkey, userid)) {
//				result.setSuccess(false);
//				System.err.println("您已经抢过一次了");
//				return result;
//			}
//			
//			jedis.watch(qtkey);
//			String qtkeyString = jedis.get(qtkey);
//			int count = Integer.parseInt(qtkeyString);
//			
//			if(count <= 0) {
//				result.setSuccess(false);
//				System.err.println("商品已经被抢完了");
//				return result;
//			}
//			
//			Transaction transaction = jedis.multi();
//			transaction.decr(qtkey);
//			transaction.sadd(usrkey,userid);
//			
//			List<Object> list = transaction.exec();
//			if(list == null || list.size() == 0) {
//				result.setSuccess(false);
//				System.err.println("商品抢购失败");
//				
//			}else {
//				result.setSuccess(true);
//				System.out.println("商品抢购成功");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			jedis.close();
//		}
//		
//		
//		return result;
//	}
	
	@ResponseBody
	@RequestMapping("/CodeSender")
	public Object CodeSender(String phoneNo) {
		AJAXResult result = new AJAXResult();
		 try {
			Jedis jedis = new Jedis("192.168.146.128",6379 );
			String countPhone = 
					VerifyCodeConfig.PHONE_PREFIX+
					phoneNo+
					VerifyCodeConfig.COUNT_SUFFIX;
			if (jedis.get(countPhone) == null) {
				jedis.set(countPhone,"1");
			}
			
			int count = Integer.parseInt(jedis.get(countPhone));
			
			System.out.println(count);
			
			if(count > 3) {
				result.setSuccess(false);
				return result;
			}else {
				jedis.incr(countPhone);
				String JedisPhone = 
						VerifyCodeConfig.PHONE_PREFIX + 
						phoneNo + 
						VerifyCodeConfig.PHONE_SUFFIX;
				String code = GetCode.getCode(6);
				jedis.setex(JedisPhone, 120, code);
				jedis.close();
				System.out.println(code);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/CodeVerify")
	public Object CodeVerify(String phoneNo,String code) {
		
		AJAXResult result = new AJAXResult();
		Jedis jedis = null;
		try {
			jedis = new Jedis("192.168.146.128",6379 );
			String JedisPhone = 
					VerifyCodeConfig.PHONE_PREFIX + 
					phoneNo + 
					VerifyCodeConfig.PHONE_SUFFIX;
			String stringCode = jedis.get(JedisPhone);
			if(code.equals(stringCode)) {
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}finally {
			jedis.close();
		}
		return result;
	}
}
