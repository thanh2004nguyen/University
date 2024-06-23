//handle onchange capacity

$("#input_capacity_edit_class").on('change',()=>{
handleAvailableRoom();
	
})


let handleAvailableRoom=()=>{
	let	capacity=$("#input_capacity_edit_class").val();
let weekday = $("#hidden_weekday_create").val();
let start= $("#select_startslot_create").val();
let dstart=$("#select_startdate_create").val();
let dend=$("#select_enddate_create").val();
	getAvailableRoomEdit(capacity,weekday,start,dstart,dend);
}

let getAvailableRoomEdit= async (capacity,weekday,start,dstart,dend )=>{
	await $.get(`${baseUrl}/api/public/room/available?capacity=${capacity}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
	 $(".room_edit_basic").each(function() {
		
            $(this).hide();
        });
        let currentCapacity=Number($(".current_room_edit").attr("data-id"));
        let currentRoomId=$(".current_room_edit").val();
        
     
        if( Number(capacity)> currentCapacity ){
			$("#select_room_nnull_edit").attr('selected','selected');
			$(".current_room_edit").hide();
		}else{
			$("#select_room_nnull_edit").attr('selected','');
				$(".current_room_edit").show();
			$(".current_room_edit").attr('selected','selected');
		
			
		}
		let str= data.map(e=> `<option value="${e.id}" style="display:${e.id==currentRoomId ?'none' :''}"  class="room_edit_basic"> ${e.name}</option>`).join();
		$("#select_room_create").append(`${str}`);
  });
}


let handleAvailableTeacher=()=>{
	let subject= $("#input_capacity_create").val();
	let weekday = $("#hidden_weekday_create").val();
let start= $("#select_startslot_create").val();
let dstart=$("#select_startdate_create").val();
let dend=$("#select_enddate_create").val();
	getAvailableTeacherEdit(subject,weekday,start,dstart,dend )
}

let getAvailableTeacherEdit= async (subject,weekday,start,dstart,dend )=>{
	await $.get(`${baseUrl}/api/public/teacher/available?id=${subject}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		$("#select_teacher_create").html(`<option value="0"> select teacher</option> ${str}`);
  });
}

$("#select_classtype_create").on("change",()=>{
	$("#select_startdate_create").val("");
	$("#select_enddate_create").val("");
 let startDay=document.getElementById("semester_start_create").value;	
		let date =new Date(startDay);

    
    if($("#select_classtype_create").val()=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	
    let next6day = new Date(date);
    next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create").attr("min",min)
    $("#select_startdate_create").attr("max",max)
    
})


//handle on change start slor
$("#select_startslot_create").on("change",(e)=>{
	let startSlot=e.target.value
	
	    let credit = $("#input_subject_value").attr("data-credit"); 
    if($("#select_classtype_create").val()=="all"){
		 $("#select_endslot_edit").val(Number(startSlot)+Number(credit) );
	}else{
		$("#select_endslot_edit").val((Number(startSlot)+Number(credit)-1)*2 );
	}
	handleAvailableTeacher();
		handleAvailableRoom();
})

//handle onchange start day
$("#select_startdate_create").on("change",()=>{
	let type=$("#select_classtype_create").val();
	let dstart=$("#select_startdate_create").val();
	console.log(type,dstart)
		let getURL=`${baseUrl}/api/public/class/dateend?start=${dstart}&type=${type}`;
	  $.get(getURL, (res,status)=>{
		 let dayData = new Date(res);
		  dend = dayData.toISOString().split('T')[0];
     
		$("#select_enddate_create").val(dend);
		handleAvailableTeacher();
		handleAvailableRoom();
  
	
})
})

