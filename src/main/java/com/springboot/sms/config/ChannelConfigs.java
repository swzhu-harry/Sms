package com.springboot.sms.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.springboot.sms.common.EnumManage.ChanelStatusEnum;
import com.springboot.sms.common.EnumManage.ChannelEnum;

import lombok.Data;

/**
 * @author zhushiwu
 */
@Data
@ConfigurationProperties(prefix="sms")
@Configuration
public class ChannelConfigs {	
	private List<Map<String,Object>> channels;
	private List<ChannelInfo> channelInfos;
	
	public List<ChannelInfo> getChannelInfos(){
		quickSort(true,0,channels.size()-1);
		return converToChannelInfos();
	} 

	@Data
	public class ChannelInfo{
		private ChannelEnum channelName;
		private Integer priority;
		private ChanelStatusEnum status;
		private String sendUrl;
		private String userName;
		private String password;
		
		public ChannelInfo(){}
		public ChannelInfo(Map<String,Object> map){
			this.userName = map.get("userName")==null?null:map.get("userName").toString();
			this.password = map.get("password")==null?null:map.get("password").toString();
			this.priority = map.get("priority")==null?1:Integer.valueOf(map.get("priority").toString());
			this.sendUrl = map.get("sendUrl")==null?null:map.get("sendUrl").toString();
			this.channelName = map.get("channelName")==null?ChannelEnum.COMMON:ChannelEnum.getChannelEnum(map.get("channelName"));
			this.status = map.get("status")==null?ChanelStatusEnum.OPRNED:ChanelStatusEnum.getChanelStatusEnum(map.get("status"));
		}
	}
	
	private List<ChannelInfo> converToChannelInfos() {
		if(channelInfos==null){
			channelInfos = new ArrayList<ChannelInfo>();
			for (Map<String,Object> map : channels) { 
				ChannelInfo e =  new ChannelInfo(map);
				if(e.getStatus()==ChanelStatusEnum.OPRNED)
					channelInfos.add(e);
			}
		}
		return channelInfos;
	}

	private void quickSort(boolean flag, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;
		if (lo >= hi)
			return;
		while (lo != hi) {
			int priority_lo = channels.get(lo).get("priority") == null ? 0
					: Integer.valueOf(channels.get(lo).get("priority").toString());
			int priority_hi = channels.get(hi).get("priority") == null ? 0
					: Integer.valueOf(channels.get(hi).get("priority").toString());
			if (flag && priority_lo > priority_hi) {
				// 交换
				Map<String, Object> temp = channels.get(lo);
				channels.set(lo, channels.get(hi));
				channels.set(hi, temp);
			}
			if (!flag && priority_lo < priority_hi) {
				// 交换
				Map<String, Object> temp = channels.get(lo);
				channels.set(lo, channels.get(hi));
				channels.set(hi, temp);
			}
			// 将指针向前或者向后移动
			if (flag)
				lo++;
			else
				hi--;
		}

		// 将数组分开两半，确定每个数字的正确位置
		lo--;
		hi++;
		quickSort(flag, lo0, lo);
		quickSort(flag, hi, hi0);
	}
	
	/*public static void main(String[] args) {
		ChannelConfigs ts = new ChannelConfigs();
		ts.channels = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<>();
		map.put("priority", 6);
		map.put("userName", "n6");
		ts.channels.add(map);
		
		map = new HashMap<>();
		map.put("priority", 5);
		map.put("userName", "n5");
		ts.channels.add(map);
		
		map = new HashMap<>();
		map.put("priority", 3);
		map.put("userName", "n5");
		map.put("status", "2");
		ts.channels.add(map);
		
		map = new HashMap<>();
		map.put("priority", 1);
		map.put("userName", "n1");
		ts.channels.add(map);
		
		map = new HashMap<>();
		map.put("priority", 8);
		map.put("userName", "n8");
		ts.channels.add(map);
		System.out.println(ts);
		System.out.println("-------------------");
		Gson gson = new GsonBuilder().create();
		long ls = System.currentTimeMillis();
		System.out.println(gson.toJson(ts.getChannelInfos())+"; tm="+(System.currentTimeMillis()-ls));
		
	}*/
}
