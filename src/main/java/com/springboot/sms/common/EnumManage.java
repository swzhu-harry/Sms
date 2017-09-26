package com.springboot.sms.common;

import com.springboot.sms.channel.imp.CommonChannel;
import com.springboot.sms.channel.imp.WangJianChannel;
import com.springboot.sms.channel.imp.XaunWuChannel;
/**
 * 枚举管理器
 * @author zhushiwu
 */
public class EnumManage {

	/**
	 * 渠道
	 */
	public enum ChannelEnum
	{
	    XUAN_WU("xuanwu",XaunWuChannel.class),
	    WANG_JIAN("wangjian",WangJianChannel.class),
	    COMMON("common",CommonChannel.class),
	    UNKNOW(null,null);
	    private final String channelName;
	    private Class<?> serviceClass;
	    ChannelEnum(String channelName,Class<?> serviceClass) {
	        this.channelName = channelName;
	        this.serviceClass = serviceClass;
	    }
	    public Class<?> serviceClass(){
			return this.serviceClass;
		}
	    public static ChannelEnum getChannelEnum(Object channelName)
		{
			if(!StringUtils.isEmpty4Obj(channelName)){
				ChannelEnum[] itms = values();
				for (ChannelEnum itm : itms) {
					if(channelName.toString().trim().equalsIgnoreCase(itm.channelName)){
						return itm;
					}
				}
			}
			return UNKNOW;
		}
	}
	
	/**
	 * 短信发送状态
	 */
	public enum SendStatusEnum
	{
	    SUCCESS("success"),
	    FAILURE("failure"),
	    ;
	    private final String stateCode;
	    SendStatusEnum(String stateCode) {
	        this.stateCode = stateCode;
	    }
	    public String getStateCode()
	    {
	        return stateCode;
	    }
	}
	
	/**
	 * 渠道状态
	 */
	public enum ChanelStatusEnum
	{
	    OPRNED(1, "opened"),
	    CLOSED(2, "closed"),
	    ;
	    private final int stateCode;
	    private final String desc;
	    ChanelStatusEnum(int stateCode, String desc) {
	        this.stateCode = stateCode;
	        this.desc = desc;
	    }
	    public int getStateCode()
	    {
	        return stateCode;
	    }
	    public String getDesc()
	    {
	        return desc;
	    }
	    public static ChanelStatusEnum getChanelStatusEnum(Object status)
		{
			if(!StringUtils.isEmpty4Obj(status)){
				ChanelStatusEnum[] itms = values();
				for (ChanelStatusEnum itm : itms) {
					if(Integer.valueOf(status.toString()) == itm.stateCode){
						return itm;
					}
				}
			}
			return OPRNED;
		}
	}
}
