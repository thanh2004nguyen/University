let selectRoleCreate = $("#account_create_select_role");

if (selectRoleCreate != null) {
	selectRoleCreate.on("change", function(e) {
		let selectedRole = $(this).val(); // Lấy giá trị của selectRoleCreate khi có sự thay đổi
		$("#hidden_role_data_create").val(selectedRole); // Gán giá trị vào hidden_role_data_create
		console.log("ROLE: " + selectedRole); // In ra giá trị role đã được chọn
		if (selectedRole == "STUDENT") {
			$("#student_form_create_account").show();
			$("#teacher_form_create_account").hide();
			$("#employee_form_create_account").hide();

		} else if (selectedRole == "TEACHER") {
			$("#hidden_role_data_create_teacher").val(selectedRole); // Gán giá trị vào hidden_role_data_create
			$("#teacher_form_create_account").show();
			$("#employee_form_create_account").hide();
			$("#student_form_create_account").hide();
		}
		else if (selectedRole == "EMPLOYEE") {
			$("#hidden_role_data_create_employee").val(selectedRole); // Gán giá trị vào hidden_role_data_create
			$("#employee_form_create_account").show();
			$("#teacher_form_create_account").hide();
			$("#student_form_create_account").hide();
		}
	});
}
$(document).ready(function() {
	$('.select2').select2();
});


$(document).ready(function() {
	$('#fileInput').change(function() {
		var file = this.files[0];

		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
			url: 'http://localhost:8081/admin/user/upload',
			type: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				console.log(response);
				alert('import success.');
				window.location.href = '/admin/user/list';
			},
			error: function(xhr, status, error) {
				console.error(error);
				alert('import success.');
			}
		});
	});
});




/*ajax send excel save in database*/
function importUsers() {
	var fileInput = document.getElementById('file');
	var file = fileInput.files[0];
	var formData = new FormData();
	formData.append('file', file);

	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/import', true);
	xhr.onload = function() {
		if (xhr.status === 200) {
			alert('Users imported successfully.');
		} else {
			alert('Failed to import users. Please try again.');
		}
	};
	xhr.send(formData);
}











$('#hoc-ky').on('change', function() {
	let selectedSemesterId = $(this).val()
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8081/api/class/listClass?semester=' + encodeURIComponent(selectedSemesterId) + '&field=4',
		data: { semesterId: selectedSemesterId },
		success: function(result) {
			let a = result.map(e => `<option value="${e.id}">${e.name}${e.subject.field.id}
	</option>`).join();
			$("#class_select_list").html(a)
		},
		error: function(xhr, status, error) {
		}
	});
});


$('#hoc-ky').on('change', function() {
	var selectedHocKy = $('#hoc-ky').val();
	if (selectedHocKy !== '') {
		$('#lop-hoc').show();
	} else {
		$('#lop-hoc').hide();
		$('#mon-hoc').hide();
	}
});

$('#lop-hoc').on('change', function() {
	var selectedLopHoc = $(this).val();
	if (selectedLopHoc !== '') {
		$('#mon-hoc').show();
	} else {
		$('#mon-hoc').hide();
	}
});












//show danh sách class
$(document).ready(function() {
	$.ajax({
		url: "http://localhost:8081/api/public/class/current",
		type: "GET",
		dataType: "json",
		success: function(response) {
			$.each(response, function(index, item) {
				// Append each class name to a select dropdown
				$('#classDropdown').append($('<option>', {
					value: item.id,
					text: item.name

				}));
			});
		},
		error: function(xhr, status, error) {
			console.error("Error fetching current classes:", error);
		}
	});
});


//show danh sách môn học 
function loadSubjects(subjects) {
	$('#subjectDropdown').empty();
	// Thêm option mặc định cho dropdown chọn môn học
	$('#subjectDropdown').append($('<option>', {
		value: '',
		text: 'Chọn Môn học'
	}));
	// Thêm danh sách môn học vào dropdown
	$.each(subjects, function(index, item) {
		$('#subjectDropdown').append($('<option>', {
			value: item.subject.id,
			text: item.subject.name
		})); console.log(item.subject.name);
	});

}


$('#classDropdown').change(function() {
	$('#subjectDropdown').hide();
	var classId = $(this).val();

	// Gửi yêu cầu AJAX để lấy danh sách các môn học tương ứng với lớp đã chọn
	$.ajax({
		url: "http://localhost:8081/api/public/class/classes/" + classId + "/subjects",
		type: "GET",
		dataType: "json",
		success: function(response) {
			loadSubjects(response);
				
				$.ajax({
					url: "http://localhost:8081/api/public/class/" + classId  + "/students",
					type: "GET",
					data: { classId: classId},
					success: function(response) {
						console.log(response);
					},
					error: function(xhr, status, error) {
						console.error("Error fetching students:", error);
					}
				});
			
		},
		error: function(xhr, status, error) {
		}
	});
});


$("#submitExport").click(function() {
	$("#exportForm").submit();
});

let checkError = $("#check_error_create_account");

if (checkError) {
	let currentRole = $("#account_create_select_role").val();
	if (currentRole == "STUDENT") {
		$("#student_form_create_account").show();
	}
	if (currentRole == "TEACHER") {
		$("#teacher_form_create_account").show();
	}
	if (currentRole == "EMPLOYEE") {
		$("#employee_form_create_account").show();
	}
}
