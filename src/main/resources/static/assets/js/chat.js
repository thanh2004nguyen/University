const radioButtons = document.querySelectorAll(".input_by_radio");

// Adding event listener to each radio button

if(radioButtons !=null){
	radioButtons.forEach(radioButton => {
    radioButton.addEventListener('change', function(e) {
        const selectedValue = e.target.value
          $("#minuteInput").show();
        if (selectedValue === 'minute') {
         $("#span_show_type_edit_minute").show();
          $("#span_show_type_edit_day").hide();
           $("#span_show_type_edit_hour").hide();

        } else if (selectedValue === 'hour') {
       $("#span_show_type_edit_minute").hide();
          $("#span_show_type_edit_day").hide();
           $("#span_show_type_edit_hour").show();
     
        } else if (selectedValue === 'day') {
        $("#span_show_type_edit_minute").hide();
          $("#span_show_type_edit_day").show();
           $("#span_show_type_edit_hour").hide();
      
        }
    });	
});

}

const handleClickDetail=(e,event)=>{
 event.stopPropagation();
	let id= e.getAttribute("data_use");
	if($(`.cover_hdden_${id}`).is(':visible')){
		$(`.cover_hdden_${id}`).hide();
	}else{
		$(".handle_hide").hide();
		$(`.cover_hdden_${id}`).show();
	}


}


$("#messageArea").on("click",()=>{
 
$(".handle_hide").hide();
})

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var chat_access_form = document.querySelector('#chat_access_form');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');  
var connectingElement = document.querySelector('.connecting');
let roomId=document.getElementById("id_room_discuss");
let studentID=roomId.getAttribute("data-id");

var stompClient = null;
var username = null;




const hanldeDelete=(e)=>{
	let id= e.getAttribute("data_use");
    if( stompClient) {
        var chatMessage = {
            sender: username,
            content: id,
            id:studentID,
            room_id:roomId.value,
            type: 'DELETE'
        };
        stompClient.send(`/app/chat.sendMessage/${roomId.value}`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}


if(chat_access_form){
	 connect()
}

function connect() {
    username ="test name"
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        // Connect to the specific room
        stompClient.connect({}, function() {
            onConnected(roomId.value);
        }, onError);
 
}



function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe(`/topic/public/${roomId.value}`, onMessageReceived);

    // Tell your username to the server
    stompClient.send(`/app/chat.addUser/${roomId.value}`,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}




function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            id:studentID,
            room_id:roomId.value,
            type: 'CHAT'
        };
        stompClient.send(`/app/chat.sendMessage/${roomId.value}`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}




function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

if(message.type=="OVER"){

	Toastify({
  text: "This discuss is expried, mesaage can't send",
  duration:2000,
   style: {
    background: "orange",
  },
  offset: {
    x: 200, // horizontal axis - can be a number or a string indicating unity. eg: '2em'
    y: 200 // vertical axis - can be a number or a string indicating unity. eg: '2em'
  },
}).showToast();
}

if(message.type=="JOIN"){
	Toastify({
  text: "new user join to chat",
  duration:2000,
   style: {
    background: "orange",
  },
  offset: {
    x: 200, // horizontal axis - can be a number or a string indicating unity. eg: '2em'
    y: 200 // vertical axis - can be a number or a string indicating unity. eg: '2em'
  },
}).showToast();

}

if(message.type=="DELETE"){
	let tag= "<p> message is recalled</p>";
$(`#handle_mess_${message.id}`).html(tag);
$(`.cover_hdden_${message.id}`).hide();
$(`#icon_option_mess_${message.id}`).hide();
}



if(message.type=="CHAT"){
	 var messageElement = document.createElement('div');
   
    
    if(message.senderId==studentID){
		
		 var currentDate = new Date();
    var formattedDate = currentDate.getDate() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getFullYear();
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    // Add leading zero if needed
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var formattedTime = hours + ':' + minutes;
		
		 messageElement.innerHTML=`
		  <div 
		  class="outgoing_msg">
		 <div  class="d-flex justify-content-end">
            
            <div  class=" sent_mess_discuss " >
                <p  style="float:right; margin-bottom:0; color:white; font-size:14px;"> ${message.text}</p>
                <p class="time_date">${formattedDate} ${formattedTime}  </p>
                
            </div>
             </div>
            </div>`
	}else{
		
    var messageDate = new Date(message.createAt);

    // Format date and time
    var formattedDate = messageDate.getDate() + '-' + (messageDate.getMonth() + 1) + '-' + messageDate.getFullYear();
    var hours = messageDate.getHours();
    var minutes = messageDate.getMinutes();
    // Add leading zero if needed
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var formattedTime = hours + ':' + minutes;
		
	 messageElement.innerHTML=	` <div class="incoming_msg">
           
                <div class="received_msg pl-3">
                    <div class=" mess_recieve_discuss" >
                    
                        <span style="font-size:14px; color:#0000cd; font-weight:bold" class="time_date mb-1"  >${message.senderName}</span>
                        <p class="mb-1" style="color:black; font-size:16px" >${message.text}</p>
                        <span class="time_date mt-0" >${formattedDate} ${formattedTime}</span>
                    </div>
                </div>
            </div>
		`
	}
   
    
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
   
}



messageForm.addEventListener('submit', sendMessage, true)