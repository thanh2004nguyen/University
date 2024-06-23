

  function setInt() {
	  var int;
    clearInterval(int);
    int = setInterval(function() {
      var btns = document.getElementsByName("carousel");
      for(var i = 0; i < btns.length; i++) {
        if(btns[i].checked) {
          btns[i].checked = false;
          if(i + 1 == btns.length) {
            btns[0].checked = true;
          }
          else {
            btns[i + 1].checked = true;
          }
          return;
        }
      }
    }, 5000); 
  }
  setInt();
function checkCourseBoxWidth() {
            var courseBoxWidth = document.querySelector('.course-box').offsetWidth;
            alert("Width of .course-box:", courseBoxWidth);
        }

document.getElementById('togglePassword').addEventListener('click', function() {
    var passwordInput = document.getElementById('password');
    var icon = this;
	
    // Toggle the type attribute
     if (passwordInput.type === "password") {
        passwordInput.type = "text";
        icon.classList.remove('uil-eye-slash');
        icon.classList.add('uil-eye');
    } else {
        passwordInput.type = "password";
        icon.classList.remove('uil-eye');
        icon.classList.add('uil-eye-slash');
    }
});