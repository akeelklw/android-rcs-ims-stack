/*******************************************************************************
 * Software Name : RCS IMS Stack
 * Version : 2.0
 * 
 * Copyright � 2010 France Telecom S.A.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.orangelabs.rcs.core.ims.service.toip;

import com.orangelabs.rcs.core.ims.protocol.rtp.MediaRtpReceiver;
import com.orangelabs.rcs.core.ims.protocol.rtp.MediaRtpSender;
import com.orangelabs.rcs.core.ims.service.ImsService;
import com.orangelabs.rcs.core.ims.service.ImsServiceSession;
import com.orangelabs.rcs.core.media.MediaPlayer;
import com.orangelabs.rcs.core.media.MediaRenderer;

/**
 * ToIP session
 * 
 * @author jexa7410
 */
public abstract class ToIpSession extends ImsServiceSession {
    /**
	 * Media player
	 */
	private MediaPlayer player = null;
	
    /**
	 * Media renderer
	 */
	private MediaRenderer renderer = null;
	
	/**
	 * RTP receiver
	 */
	private MediaRtpReceiver rtpReceiver = null;
	
	/**
	 * RTP sender
	 */
	private MediaRtpSender rtpSender = null;
	
	/**
	 * Constructor
	 * 
	 * @param parent IMS service
	 * @param contact Remote contact
	 */
	public ToIpSession(ImsService parent, String contact) {
		super(parent, contact);
	}

	/**
	 * Get the media player
	 * 
	 * @return Player
	 */
	public MediaPlayer getMediaPlayer() {
		return player;
	}
	
	/**
	 * Set the media player
	 * 
	 * @param player Player
	 */
	public void setMediaPlayer(MediaPlayer player) {
		this.player = player;
	}
	
	/**
	 * Get the media renderer
	 * 
	 * @return Renderer
	 */
	public MediaRenderer getMediaRenderer() {
		return renderer;
	}
	
	/**
	 * Set the media renderer
	 * 
	 * @param renderer Renderer
	 */
	public void setMediaRenderer(MediaRenderer renderer) {
		this.renderer = renderer;
	}	
	
	/**
	 * Get the RTP receiver
	 * 
	 * @return RTP receiver
	 */
	public MediaRtpReceiver getRtpReceiver() {
		return rtpReceiver;
	}

	/**
	 * Set the RTP receiver
	 * 
	 * @param rtpReceiver RTP receiver
	 */
	public void setRtpReceiver(MediaRtpReceiver rtpReceiver) {
		this.rtpReceiver = rtpReceiver;
	}

	/**
	 * Get the RTP sender
	 * 
	 * @return RTP sender
	 */
	public MediaRtpSender getRtpSender() {
		return rtpSender;
	}

	/**
	 * Set the RTP sender
	 * 
	 * @param rtpSender RTP sender
	 */
	public void setRtpSender(MediaRtpSender rtpSender) {
		this.rtpSender = rtpSender;
	}	
	
	/**
	 * Returns the event listener
	 * 
	 * @return Listener
	 */
	public ToIpSessionListener getListener() {
		return (ToIpSessionListener)super.getListener();
	}
}
