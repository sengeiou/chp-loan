package com.creditharmony.loan.aops.aspects;

import com.creditharmony.core.thd.AbsThrdSendMsg;
import com.creditharmony.core.thd.pools.ThreadPool01;

public class ThreadPoolManage {
	public static ThreadPool01<AbsThrdSendMsg> thdpMang_MsgSend = null;
}
