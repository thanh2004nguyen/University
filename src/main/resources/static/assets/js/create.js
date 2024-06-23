

let select_subject = document.querySelector(".select_subject_create");
 const currentUrl = window.location.href;
 const url = new URL(currentUrl);
 oneForm=$("#form_create_one");
const baseUrl = url.origin;
 let weekday;
 let start;
 let dstart;
 let dend;
 let subject;
 let slotday=[1,2,3,4,5,6,7,8,9,10,11,12]
 let credit;
 let creditAction;
 let result=[]
 let capacity;
 let startDay=document.getElementById("semester_start_create").value;
 let dendTheory;
 let dendAction;
 	
 
 
 
if($("#error_create_class_hidden")){
	if($("#error_create_class_hidden").val()==1){
		$(".hiddne_div_create").show()
		$(".handle_hidden_error").show()
	}
	
}





 
 if($(".select_subject_create").val()!= 0){
	 credit= $("#Credit_selected_when_error").val();
	  let selectedOption = select_subject.options[select_subject.selectedIndex];
	   let checkType = selectedOption.getAttribute("data-check");
	    if(checkType=="both"){
	  oneForm.hide()
	   $("#form_create_both").show();
   }else{   
	    $("#form_create_both").hide
	    oneForm.show();
	    $("#select_enddate_create").parent().show();
	    

	}	
	 $("#select_endslot_createss").parent().show();   
	let slectec_startSlot=  $("#select_startslot_create").attr("data-check")
	 
	 	slotday.forEach(x=>{
			 if($("#select_classtype_create").val()=="all"){
				 	if(x<6 && x+Number(credit)<=6 || x>6 && x<12&& x+Number(credit)<=12){
				result.push(x)
			}
			 }else{
				
				  	if(x<6 && x+Number(credit)*2 -1<=6 || x>6 && x<12&& (x+Number(credit)*2-1)<=12){
				result.push(x)
			 }
		}
		})
		let data= result.map(e=>`<option value="${e}" ${slectec_startSlot==e? "selected" :""}>${e}</option>`).join()
		    $("#select_startslot_create").empty();
		$("#select_startslot_create").html(`<option value="0"> select slot</option> ${data} `)
	  
   }
 
 
 

 
 let smId=$("#semester_start_create").attr("data-id"); 
 $("#semester_value_create").val(smId);
 // handle after select subject
 
select_subject.addEventListener('change', function(e) {
  let selectedOption = select_subject.options[select_subject.selectedIndex];
  let selectedId = selectedOption.getAttribute("data-id");
  let checkType = selectedOption.getAttribute("data-check");
 credit=selectedOption.getAttribute("data-credit");
 creditAction= selectedOption.getAttribute("data-creditac");
 
if (credit > 2) {
    $(".option_of_select_type").each(function() {
        if ($(this).val() === "fhalf" || $(this).val() === "lhalf") {
            $(this).hide();
        }
    });
}else{
	    $(".option_of_select_type").each(function() {
        if ($(this).val() === "fhalf" || $(this).val() === "lhalf") {
            $(this).show();
        }
    });
}
 
  $("#subject_value_create").val(e.target.value)
 $("#subject_hidden_creaet_both").val(e.target.value)
   if(checkType=="both"){
	   $("#form_create_both").hide()
	   $("#form_create_one").hide()
	   $("#form_create_both").show();
   }else{  
	     $("#form_create_both").hide()
	    $("#form_create_one").show();
	
   }
  $("#subject_hidden_creaet").val(selectedId);
  subject=selectedId
  
  //shwo capacity both
   $("#input_capacity_create_both").parent().parent().show();
   
   //show capacity one
    $("#input_capacity_create").parent().parent().show()
    

 
//set credit for start slot
 $("#select_startslot_create").attr("data-id",credit)
  


  
});

//handle onchange capacity
$("#input_capacity_create").on('change',(e)=>{
	capacity=e.target.value 
	$("#select_classtype_create").parent().parent().show();
})
$("#input_capacity_create_both").on('change',(e)=>{
	
	$("#select_classtype_create_theory").parent().parent().show();
	capacity=e.target.value 


})

