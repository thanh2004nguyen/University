$(document).ready(function() {
 	$('.edit-input').each(function() {
        if ($(this).val().trim() == '') {
            $(this).prop('readonly', true);
        }
    });
    function getParameterByName(name, url) {
                if (!url) url = window.location.href;
                name = name.replace(/[\[\]]/g, '\\$&');
                var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                    results = regex.exec(url);
                if (!results) return null;
                if (!results[2]) return '';
                return decodeURIComponent(results[2].replace(/\+/g, ' '));
            }

            // Get the notification parameter from the URL
            var notification = getParameterByName('notification');
            console.log(notification)
            if(notification == "Class has no students yet!"){
                showToast("Class has no students yet!", "Export", "warning");
            }
            if(notification == "Class has no student mark yet!"){
                showToast("Class has no student mark yet!", "Export", "warning");
            }
            if(notification == "No lesson"){
                showToast("No current lesson available.", "Attendance", "warning");
            }
	//edit mark for class
	$(".mark-input").change(function() {
        var studentId = $(this).closest("tr").find(".student-id").text();
        var markType = $(this).data("mark-type");
        var updatedMark = $(this).val();

        console.log("studentId: ", studentId);
        console.log("markType: ", markType);
        console.log("updatedMark: ", updatedMark);

        $.ajax({
            url: '/admin/mark/updateStudentMark',
            type: 'POST',
            data: {
                studentId: studentId,
                markType: markType,
                updatedMark: updatedMark
            },
            success: function(response) {
                showToast('Success', 'Mark updated successfully', 'success');
            },
            error: function(xhr, status, error) {
                showToast('Error', 'Failed to update mark', 'error');
            }
        });
    });

    function showToast(title, message, type) {
        var toast = $("#toastTemplate").clone().removeAttr("id");
        toast.find(".toast-title").text(title);
        toast.find(".toast-body").text(message);
        toast.addClass("show");
        if (type === 'success') {
            toast.addClass("bg-success text-white");
        } else if (type === 'error') {
            toast.addClass("bg-danger text-white");
        }
        $("#toastContainer").append(toast);
        toast.toast('show');
    }
    
    
    
	// ẩn hiện form insert điểm
	$('#markForm').hide();
	$('.mark-style').change(function() {
		if ($(this).val() !== "") {
			$('#markForm').show();
		} else {
			$('#markForm').hide();
		}
	});
	$('.mark-style').change(function() {
		const selectedMarkType = $(this).val();
		const classId = $('input[name="classId"]').val();
		const submitButton = $('#btn-insert');
		$.ajax({
			url: '/admin/mark/checkStudentMarks',
			method: 'GET',
			data: {
				classId: classId,
				markType: selectedMarkType
			},
			success: function(response) {
				$('#markForm tbody tr').each(function() {
					const studentId = $(this).find('td:nth-child(2)').text().trim(); // Assuming second column is student ID
					const markInput = $(this).find('input[name="marks[]"]');
					const existingMark = response.marks[studentId];

					if (existingMark !== undefined) {
						markInput.val(existingMark);
						markInput.prop('readonly', true);
						submitButton.hide();
					} else {
						markInput.val('');
						markInput.prop('readonly', false);
						submitButton.show();
					}
				});
			},
			error: function() {
				alert('Error checking student marks.');
			}
		});
	});




	var classSelect = $('#classId');
	var markTable = $('#markTable tbody');
	var exportButton = $('#exportButton');
	var classForm = $('#classForm');
	$.get("/api/public/class/classes")
		.done(function(data) {
			classSelect.empty();
			data.forEach(function(classItem) {
				classSelect.append(new Option(classItem.name, classItem.id));
			});
			var storedClassId = localStorage.getItem('selectedClassId');
			if (storedClassId) {
				classSelect.val(storedClassId);
				fetchMarks(firstClassId);
			} else if (data.length > 0) {
				var firstClassId = data[0].id;
				classSelect.val(firstClassId);
			}

		})
		.fail(function() {
			alert("Failed to load classes.");
		});
	classSelect.change(function() {
		var classId = $(this).val();
		localStorage.setItem('selectedClassId', classId);
		//$(classForm).submit();
		//$('#classId').val(classId);
		//fetchMarks(firstClassId);
	});

	function fetchMarks(classId) {
		$.get("/web/mark/getMarkSubject?classId=" + classId, function(data) {
			console.log(data);
			$('#markTable').html(data);
			//$('#classId').val(classId);
		});
	}

	exportButton.click(function(e) {
		e.preventDefault();
		var selectedClassId = classSelect.val();
		window.location.href = "/web/mark/export?classId=" + selectedClassId;
	});

    
    
    
    
    
  
    
	function showToast(message, title, type) {
		var toastTemplate = $('#toastTemplate').clone();
		toastTemplate.attr('id', ''); // Clear the ID to avoid duplicates
		toastTemplate.find('.toast-title').text(title);
		toastTemplate.find('.toast-body').text(message);

		switch (type) {
			case 'success':
				toastTemplate.find('.toast-header').addClass('bg-success text-white');
				break;
			case 'error':
				toastTemplate.find('.toast-header').addClass('bg-danger text-white');
				break;
			case 'warning':
				toastTemplate.find('.toast-header').addClass('bg-warning text-dark');
				break;
			case 'info':
			default:
				toastTemplate.find('.toast-header').addClass('bg-info text-white');
				break;
		}

		$('#toastContainer').append(toastTemplate);
		toastTemplate.toast('show');

		toastTemplate.on('hidden.bs.toast', function() {
			$(this).remove();
		});
	}

	//set all style

	$(document).ready(function() {
		$('#markForm').submit(function(event) {
			event.preventDefault(); // Prevent the default form submission

			let allValid = true; // Biến để theo dõi tính hợp lệ của tất cả các trường điểm

			// Lặp qua tất cả các ô nhập điểm
			$('input[name="marks[]"]').each(function() {
				const mark = $(this).val().trim(); // Lấy giá trị và loại bỏ khoảng trắng
				const markValue = parseFloat(mark);

				if (mark === '' || isNaN(markValue) || markValue < 0 || markValue > 100) {
					// Nếu không có giá trị hoặc giá trị không hợp lệ, hiển thị thông báo lỗi
					$(this).siblings('.feedback').show();
					allValid = false;
				} else {
					// Nếu có giá trị hợp lệ, ẩn thông báo lỗi
					$(this).siblings('.feedback').hide();
				}
			});

			// Nếu tất cả các trường đều hợp lệ, tiến hành gửi form
			if (allValid) {
				const marks = $('input[name="marks[]"]').map(function() { return parseFloat(this.value); }).get();
				const style = $('.mark-style').val();
				const studentIds = $('input[name="studentIds"]').val().split(',').map(function(id) { return parseInt(id.trim(), 10); }); // Trim and parse each ID
				const classId = parseInt($('input[name="classId"]').val(), 10);
				const subjectId = parseInt($('input[name="subjectId"]').val(), 10);

				const markSubjectCreateDtoList = marks.map((mark, index) => {
					return {
						studentId: studentIds[index],
						classId: classId,
						subjectId: subjectId,
						mark: mark,
						style: style
					};
				});

				console.log("marks", marks, "styles", style, "studentIds", studentIds, "classId", classId, "subjectId", subjectId);

				$.ajax({
					url: $(this).attr('action'),
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(markSubjectCreateDtoList),
					success: function(response) {
						console.log('Success:', response);
						window.location.href = "/admin/mark/list";
					},
					error: function(xhr, status, error) {
						console.error('Error:', error);
					}
				});
			}
		});
	});
	//mark admin
	$('#select_semeser_mark_admin').change(function() {
		var selectedSemester = $(this).val();
		window.location.href = '/admin/mark/list?semesterId=' + selectedSemester;
	});
});