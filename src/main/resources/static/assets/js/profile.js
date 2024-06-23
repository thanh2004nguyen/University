
	
	
	
	$("#btn_update_status_student").on("click",()=>{
		
		let checkbox=document.querySelectorAll('.checkbox_status');
		let str="";
		checkbox.forEach(e=>{
		if(e.checked){
			let value=e.value;
	
			str+=`<input name="student[]" readonly value="${value}" type="hidden" >`
		}	
		})
		
		$("#wrap_input_appen").html(str);
	});
	
	
$(".submit_update_statusList").on("click", ()=>{
	$("#updateStatusForm").submit();
})

//update student status
$("#status_update_student").on("change", function(){
	$("#form_search_update_status").submit();
})


function toggleOverlay() {
	var editOverlay = document.getElementById('edit-overlay');
	var mainOverlay = document.getElementById('profile-form');
	editOverlay.style.display = 'block';
	mainOverlay.style.display = 'none';
}


fetch('/api/class/greeting')
	.then(response => response.text())
	.then(greeting => {
		// Hiển thị lời chào trong phần tử có id là "welcome"
		document.getElementById("welcome").innerText = greeting + " ";
	})
	.catch(error => console.error('Error fetching greeting:', error));




function closeOpenDropdowns(e) {
	let openDropdownEls = document.querySelectorAll("details.dropdown[open]");

	if (openDropdownEls.length > 0) {
		if (e.target.parentElement.nodeName.toUpperCase() !== "SUMMARY") {
			openDropdownEls.forEach((dropdown) => {
				dropdown.removeAttribute("open");
			});
		}
	}
}

document.addEventListener("click", closeOpenDropdowns);



document.addEventListener("DOMContentLoaded", function() {
	var avatarHidden = document.querySelector(".avatar_hiddent");
	if (avatarHidden && avatarHidden.textContent) {
		var avatar = avatarHidden.textContent;
		var imageUrl = "http://localhost:8081/getimage/" + avatar;
		var avatarImage = document.querySelector(".avatar img");
		if (avatarImage) {
			avatarImage.src = imageUrl;
		}
	}

	//profile
	const profileLink = document.getElementById('profile-link');
	const overlay = document.getElementById('overlay');
	const profileForm = document.getElementById('profile-form');

	profileLink.addEventListener('click', function() {
		overlay.style.display = 'block';
		profileForm.style.display = 'block';
	});
	overlay.addEventListener('click', function(event) {
		if (!profileForm.contains(event.target)) {
			overlay.style.display = 'none';
			profileForm.style.display = 'none';
		}
	});



	//change value img
	profileLink.addEventListener('click', function() {
		overlay.style.display = 'block';
		profileForm.style.display = 'block';
	});

	overlay.addEventListener('click', function(event) {
		if (!profileForm.contains(event.target)) {
			overlay.style.display = 'none';
			profileForm.style.display = 'none';
		}
	});

	//img information 
	const avatarImg = document.querySelector('.avatar img');
	const largeAvatarImg = document.getElementById('avatar-large');
	const smallAvatarImg = document.getElementById('avatar-small');
	largeAvatarImg.src = avatarImg.src;
	smallAvatarImg.src = avatarImg.src;
});


//hiển thị avatar trên header khi update profile thành công.
// Assuming you're using jQuery for simplicity, you can adapt this to vanilla JavaScript if needed
$(document).ready(function() {
	// Check if there's updated profile information passed in the URL
	var newAvatar = getUrlParameter('newAvatar');
	var newName = getUrlParameter('newName');
	var newEmail = getUrlParameter('newEmail');
	var newPhone = getUrlParameter('newPhone');
	var newAddress = getUrlParameter('newAddress');
	var newInfo = getUrlParameter('newInfo');
	// Add other profile information as needed

	if (newAvatar && newName && newEmail) {
		// Update avatar image source in the header and overlay
		$('.avatar img').attr('src', 'http://localhost:8081/getimage/' + newAvatar);
		$('#avatar-large').attr('src', 'http://localhost:8081/getimage/' + newAvatar);
		$('#avatar-small').attr('src', 'http://localhost:8081/getimage/' + newAvatar);

		// Update profile information in the overlay
		$('#welcome').text('Welcome, ' + newName + '!');
		$('.avatar_hiddent').text(newAvatar);
		$('.block.italic').text(newEmail);
		$('.namejs').text(newName);
		$('.phonejs').text(newPhone);
		$('.addressjs').text(newAddress);
		$('.infojs').text(newInfo);
		// Update other profile information in the overlay as needed
	}
});

// Function to get URL parameter by name
function getUrlParameter(name) {
	name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
	var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
	var results = regex.exec(location.search);
	return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};
