package com.aptech.group3.Dto;





import java.util.Date;

import com.aptech.group3.entity.DiscussRoom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageReturnDto {

	  private MessageType type;
	  private Long id;
		private String text;		
		private Date createAt;		
		private Long senderId;
		private String senderName;
		private Long discussRoomId;
}
