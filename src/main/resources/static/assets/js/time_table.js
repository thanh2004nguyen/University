let form= $("#form_search_time_table");

const currentUrlTime = window.location.href;
const urlTimeTable = new URL(currentUrlTime);
const baseUrlTimeTable = urlTimeTable.origin;

$("#select_week_search_time_table").on("change",()=>{
	console.log(1)
	form.submit();
});

$("#slect_semester_time_table").on("change",()=>{
		form.submit();
});


//handel button  rime table
if($("#slect_semester_time_table")){
 let currentSemester= $("#slect_semester_time_table").val();
 let list= document.querySelectorAll(".check_week_time_table");
 let currentWeek=$("#select_week_search_time_table").val();
$("#first_week_schedule").attr("href",`${baseUrlTimeTable}/web/time_table/show?semester=${currentSemester}&week=1`)
if(currentWeek!=1){
$("#previous_week_schedule").attr("href",`${baseUrlTimeTable}/web/time_table/show?semester=${currentSemester}&week=${currentWeek-1}`)
}
if(currentWeek<list.length){
	$("#next_week_schedule").attr("href",`${baseUrlTimeTable}/web/time_table/show?semester=${currentSemester}&week=${Number(currentWeek)+1}`)
}
$("#last_week_schedule").attr("href",`${baseUrlTimeTable}/web/time_table/show?semester=${currentSemester}&week=${list.length}`)

}


//js for attendance

let submitFormAttendance=(e)=>{
	let studentId=e.parentElement.parentElement.getAttribute("data_id");
	let status=e.value;
	let classId= e.parentElement.parentElement.parentElement.getAttribute("data_id");
	
let data={studentId,status};
$.post({
    url: `${baseUrlTimeTable}/api/attendance/create/${classId}`,
    data: JSON.stringify(data),
    contentType: "application/json",
    success: () => {
        console.log("success");
    }
});

}

//change radio hiliday
$(".radio_check_type_holiday").on("change",(e)=>{
	console.log(e.target.value)
	 if(e.target.value=="one"){
		 
		 $("#input_start_holiday_create").show();
		 $("#input_end_holiday_create").hide();
		  $("#input_date_end_create_holiday").val($("#input_date_start_create_holiday").val());
	 }else{

		 $("#input_start_holiday_create").show();
		 $("#input_end_holiday_create").show();
	 }
});



 $('.radio_check_type_holiday').each(function() {
        if ($(this).is(':checked')) {
      if($(this).val()=="one"){
		  $("#input_date_end_create_holiday").val(" ");
		  	 $("#input_start_holiday_create").show();
	  }else{
		   $("#input_start_holiday_create").show();
		 $("#input_end_holiday_create").show();
	  }
        }
        })






//show notify error attenadance
let errorHidden=document.getElementById("hidden_error_attend");
	if(errorHidden){
		console.log("thanh nien moi 1 ")
			Toastify({
  text: errorHidden.value,
  duration:2000,
   style: {
    background: "orange",
  },
  offset: {
    x: 200, 
    y: 100 
  },
}).showToast();
	}
	
//seacrh for attendance
let searchForm= $("#form_search_attendance");
$("#select_attendace_show_form").on("change",()=>{

searchForm.submit();
})

// check all  stuend attend 	

let list=document.querySelectorAll(".to_check_attend_all");
$("#check_all_attend_class").on("click",()=>{
	let status="attend"
	
list.forEach(e=>{
	e.checked=true
	let classId= e.parentElement.parentElement.parentElement.getAttribute("data_id");
	let studentId=e.parentElement.parentElement.getAttribute("data_id");
	let data={studentId,status};
	
$.post({
    url: `${baseUrlTimeTable}/api/attendance/create/${classId}`,
    data: JSON.stringify(data),
    contentType: "application/json",
    success: () => {
        console.log("success");
    }
});

});
});

// handle for day create holiday
 const todayHoliday = new Date().toISOString().split('T')[0];
    
    let startInputHoliday= $("#input_date_start_create_holiday");
if(startInputHoliday){
	/*startInputHoliday.attr("min",todayHoliday)*/
}
   
    
$("#input_date_start_create_holiday").on("change",(e)=>{
	
	 $('.radio_check_type_holiday').each(function() {
        if ($(this).is(':checked')) {
      if($(this).val()=="one"){
		$("#input_date_end_create_holiday").val($("#input_date_start_create_holiday").val())
        }
        }
        })
	
	
	
	$("#input_date_end_create_holiday").attr("min",e.target.value);
	$("#input_date_end_create_holiday").prop("disabled",false);
	
});

	