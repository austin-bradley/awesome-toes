package com.example.awesome_toe;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NetworkClientHandler extends ChannelInboundHandlerAdapter {
	
	private OnDataPass datapasser;
	
	 public NetworkClientHandler(OnDataPass m_handler) {
		datapasser = m_handler;		
	}
	@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		 System.out.println("ABDEBUG: in clienthandler channelRead!");
		 
	        UpdatePacket m = (UpdatePacket) msg; // (1)
	        try {
	            long currentTimeMillis = (m.value())/* - 2208988800L) * 1000L*/;
	            System.out.println("ABDEBUG: Updated gameState from network handler with - " + new Date(currentTimeMillis));
	            datapasser.updateGameState((int)currentTimeMillis);
	            ctx.close();
	        } finally {
	        }
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }
}