//handle after select class type 
$("#select_classtype_create").on("change",()=>{
		
	let date =new Date(startDay);
	console.log(date)

    $("#select_startdate_create").parent().parent().show();
    
    if($("#select_classtype_create").val()=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	
    let next6day = new Date(date);
    next6day.setDate(next6day.getDate() + 6);

const options = { year: 'numeric', month: '2-digit', day: '2-digit', timeZone: 'Asia/Bangkok' };
const min = new Intl.DateTimeFormat('en-CA', options).format(date).replace(/\//g, '-');
console.log(min


)
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create").attr("min",min)
    $("#select_startdate_create").attr("max",max)
result=[]
      // loai bo nhung tiet khong duoc lua chon
		slotday.forEach(x=>{
			 if($("#select_classtype_create").val()=="all"){
				 	if(x<6 && x+Number(credit)<=6 || x>6 && x<12&& x+Number(credit)<=12){
				result.push(x)
			}
			 }else{
				
				  	if(x<6 && x+Number(credit)*2 -1<=6 || x>6 && x<12&& (x+Number(credit)*2-1)<=12){
				result.push(x)
			 }
		}
		})
		let data= result.map(e=>`<option value="${e}">${e}</option>`).join()
		    $("#select_startslot_create").empty();
		$("#select_startslot_create").html(`<option value="0"> select slot</option> ${data} `)
  
  
});


//handle after select start date
 $("#select_startdate_create").on("change",(e)=>{	
		$("#select_startslot_create").parent().show();
		dstart=e.target.value;
		let date = new Date(dstart);
		weekday=date.getDay();
		$("#hidden_weekday_create").val(weekday);
		$("#select_enddate_create").parent().show();
		
	

		let type=$("#select_classtype_create").val();
		let getURL=`${baseUrl}/api/public/class/dateend?start=${dstart}&type=${type}`;
	  $.get(getURL, (res,status)=>{
		 let dayData = new Date(res);
		  dend = dayData.toISOString().split('T')[0];
     
		$("#select_enddate_create").val(dend);
		
	  });
	

		$("#select_startslot_create").parent().parent().show();
			
			if(weekday!=null){
			getAvailableRoom(capacity,weekday,start,dstart,dend)
		}
	});
	
	
       
//handle after select start slot
$("#select_startslot_create").on("change", () => {
    let selectedStartSlot = $("#select_startslot_create").val();
      
    start=selectedStartSlot;
    
    if($("#select_classtype_create").val()=="all"){
		 $("#select_endslot_createss").val( Number(selectedStartSlot)+ Number(credit) );
	}else{
		$("#select_endslot_createss").val( Number(selectedStartSlot)+Number(credit)*2 -1 );
	}
	
	 $("#select_endslot_createss").parent().show();
	 $("#select_room_create").parent().parent().show();
	 getAvailableRoom(capacity,weekday,start,dstart,dend) 
});

//handle after slect room
$("#select_room_create").on("change", () => {
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	$("#select_teacher_create").parent().parent().show();
});




//set semesterid for all 

$("#semester_value_create_both").val(smId)

//handle after select type for theory
let typeTheo
$("#select_classtype_create_theory").on("change",e=>{
	if($("#select_classtype_create_action").val()!=""){
		$("#select_startdate_create_theory").parent().parent().show();
	}
	
	let date =new Date(startDay)
	typeTheo=e.target.value;
	 if(e.target.value=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	 let next6day = new Date(date);
	 next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create_theory").attr("min",min)
    $("#select_startdate_create_theory").attr("max",max)
})

//handle after select type for action

let typeAction
$("#select_classtype_create_action").on("change",e=>{
	if($("#select_classtype_create_theory").val()!=""){
			$("#select_startdate_create_action").parent().parent().show();
	}

	let date =new Date(startDay)
	typeAction=e.target.value;
	 if(e.target.value=="lhalf"){
		date=newData = new Date(date.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
	}
	 let next6day = new Date(date);
	 next6day.setDate(next6day.getDate() + 5);
    let min = date.toISOString().split('T')[0];
    let max = next6day.toISOString().split('T')[0];
    $("#select_startdate_create_action").attr("min",min)
    $("#select_startdate_create_action").attr("max",max)
})



//dandle after select start day for theory
$("#select_startdate_create_theory").on("change",e=>{
	
   	let date = new Date(e.target.value);
		wdt=date.getDay()+1;
	$("#hidden_weekday_create_theory").val(wdt);
   
   
   let getURL=`${baseUrl}/api/public/class/dateend?start=${e.target.value}&type=${typeTheo}`;
	  $.get(getURL, (res,status)=>{
		 let dayData = new Date(res);
		  dend = dayData.toISOString().split('T')[0];
    
		$("#select_enddate_create_theory").val(dend);
		$("#select_enddate_create_theory").parent().show()
	  });
   
		let slotArray=[];
		
			
		slotday.forEach(x=>{
			 if($("#select_classtype_create_theory").val()=="all"){
				 	if(x<6 && x+Number(credit)<=6 || x>6 && x<12&& x+Number(credit)<=12){
				slotArray.push(x)
			}
			 }else{
				  	if(x<6 && (x+Number(credit)-Number(creditAction)-1)*2<=6 || x>6 && x<12&& (x+(Number(credit)-Number(creditAction))*2-1)<=12){
				slotArray.push(x)
			 }
		}
		})	
let data= slotArray.map(e=>`<option value="${e}">${e}</option>`).join()
		$("#select_startslot_create_theory").html(`<option value="0"> select slot for theory</option> ${data}`)
		if($("#select_startdate_create_action").val()!=""){
					$("#select_startslot_create_theory").parent().parent().show();
		}

})

//dandle after slect start day for action
$("#select_startdate_create_action").on("change",e=>{
  let getURL=`${baseUrl}/api/public/class/dateend?start=${e.target.value}&type=${typeAction}`;
	  $.get(getURL, (res,status)=>{
		 let dayData = new Date(res);
		  dend = dayData.toISOString().split('T')[0];
     
		$("#select_enddate_create_action").val(dend);
		$("#select_enddate_create_action").parent().show()
		
	  });
	let date = new Date(e.target.value);
		wdt=date.getDay()+1;
	$("#hidden_weekday_create_action").val(wdt);
	
		
				let slotArray=[];
				
		slotday.forEach(x=>{
			 if($("#select_classtype_create_action").val()=="all"){
				 	if(x<6 && x+Number(credit)<=6 || x>6 && x<12&& x+Number(credit)<=12){
				slotArray.push(x)
			}
			 }else{
				
				  	if(x<6 && (x+Number(creditAction)-1)*2<=6 || x>6 && x<12&& (x+Number(creditAction)*2-1)<=12){
				slotArray.push(x)
			 }
		}
		})	
				
let data= slotArray.map(e=>`<option value="${e}">${e}</option>`).join()
		$("#select_startslot_create_action").html(`<option value="0"> select slot for action</option> ${data}`)
		console.log($("#select_startdate_create_theory").val())
		if($("#select_startdate_create_theory").val()!=""){
			$("#select_startslot_create_action").parent().parent().show();
		}
		
})


//handle after select startslot for theory
$("#select_startslot_create_theory").on("change",e=>{
	
	  if($("#select_classtype_create_theory").val()=="all"){
		 $("#select_endslot_create_theory").val(Number(e.target.value)+Number(credit) );
	}else{
		$("#select_endslot_create_theory").val((Number(e.target.value)+(Number(credit)-Number(creditAction))-1)*2 );
	}
	

	if($("#select_startslot_create_action").val!=0){
		 $("#select_endslot_create_theory").parent().show();
	 $("#select_room_create_theory").parent().parent().show();
	}
	
	
	
	 getAvailableRoomTheory(capacity,$("#hidden_weekday_create_theory").val(),e.target.value,$("#select_startdate_create_theory").val(),$("#select_enddate_create_theory").val(),"theory")
	
})

//handle after select startslot for action
$("#select_startslot_create_action").on("change",e=>{
	$("#select_endslot_create_action").val(Number(e.target.value)+Number(creditAction) );
	
	  if($("#select_classtype_create_action").val()=="all"){
		 $("#select_endslot_create_action").val(Number(e.target.value)+Number(credit) );
	}else{
		$("#select_endslot_create_action").val((Number(e.target.value)+Number(creditAction)-1)*2 );
	}
	
	
	if($("#select_startslot_create_theory").val!=0){
		 $("#select_endslot_create_action").parent().show();
	 $("#select_room_create_action").parent().parent().show();
	}
	
	 getAvailableRoomTheory(capacity,$("#hidden_weekday_create_action").val(),e.target.value,$("#select_startdate_create_action").val(),$("#select_enddate_create_action").val(),"action")
	
})

//handle after select room theory
$("#select_room_create_theory").on("change",e=>{
	
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	 getAvailableTeacherTheory(subject,$("#hidden_weekday_create_theory").val(),$("#select_startslot_create_theory").val(),$("#select_startdate_create_theory").val(),$("#select_enddate_create_theory").val(),"theory")
	 if($("#select_room_create_action").val()!=0){
		 $("#select_teacher_create_theory").parent().parent().show();
	 }
	
})

//handle after select room action
$("#select_room_create_action").on("change",e=>{
	
	 getAvailableTeacher(subject,weekday,start,dstart,dend)
	 getAvailableTeacherTheory(subject,$("#hidden_weekday_create_action").val(),$("#select_startslot_create_action").val(),$("#select_startdate_create_action").val(),$("#select_enddate_create_action").val(),"action")
	 if($("#select_room_create_theory").val()!=0){
	$("#select_teacher_create_theory").parent().parent().show();
	}
})

//suport function
let getAvailableTeacher= async (subject,weekday,start,dstart,dend )=>{
	await $.get(`${baseUrl}/api/public/teacher/available?id=${subject}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		$("#select_teacher_create").html(`<option value="0"> select teacher</option> ${str}`);
  });
}
		
let getAvailableRoom= async (capacity,weekday,start,dstart,dend )=>{
	await $.get(`${baseUrl}/api/public/room/available?capacity=${capacity}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		$("#select_room_create").html(`<option value="0"> Select room</option> ${str}`);
  });
}

let getAvailableRoomTheory= async (capacity,weekday,start,dstart,dend,type )=>{
	await $.get(`${baseUrl}/api/public/room/available?capacity=${capacity}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		if(type="theory"){
			$("#select_room_create_theory").html(`<option value="0"> Select room for theory</option> ${str}`);
		}
		
		if(type="action"){
			$("#select_room_create_action").html(`<option value="0"> Select room for action</option> ${str}`);
		}
		
  });
}

let getAvailableTeacherTheory= async (subject,weekday,start,dstart,dend,type )=>{
	await $.get(`${baseUrl}/api/public/teacher/available?id=${subject}&weekday=${weekday}&start=${start}&dstart=${dstart}&dend=${dend}`, 
	function(data,status ){
		let str= data.map(e=> `<option value="${e.id}"> ${e.name}</option>`).join();
		if(type="theory"){
			$("#select_teacher_create_theory").html(`<option value="0"> select teacher for theory</option> ${str}`);
		}
		
		if(type="action"){
			$("#select_teacher_create_action").html(`<option value="0"> select teacher for action</option> ${str}`);
		}
		
  });
}


let setDay= (data,check)=>{
	if(check=="all"){
		return futureDate = new Date(data.getTime() + (15 * 7 * 24 * 60 * 60 * 1000));  
	}
	 if(check=="lhalf"){
		
		newData = new Date(data.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));	
		return futureDate = new Date(newData.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));  
	}
	 if(check=="fhalf"){
		return futureDate = new Date(data.getTime() + (8 * 7 * 24 * 60 * 60 * 1000));  
	}
}