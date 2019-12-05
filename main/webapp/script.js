function onSignIn(googleUser,setSession) {
    var profile = googleUser.getBasicProfile();
//    profileInfo(profile.getName(),profile.getImageUrl(),profile.getEmail());
var proId = profile.getId();
    // console.log('ID: ' + profile.getId()); 
    console.log('Name: ' + proId);
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    setSession(proId);
    function setSession(id){
        axios.post('http://localhost:8080/app?id='+id, {})
        .then(res => {
        window.location.replace("http://localhost:8080/app.html");
    })
      }
    }

  

//function profileInfo(name,imgUrl,email){
//	document.getElementById('proName').innerText = name;
//	let str = ``;
//	document.getElementById('update').innerText = name;
//	document.getElementById('proEmail').innerText = email;
//}

  function signOut() {
    window.location.replace("http://localhost:8080/logout.html");
    var auth2 = gapi.auth2.getAuthInstance().disconnect();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

  function login(){
    window.location.replace("http://localhost:8080/index.html");
  }