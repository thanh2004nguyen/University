// notify in holiday inndex


	
	let createNotifyEdit=(notify,status)=>{
		let cssStatus;
		let iconStatus;
		let titleText;
		if(status=="error"){
			cssStatus='modify_tost_body_error';
			iconStatus="fas fa-times";
			titleText="Error notify"
		}
		
		if(status=="success"){
			cssStatus='modify_tost_body_success';
			iconStatus="fas fa-check"
			titleText="Success notify"
		}
		
$(document).Toasts('create', {
  title:titleText,
autohide:true,
  delay:2500,
  class:cssStatus,
  body: ` <div class="d-flex flex-column align-items-md-center">
       <i class="${iconStatus}" style="font-size:40px; margin-bottom :15px; color:white"></i>
       <p style="font-size:18px; text-align:center ; color:white">${notify}</p>
       </div>`,
})
	}

	if($("#error_index_holiday").val()){
createNotifyEdit($("#error_index_holiday").val())
	}

		if($("#success_index_holiday").val()){
createNotifyEdit($("#success_index_holiday").val())
	}
	
	//notify update status class
	let errorTost= document.querySelectorAll(".show_error_toast");
	let successTost= document.querySelectorAll(".show_success_toast");
	errorTost.forEach(e=>{
					if(e.value){
createNotifyEdit(e.value,"error")
	}
	});

	successTost.forEach(e=>{
					if(e.value){
createNotifyEdit(e.value,"success")
	}
	});

	
//js for notifiation page

$("#select_type_creaet_notify").on("change",()=>{
	let value=$("#select_type_creaet_notify").val();
	if(value== "FIELD"){
		$("#cover_field_create_notify").show();
	}
	if(value== "CLASS"){
	$("#cover_class_create_notify").show();
	$("#cover_field_create_notify").hide();
	}
	
	if(value== "ALL"){
		$("#cover_class_create_notify").hide();
	$("#cover_field_create_notify").hide();
	}
})	
