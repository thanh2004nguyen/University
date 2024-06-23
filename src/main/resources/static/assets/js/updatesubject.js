 const currentUrlupdate = window.location.href;
 const urlupdate = new URL(currentUrlupdate);

const baseUrlupdate = urlupdate.origin;


$("#select_type_create_subject").on("change",()=>{
	let value= $("#select_type_create_subject").val();
	
	if(value=="BOTH"){
		$("#select_credit_proper").show();
	}else if(value==""){
		$("#select_credit_proper").hide();
		$("#credit_action_proper").hide();
	}else if(value="THEORY") {
	$("#select_credit_proper").show();
			$("#credit_action_proper").hide();
	
	}else{
		$("#select_credit_proper").show();
	}
		
})



$("#credit_create_subject").on("change",()=>{
let value=	$("#credit_create_subject").val();

let str=`<option th:value="0">Select Credit for Action</option>	`
for(i=0; i<value-1;i++){
	str+=`<option th:value="${i+1}">${i+1}</option>	`
}

$("#creditAction_create_subject").html(str);
if($("#select_type_create_subject").val()=="BOTH"){
	$("#credit_action_proper").show();
}

})

$("#select_level_subject").on("change",()=>{
	$("#form_sort_level").submit()
})