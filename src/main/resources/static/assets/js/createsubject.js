 const currentUrlsubject = window.location.href;
 const urlsubject = new URL(currentUrlsubject);

const baseUrlsubject = urlsubject.origin;

$("#subjectlevel").on("change",async(e) =>{
	
	let sblevelpass = $("#subjectlevel").val()
	
	
	//let sblevelop =sblevelpass;

	
	let data= e.target.value
	let field = $("#field_create_subject").val()

        if (sblevelpass==1) {
            $("#passsubject").hide();
            $("#subopt").hide();
        } else {
            $("#passsubject").show();
            $("#subopt").show();
        }

	/*alert(field);*/
	await $.get(`${baseUrlsubject}/api/public/list/subject?field=${field}&level=${data} `, 
	function(data,status ){
		//alert("a");
		
		let  stl= data.map(e =>`<option value="${e.id}"> ${e.name}</option>`);		
		let stl1 = stl;
		
		
	let use= 	`<option value="0">select required subject</option>`+stl;
	
	let use1= 	`<option value="0">select required subject</option>`+stl1;
	
	 
		
	let select = $("#select_create_subject");
	let select1 = $("#select_create_subject1");
	select.html(use);
	select.show();
	
	select1.html(use1);
	select1.show();
	}
)
})

/*$("#subjectlevel1").on("change",async(e) =>{
	let data= e.target.value
	let field = $("#field_create_subject1").val()

	
	alert(field);
	await $.get(`${baseUrlsubject}/api/public/list/subject?field=${field}&level=${data} `, 
	function(data,status ){
		let  stl= data.map(e =>`<option value="${e.id}"> ${e.name}</option>`);
		
	let use= 	`<option value="0">select required subject</option>`+stl;
	let select = $("#select_create_subject1");
	select.html(use);
	select.show();
	}
)
})*/





$("form").submit(function() {
    // Get the selected values from both select elements
    let selectedValues1 = $("#select_create_subject").val() || [];
    let selectedValues2 = $("#select_create_subject1").val() || [];

    // Convert the arrays to sets for easier comparison
    let selectedSet1 = new Set(selectedValues1);
    let selectedSet2 = new Set(selectedValues2);

    // Check for intersection between the sets
    let intersection = new Set([...selectedSet1].filter(x => selectedSet2.has(x)));

    // If there is any intersection, it means some options are selected in both selects
    if (intersection.size > 0) {
        alert("Error: Cannot choose Same options are selected in both lists.");
        return false; // Prevent form submission
    }
    // If no intersection, continue with form submission
    return true;
});





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




$("#credit_create_subject").on("change", () => {
    let value = $("#credit_create_subject").val();
    //$("#credit_action_proper").show();
    
    let str = "";

    if ($("#select_type_create_subject").val() == "BOTH" && value ==1) {
		alert("ko dc chon 1");
		$("#credit_action_proper").show();
        str += `<option value="10">Select create for action</option>`;
    } else {
		$("#credit_action_proper").hide();
        str = `<option value="0">0</option>`;
    }

    for (let i = 0; i < value - 1; i++) {
        str += `<option value="${i + 1}">${i + 1}</option>`;
    }

    $("#creditAction_create_subject_new").html(str);
});

$("#select_level_subject").on("change",()=>{
	$("#form_sort_level").submit()
})



/*$("status").on("change",async(e) =>{
	let data =e.target.value
	let level = $("#select_create_subject").val()
	
	await $.get(`${baseUrlsubject}/api/public/list/subject?level=${level}&status=${data} `, 
	
	function(data,status){
		
		let stl = data.map(e => `<option value="${e.id}"> ${e.status}</option>`);
		let use= 	`<option value="0">select required status</option>`+stl;
		let select = $("#select_subject_status");
		select.html(use);
	select.show();
	}
	)
})*/